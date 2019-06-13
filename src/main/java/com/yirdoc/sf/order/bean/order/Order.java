package com.yirdoc.sf.order.bean.order;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 订单
 * <p>
 * 添加新字段需要加上{@link com.thoughtworks.xstream.annotations.XStreamAlias XStreamAlias}注解指定xml中对应的字段,
 * 如果是对应到xml中是`Attribute`类型,还需要加上{@link com.thoughtworks.xstream.annotations.XStreamAsAttribute}注解.
 * </p>
 * @see XStreamAlias
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("Order")
public class Order {

	/**
	 * 订单号
	 */
	@XStreamAlias("orderid")
	@XStreamAsAttribute
	private String orderId;

	/**
	 * 顺丰运单号,一个订单只能有一个母单号,如果是子母单的情况,以半角逗号分隔,
	 * 主单号在第一个位置,如 “755123456789,001123456789,00212 3456789”,
	 * 对于路由推送注册,此字段为必填
	 */
	@XStreamAlias("mailno")
	@XStreamAsAttribute
	private String mailNo;
	/**
	 * 是否要求返回顺丰运单号
	 * 1 - 要求
	 * is_gen_bill_no
	 */
	@XStreamAlias("is_gen_bill_no")
	@XStreamAsAttribute
	@Builder.Default
	private int isGenBillNo = 1;
	/**
	 * 寄件方公司名称
	 * j_company String(100)
	 */
	@XStreamAlias("j_company")
	@XStreamAsAttribute
	private String sendCompany;
	/**
	 * 寄件方联系人
	 * j_contact String(100)
	 */
	@XStreamAlias("j_contact")
	@XStreamAsAttribute
	private String sendContact;
	/**
	 * 寄件方电话
	 * j_tel String(20)
	 */
	@XStreamAlias("j_tel")
	@XStreamAsAttribute
	private String sendTel;
	/**
	 * 寄件方省份
	 * j_province
	 */
	@XStreamAlias("j_province")
	@XStreamAsAttribute
	private String sendProvince;
	/**
	 * 寄件方城市
	 * j_city
	 */
	@XStreamAlias("j_city")
	@XStreamAsAttribute
	private String sendCity;
	/**
	 * 寄件方县区
	 * j_county
	 */
	@XStreamAlias("j_county")
	@XStreamAsAttribute
	private String sendCounty;
	/**
	 * 寄件方详细地址
	 * j_address String(200)
	 */
	@XStreamAlias("j_address")
	@XStreamAsAttribute
	private String sendAddress;
	/**
	 * 收件方公司名称
	 * d_company String(100）
	 */
	@XStreamAlias("d_company")
	@XStreamAsAttribute
	private String receiverCompany;
	/**
	 * 收件方联系人
	 * d_contact String(100)
	 */
	@XStreamAlias("d_contact")
	@XStreamAsAttribute
	private String receiverContact;
	/**
	 * 收件方联系电话
	 * d_tel String(20)
	 */
	@XStreamAlias("d_tel")
	@XStreamAsAttribute
	private String receiverTel;
	/**
	 * 到件方所在省份,必须是标准的省名称称谓如:广东省,如果是直辖市, 请直接传北京、上海等。
	 * d_province String(30)
	 */
	@XStreamAlias("d_province")
	@XStreamAsAttribute
	private String receiverProvince;
	/**
	 * 到件方所在城市名称,必须是标准的 城市称谓如:深圳市,如果是直辖市,请直接传北京(或北京市)、上海(或上海市)等。
	 * d_city String(100)
	 */
	@XStreamAlias("d_city")
	@XStreamAsAttribute
	private String receiverCity;
	/**
	 * 收件方县区
	 * d_county
	 */
	@XStreamAlias("d_county")
	@XStreamAsAttribute
	private String receiverCounty;
	/**
	 * 到件方详细地址
	 */
	@XStreamAlias("d_address")
	@XStreamAsAttribute
	private String receiverAddress;
	/**
	 * 付款方式
	 * 1 - 寄件方付
	 * 2 - 收件方付
	 * 3 - 第三方付
	 */
	@XStreamAlias("pay_method")
	@XStreamAsAttribute
	private int payMethod;

	/**
	 * 顺丰月结卡号.
	 *
	 * pay_method="1"寄付有两种:
	 * 寄付月结：付款方式为：“1”   报文中传“月结卡号”；   面单打印   寄付月结；
	 * pay_method="1" custid="XXXXXXXXXXX"
	 * 寄付现结：付款方式为：“1”   报文中不传“月结卡号”    面单打印   寄付现结;
	 * pay_method="1" custid=""
	 *
	 * 到付：    付款方式为：“2”     报文中不传”月结卡号“     面单打印：到付；
	 *
	 * pay_method="2" custid=""
	 * 第三方付： 付款方式为：”3“    报文中传”月结卡号“    面单打印：”第三方付“;
	 */
	@XStreamAlias("custid")
	@XStreamAsAttribute
	private String custid;

	/**
	 * 业务类型.
	 * 1. 顺丰标快.
	 * 2. 顺丰特惠.
	 */
	@XStreamAlias("express_type")
	@XStreamAsAttribute
	private String expressType;

	/**
	 * 是否通过手持终端通知顺丰收派员上门收件.
	 * 下印字call, 收派员上门打印面单  is_docall='1'
	 */
	@XStreamAlias("is_docall")
	@XStreamAsAttribute
	@Builder.Default
	private int isDoCall = 1;


	/**
	 * 发送货物信息
	 */
	@XStreamAlias("Cargo")
	private List<Cargo> cargo;

}
