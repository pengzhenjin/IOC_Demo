package com.pzj.ioc.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: PengZhenjin
 * @Date: 2019/9/9 17:55
 * @Description: 注入布局的注解
 */
@Target(ElementType.TYPE) // 该注解作用在类之上
@Retention(RetentionPolicy.RUNTIME) // JVM在运行时通过反射的技术，获取注解的值
public @interface InjectLayout {
  // 布局文件的ID（如：R.layout.activity_main）
  int value();
}
