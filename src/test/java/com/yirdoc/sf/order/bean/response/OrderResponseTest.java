package com.yirdoc.sf.order.bean.response;

import org.testng.Assert;


public class OrderResponseTest {

    @org.junit.Test
    public void fromXml() {
        OrderResponse orderResponse = OrderResponse.fromXml("<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderService\"><Head>OK</Head><Body><OrderResponse filter_result=\"2\" destcode=\"532\" mailno=\"231246090090\" origincode=\"532\" orderid=\"20190426113105595\"><rls_info rls_errormsg=\"444070546918:\" invoke_result=\"OK\" rls_code=\"1000\"><rls_detail waybillNo=\"444070546918\" sourceTransferCode=\"532W\" sourceCityCode=\"532\" sourceDeptCode=\"532DC\" sourceTeamCode=\"027\" destCityCode=\"532\" destDeptCode=\"532\" destRouteLabel=\"532\" proName=\"标准快递\" cargoTypeCode=\"C201\" limitTypeCode=\"T4\" expressTypeCode=\"B1\" xbFlag=\"0\" printFlag=\"000000000\" twoDimensionCode=\"MMM={'k1':'','k2':'532','k3':'','k4':'T4','k5':'444070546918','k6':'A','k7':'6dbdfe90'}\" proCode=\"T4\" printIcon=\"00000000\" abFlag=\"A\"/></rls_info></OrderResponse></Body></Response>");
        Assert.assertEquals(orderResponse.getFilterResult(), "2");
        Assert.assertEquals(orderResponse.getOrderId(), "20190426113105595");
    }
}