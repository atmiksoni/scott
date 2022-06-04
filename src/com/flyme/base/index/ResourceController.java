package com.flyme.base.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.account.vo.Language;
import com.flyme.base.rbac.accountrole.service.IAccountRoleService;
import com.flyme.base.rbac.resource.pojo.Resource;
import com.flyme.base.rbac.resource.service.IResourceService;
import com.flyme.base.rbac.resource.vo.ResourceModel;
import com.flyme.base.rbac.resource.vo.ResourceVo;
import com.flyme.common.constants.Global;
import com.flyme.common.json.model.ClienData;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.shiro.metasource.ChainDefinitionSectionMetaSource;
import com.flyme.core.shiro.util.BaseShiroUtils;
import com.flyme.core.shiro.util.PasswordHelper;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 用户资源
 */
@Controller
public class ResourceController extends MbsBaseController {

	@Autowired
	private IResourceService resourceService;
	@Autowired
	public IAccountRoleService accountRoleService;
	private String theme = Global.THEME;

	@RequestMapping("/resource.do")
	@ResponseBody
	public ResourceVo getResource() {
		Account account = BaseShiroUtils.getAccount();
		String accountId = account.getAccountId();
		String password = account.getPassword();
		List<String> roleIdArray = roleService.findAccountRoleIds(accountId);
		ResourceVo resourceVo = new ResourceVo();
		List<Resource> resources1 = resourceService.selectResourceId(roleIdArray, 1, "");
		List<Resource> addResList1 = resourceService.selectByAccountId(accountId, 1, "add");
		List<Resource> delResList1 = resourceService.selectByAccountId(accountId, 1, "del");
		ObjectUtils.merge(resources1, addResList1);
		ObjectUtils.toRepeat(resources1, delResList1);
		List<Resource> resources2 = resourceService.selectResourceId(roleIdArray, 2, "");
		List<Resource> addResList2 = resourceService.selectByAccountId(accountId, 2, "add");
		List<Resource> delResList2 = resourceService.selectByAccountId(accountId, 2, "del");
		ObjectUtils.merge(resources2, addResList2);
		ObjectUtils.toRepeat(resources2, delResList2);
		List<Resource> resources3 = resourceService.selectResourceId(roleIdArray, 3, "");
		/* 检查自主选择的权限 */
		List<Resource> addResList3 = resourceService.selectByAccountId(accountId, 3, "add");
		List<Resource> delResList3 = resourceService.selectByAccountId(accountId, 3, "del");
		ObjectUtils.merge(resources3, addResList3);
		ObjectUtils.toRepeat(resources3, delResList3);
		List<String> first = new ArrayList<String>();
		for (Resource f : resources1) {
			String resourceId1 = f.getResourceId();
			Resource resource1 = ChainDefinitionSectionMetaSource.resourceMap.get(resourceId1);
			if (resource1.getResPriority() == 1) {
				List<String> res = new ArrayList<String>();
				res.add(resource1.getResName());
				res.add(resource1.getResCode());
				res.add(resource1.getIcon());
				first.add(resource1.getResourceId());
				Map<String, Object[]> funArrayMap = new HashMap<String, Object[]>();
				funArrayMap.put("m" + resource1.getResourceId(), res.toArray());
				resourceVo.getFun_array().add(funArrayMap);
			}

			Map<String, Object[]> map2 = new HashMap<String, Object[]>();
			List<String> list2 = new ArrayList<String>();
			for (Resource s : resources2) {
				String resourceId2 = s.getResourceId();
				Resource resource2 = ChainDefinitionSectionMetaSource.resourceMap.get(resourceId2);
				if (resourceId1.equals(resource2.getParentId())) {
					list2.add(resource2.getResourceId());
					List<String> res = new ArrayList<String>();
					res.add(resource2.getResName());
					res.add(resource2.getResUrl());
					res.add(resource2.getIcon());
					Map<String, Object[]> funArrayMap = new HashMap<String, Object[]>();
					funArrayMap.put("f" + resource2.getResourceId(), res.toArray());
					resourceVo.getFun_array().add(funArrayMap);
				}

				Map<String, Object[]> map3 = new HashMap<String, Object[]>();
				List<String> list3 = new ArrayList<String>();
				for (Resource t : resources3) {
					String resourceId3 = t.getResourceId();
					Resource resource3 = ChainDefinitionSectionMetaSource.resourceMap.get(resourceId3);
					if (resourceId2.equals(resource3.getParent())) {
						list3.add(resource3.getResourceId());
						List<String> res = new ArrayList<String>();
						res.add(resource3.getResName());
						res.add(resource3.getResUrl());
						res.add(resource3.getIcon());
						Map<String, Object[]> funArrayMap = new HashMap<String, Object[]>();
						funArrayMap.put("f" + resource3.getResourceId(), res.toArray());
						resourceVo.getFun_array().add(funArrayMap);
					}
				}
				if (ObjectUtils.isNotEmpty(list3)) {
					map3.put("f" + resourceId2, list3.toArray());
					resourceVo.getThird_array().add(map3);
				}

			}
			if (ObjectUtils.isNotEmpty(list2)) {
				map2.put("m" + resourceId1, list2.toArray());
				resourceVo.getSecond_array().add(map2);
			}

		}
		resourceVo.setFirst_array(first.toArray());
		String def = PasswordHelper.getOldPassword(account, Global.DEFAULTPWD);
		if (password.equals(def)) {
			resourceVo.setFlag(true);
		} else {
			resourceVo.setFlag(false);
		}
		resourceVo.setTheme(theme);
		return resourceVo;
	}

	@RequestMapping("/get_resource.do")
	@ResponseBody
	public ClienData getResourceModel() {
		ClienData clienData = new ClienData();
		List<ResourceModel> resourceModels = ObjectUtils.getArrayList();
		Account account = BaseShiroUtils.getAccount();
		String userId = account.getAccountId();
		List<String> roleIdArray = roleService.findAccountRoleIds(userId);
		List<Resource> resources1 = resourceService.selectResourceId(roleIdArray, 1, "");
		List<Resource> addResList1 = resourceService.selectByAccountId(userId, 1, "add");
		List<Resource> delResList1 = resourceService.selectByAccountId(userId, 1, "del");
		ObjectUtils.merge(resources1, addResList1);
		ObjectUtils.toRepeat(resources1, delResList1);
		List<Resource> resources2 = resourceService.selectResourceId(roleIdArray, 2, "");
		List<Resource> addResList2 = resourceService.selectByAccountId(userId, 2, "add");
		List<Resource> delResList2 = resourceService.selectByAccountId(userId, 2, "del");
		ObjectUtils.merge(resources2, addResList2);
		ObjectUtils.toRepeat(resources2, delResList2);
		List<Resource> resources3 = resourceService.selectResourceId(roleIdArray, 3, "");
		List<Resource> addResList3 = resourceService.selectByAccountId(userId, 3, "add");
		List<Resource> delResList3 = resourceService.selectByAccountId(userId, 3, "del");
		ObjectUtils.merge(resources3, addResList3);
		ObjectUtils.toRepeat(resources3, delResList3);
		for (Resource f : resources1) {
			String resourceId = f.getResourceId();
			Resource resource = ChainDefinitionSectionMetaSource.resourceMap.get(resourceId);
			ResourceModel resourceModel = new ResourceModel();
			resourceModel.setResourceId(resource.getResourceId());
			resourceModel.setParentId("0");
			resourceModel.setResKey(resource.getResKey());
			String  language=SessionUtil.get(Language.English);
			String resName=language !=null?resource.getResEnglishName():resource.getResName();
			resourceModel.setResName(resName);
			resourceModel.setTarget("expand");
			resourceModel.setIcon("fa fa-desktop");
			resourceModel.setIsMenu("0");
			resourceModels.add(resourceModel);
		}
		for (Resource s : resources2) {
			String resourceId = s.getResourceId();
			Resource resource = ChainDefinitionSectionMetaSource.resourceMap.get(resourceId);
			ResourceModel resourceModel = new ResourceModel();
			resourceModel.setResourceId(resource.getResourceId());
			resourceModel.setParentId(resource.getParentId());
			resourceModel.setResKey(resource.getResKey());
			String  language=SessionUtil.get(Language.English);
			String resName=language !=null?resource.getResEnglishName():resource.getResName();
			resourceModel.setResName(resName);
			resourceModel.setTarget("expand");
			resourceModel.setIcon("fa fa-" + resource.getIcon());
			resourceModel.setResUrl(resource.getResUrl());
			resourceModel.setIsMenu("0");
			resourceModels.add(resourceModel);
		}
		for (Resource t : resources3) {
			String resourceId = t.getResourceId();
			Resource resource = ChainDefinitionSectionMetaSource.resourceMap.get(resourceId);
			ResourceModel resourceModel = new ResourceModel();
			resourceModel.setResourceId(resource.getResourceId());
			resourceModel.setParentId(resource.getParentId());
			resourceModel.setResKey(resource.getResKey());
			String  language=SessionUtil.get(Language.English);
			String resName=language !=null?resource.getResEnglishName():resource.getResName();
			resourceModel.setResName(resName);
			resourceModel.setResUrl(resource.getResUrl());
			resourceModel.setTarget("iframe");
			resourceModel.setIcon("fa fa-leaf");
			resourceModel.setIsMenu("1");
			resourceModels.add(resourceModel);
		}
		clienData.setResources(resourceModels);
		String password = account.getPassword();
		String def = PasswordHelper.getOldPassword(account, Global.DEFAULTPWD);
		if (password.equals(def)) {
			clienData.setFlag(true);
		} else {
			clienData.setFlag(false);
		}
		return clienData;
	}

}
