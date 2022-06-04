package com.flyme.common.util.xmltobean;


import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name="CurrAcuRsp",namespace="http://www.example.org/webservice_zz/")
public class JAXBEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String code;
	private String name;
	private List<JAXBEntity1> list;
	
	@XmlElement
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	@XmlElement
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	@XmlElement(name = "JAXBEntity1")
	public List<JAXBEntity1> getList()
	{
		return list;
	}
	public void setList(List<JAXBEntity1> list)
	{
		this.list = list;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
