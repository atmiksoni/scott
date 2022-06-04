package com.flyme.app.bus;

import com.flyme.base.system.notice.service.INoticeService;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.html.HTMLSpirit;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.core.springmvc.designpattern.proxy.ISubject;
import com.flyme.core.springmvc.designpattern.proxy.Proxy;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消息模块接口(个人消息,系统消息)
 */
@Controller
@RequestMapping("/api")
public class App_Notice extends MbsBaseController implements ISubject{

	@Autowired
	private INoticeService noticeService;

	/**
	 * 查询公告
	 */
	@RequestMapping(value = "/notice_list.rm")
	public void noticeList(HttpServletResponse response, PagerInfo pagerInfo ) {
		CriteriaQuery cq = new CriteriaQuery(pagerInfo,GroupOp.and);
		cq.addSqlField("n.noticeId,n.accountId,n.noticeType,n.imgUrl,n.title,n.content,n.createDate,n.type,n.sendStatus,account.userName");
		cq.createAlias("account", "account");
		cq.setProxy( new Proxy(this));
		cq.eq("n.sendStatus","1");
		List<CriterionMap> messageList = noticeService.pagerMap(cq, true);
		for (int index=0;index<messageList.size();index++){
			CriterionMap criterionMap = messageList.get(index);
			String temp = criterionMap.getString("content");

			/*  用正则表达式把全部的标签替换成空字符串，然后把被转义的内容（尖括号，空格等）转回来 */
			temp = temp.replaceAll("\\<.*?\\>","");
			temp = StringEscapeUtils.unescapeHtml(temp);

			criterionMap.put("content",temp);
		}
		PqGrid.toPqGrid(messageList, cq);
		JsonUtil.writeObj(response, messageList);
	}

	/**
	 * 获取公告明细
	 */
	@RequestMapping(value = "/notice_details.rm")
	public void msgDetails(HttpServletResponse response, String noticeId) {
		if(ObjectUtils.isEmpty(noticeId)){
			JsonUtil.writeError(response, "公告ID不可为空");
		}else{
			CriterionMap notice = noticeService.getDataById(noticeId);
			CriterionMap map = new CriterionMap();
			map.put("notice", notice);
			JsonUtil.writeMap(response,map);
		}
	}

	@Override
	public void operate(CriterionMap map, String fieldName, Object value, CriterionMap params) {
		String content = map.get("content");
		if (ObjectUtils.isNotEmpty(content)) {
			map.put("content", HTMLSpirit.delHTMLTag(content));
		}
	}
}