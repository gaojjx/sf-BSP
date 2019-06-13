package com.yirdoc.sf.order.xml;

import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * 重写NameCoder，解决单下划线变双下划线的问题.
 */
public class XStreamNameCoder extends XmlFriendlyNameCoder {
	public XStreamNameCoder() {
		super("_'", "_");
	}
}