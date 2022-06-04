package com.flyme.common.json.pqgrid;

import java.util.ArrayList;
import java.util.List;

import com.flyme.common.util.map.CriterionMap;

public class PqData {
	private List<CriterionMap> footer = new ArrayList<CriterionMap>();
	private CriterionMap total;
	private List<?> data;
	
	public List<CriterionMap> getFooter() {
		return footer;
	}
	
	public void setFooter(List<CriterionMap> footer) {
		this.footer = footer;
	}
	
	public List<?> getData() {
		return data;
	}
	
	public void setData(List<?> data) {
		this.data = data;
	}
	
	public void addFoote(CriterionMap footer) {
		this.footer.add(footer);
	}

	public CriterionMap getTotal() {
		return total;
	}

	public void setTotal(CriterionMap total) {
		this.total = total;
	}
	
}
