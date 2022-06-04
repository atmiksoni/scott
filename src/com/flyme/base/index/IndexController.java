package com.flyme.base.index;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.account.vo.Language;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.rbac.accountrole.service.IAccountRoleService;
import com.flyme.business.checkorder.service.ICheckorderService;
import com.flyme.business.firm.pojo.Firm;
import com.flyme.business.firm.service.IFirmService;
import com.flyme.business.repaircompany.pojo.Repaircompany;
import com.flyme.business.workorder.service.IWorkorderService;
import com.flyme.common.constants.Global;
import com.flyme.common.constants.RoleConstants;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.shiro.util.BaseShiroUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.core.websocet.config.MyMessage;

/**
 * 系统首页
 */
@Controller
public class IndexController extends MbsBaseController {

	private String theme = Global.THEME;
	@Autowired
	public IAccountRoleService accountRoleService;
	@Autowired
	private ICheckorderService checkorderService;
	@Autowired
	private IWorkorderService workorderService;
	@Autowired
	private IFirmService firmService;
	/**
	 * 首页跳转
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, ModelMap modelMap) throws Exception {
		Account account = BaseShiroUtils.getAccount();
		String  language=SessionUtil.get(Language.English);
		int userType = 0;
		if (ObjectUtils.isNotEmpty(account)) {
			if (BaseShiroUtils.hasRole(RoleConstants.ROLE_ADMIN)) {
				if (BaseShiroUtils.hasRole(RoleConstants.ROLE_SYS_ADMIN)) {
					userType = 1;
				}
				List<String> accountRole = accountRoleService.findRoleNameByAccountId(account.getAccountId());
				String RoleName = "";
				for (String s : accountRole) {
					RoleName += s;
				}
				modelMap.addAttribute("userType", userType);
				modelMap.addAttribute("RoleNames", RoleName);
			}
		}
		if(ObjectUtils.isNotEmpty(language)){
			EnglishLoad(modelMap);
		}
		return "index/" + theme + "/index";
	}
	/**
	 * 加载英文资源
	 * @param modelMap
	 */
	public void EnglishLoad(ModelMap modelMap){
		modelMap.addAttribute("projectName",Language.projectName);
		modelMap.addAttribute("backgroundManageMent",Language.backgroundManageMent);
		modelMap.addAttribute("welcomePage",Language.welcomePage);
		modelMap.addAttribute("Navigationmenu",Language.Navigationmenu);
		modelMap.addAttribute("Taboperation",Language.Taboperation);
		modelMap.addAttribute("RefreshCurrent",Language.RefreshCurrent);
		modelMap.addAttribute("CloseCurrent",Language.CloseCurrent);
		modelMap.addAttribute("AllClosed",Language.AllClosed);
		modelMap.addAttribute("outThisCloseCurrent",Language.outThisCloseCurrent);
		modelMap.addAttribute("Loadforyou",Language.Loadforyou);
		modelMap.addAttribute("ChangePassword",Language.ChangePassword);
		modelMap.addAttribute("Safeexit",Language.Safeexit);
		modelMap.addAttribute("QuitSystem",Language.QuitSystem);
		modelMap.addAttribute("Quit",Language.Quit);
		modelMap.addAttribute("ConfirmExit",Language.ConfirmExit);
		modelMap.addAttribute("cancel",Language.cancel);
		modelMap.addAttribute("modifypassword",Language.modifypassword);
		modelMap.addAttribute("Confirm",Language.Confirm);
		modelMap.addAttribute("close",Language.close);
	}
	/**
	 * 订单提醒
	 */
	@RequestMapping("/msg.do")
	public String message(HttpServletRequest request) {
		return "index/" + theme + "/msg";
	}

	/** 桌面 */
	@RequestMapping("/desktop.do")
	public String desktop(ModelMap modelMap) {
		return "index/" + theme + "/desktop";
	}

	/**
	 * 更新公告
	 */
	@RequestMapping("/updatelog.do")
	public String updatelog(HttpServletRequest request) {
		return "updatelog";
	}
	/** 当日检查卡提醒  */
	@RequestMapping(value = "/next_checkorder.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson sendSub(ApiJson j) {
		Repaircompany repaircompany=SessionUtil.get(RoleConstants.ROLE_COMPANY);
		if(ObjectUtils.isNotEmpty(repaircompany)){
			CriteriaQuery orderCq=new CriteriaQuery(GroupOp.and);
			orderCq.addSqlField("IFNULL(count(*), 0) todayOrderNum");
			orderCq.eq("DATEDIFF(nextRepairTime, NOW())",0);
			orderCq.eq("repaircompanyId", repaircompany.getRepairCompanyId());
			List<CriterionMap> checkOrders=checkorderService.pagerMap(orderCq, false);	
			Long todayOrderNum=checkOrders.get(0).get("todayOrderNum");// 今日订单数
			j.setObject(todayOrderNum);
			String  languaged=SessionUtil.get(Language.English);
			if(ObjectUtils.isNotEmpty(languaged)){
				j.setInfo("Today you have "+todayOrderNum +"checking orders waiting to be processed.");
			}else{
				j.setInfo("今日您有"+todayOrderNum +"个检查工单等待处理");
			}
		}else{
			j.setObject(0);
		}
		
		return j;
	}
	/** 当日维修公司提醒  */
	@RequestMapping(value = "/next_firm.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson nextFirm(ApiJson j) {
		Repaircompany repaircompany=SessionUtil.get(RoleConstants.ROLE_COMPANY);
		if(ObjectUtils.isNotEmpty(repaircompany)){
			CriteriaQuery orderCq=new CriteriaQuery(GroupOp.and);
			orderCq.addSqlField("IFNULL(count(*), 0) todayOrderNum");
			orderCq.eq("DATEDIFF(reminderDate, NOW())",0);
			orderCq.eq("repaircompanyId", repaircompany.getRepairCompanyId());
			List<CriterionMap> checkOrders=firmService.pagerMap(orderCq, false);
			Long todayOrderNum=checkOrders.get(0).get("todayOrderNum");// 今日订单数
			j.setObject(todayOrderNum);
			String  languaged=SessionUtil.get(Language.English);
			if(ObjectUtils.isNotEmpty(languaged)){
				j.setInfo("Today you have "+todayOrderNum +"customers waiting to be processed.");
			}else{
				j.setInfo("今日您有"+todayOrderNum +"个客户等待处理");
			}
		}else{
			j.setObject(0);
		}
		
		return j;
	}
	/** 当日工作卡提醒  */
	@RequestMapping(value = "/next_workorder.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson sendSu1(ApiJson j) {
		Repaircompany repaircompany=SessionUtil.get(RoleConstants.ROLE_COMPANY);
		if(ObjectUtils.isNotEmpty(repaircompany)){
			CriteriaQuery orderCq=new CriteriaQuery(GroupOp.and);
			orderCq.addSqlField("IFNULL(count(*), 0) todayOrderNum");
			orderCq.eq("DATEDIFF(nextRepairTime, NOW())",0);
			orderCq.eq("repaircompanyId", repaircompany.getRepairCompanyId());
			List<CriterionMap> workOrders=workorderService.pagerMap(orderCq, false);	
			Long todayOrderNum=workOrders.get(0).get("todayOrderNum");// 今日订单数
			j.setObject(todayOrderNum);
			String  languaged=SessionUtil.get(Language.English);
			if(ObjectUtils.isNotEmpty(languaged)){
				j.setInfo("Today you have "+todayOrderNum +"work orders waiting to be processed.");
			}else{
				j.setInfo("今日您有"+todayOrderNum +"个工作工单等待处理");
			}
		}else{
			j.setObject(0);
		}
		return j;
	}
	/** 图标上数量 */
	@RequestMapping("/count.do")
	@ResponseBody
	public Map<String, Object> test(ApiJson j, ModelMap modelMap) {
		String communityId = "";
		Map<String, Object> modules = ObjectUtils.getHashMap();
		if (ObjectUtils.isNotEmpty(communityId)) {
			modules.put("xqgg", 3);// 小区公告
			modules.put("repairs", 0);// 报修维修
		}
		return modules;
	}

	/** 图标上数量 */
	@RequestMapping("/content.do")
	@ResponseBody
	public MyMessage centent(ApiJson j, ModelMap modelMap, String type, int xqgg, int repairs) {
		MyMessage myMessage = new MyMessage();
		if (type.equals("repairs") && repairs > 0) {// 报修维修
			String text = "您好,有" + repairs + "户申请了报修维修，请尽快受理，谢谢！";
			myMessage.setTitle("报修信息");
			myMessage.setText(text);
		} else if (type.equals("xqgg") && xqgg > 0) {// 小区公告
			CriterionMap map = new CriterionMap();
			map.put("isUse", 1);

		}
		return myMessage;
	}

	/** 图标上数量提醒 */
	@RequestMapping("/test.do")
	@ResponseBody
	public AccountInfo test(String username) {
		AccountInfo accountInfo = new AccountInfo();
		return accountInfo;
	}

	/** 图标上数量提醒 */
	@RequestMapping("/testlogin.do")
	@ResponseBody
	public AccountInfo testlogin(String username) {
		AccountInfo accountInfo = new AccountInfo();
		return accountInfo;
	}
}
