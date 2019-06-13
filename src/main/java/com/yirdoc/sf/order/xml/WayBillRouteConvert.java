package com.yirdoc.sf.order.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import com.yirdoc.sf.order.bean.order.WayBillRoute;
import com.yirdoc.sf.order.bean.request.WayBillRouteResult;
import com.yirdoc.sf.order.bean.response.WayBillRouteRequest;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WayBillRouteConvert extends AbstractReflectionConverter {

	public WayBillRouteConvert(Mapper mapper, ReflectionProvider reflectionProvider) {
		super(mapper, reflectionProvider);
	}

	@Override
	public void marshal(Object original, HierarchicalStreamWriter writer, MarshallingContext context) {
		final WayBillRouteResult wayBillRouteResult = (WayBillRouteResult) original;
		writer.addAttribute("service", wayBillRouteResult.getServiceName());
		writer.startNode("head");
		writer.setValue(wayBillRouteResult.getHead());
		writer.endNode();
		WayBillRouteResult.WayBillRouteResultError error = wayBillRouteResult.getWayBillRouteResultError();
		if (error != null) {
			writer.startNode("ERROR");
			writer.addAttribute("code", error.getCode());
			writer.setValue(error.getErrorMessage());
			writer.endNode();
		}
		super.marshal(original, writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		final WayBillRouteRequest wayBillRouteRequest = new WayBillRouteRequest();
		//解析Request标签
		wayBillRouteRequest.setServiceName(reader.getAttribute("service"));
		wayBillRouteRequest.setLang(reader.getAttribute("lang"));
		reader.moveDown();

		//向下解析Body -> WaybillRoute
		reader.moveDown();
		final WayBillRoute wayBillRoute = new WayBillRoute();
		final Field[] fields = wayBillRoute.getClass().getDeclaredFields();
		Map<String, Field> routePushFieldMap = Stream.of(fields).distinct()
				.collect(Collectors.toMap(this::getFieldName, field -> field));
		int attributeCount = reader.getAttributeCount();
		for (int i = 0; i < attributeCount; i++) {
			final String attributeName = reader.getAttributeName(i);
			final Field field = routePushFieldMap.get(attributeName);
			field.setAccessible(true);
			try {
				field.set(wayBillRoute, reader.getAttribute(i));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return wayBillRouteRequest;
	}

	@Override
	public boolean canConvert(Class type) {
		return WayBillRouteRequest.class.equals(type);
	}

	private String getFieldName(Field field) {
		if (field.isAnnotationPresent(XStreamAlias.class)) {
			return field.getAnnotation(XStreamAlias.class).value();
		}
		return field.getName();
	}
}
