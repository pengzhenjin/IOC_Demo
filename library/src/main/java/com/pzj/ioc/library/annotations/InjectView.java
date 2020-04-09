package com.pzj.ioc.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: PengZhenjin
 * @Date: 2019/9/9 18:21
 * @Description: 控件的注解
 */
@Target(ElementType.FIELD) // 该注解作用在属性之上
@Retention(RetentionPolicy.RUNTIME) // JVM在运行时通过反射的技术，获取注解的值
public @interface InjectView {
  // 控件的ID（如：R.id.btn）
  int value();
}
