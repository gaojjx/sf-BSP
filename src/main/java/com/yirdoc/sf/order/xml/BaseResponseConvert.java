package com.yirdoc.sf.order.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;
import com.yirdoc.sf.order.bean.response.BaseResponse;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseResponseConvert<T extends BaseResponse> extends AbstractReflectionConverter {

	private Class<T> clz;

	public BaseResponseConvert(Mapper mapper, ReflectionProvider reflectionProvider, Class<T> clz) {
		super(mapper, reflectionProvider);
		this.clz = clz;
	}

	@Override
	public boolean canConvert(Class type) {
		return type.equals(BaseResponse.class);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		T t;
		try {
			t = this.clz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		final List<Field> fields = Arrays.asList(this.clz.getDeclaredFields());
		final Map<String, Field> fieldMap = fields.stream().distinct()
				.collect(Collectors.toMap(this::getFieldName, field -> field));
		t.setServiceName(reader.getAttribute("service"));
		reader.moveDown();
		final String headValue = reader.getValue();
		t.setHead(headValue);
		//移动Head标签
		reader.moveUp();
		reader.moveDown();
		if ("ERR".equals(headValue)) {
			// 下单失败
			final BaseResponse.Error error = new BaseResponse.Error();
			final String code = reader.getAttribute("code");
			error.setErrorCode(code);
			error.setErrorMessage(reader.getValue());
			t.setError(error);
			return t;
		}
		//下单成功继续解析Body标签中返回的订单信息
		//移动到Body标签
		reader.moveDown();
		//移动到Body标签下一层
		final int attributeCount = reader.getAttributeCount();
		for (int i = 0; i < attributeCount; i++) {
			String attributeName = reader.getAttributeName(i);
			Field field = fieldMap.get(attributeName);
			field.setAccessible(true);
			try {
				field.set(t, reader.getAttribute(i));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return t;
	}



	protected String getFieldName(Field field) {
		if (field.isAnnotationPresent(XStreamAlias.class)) {
			return field.getAnnotation(XStreamAlias.class).value();
		}
		return field.getName();
	}

}
