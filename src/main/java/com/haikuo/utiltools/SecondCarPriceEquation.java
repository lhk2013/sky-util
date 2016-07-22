package com.haikuo.utiltools;

/**
 * @author LiPenghui
 * 二手车估价方程
 * */
public class SecondCarPriceEquation {
	
	
	/**
	 * @param newPrice  新车价格 （单位：万元）
	 * @param driverMonth 行驶月数 （单位：月）
	 * @param distance 行驶里程 （单位：公里）
	 * */
	public static double getCarPriceGaussian(double newPrice,double driverMonth,double distance){		       
		double ePrice =  (getGaussianDepreciationRate(driverMonth) + getDistanceDepreciationRate(driverMonth,distance)) * newPrice;	
		return ePrice > 0 ? ePrice : 0;
	}
	
	/**
	 * General model Gauss1:
        f(x) =  a1*exp(-((x-b1)/c1)^2)
	  Coefficients (with 95% confidence bounds):
       a1 =      0.9768  (0.9545, 0.999)
       b1 =      -55.15  (-60.64, -49.67)
       c1 =       167.8  (164.2, 171.5)
	 * */
	public static double getGaussianDepreciationRate(double driverMonth){
		double a = 0.9768;
		double b = -55.15;
		double c = 167.8;
		return a * Math.exp(-Math.pow((driverMonth-b)/c, 2));
	}
	
	public static double getDistanceDepreciationRate(double driverMonth,double distance){
		return getDistanceDepreciationRate(driverMonth,distance,1200);
	}
	
	public static double getDistanceDepreciationRate(double driverMonth,double distance,double milePerMonth){
		return (driverMonth * milePerMonth - distance)/10000 * (distance > 50000?0.012:0.003);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double eprice = SecondCarPriceEquation.getCarPriceGaussian(30, 12, 10000);
		System.out.println(eprice);
	}
	
	
}
