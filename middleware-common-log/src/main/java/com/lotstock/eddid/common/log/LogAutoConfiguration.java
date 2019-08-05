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

package com.lotstock.eddid.common.log;

import com.lotstock.eddid.common.log.aspect.SysLogAspect;
import com.lotstock.eddid.common.log.event.SysLogListener;
import com.lotstock.eddid.upms.api.feign.RemoteLogService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author River
 * @date 2018/6/28
 * <p>
 * 日志自动配置
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration {
	private final RemoteLogService remoteLogService;

	@Bean
	public SysLogListener sysLogListener() {
		return new SysLogListener(remoteLogService);
	}

	@Bean
	public SysLogAspect sysLogAspect(ApplicationEventPublisher publisher) {
		return new SysLogAspect(publisher);
	}
}
