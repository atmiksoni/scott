package com.flyme.common.json.tree;

import java.util.ArrayList;
import java.util.List;

import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.reflect.ReflectHelper;

public class ComboUtil {
	
	public static List<ComboModel> getResultList(List<?> list,String checkId) {
		List<ComboModel> comboModelList =  new ArrayList<ComboModel>();
		ComboModel comboModel = new ComboModel();
		for (Object object : list) {
			comboModel = getComBo(object, checkId);
			comboModelList.add(comboModel);
		}
		return comboModelList;
	}
	
	public static ComboModel getComBo(Object object,String checkId) {
		ComboModel comboModel = new ComboModel();
		ReflectHelper reflectHelper = new ReflectHelper(object);
		String value = ConvertUtils.getString(reflectHelper.getMethodValue("value"));
		String text = ConvertUtils.getString(reflectHelper.getMethodValue("text",""));
		String group = ConvertUtils.getString(reflectHelper.getMethodValue("group",""));
		if(!value.equals(""))
			comboModel.setValue(value);
		if(!group.equals(""))
			comboModel.setGroup(group);
		comboModel.setText(text);
		if(checkId!=null && checkId.equals(value)){
			comboModel.setSelected(true);
		}
		return comboModel;
	}
}