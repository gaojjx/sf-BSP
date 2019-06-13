package com.yirdoc.sf.order.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.yirdoc.sf.order.bean.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@XStreamAlias("Request")
public class OrderRequest {
	@XStreamAlias("service")
	@XStreamAsAttribute
	@Builder.Default
	private String serviceName = "OrderService";

	@XStreamAlias("lang")
	@XStreamAsAttribute
	@Builder.Default
	private String language = "zh-CN";

	@XStreamAlias("Head")
	private String head;

	@XStreamAlias("Body")
	private OrderRequestBody body;

	@XStreamOmitField
	private String apiCode;

	@Data
	@AllArgsConstructor
	@Builder
	public static class OrderRequestBody {
		@XStreamAlias("Order")
		private Order order;
	}
}
