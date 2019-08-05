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

package com.lotstock.eddid.common.log.aspect;

import com.lotstock.eddid.common.log.annotation.SysLogger;
import com.lotstock.eddid.common.log.event.SysLogEvent;
import com.lotstock.eddid.common.log.util.SysLogUtils;
import com.lotstock.eddid.upms.api.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 操作日志使用spring event异步入库
 *
 * @author L.cm
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class SysLogAspect {
	private final ApplicationEventPublisher publisher;

	@SneakyThrows
	@Around("@annotation(sysLogger)")
	public Object around(ProceedingJoinPoint point, SysLogger sysLogger) {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

		SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle(sysLogger.value());
		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Object obj = point.proceed();
		Long endTime = System.currentTimeMillis();
		logVo.setTime(endTime - startTime);
		publisher.publishEvent(new SysLogEvent(logVo));
		return obj;
	}
}
