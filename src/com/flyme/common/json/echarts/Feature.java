package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts模型启用功能)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class Feature {

	private Mark mark=new Mark();
	private DataView dataView=new DataView();
	private MagicType magicType=new MagicType();
	private Restore restore=new Restore();
	private SaveAsImage saveAsImage=new SaveAsImage();

	public Mark getMark() {
		return mark;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}

	public DataView getDataView() {
		return dataView;
	}

	public void setDataView(DataView dataView) {
		this.dataView = dataView;
	}

	public MagicType getMagicType() {
		return magicType;
	}

	public void setMagicType(MagicType magicType) {
		this.magicType = magicType;
	}

	public Restore getRestore() {
		return restore;
	}

	public void setRestore(Restore restore) {
		this.restore = restore;
	}

	public SaveAsImage getSaveAsImage() {
		return saveAsImage;
	}

	public void setSaveAsImage(SaveAsImage saveAsImage) {
		this.saveAsImage = saveAsImage;
	}
	
	
}
