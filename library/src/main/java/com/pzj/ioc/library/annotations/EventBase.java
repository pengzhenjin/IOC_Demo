package com.pzj.ioc.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: PengZhenjin
 * @Date: 2019/9/9 21:06
 * @Description: 事件注解基类
 */
@Target(ElementType.ANNOTATION_TYPE) // 该注解作用在另外一个注解之上
@Retention(RetentionPolicy.RUNTIME) // JVM在运行时通过反射的技术，获取注解的值
public @interface EventBase {
  // 设置监听的方法，如：setOnClickListener()方法；
  String listenerSetter();

  // 监听对象，如：new View.OnClickListener() {}
  Class<?> listenerType();

  // 监听回调方法，如：public void onClick(View view) {}
  String listenerCallBack();
}
