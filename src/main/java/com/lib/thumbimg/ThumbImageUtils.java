package com.lib.thumbimg;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.lib.util.MathUtils;
import com.lib.util.StringUtils;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 
 * @author libing
 * @de
 *
 */
public class ThumbImageUtils {
	private static int fileCount = 0;
	private static int currFileCount = 0;
	private static long length = 0;
	private static int floderCount = 0;
	
	public static void main(String[] args) {
		String directoryName = StringUtils.isNotNull(args)?args[0]:"";
		thumbImage(directoryName);
	}
	
	public static void thumbImage(String directoryName){
		File file = new File(StringUtils.isNotEmpty(directoryName)?directoryName:"C://Users//Administrator//Desktop//news");
		fileCount = 0 ;
		length = 0;
		floderCount = 0;
        //1.获取文件或文件夹信息
		CountDir(file);
		System.out.println("文件夹数："+floderCount+"文件个数："+fileCount+"文件大小："+length);
		 //2.TimerTask实现1分钟执行一次
        final Timer timer=new Timer();
        NumberFormat numberFormat = NumberFormat.getInstance();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	
            	// 设置精确到小数点后2位
            	Double percent = MathUtils.divide(currFileCount+"", fileCount+"").doubleValue();
            	percent = percent*100;
            	//完成后
            	if(currFileCount == fileCount ){
            		timer.cancel();
            	}else{
            		System.out.println("已完成"+percent+"%——"+currFileCount+"个文件");
            	}
            }
        },2*1000,1*60*1000); 
		//3.压缩
		checkDir(file);
		//4.完成后
    	Double percent = MathUtils.divide(currFileCount+"", fileCount+"").doubleValue();
    	percent = percent*100;
    	if(currFileCount == fileCount ){
    		timer.cancel();
    		System.out.println("已完成"+percent+"%——"+currFileCount+"个文件");
    	}
	}
	
	
	//1.递归调用检查文件
	public static void CountDir(File file){
		File[] files = file.listFiles();
        for (File f : files) {
        	if(f.isFile()){
        		fileCount++;
        		length +=  f.length();
        	}else{
        		floderCount++;
        		CountDir(f);
        	}
        }
	}
	public static void checkDir(File file){
		File[] files = file.listFiles();
		 
        for (File f : files) {
        	if(f.isDirectory()){
        		checkDir(f);
        	}else{
        		currFileCount++;
        		zipImg(f);
        	}
        }
	}
	
	//2.压缩图片
	public static  void zipImg(File f){
		
		try {
            //读取图片转换为流
            FileInputStream fis = new FileInputStream(f);
          	//（ps:此工具方法贴在了下方）
            Thumbnails.of(f.getPath()).scale(1f).outputQuality(0.25f).toFile(f.getPath());
			//设置图片宽度不超过1000
			int maxWidth = 1000;
			double width = 0;
			double height = 0;
            BufferedImage bufferedImage = ImageIO.read(fis); //获取图片流
            if (bufferedImage != null) {
                // 证明上传的文件不是图片，获取图片流失败，不进行下面的操作
            	width = bufferedImage.getWidth(); // 通过图片流获取图片宽度
                height = bufferedImage.getHeight(); // 通过图片流获取图片高度
                if(width > maxWidth){
                	height = height/(width/1000);
                	Thumbnails.of(f.getPath()).size(1000, new Double(height).intValue()).toFile(f.getPath());
                }
            }
            System.err.println("成功");
        } catch ( Exception e) {
            System.err.println("错误");
        }
	}
}
