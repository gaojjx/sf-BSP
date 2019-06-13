package com.yirdoc.sf.order.bean.response;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.yirdoc.sf.order.bean.order.WayBillRoute;
import lombok.Data;

import java.util.List;

/**
 * 接收顺丰订单路由信息推送.
 */
@Data
@XStreamAlias("Request")
public class WayBillRouteRequest {
	@XStreamAlias("service")
	@XStreamAsAttribute
	private String serviceName;
	@XStreamAlias("lang")
	@XStreamAsAttribute
	private String lang;
	@XStreamAlias("Body")
	private Body body;

	/**
	 * @param xml 解析xml内容转成bean对象.
	 * @return
	 */
	public static WayBillRouteRequest fromXml(String xml) {
		XStream xStream = new XStream(new DomDriver());
		XStream.setupDefaultSecurity(xStream);
//		xStream.registerConverter(new WayBillRouteConvert(xStream.getMapper(), xStream.getReflectionProvider()));
		xStream.processAnnotations(WayBillRouteRequest.class);
		xStream.allowTypeHierarchy(WayBillRouteRequest.class);
		return (WayBillRouteRequest) xStream.fromXML(xml);
	}

	/**
	 * XML内Body标签包含订单的多条路由信息.
	 */
	@Data
	@XStreamAlias("Body")
	public class Body {
		@XStreamAlias("WaybillRoute")
		@XStreamImplicit
		private List<WayBillRoute> wayBillRoute;
	}

}
