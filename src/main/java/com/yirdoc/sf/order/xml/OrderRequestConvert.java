package com.yirdoc.sf.order.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import com.yirdoc.sf.order.bean.order.Cargo;
import com.yirdoc.sf.order.bean.order.Order;
import com.yirdoc.sf.order.bean.request.OrderRequest;

import javax.xml.bind.annotation.XmlAttribute;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OrderRequestConvert extends AbstractReflectionConverter {
    public OrderRequestConvert(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    @Override
    protected void doMarshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        final OrderRequest orderRequest = (OrderRequest) source;
        writer.addAttribute("service", "OrderService");
        writer.addAttribute("lang", "zh-CN");
        writer.startNode("Head");
        writer.setValue(orderRequest.getApiCode());
        writer.endNode();
        writer.startNode("Body");
        writer.startNode("Order");
        super.doMarshal(source, writer, context);
        final Order order = orderRequest.getBody().getOrder();
        Field[] orderFields = Order.class.getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>();
        Stream.of(orderFields)
                .filter(field -> field.isAnnotationPresent(XmlAttribute.class))
                .forEach(field -> {
                    // 字段名称
                    final String value = field.getAnnotation(XStreamAlias.class).value();
                    try {
                        // 属性值
                        writer.addAttribute(value, field.get(order).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        List<Cargo> cargoList = order.getCargo();
        for (Cargo cargo : cargoList) {
            Field[] cargoFields = cargo.getClass().getDeclaredFields();
            Stream.of(cargoFields);
        }

//                .collect(Collectors.toMap(field -> field.getAnnotation(XStreamAlias.class).value(), field -> field));
    }

    private String getFieldName(Field field) {
        String value = field.getAnnotation(XStreamAlias.class).value();
        return value;
    }

    @Override
    public boolean canConvert(Class type) {
        return OrderRequest.class.equals(type);
    }
}
