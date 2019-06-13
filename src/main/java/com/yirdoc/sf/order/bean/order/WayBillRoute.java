package com.yirdoc.sf.order.bean.order;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.io.Serializable;

@Data
@XStreamAlias("WaybillRoute")
public class WayBillRoute implements Serializable {
	@XStreamAlias("id")
	@XStreamAsAttribute
	private String id;
	@XStreamAlias("mailno")
	@XStreamAsAttribute
	private String mailNo;
	@XStreamAlias("orderid")
	@XStreamAsAttribute
	private String orderId;
	@XStreamAlias("acceptTime")
	@XStreamAsAttribute
	private String acceptTime;
	@XStreamAlias("acceptAddress")
	@XStreamAsAttribute
	private String acceptAddress;
	@XStreamAlias("remark")
	@XStreamAsAttribute
	private String remark;
	@XStreamAlias("opCode")
	@XStreamAsAttribute
	private String opCode;
}
