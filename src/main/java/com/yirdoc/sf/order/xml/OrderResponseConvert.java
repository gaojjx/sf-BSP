package com.yirdoc.sf.order.xml;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;
import com.yirdoc.sf.order.bean.response.BaseResponse;
import com.yirdoc.sf.order.bean.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class OrderResponseConvert extends BaseResponseConvert<OrderResponse> {

    public OrderResponseConvert(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider, OrderResponse.class);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        OrderResponse orderResponse = (OrderResponse) super.unmarshal(reader, context);
        if (orderResponse.getError() != null) {
            return orderResponse;
        }
        try {
            reader.moveDown();
            //移动到OrderResponse下一层rls_info
            OrderResponse.RlsInfo rlsInfo = new OrderResponse.RlsInfo();
            setValue(reader, rlsInfo);
            orderResponse.setRlsInfo(rlsInfo);
            reader.moveDown();
            //移动到rls_info下一层rls_detail
            OrderResponse.RlsDetail rlsDetail = new OrderResponse.RlsDetail();
            setValue(reader, rlsDetail);
            rlsInfo.setRlsDetail(rlsDetail);
        } catch (Exception e) {
            log.error("下单返回的响应数据解析出错: \n{}", orderResponse.toString());
            e.printStackTrace();
        }
        return orderResponse;
    }

    private void setValue(HierarchicalStreamReader reader, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Field> fieldMap = Arrays.stream(fields).distinct()
                .collect(Collectors.toMap(super::getFieldName, field -> field));
        int rlsDetailAttributeCount = reader.getAttributeCount();
        for (int i = 0; i < rlsDetailAttributeCount; i++) {
            Field field = fieldMap.get(reader.getAttributeName(i));
            field.setAccessible(true);
            try {
                field.set(obj, reader.getAttribute(i));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(BaseResponse.class);
    }
}
