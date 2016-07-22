package com.haikuo.utiltools;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberTool {

	private static NumberTool numberTool = new NumberTool();

	/**
	 * 格式化价格保留两位小数
	 * 
	 * @param price
	 * @return
	 */
	public static String fromPrice(double price) {
		DecimalFormat df = new DecimalFormat("##0.00");
		return df.format(price);
	}

	/**
	 * 数字格式化
	 * 
	 * @param num
	 *            待格式化的数字
	 * @param numAfterDot
	 *            需要保留的小数点位数
	 * @return
	 */
	public static String formatNumber(double num, int numAfterDot) {
		String format = "##0";
		if (numAfterDot < 0) {
			numAfterDot = 0;
		}

		for (int i = 0; i < numAfterDot; i++) {
			if (i == 0) {
				format += ".0";
			} else {
				format += "0";
			}
		}

		DecimalFormat df = new DecimalFormat(format);
		return df.format(num);
	}

	public static NumberTool getInstance() {
		return numberTool;
	}

	public int paresInt(String val) {
		if (val == null || val.equals("")) {
			return 0;
		}

		try {
			return Integer.parseInt(val.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	public long paresLong(String val) {
		if (val == null || val.equals("")) {
			return 0;
		}

		try {
			return Long.parseLong(val.trim());
		} catch (Exception e) {
			return 0l;
		}
	}

	/**
	 * 对double数据进行舍入操作
	 * 
	 * @param value
	 *            原始值
	 * @param count
	 *            保留的小数点位数
	 * **/
	public double praseDouble(double value, int count) {
		BigDecimal b = new BigDecimal(value);
		return b.setScale(count, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 判断整数是否为偶数
	 * @param value
	 * @return true:偶数 false：奇数
	 * **/
	public boolean isEven(int value){
		return (value & 1) == 0;
	}
	
	/**
	 * 判断一个数是否为奇数
	 * @param value
	 * @return true:奇数 false:偶数
	 * */
	public boolean isOdd(int value){
		return (value & 1) == 1;
	}
	
	
	public boolean AndOperator(int value1, int value2){
		return (value1 & value2)>0;
	}
	
	/**
	 * 千位分隔的格式，格式化数字
	 * 例如： 21234  格式化后 21,234
	 * **/
	public String thousandSeparate(int num){
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}
	
	public String thousandSeparate(double num){
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}
	
	/**
	 *  计算目标数在来源数的百分比，默认保留2位小数
	 * @param src
	 * @param des
	 * @param n    保留小数位
	 * @return
	 */
	
	public String percentage(double src,double des,int n){
		if(src==0){
			return 0+"";
		}
		DecimalFormat df = new DecimalFormat();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(n);
		return nf.format(des/src*100)+"%";
		
		
	}
	public String percentage(double src,double des){
		return percentage(src,des,2);
	}
	
	/**
	 * 求与
	 */
	public int parseAnd(int value1,int value2){
		return value1&value2;
	}
	
	/**
	 * 求与
	 */
	public long parseAnd(long value1,long value2){
		return value1&value2;
	}
	
	/**
	 * 格式化两个整数相除 结果乘100 后保留2位小数 
	 * @param child
	 * @param parent
	 * @return
	 */
	public String fomatDiv(int child ,int parent){
		if(parent==0){
			return "0.00";
		}
		
		double res = child/(parent*1.0)*100;
		return formatNumber(res, 2);
	}
	public static String getRate(double oldPrice,double newPrice){
		if(newPrice == 0){
			return "暂无";
		}
		double rate = 1-(oldPrice/newPrice);
		return formatNumber(rate, 3);
	}
	/**
	 * 获取double的百位+1，十位，个位忽略
	 * @param price
	 * @return
	 */
	public double getUpperPrice(double price){
		double result = 0.0;
		if(price % 100 >0){
			double temp = Math.floor(price/100);
			if(temp % 10 == 3){
				temp += 2;
			}else{
				temp += 1;
			}
			result = temp*100;
		}else{
			result = price;
		}
		return result;
	}
}
