package com.flyme.app.bus;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.account.service.IAccountService;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.rbac.accountinfo.service.IAccountInfoService;
import com.flyme.base.system.about.service.IAboutService;
import com.flyme.base.system.feedback.pojo.FeedBack;
import com.flyme.base.system.feedback.service.IFeedBackService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/api")
public class App_Set extends MbsBaseController {

	@Autowired
	private IAboutService aboutService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountInfoService accountInfoService;
	@Autowired
	private IFeedBackService feedBackService;
	
	

	/*************
	 * 系统设置 US14 2017/04/21
	 *****************************/
	@RequestMapping(value = "/system_set.rm")
	@Authorization
	public void systemSet(@CurrentUser Account account, HttpServletResponse response, PagerInfo pagerInfo) {
		ApiJson j = new ApiJson();
		AccountInfo accountInfo = accountInfoService.findById(account.getAccountId());
		if (ObjectUtils.isEmpty(accountInfo)) {
			j.setError("未获取用户信息！");
		} else {
			// 1.返回版本号

		}
		JsonUtil.writeApiJson(response, j);
	}
	
	
	/*************
	 * 关于我们内容 SE01 2017/04/11
	 ************************/
	@RequestMapping(value = "/aboutus_data.rm")
	public void aboutUsData(HttpServletResponse response){
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap("aboutId","title","content","image");
		CriterionMap about = aboutService.getNewestOne();
		map.putMap(about);
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}
	
	/**************
	 * 修改密码 2017/04/11
	 **************************/
	@RequestMapping(value = "/password_edit.rm")
	@Authorization
	public void passwordUpdate(@CurrentUser Account account, HttpServletResponse response,String oldPwd ,String newPwd ,String newPwd2) {
		ApiJson j = new ApiJson();
		if(ObjectUtils.isEmpty(newPwd) || ObjectUtils.isEmpty(newPwd2)){
			j.setError("密码不能为空!");
			JsonUtil.writeApiJson(response, j);
		}else if (!newPwd.equals(newPwd2)) {
			j.setError("新密码输入不一致!");
			JsonUtil.writeApiJson(response, j);
		}else{
			j = accountService.updatePassword(newPwd, oldPwd, account);
			JsonUtil.writeApiJson(response, j);
		}
	}
	
	/**************
	 * 用户反馈 SE03 2017/04/11
	 ***********************/
	@RequestMapping(value = "/feedback_sub.rm")
	@Authorization
	public void feedback(@CurrentUser Account account, HttpServletResponse response,FeedBack feedback) {
		ApiJson j = new ApiJson();
		String content = feedback.getContent();
		if(ObjectUtils.isEmpty(content)){
			j.setError("内容不能为空!");
			JsonUtil.writeApiJson(response, j);
		}else{
			String  contentUnicode = ConvertUtils.string2Unicode(content);
			AccountInfo accountInfo = accountInfoService.findById(account.getAccountId());
			if (ObjectUtils.isNotEmpty(accountInfo)) {
				feedback.setAccountInfo(accountInfo);
				feedback.setStatus(0);//0:未处理1:已处理
				feedback.setContent(contentUnicode);
				feedback.setMobile(accountInfo.getTelephone());
				feedBackService.add(feedback);
				j.setInfo("提交成功!");
			}else{
				j.setError("提交失败,请重新提交!");
			}
		}
		JsonUtil.writeApiJson(response, j);
	}
}
