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

package org.springframework.cloud.openfeign;

import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author River
 * <p>
 * 默认 Fallback，避免写过多fallback类
 */
@AllArgsConstructor
public class MiddlewareFallbackFactory<T> implements FallbackFactory<T> {
	private final Target<T> target;

	@Override
	@SuppressWarnings("unchecked")
	public T create(Throwable cause) {
		final Class<T> targetType = target.type();
		final String targetName = target.name();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetType);
		enhancer.setUseCache(true);
		enhancer.setCallback(new MiddlewareFeignFallback<>(targetType, targetName, cause));
		return (T) enhancer.create();
	}
}
