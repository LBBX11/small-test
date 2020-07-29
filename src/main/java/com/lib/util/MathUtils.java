package com.lib.util;

import java.math.BigDecimal;

public class MathUtils {
	public static Float parseFloat(Object object, Float defaultValue) {
		if (object != null) {
			try {
				return Float.parseFloat(object.toString());
			} catch (Exception e) {
				//
			}
		}
		return defaultValue;

	}

	public static Double parseDouble(Object object, Double defaultValue) {
		if (object != null) {
			try {
				return Double.parseDouble(object.toString());
			} catch (Exception e) {
				//
			}
		}
		return defaultValue;

	}
	public static Long parseLong(Object object, Long defaultValue) {
		if (object != null) {
			try {
				return Long.parseLong(object.toString());
			} catch (Exception e) {
				//
			}
		}
		return defaultValue;

	}
	public static Integer parseInt(Object object, Integer defaultValue) {
		if (object != null) {
			try {
				return Integer.parseInt(object.toString());
			} catch (Exception e) {
				//
			}
		}
		return defaultValue;

	}

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	
	public static int getSplitCount(int listSize){
		int splitCount = 2000;
		if(listSize >= 200000){
			splitCount = listSize/100;
		}
		return splitCount;
	}
	
	public boolean equals(Integer a,Integer b) {
		if(a==null && b==null) {
			return true;
		}
		if(a==null || b==null) {
			return false;
		}
		if(a!=null && b!=null) {
			return a==b;
		}
		return false;
	}
	
	public static BigDecimal divide(String arg1, String arg2) {
		if (StringUtils.isEmpty(arg1)) {
			arg1 = "0.0";
		}
		if (StringUtils.isEmpty(arg2)) {
			arg2 = "0.0";
		}
		BigDecimal big3 = new BigDecimal("0.00");
		if (Double.parseDouble(arg2) != 0) {
			BigDecimal big1 = new BigDecimal(arg1);
			BigDecimal big2 = new BigDecimal(arg2);
			return big3 = big1.divide(big2, 2, BigDecimal.ROUND_HALF_EVEN);
		}
		return big3.setScale(1, BigDecimal.ROUND_HALF_UP);
	}
	
	//返回string  除法
	public static String  dividetoStr(String arg1, String arg2) {
		BigDecimal big3 = new BigDecimal("0.00");
		big3=divide(arg1,arg2);
		return big3.toString();
	}

	/**
	 * 乘法
	 */
	public static BigDecimal mul(String arg1, String arg2) {
		if (StringUtils.isEmpty(arg1)) {
			arg1 = "0.0";
		}
		if (StringUtils.isEmpty(arg2)) {
			arg2 = "0.0";
		}
		BigDecimal big1 = new BigDecimal(arg1);
		BigDecimal big2 = new BigDecimal(arg2);
		BigDecimal big3 = big1.multiply(big2);
		return big3.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 减法
	 */
	public static BigDecimal sub(String arg1, String arg2) {
		if (StringUtils.isEmpty(arg1)) {
			arg1 = "0.0";
		}
		if (StringUtils.isEmpty(arg2)) {
			arg2 = "0.0";
		}
		BigDecimal big1 = new BigDecimal(arg1);
		BigDecimal big2 = new BigDecimal(arg2);
		BigDecimal big3 = big1.subtract(big2);
		return big3.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 加法
	 */
	public static BigDecimal add(String arg1, String arg2) {
		if (StringUtils.isEmpty(arg1)) {
			arg1 = "0.0";
		}
		if (StringUtils.isEmpty(arg2)) {
			arg2 = "0.0";
		}
		BigDecimal big1 = new BigDecimal(arg1);
		BigDecimal big2 = new BigDecimal(arg2);
		BigDecimal big3 = big1.add(big2);
		return big3.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 加法
	 */
	public static String add2(String arg1, String arg2) {
		if (StringUtils.isEmpty(arg1)) {
			arg1 = "0.0";
		}
		if (StringUtils.isEmpty(arg2)) {
			arg2 = "0.0";
		}
		BigDecimal big1 = new BigDecimal(arg1);
		BigDecimal big2 = new BigDecimal(arg2);
		BigDecimal big3 = big1.add(big2);
		return big3.toString();
	}

	/**
	 * 四舍五入保留N位小数 先四舍五入在使用double值自动去零
	 * 
	 * @param arg
	 * @param scare
	 *            保留位数
	 * @return
	 */
	public static String setScare(BigDecimal arg, int scare) {
		BigDecimal bl = arg.setScale(scare, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(bl.doubleValue());
	}

	public static double setDifScare(double arg) {
		BigDecimal bd = new BigDecimal(arg);
		BigDecimal bl = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return Double.parseDouble(bl.toString());
	}

	/**
	 * 四舍五入保留两位小数 先四舍五入在使用double值自动去零
	 * 
	 * @param arg
	 * @return
	 */
	public static String setDifScare(String arg) {
		BigDecimal bd = new BigDecimal(arg);
		BigDecimal bl = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bl.toString();
	}

	/**
	 * 四舍五入保留N位小数 先四舍五入在使用double值自动去零（传参String类型）
	 * 
	 * @param arg
	 * @return
	 */
	public static String setDifScare(String arg, int i) {
		BigDecimal bd = new BigDecimal(arg);
		BigDecimal bl = bd.setScale(i, BigDecimal.ROUND_HALF_UP);
		return bl.toString();
	}

	/**
	 * 转化成百分数 先四舍五入在使用double值自动去零
	 * 
	 * @param arg
	 * @return
	 */
	public static String setFenScare(BigDecimal arg) {
		BigDecimal bl = arg.setScale(3, BigDecimal.ROUND_HALF_UP);
		String scare = String.valueOf(mul(bl.toString(), "100").doubleValue());
		String fenScare = scare + "%";
		return fenScare;
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
	
	public static double fenToyuan(Integer fen) {
		if(fen==null) {
			return 0.0;
		}
		return MathUtils.divide(String.valueOf(fen), String.valueOf(100)).doubleValue();
	}
	public static Integer yuanToFen(Double yuan) {
		if(yuan==null) {
			return 0;
		}
		return MathUtils.mul(String.valueOf(yuan), String.valueOf(100)).intValue();
	}
	
	public static void main(String[] args) {
		System.out.println(divide("123", "10"));
		
	}
}
