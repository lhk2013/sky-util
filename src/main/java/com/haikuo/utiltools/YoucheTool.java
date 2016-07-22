package com.haikuo.utiltools;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class YoucheTool {

	private static final YoucheTool youcheTool = new YoucheTool();
	private static final DateTool dateTool = DateTool.getInstance();
	private static final PageTool pageTool = PageTool.getInstance();
	private static final NumberTool numberTool = NumberTool.getInstance();
	private static final MoneyTool moneyTool = MoneyTool.getInstance();
	
	
	public static YoucheTool getInstance() {
		return youcheTool;
	}


	public DateTool getDateTool() {
		return dateTool;
	}


	public PageTool getPageTool() {
		return pageTool;
	}

	public NumberTool getNumberTool() {
		return numberTool;
	}

	
	public MoneyTool getMoneyTool(){
		return moneyTool;
	}
	
	
	/**
	 * 返回两位小数的万公里
	 * 
	 * @param meal
	 *            公里
	 * @return
	 */
	public String formatMeal(int meal) {
		NumberFormat format = new DecimalFormat("0.00");
		return format.format(meal / 10000.0);
	}

	/**
	 * 格式化日期 返回年月
	 */
	public String formatDt(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		return sdf.format(dt);
	}

	/**
	 * 获得图片json中的第一张图片地址
	 */
	public String[] getImg(String jsonImg) {
		if (jsonImg == null || "".equals(jsonImg)) {
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(jsonImg);
		String[] imgs = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(0);
			imgs[i] = json.getString("v");
		}
		return imgs;
	}

	/**
	 * 格式化日期为字符串
	 * 
	 * @param date
	 *            目标日期
	 * @param partten
	 *            模式
	 * @return
	 */
	public static String formatDate(Date date, String... partten) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = null;
		if (partten != null && partten.length > 0) {
			sdf = new SimpleDateFormat(partten[0]);
		} else {
			sdf = new SimpleDateFormat("yyyy年MM月");
		}
		return sdf.format(date);
	}
}
