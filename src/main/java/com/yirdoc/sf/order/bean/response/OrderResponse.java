package com.yirdoc.sf.order.bean.response;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.yirdoc.sf.order.xml.OrderResponseConvert;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@XStreamAlias("OrderResponse")
public class OrderResponse extends BaseResponse<OrderResponse> implements Serializable {

	@XStreamAlias("orderid")
	private String orderId;

	@XStreamAlias("mailno")
	private String mailNo;

	@XStreamAlias("filter_result")
	private String filterResult;

	@XStreamAlias("destcode")
	private String destCode;

	@XStreamAlias("origincode")
	private String originCode;

	@XStreamAlias("rls_info")
	private RlsInfo rlsInfo;

	public static OrderResponse fromXml(String xml) {
		XStream xStream = new XStream(new DomDriver());
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypeHierarchy(BaseResponse.class);
		xStream.registerConverter(new OrderResponseConvert(xStream.getMapper(), xStream.getReflectionProvider()));
		xStream.processAnnotations(BaseResponse.class);
		return (OrderResponse) xStream.fromXML(xml);
	}

	@Data
	@NoArgsConstructor
	public static class RlsInfo implements Serializable {
		@XStreamAlias("rls_errormsg")
		private String rlsErrorMsg;
		@XStreamAlias("invoke_result")
		private String invokeResult;
		@XStreamAlias("rls_code")
		private String rlsCode;
		@XStreamAlias("errorDesc")
		private String errorDesc;
		@XStreamAlias("rls_detail")
		private RlsDetail rlsDetail;
	}

	@Data
	@NoArgsConstructor
	public static class RlsDetail implements Serializable {

		@XStreamAlias("waybillNo")
		private String wayBillNo;

		/**
		 * 原寄地中转场.
		 */
		@XStreamAlias("sourceTransferCode")
		private String sourceTransferCode;
		@XStreamAlias("sourceCityCode")
		private String sourceCityCode;
		@XStreamAlias("sourceDeptCode")
		private String sourceDeptCode;
		@XStreamAlias("sourceTeamCode")
		private String sourceTeamCode;
		@XStreamAlias("destCityCode")
		private String destCityCode;
		@XStreamAlias("destDeptCode")
		private String destDeptCode;
		@XStreamAlias("destRouteLabel")
		private String destRouteLabel;
		@XStreamAlias("proName")
		private String proName;
		@XStreamAlias("cargoTypeCode")
		private String cargoTypeCode;
		@XStreamAlias("limitTypeCode")
		private String limitTypeCode;
		@XStreamAlias("expressTypeCode")
		private String expressTypeCode;
		@XStreamAlias("xbFlag")
		private String xbFlag;
		@XStreamAlias("printFlag")
		private String printFlag;
		@XStreamAlias("twoDimensionCode")
		private String twoDimensionCode;
		@XStreamAlias("proCode")
		private String proCode;
		@XStreamAlias("printIcon")
		private String printIcon;
		@XStreamAlias("abFlag")
		private String abFlag;
	}

}
