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

package com.lotstock.eddid.common.core.constant;

/**
 * @author river
 * @date 2017/10/29
 */
public interface CommonConstants {
	/**
	 * header 中租户ID
	 */
	String TENANT_ID = "TENANT_ID";
	/**
	 * 删除
	 */
	String STATUS_DEL = "1";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "middleware-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "middleware";

	/**
	 * 验证码前缀
	 */
	String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

	/**
	 * 公共参数
	 */
	String MIDDLEWARE_PUBLIC_PARAM_KEY = "MIDDLEWARE_PUBLIC_PARAM_KEY";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 200;
	/**
	 * 失败标记
	 */
	Integer FAIL = 500;

	/**
	 * 默认存储bucket
	 */
	String BUCKET_NAME = "eddid";

	/**
	 * eddid customer id
	 */
	String EDDID_CUSTOMER_ID = "eddid_customer_id";

	/**
	 * 字符
	 */
	String DEFAULT_CHARSET = "UTF-8";

	String CONFIG_PREFIX = "gateway";
}
