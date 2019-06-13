package com.yirdoc.sf.order.bean.order;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 货物类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("Cargo")
public class Cargo {

	/**
	 * 货物名称 String(60)
	 * 中文的话不超过20字
	 */
	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;
	/**
	 * 货物数量
	 */
	@XStreamAlias("count")
	@XStreamAsAttribute
	private int count;
	/**
	 * 货物单位
	 * 如：个、台、本
	 */
	@XStreamAlias("unit")
	@XStreamAsAttribute
	private String unit;
	/**
	 * 重量
	 */
	@XStreamAlias("weight")
	@XStreamAsAttribute
	private int weight;
	/**
	 * 货物单价
	 */
	@XStreamAlias("amount")
	@XStreamAsAttribute
	private float amount;
	/**
	 * 币种
	 * 缺省为CNY
	 */
	@XStreamAlias("currency")
	@XStreamAsAttribute
	private String currency;

	/**
	 * @param name 货物名称
	 */
	public Cargo(String name) {
		this.name = name;
	}

}