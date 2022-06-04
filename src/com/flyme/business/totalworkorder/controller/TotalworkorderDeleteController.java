package com.flyme.business.totalworkorder.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.totalworkorder.service.ITotalworkorderService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 总工作工单管理(删除)
 * @author zyf
 * @date 2019-2-1
 */
@Controller
@RequestMapping("/totalworkorder")
public class TotalworkorderDeleteController extends MbsBaseController{
	@Autowired
	private ITotalworkorderService totalworkorderService;

	@RequestMapping(value = "/totalworkorder_del_sub.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson del(ApiJson j, String id) {
		ApiJson a=totalworkorderService.delById(id);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			a.setInfo(Language.DeleteSuccess);
		}
		return a;
	}
}
