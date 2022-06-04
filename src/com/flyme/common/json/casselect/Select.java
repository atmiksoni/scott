package com.flyme.common.json.casselect;

import java.util.List;

public class Select {
	private City city;
	private List<Select> child;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Select> getChild() {
		return child;
	}

	public void setChild(List<Select> child) {
		this.child = child;
	}
}
