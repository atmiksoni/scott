package com.flyme.app.bus;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.base.system.version.pojo.Version;
import com.flyme.base.system.version.service.IVersionService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping(value = "/api")
public class App_Version extends MbsBaseController {
	
	@Autowired
	private IVersionService versionService;
	
	/** 获取最新版本*/
	@RequestMapping(value = "/get_version.rm")
	public void getversion_a(HttpServletResponse response, String type,String versionNo) {
		ApiJson j = new ApiJson();
		if (ObjectUtils.isEmpty(type))
			type = "Android";
		CriterionMap map = new CriterionMap();
		Version version = versionService.getVersion(1,type,versionNo);
		if(ObjectUtils.isNotEmpty(version)){
			map.add("versionId", version.getVersionId());
			map.add("title", version.getTitle());
			map.add("name", version.getName());
			map.add("content", version.getContent());
			map.add("force", version.getForce());
			map.add("versionNo", version.getVersionNo());
			map.add("url", version.getUrl());
			if(ObjectUtils.isNotEmpty(type) && type.equals("IOS")){
				map.add("url", " itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/viewSoftware?id=1318129752");
			}
			if(versionNo.equals(version.getVersionNo())){
				j.setObject(map);
				j.setInfo("0");
			}else{
				j.setInfo("1");
				j.setObject(map);
			}
		}else{
			j.setObject(map);
			j.setInfo("0");
		}
		JsonUtil.writeApiJson(response, j);
	}
}
