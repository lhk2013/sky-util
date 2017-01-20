package com.haikuo.utils;

public class PageTool {	
	
	private static final PageTool tool = new PageTool();
	
	public static PageTool getInstance() {
		return tool;
	}
	
	private PageTool() {
		
	}
	
	public String page(String path, int currentPage, int pageSize, int totalCount) {
		return page(path, currentPage, pageSize, totalCount, null);
	}
	
	/**
	 * 分页按钮
	 * @param currentPage
	 * @param pageSize
	 * @param totalCount
	 * @param pcate
	 * @param scate
	 * @param sort
	 * @param query
	 * @return
	 */
	public String page(String path, int currentPage, int pageSize, int totalCount, String params) {
		if(params == null) {
			params = "";
		} else if(params.length()>0 && !params.startsWith("\\?")) {
			params = "?" + params;
		}
		
		StringBuilder html = new StringBuilder();
		if(currentPage > 1) {
			html.append("<a href=\"").append(path).append(currentPage-1).append(params);
			html.append("\" class=\"pre\">上一页</a>");
		}
		
		if(currentPage < 9) {
			for(int i=1; i<=12; i++) {
				html.append("<a href=\"").append(path).append(i).append(params);
				html.append("\"");
				if(i == currentPage) {
					html.append(" class=\"cur\"");
				}
				html.append(">").append(i).append("</a>");
				
				if(i*pageSize >= totalCount) {
					break;
				}
			}
		} else {
			for(int i=1; i<=3; i++) {
				html.append("<a href=\"").append(path).append(i).append(params);
				html.append("\">").append(i).append("</a>");
			}
			
			html.append(" ... ");
			
			for(int i=3; i>=0; i--) {
				html.append("<a href=\"").append(path).append(currentPage - i).append(params);
				html.append("\"");
				if(i == 0) {
					html.append(" class=\"cur\"");
				}
				html.append(">").append(currentPage - i).append("</a>");
			}
			
			for(int i=1; i<=3; i++) {
				if((i-1+currentPage)*pageSize >= totalCount) {
					break;
				}
				html.append("<a href=\"").append(path).append(currentPage + i).append(params);
				html.append("\">").append(currentPage + i).append("</a>");
			}
		}

		if(totalCount > currentPage*pageSize) {
			html.append("<a href=\"").append(path).append(currentPage + 1).append(params);
			html.append("\" class=\"next\">下一页</a>");
		}
		
		return html.toString();
	}
}
