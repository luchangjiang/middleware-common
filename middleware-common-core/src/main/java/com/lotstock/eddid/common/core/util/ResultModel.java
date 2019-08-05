/*
 *
 *      Copyright (c) 2018-2025, River All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the newtype.io developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: River (20207075@qq.com)
 *
 */

package com.lotstock.eddid.common.core.util;

import com.lotstock.eddid.common.core.constant.CommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author River
 */
@Builder
@ToString
@Accessors(chain = true)
@ApiModel(description = "响应信息主体")
public class ResultModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
	private int code = 0;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回信息")
	private String message = "success";

	@Getter
	@Setter
	@ApiModelProperty(value = "数据")
	private Object result;

	public ResultModel() {
		super();
	}

	public ResultModel(Object result) {
		super();
		this.result = result;
	}

	public ResultModel(Object result, String msg) {
		super();
		this.result = result;
		this.message = message;
	}

	public ResultModel(Throwable e) {
		super();
		this.message = e.getMessage();
		this.code = CommonConstants.FAIL;
	}

	public ResultModel(int code, String msg) {
		this(code, msg, null);
	}

	public ResultModel(int code, String msg, Object result) {
		this.code = code;
		if (msg != null) {
			this.message = msg;
		}
		this.result = result;
	}

	public static ResultModel ok(Object result) {
		return new ResultModel(CommonConstants.SUCCESS, "common.request.success", result);
	}

	public static ResultModel fail(int code, String msg) {
		return new ResultModel(code, msg, null);
	}

	public static ResultModel fail(String msg) {
		return fail(CommonConstants.FAIL, msg);
	}

	public boolean isSuccess() {
		return code == 0;
	}

	@Override
	public String toString() {
		return "R{" +
				"code=" + code +
				", msg='" + message + '\'' +
				", result=" + result +
				'}';
	}
}
