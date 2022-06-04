package com.flyme.business.obligation.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.obligation.service.IObligationService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 设计职责管理(删除)
 * @author zyf
 * @date 2018-12-3
 */
@Controller
@RequestMapping("/obligation")
public class ObligationDeleteController extends MbsBaseController{
	@Autowired
	private IObligationService obligationService;

	@RequestMapping(value = "/obligation_del_sub.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson del(ApiJson j, String id) {
		ApiJson a=obligationService.delById(id);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			a.setInfo(Language.DeleteSuccess);
		}
		return a;
	}
}
