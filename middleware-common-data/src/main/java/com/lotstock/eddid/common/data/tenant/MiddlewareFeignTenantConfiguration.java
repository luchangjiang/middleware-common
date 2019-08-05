/*
 *    Copyright (c) 2018-2025, River All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the newtype.io developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: River (20207075@qq.com)
 */

package com.lotstock.eddid.common.data.tenant;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author River
 * @date 2018/9/14
 * feign 租户信息拦截
 */
@Configuration
public class MiddlewareFeignTenantConfiguration {
	@Bean
	public RequestInterceptor middlewareFeignTenantInterceptor() {
		return new MiddlewareFeignTenantInterceptor();
	}
}
