package com.genee.service.framework.core.adapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

//如果传递的是List<Map<String,User>>,必须要@XmlSeeAlso注解
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MapConvertor {

	@XmlAttribute
	public String key;

	@XmlAttribute
	public Object value;

}
