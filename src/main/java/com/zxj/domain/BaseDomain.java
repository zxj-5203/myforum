package com.zxj.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

// 实现Serializable接口，以便JVM可以序列化OP实例；
public class BaseDomain implements Serializable {

	// 统一的toString方法
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
