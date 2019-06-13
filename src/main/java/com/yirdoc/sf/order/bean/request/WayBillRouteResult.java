package com.yirdoc.sf.order.bean.request;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.yirdoc.sf.order.bean.order.WayBillRoute;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@XStreamAlias("Response")
public class WayBillRouteResult {
	@XStreamAlias("service")
	@XStreamAsAttribute
	private String serviceName = "RoutePushService";
	@XStreamAlias("Head")
	@XStreamAsAttribute
	private String head;

	@XStreamAlias("ERROR")
	private WayBillRouteResultError wayBillRouteResultError;

	public static String toXml(WayBillRouteResult wayBillRouteResult) {
		StringBuilder sb = new StringBuilder()
				.append("<Response service=\"RoutePushService\">")
				.append("<Head>").append(wayBillRouteResult.getHead()).append("</Head>");
		final WayBillRouteResult.WayBillRouteResultError error = wayBillRouteResult.getWayBillRouteResultError();
		if (error != null) {
			// 构建失败响应
			sb.append("<ERROR code=\"")
					.append(error.getCode())
					.append("\">")
					.append(error.getErrorMessage())
					.append("</ERROR>");
		}
		sb.append("</Response>");
		return sb.toString();
	}

	/**
	 * 返回给顺丰订单路由信息成功response.
	 * @return
	 */
	public static String sucess() {
		WayBillRouteResult wayBillRouteResult = WayBillRouteResult.builder()
				.head("OK")
				.build();
		XStream xStream = getXStream();
		return xStream.toXML(wayBillRouteResult);
	}

	/**
	 * 返回给顺丰订单路由信息失败response.
	 * @param message
	 * @return
	 */
	public static String fail(String message) {
		WayBillRouteResultBuilder errResult = WayBillRouteResult.builder()
				.head("ERR")
				.wayBillRouteResultError(WayBillRouteResultError.builder()
						.code("4001")
						.errorMessage(message)
						.build());
		XStream xStream = getXStream();
		return xStream.toXML(errResult);
	}

	private static XStream getXStream() {
		XStream xStream = new XStream(new DomDriver());
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypeHierarchy(WayBillRouteResult.class);
		xStream.processAnnotations(WayBillRouteResult.class);
		return xStream;
	}

	@Builder
	@Data
	public static class WayBillRouteResultError {
		@Builder.Default
		private String code = "4001";
		private String errorMessage;
	}
}
