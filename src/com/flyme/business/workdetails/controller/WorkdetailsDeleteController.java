package com.flyme.business.workdetails.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.workdetails.service.IWorkdetailsService;

/**
 * 管理(删除)
 * @author zyf
 * @date 2019-1-29
 */
@Controller
@RequestMapping("/workdetails")
public class WorkdetailsDeleteController extends MbsBaseController{
	@Autowired
	private IWorkdetailsService workdetailsService;

	@RequestMapping(value = "/workdetails_del_sub.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson del(ApiJson j, String id) {
		ApiJson a=workdetailsService.delById(id);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			a.setInfo(Language.DeleteSuccess);
		}
		return a;
	}
}
