package com.flyme.common.util.xmltobean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

class JAXBTest {
	public static void main(String[] args) {
		try {
			JAXBEntity1 entity = new JAXBEntity1();

			entity.setElementName("elementName");
			entity.setEntityField("entityField");
			entity.setSequence("sequence");
			entity.setStyleId(111111);
			String xml = JAXBParserUtil.serializaToXml(entity, new Class[] { JAXBEntity1.class });

			System.out.println(xml);

			entity = (JAXBEntity1) JAXBParserUtil.unserializeFromXml(xml, new Class[] { JAXBEntity1.class });
			System.out.println(entity);

			System.out.println();

			JAXBEntity ent = new JAXBEntity();
			ent.setCode("code");
			ent.setName("name");
			List<JAXBEntity1> list = new ArrayList<JAXBEntity1>();
			JAXBEntity1 ent1 = new JAXBEntity1();
			ent1.setElementName("elementName");
			ent1.setEntityField("entityField");
			ent1.setSequence("sequence");
			ent1.setStyleId(111111);
			list.add(ent1);
			JAXBEntity1 ent2 = new JAXBEntity1();
			ent2.setElementName("elementName");
			ent2.setEntityField("entityField");
			ent2.setSequence("sequence");
			ent2.setStyleId(2222);
			list.add(ent2);
			ent.setList(list);

			String xml1 = JAXBParserUtil.serializaToXml(ent, new Class[] { JAXBEntity.class, JAXBEntity1.class });

			System.out.println(xml1);

			ent = (JAXBEntity) JAXBParserUtil.unserializeFromXml(xml1, new Class[] { JAXBEntity.class, JAXBEntity1.class });

			System.out.println(ent);

			String xml2 = " <JAXBEntity1> <elementName>elementName</elementName> <entityField>entityField</entityField> <sequence>sequence</sequence> <styleId>111111</styleId> </JAXBEntity1>";
			entity = (JAXBEntity1) JAXBParserUtil.unserializeFromXml(xml2, new Class[] { JAXBEntity1.class });
			System.out.println(entity);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}