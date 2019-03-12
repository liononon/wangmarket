package com.xnx3.wangmarket.admin.controller;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.j2ee.util.TerminalDetection;
import com.xnx3.wangmarket.admin.Func;
import com.xnx3.wangmarket.admin.bean.UserBean;
import com.xnx3.wangmarket.admin.entity.Site;
import com.xnx3.wangmarket.superadmin.entity.Agency;
import com.xnx3.wangmarket.superadmin.entity.AgencyData;

public class BaseController extends com.xnx3.j2ee.controller.BaseController {
	
	/**
	 * 获取当前用户的代理相关信息、以及当前用户的上级的代理相关信息
	 * @return {@link AgencyBean} 或 null
	 */
	public UserBean getUserBean(){
		return Func.getUserBeanForShiroSession();
	}
	
	/**
	 * 获取get、post传递的参数
	 * @param request
	 * @param name 参数名
	 * @return 值，若没有，则返回空字符串 ""
	 */
	public String getRequestParam(HttpServletRequest request, String name){
		if(name == null){
			return "";
		}
		if(request.getParameter(name) == null){
			return "";
		}
		return request.getParameter(name);
	}

	/**
	 * 获取当前登陆用户的上级用户代理信息的变长表 (agency_data) 数据。如果当前用户的上级有，且是代理的话
	 * @return {@link Agency} 或 null
	 */
	public Agency getParentAgency(){
		return com.xnx3.wangmarket.superadmin.Func.getParentAgency();
	}
	
	/**
	 * 获取当前登陆用户的上级用户代理信息。如果当前用户的上级有，且是代理的话
	 * @return {@link Agency} 或 null
	 */
	public AgencyData getParentAgencyData(){
		return com.xnx3.wangmarket.superadmin.Func.getParentAgencyData();
	}
	
	/**
	 * 获取当前登陆用户的站点信息。
	 * @return 若是当前用户没有所属的站点，返回null
	 */
	public Site getSite(){
		UserBean userBean = getUserBean();
		if(userBean == null || userBean.getSite() == null){
			return null;
		}else{
			return userBean.getSite();
		}
	}
	
	/**
	 * 设置 site 信息。当 site 数据表有更改后，可以执行此来刷新session存储的site信息
	 */
	public void setSite(Site site){
		UserBean userBean = getUserBean();
		if(userBean == null || userBean.getSite() == null){
			return;
		}else{
			userBean.setSite(site);;
		}
		
	}
	
	/**
	 * 获取当前登陆用户的站点id
	 * @return 若当前用户没有站点，返回0
	 */
	public int getSiteId(){
		UserBean userBean = getUserBean();
		if(userBean == null){
			return 0;
		}
		if(userBean.getSite() == null){
			return 0;
		}else{
			return userBean.getSite().getId();
		}
	}
	
	/**
	 * 获取传递的client参数，来判断当前是手机还是电脑显示
	 * @return 如 wap_
	 */
	public String getPcOrWapPrefix(HttpServletRequest request){
		String client = request.getParameter("client");
		if(client == null || client.length() == 0){
			return getPcOrWapPrefix_Auto(request);
		}else{
			if(client.equals("wap")){
				return "wap_";
			}else{
				return "pc_";
			}
		}
	}
	
	/**
	 * 自动根据浏览器检测是否是手机访问
	 * @param request
	 * @return pc_ 或者 wap_
	 */
	public String getPcOrWapPrefix_Auto(HttpServletRequest request){
		if(isMobile(request)){
			return "wap_";
		}else{
			return "pc_";
		}
	}
	
	
	/**
	 * 判断是否是手机端访问，若是，返回true，PC访问返回false
	 */
	public boolean isMobile(HttpServletRequest request){
		return TerminalDetection.checkMobileOrPc(request);
	}
}
