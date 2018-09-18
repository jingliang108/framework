package com.tanjin.framework.base.wechat.utils;

public class WechatMessageData {
	


	
	public static String getPosterContent(String url){
		String joinLink = "<a href= \""+url+"/course\">"+"戳此立即报名"+"</a>";
		StringBuffer content = new StringBuffer();		
		content.append("\ue231三米田儿童创意美术体验课，开始报名啦 ！ \n");
		content.append(" \n");
		content.append(joinLink+" \n");
		content.append(" \n");
		content.append("\ue22f将下方活动海报分享或转发朋友圈，并成功邀请小伙伴关注三米田公众号，即可获得5—20元随机现金抵用券； \n");
		content.append(" \n");
		content.append("如果您的小伙伴成功关注三米田公众号后，又报名并参加了我们免费的儿童创意美术体验课，您可再次获得100元现金抵用券，每增加一位，您的现金抵用券将增加100元。数量有限，先到先得[机智][机智][机智]   \n");
		return content.toString();
	}
	
	
	public static String getInviteContent(String url){
		StringBuffer content = new StringBuffer();		
		String joinLink = "<a href= \""+url+"/course\">"+"戳此立即报名"+"</a>";
		content.append("\ue231三米田儿童创意美术体验课，开始报名啦 ！ \n");
		content.append(" \n");
		content.append(joinLink+" \n");
		content.append(" \n");
		content.append("\ue22f将下方活动海报分享或转发朋友圈，并成功邀请小伙伴关注三米田公众号，即可获得5—20元随机现金抵用券； \n");
		content.append(" \n");
		content.append("如果您的小伙伴成功关注三米田公众号后，又报名并参加了我们免费的儿童创意美术体验课，您可再次获得100元现金抵用券，每增加一位，您的现金抵用券将增加100元。数量有限，先到先得[机智][机智][机智]   \n");
		return content.toString();
	}
	
	public static String getWecomeContent(){
		StringBuffer content = new StringBuffer();		
		content.append("欢迎关注三米田儿童创意美术！ \n");
		return content.toString();
	}
	
	
	public static String getCouponContent(int amount,String url){
		StringBuffer content = new StringBuffer();		
		String joinLink = "<a href= \""+url+"/user/coupon\">"+"点击查看"+"</a>";
		content.append("恭喜！获得"+amount+"元抵用券      "+joinLink+"\n");
		return content.toString();
	}
	
	
	public static String getTeacherContent(){
		StringBuffer content = new StringBuffer();		
		content.append("恭喜，您已成为三米田代言人！ \n");
		content.append("\n");
		content.append("进入“个人中心-代言人”查看详情");
		return content.toString();
	}
	
	public static String getScanTeacherContent(){
		StringBuffer content = new StringBuffer();		
		content.append("\ue22f将下方活动海报分享或转发朋友圈 \n");
		content.append(" \n");
		content.append("如果您成功推荐一名学员，报名付费三米田儿童创意美术课后，您可获得100元教育鼓励基金。 \n");
		return content.toString();
	}
	
}
