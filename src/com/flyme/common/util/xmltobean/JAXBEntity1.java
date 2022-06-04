package com.flyme.common.util.xmltobean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "JAXBEntity1")
public class JAXBEntity1 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String sequence;
	private Integer styleId;
	private String elementName;
	private String entityField;

	@XmlElement
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@XmlElement
	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	@XmlElement
	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	@XmlElement
	public String getEntityField() {
		return entityField;
	}

	public void setEntityField(String entityField) {
		this.entityField = entityField;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
