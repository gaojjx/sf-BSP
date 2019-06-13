package com.yirdoc.sf.order.bean.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@XStreamAlias("Response")
public class BaseResponse<T extends BaseResponse> implements Serializable {
	@XStreamAlias("service")
	@XStreamAsAttribute
	private String serviceName;
	@XStreamAlias("lang")
	@XStreamAsAttribute
	private String language;
	@XStreamAlias("Head")
	private String head;
	@XStreamAlias("Body")
	private String body;
	@XStreamAlias("Error")
	private Error error;
	@XStreamAlias("serviceResponse")
	private T serviceResponse;

	@Data
	@NoArgsConstructor
	public static class Error {
		@XStreamAlias("code")
		private String errorCode;
		private String errorMessage;
	}
}
