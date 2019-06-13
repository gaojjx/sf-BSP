package com.yirdoc.sf.order.service;

import com.yirdoc.sf.order.bean.order.Order;
import com.yirdoc.sf.order.bean.response.OrderResponse;
import com.yirdoc.sf.order.config.SFConfig;

/**
 * 顺丰接口方法能力声明
 * Created by terry on 15-7-22.
 */
public interface ISFMethod {

    SFConfig getConfig();

    void setConfig(SFConfig config);

    /**
     * 下单
     * @param order 订单内容
     * @return 下单结果
     */
    OrderResponse order(Order order);

//    /**
//     * 订单查询
//     * @param type 查询类型 1 - 运单号查询 2 - 订单号查询
//     * @param mailno 订单号
//     * @return 查询结果
//     */
//    String query(int type, String mailno);
//
//    /**
//     * 订单查询
//     * @param mailno 订单号
//     * @return 查询结果
//     */
//    String query(String mailno);

    /**
     * 路由推送接口.
     * @param xmlMessage 接收到的xml信息
     * @return xml格式的响应result
     */
    String routePush(String xmlMessage);
}