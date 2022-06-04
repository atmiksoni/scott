package com.flyme.thirdsdk.jpush;

import com.flyme.base.system.msgrecive.pojo.MsgRecive;
import com.flyme.base.system.push.pojo.Pushs;
import com.flyme.base.vip.identity.pojo.Identity;
import com.flyme.business.workorder.pojo.Workorder;
import com.flyme.common.constants.Global;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.thirdsdk.jpush.util.JpushPush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpushUtil {

	private static final String title = "货斯基";
	
	/**
	 * 预约推送
	 */
	public static void appointmentPush(CriterionMap m) {
		Map<String, String> map = new HashMap<>();
		map.put("type", Global.PUSH_APPOINTMENT);
		map.put("businessId", m.get("appointmentId"));
		map.put("status", m.get("status"));
		List<String> alias=new ArrayList<>();
		alias.add(m.get("accountInfoId"));
		JpushPush jpushPush = new JpushPush(title, m.get("msg"), "", null, null, alias, map);
		jpushPush.pushByAlias();
	}
	
	/**
	 * 订单推送
	 */
	public static void orderBusPush(CriterionMap m) {
		Map<String, String> map = new HashMap<>();
		map.put("type", Global.PUSH_ORDERBUS);
		map.put("businessId", m.get("orderBusId"));
		List<String> alias=new ArrayList<>();
		alias.add(m.get("accountInfoId"));
		JpushPush jpushPush = new JpushPush(title, m.get("msg"), "", null, null, alias, map);
		jpushPush.pushByAlias();
	}
	
	/**
	 * 服务检查
	 */
	public static void sendOrdersPush(CriterionMap m) {
		Map<String, String> map = new HashMap<>();
		map.put("type", Global.PUSH_ORDERBUS);
		map.put("checkOrderId", m.get("checkOrderId"));
		List<String> alias=new ArrayList<>();
		alias.add(m.get("engineerId"));
		JpushPush jpushPush = new JpushPush(title, m.get("msg"), "", null, null, alias, map);
		jpushPush.pushByAlias();
	}
	
	/**
	 * 服务检查英文
	 */
	public static void sendOrdersPushEnglish(CriterionMap m1) {
		Map<String, String> map = new HashMap<>();
		map.put("type", Global.PUSH_ORDERBUS);
		map.put("checkOrderId", m1.get("checkOrderId"));
		List<String> alias=new ArrayList<>();
		alias.add(m1.get("engineerId"));
		JpushPush jpushPush = new JpushPush(title, m1.get("msg"), "", null, null, alias, map);
		jpushPush.pushByAlias1();
	}
	
	/**
	 * 工作工单英文
	 */
	public static void inspectionOrdersPushEnglish(CriterionMap m1) {
		Map<String, String> map = new HashMap<>();
		map.put("type", Global.PUSH_ORDERBUS);
		map.put("workorderId", m1.get("workorderId"));
		List<String> alias=new ArrayList<>();
		alias.add(m1.get("engineerId"));
		JpushPush jpushPush = new JpushPush(title, m1.get("msg"), "", null, null, alias, map);
		jpushPush.pushByAlias3();
	}
	
	/**
	 * 工作工单
	 */
	public static void inspectionOrdersPush(CriterionMap m) {
		Map<String, String> map = new HashMap<>();
		map.put("type", Global.PUSH_ORDERBUS);
		map.put("workorderId", m.get("workorderId"));
		List<String> alias=new ArrayList<>();
		alias.add(m.get("engineerId"));
		JpushPush jpushPush = new JpushPush(title, m.get("msg"), "", null, null, alias, map);
		jpushPush.pushByAlias2();
	}
	
	
	/**
	 * 系统推送
	 */
	public static void sysPush(Pushs push) {
		Map<String, String> map = new HashMap<>();
		map.put("type", Global.PUSH_SYS);
		map.put("businessId", push.getPushId());
		JpushPush jpushPush = new JpushPush(title, push.getPushTitle(), "", null, null, null, map);
		jpushPush.pushAllAlert();
	}

	/*
	 * 实名认证审批推送
	 */
	public static void identityPush(Identity identity, MsgRecive msgRecive, String accountType) {
		String alert = (identity.getIsPass() == 1 ? "恭喜您已经通过实名认证" : "很遗憾您未通过实名认证，请重新认证！");
		Map<String, String> map = new HashMap<>();
		if (identity.getIsPass() == 1) {
			map.put("type", Global.PUSH_IDENTITY_PASS);
		} else {
			map.put("type", Global.PUSH_IDENTITY_FAIL);
			map.put("reason", identity.getReason());
		}
		map.put("businessId", msgRecive.getMsgReciveId().toString());
		List<String> alias = new ArrayList<>();
		alias.add(identity.getAccountInfoId());
		JpushPush jpushPush = new JpushPush(title, alert, "", null, null, alias, map);
		jpushPush.pushByAlias();
	}
	

	public static void main(String[] args) {
		/*Identity identity = new Identity();
		identity.setIsPass(-1);
		identity.setReason("失败啦");*/
		Workorder workorder = new Workorder();
		
		CriterionMap map = new CriterionMap();
		map.put("workOrderId", "8185F6B524F845D8A20C3DAA1793C27E");
		map.put("engineerId", "C137045A5E264A4597FB90F8109ADA43");
		map.put("engineerName", "ikjjjh");
		/*map.put("freightId", "40032C0B4ED948A29FC1D58064C50F51");
		map.put("inviteId", "111111111111111111111");
		map.put("driverInfoId", "40032C0B4ED948A29FC1D58064C50F51");
		map.put("userName", "昌哥111111s");
		map.put("beginCityName", "南京");
		map.put("endCityName", "郑州");*/
	}
}
