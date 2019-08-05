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

package com.lotstock.eddid.common.log.event;

import com.lotstock.eddid.upms.api.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author River
 * 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {
	private final SysLog sysLog;
}
