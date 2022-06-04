package com.test;

/** 颜色类型 */
public enum ColorEnum {
	A("A", "FF1C28"),B("B", "1D6FF3"),C("C", "F7AE33"),	D("D", "00B050"),E("E", "CCEED0");
	private String value;
	private String index;

	ColorEnum(String index, String value) {
		this.index = index;
		this.value = value;
	}

	ColorEnum(String index) {
		this.index = index;
		this.value = getText(index);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public static String getText(String index) {
		String v = "";
		for (ColorEnum orderEnum : ColorEnum.values()) {
			if (orderEnum.getIndex().equals(index) ) {
				v = orderEnum.name();
				break;
			}
		}
		return v;
	}
public static void main(String[] args) {
//String color=	ColorEnum.valueOf("F")!=null ?ColorEnum.valueOf("F").getValue():"default";
String color=getText("F")!=""?getText("F"):"defalult";
System.out.println(color);
}
}
