package com.pzj.ioc.library.annotations;

import android.view.View;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: PengZhenjin
 * @Date: 2019/9/9 21:13
 * @Description: 点击事件注解
 */
@Target(ElementType.METHOD) // 该注解作用在方法之上
@Retention(RetentionPolicy.RUNTIME) // JVM在运行时通过反射的技术，获取注解的值
@EventBase(listenerSetter = "setOnClickListener", listenerType = View.OnClickListener.class, listenerCallBack = "onClick")
public @interface OnClick {
  // 点击事件的ID，可以有多个ID，所以用数组
  int[] value();
}
