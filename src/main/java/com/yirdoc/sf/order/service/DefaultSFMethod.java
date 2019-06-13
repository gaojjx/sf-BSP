package com.yirdoc.sf.order.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.yirdoc.sf.order.bean.order.Order;
import com.yirdoc.sf.order.bean.order.WayBillRoute;
import com.yirdoc.sf.order.bean.request.OrderRequest;
import com.yirdoc.sf.order.bean.request.WayBillRouteResult;
import com.yirdoc.sf.order.bean.response.OrderResponse;
import com.yirdoc.sf.order.bean.response.WayBillRouteRequest;
import com.yirdoc.sf.order.config.SFConfig;
import com.yirdoc.sf.order.util.SFUtil;
import com.yirdoc.sf.order.xml.XStreamNameCoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用xml实现的顺丰基础功能类
 * Created by terry on 15-7-22.
 */
@Slf4j
public class DefaultSFMethod implements ISFMethod {

	public static final int TYPE_MAILNO = 1;
	public static final int TYPE_ORDERNO = 2;

	private SFConfig sfConfig;

	@Override
	public SFConfig getConfig() {
		return this.sfConfig;
	}

	@Override
	public void setConfig(SFConfig config) {
		this.sfConfig = config;
	}

	@Override
	public OrderResponse order(Order order) {
		OrderRequest orderRequest = OrderRequest.builder()
				.serviceName("OrderService")
				.head(this.getConfig().getApiCode())
				.body(new OrderRequest.OrderRequestBody(order))
				.build();
		XStream xStream = new XStream(new DomDriver("UTF-8", new XStreamNameCoder()));
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypeHierarchy(OrderRequest.class);
		xStream.processAnnotations(OrderRequest.class);
		final String xml = xStream.toXML(orderRequest);
		final String request = xml.replaceAll("<Cargo>\\s+", "")
				.replaceAll("\\s+</Cargo>", "");
		String responseXml = post(request);
		return OrderResponse.fromXml(responseXml);
	}

	@Override
	public String routePush(String xmlMessage) {
		final WayBillRouteResult wayBillRouteResult = WayBillRouteResult.builder()
				.head("OK")
				.build();
		final WayBillRouteRequest wayBillRouteRequest = WayBillRouteRequest.fromXml(xmlMessage);
		final WayBillRouteRequest.Body wayBillRouteRequestBody = wayBillRouteRequest.getBody();
		if (wayBillRouteRequestBody == null) {
			log.warn("订单路由推送消息没有数据", wayBillRouteRequest);
			wayBillRouteResult.setHead("ERR");
			wayBillRouteResult.setWayBillRouteResultError(WayBillRouteResult.WayBillRouteResultError.builder()
					.code("8018")
					.errorMessage("没有订单路由信息")
					.build());
			return WayBillRouteResult.toXml(wayBillRouteResult);
		}
		final List<WayBillRoute> wayBillRoute = wayBillRouteRequestBody.getWayBillRoute();
		log.debug("\n订单路由推送消息： {}\n【订单详情】：{}", wayBillRouteRequest, wayBillRoute);
		return WayBillRouteResult.toXml(wayBillRouteResult);
	}

	private String post(String xml) {
		try {
			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			HttpPost httpPost = this.createHttpPost(this.getConfig().getUrl(), xml);
			try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
				try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
					String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
					log.debug("\n【请求数据】：{}\n【响应数据】：{}", xml, responseString);
					return responseString;
				}
			} finally {
				httpPost.releaseConnection();
			}
		} catch (Exception e) {
			log.error("\n【请求数据】：{}\n【异常信息】：{}", xml, e.getMessage());
			return null;
		}
	}

	private HttpPost createHttpPost(String url, String xml) {
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("xml", xml));

		params.add(new BasicNameValuePair("verifyCode", SFUtil.getVerifyCode(xml, this.getConfig().getCheckWord())));
		httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
		return httpPost;
	}

}