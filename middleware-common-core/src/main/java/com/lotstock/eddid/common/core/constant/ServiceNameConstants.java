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
 * @author River
 * @date 2018年06月22日16:41:01
 * 服务名称
 */
public interface ServiceNameConstants {
	/**
	 * 认证中心
	 */
	String AUTH_SERVICE = "middleware-auth";

	/**
	 * UMPS模块
	 */
	String UMPS_SERVICE = "middleware-upms-biz";

	/**
	 * 分布式事务协调服务
	 */
	String TX_MANAGER = "middleware-tx-manager";

	/**
	 * 客户服务
	 */
	String EDDID_FH = "eddid-fh";

	/**
	 * 客户服务
	 */
	String EDDID_CUSTOMER = "eddid-customer";

	/**
	 * 服务配置
	 */
	String EDDID_PCINFO = "eddid-pcinfo";

	/**
	 * 订阅服务
	 */
	String EDDID_SUBSCRIBE = "eddid-subscribe";

	/**
	 * 消息推送
	 */
	String EDDID_PUSH = "eddid-push";

	/**
	 * 股票配置服务
	 */
	String EDDID_STOCKCONFIG = "eddid-stockconfig";

	/**
	 * ayers行情转码
	 */
	String EDDID_GXSOCKET = "eddid-gxsocket";
}
