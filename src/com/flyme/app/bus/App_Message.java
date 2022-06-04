package com.flyme.app.bus;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.msgrecive.pojo.MsgRecive;
import com.flyme.base.system.msgrecive.service.IMsgReciveService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.html.HTMLSpirit;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.mybatis.filters.Sort;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.core.springmvc.designpattern.proxy.ISubject;
import com.flyme.core.springmvc.designpattern.proxy.Proxy;

/**
 * 消息模块接口(个人消息,系统消息)
 */
@Controller
@RequestMapping("/api")
public class App_Message extends MbsBaseController implements ISubject {

	@Autowired
	private IMsgReciveService msgReciveService;

	/**
	 * 查询个人消息
	 * 
	 * @param readStatus(0:未读,1:已读)
	 */
	@RequestMapping(value = "/message_list.rm")
	@Authorization
	public void msgList(@CurrentUser Account account, HttpServletResponse response, String status,PagerInfo pagerInfo) {
		msgReciveService.initMSgRecive(account.getAccountId(), account.getAccountType());
		msgReciveService.readAll(account.getAccountId());
		CriteriaQuery cq = new CriteriaQuery(pagerInfo,GroupOp.and);
		cq.addSqlField("m.msgReciveId,m.accountId,m.status,m.isDel,m.sendDate,m.title,m.content");
		cq.eq("m.accountId", account.getAccountId());
		cq.eq("isDel", "0");
		cq.order("m.createDate", Sort.desc);
		cq.setProxy(new Proxy(this));
		List<CriterionMap> messageList = msgReciveService.pagerMap(cq, true);
		PqGrid.toPqGrid(messageList, cq);
		JsonUtil.writeObj(response, messageList);
	}

	/**
	 * 获取消息明细
	 */
	@RequestMapping(value = "/message_details.rm")
	@Authorization
	public void msgDetails(@CurrentUser Account account,HttpServletResponse response, String msgReciveId) {
		if(ObjectUtils.isEmpty(msgReciveId)){
			JsonUtil.writeError(response, "消息ID不可为空");
		}else{
			CriterionMap msg = msgReciveService.findByMsgReciveId(msgReciveId);
			msg.put("content",  msg.get("content"));
			JsonUtil.writeMap(response,msg);
		}
	}

	/**
	 * 设置全部已读
	 */
	@RequestMapping(value = "/message_read_all.rm")
	@Authorization
	public void readAll(@CurrentUser Account account,HttpServletResponse response, String msgReciveId) {
		ApiJson apiJson= new ApiJson();
		msgReciveService.readAll(account.getAccountId());
		JsonUtil.writeApiJson(response,apiJson);
	}
	
	/**
	 * 删除消息
	 */
	@RequestMapping(value = "/message_delete.rm")
	@Authorization
	public void delete(@CurrentUser Account account,HttpServletResponse response, String msgReciveId) {
		ApiJson apiJson= new ApiJson();
		MsgRecive msgRecive = new MsgRecive(Integer.parseInt(msgReciveId));
		msgRecive.setIsDel("1");
		int a=msgReciveService.update(msgRecive);
		if(a>0){
			apiJson.setInfo("操作成功");
		}else{
			apiJson.setError("操作失败");
		}
		JsonUtil.writeApiJson(response,apiJson);
	}

	@Override
	public void operate(CriterionMap map, String fieldName, Object value, CriterionMap params) {
		String content=map.get("content");
		map.put("content", HTMLSpirit.delHTMLTag(content));
	}
}
