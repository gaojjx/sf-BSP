package com.yirdoc.sf.order.bean.response;

import com.yirdoc.sf.order.bean.order.WayBillRoute;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WayBillRouteRequestTest {

    @Test
    public void fromXml() {
        String xml = "<?xml version='1.0' encoding='UTF-8'?><Request service=\"RoutePushService\" lang=\"zh-CN\"><Body><WaybillRoute id=\"45173393184\" mailno=\"231627640143\" orderid=\"20190513114214867\" acceptTime=\"2019-05-14 07:09:04\" acceptAddress=\"潍坊市\" remark=\"快件到达 【潍坊市奎文区清溪街营业点】\" opCode=\"31\"/></Body></Request>";
        WayBillRouteRequest wayBillRouteRequest = WayBillRouteRequest.fromXml(xml);
        WayBillRouteRequest.Body body = wayBillRouteRequest.getBody();
        List<WayBillRoute> wayBillRoutes = body.getWayBillRoute();
        WayBillRoute wayBillRoute = wayBillRoutes.get(0);
        Assert.assertEquals("45173393184", wayBillRoute.getId());
        Assert.assertEquals("231627640143", wayBillRoute.getMailNo());
        Assert.assertEquals("20190513114214867", wayBillRoute.getOrderId());
    }
}