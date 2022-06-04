package com.flyme.common.util.xmltobean;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

public class PreferredMapper extends NamespacePrefixMapper {
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		return "web";
	}

}