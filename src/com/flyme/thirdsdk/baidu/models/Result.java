package com.flyme.thirdsdk.baidu.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
	private BaiduLocation location;
	private String formatted_address;
	private AddressComponent addressComponent;

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public AddressComponent getAddressComponent() {
		return addressComponent;
	}

	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

	public BaiduLocation getLocation() {
		return location;
	}

	public void setLocation(BaiduLocation location) {
		this.location = location;
	}
}
