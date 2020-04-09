package com.pzj.ioc.library;

import android.app.Activity;
import android.view.View;
import com.pzj.ioc.library.annotations.EventBase;
import com.pzj.ioc.library.annotations.InjectLayout;
import com.pzj.ioc.library.annotations.InjectView;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: PengZhenjin
 * @Date: 2019/9/9 17:45
 * @Description: 注解管理器
 */
public class InjectManager {

  /**
   * 注入方法
   */
  public static void inject(Activity activity) {
    // 布局的注入
    injectLayout(activity);

    // 控件的注入
    injectViews(activity);

    // 事件的注入
    injectEvents(activity);
  }

  /**
   * 布局的注入
   */
  private static void injectLayout(Activity activity) {
    try {
      Class<? extends Activity> clazz = activity.getClass(); // 获取类
      InjectLayout annotation = clazz.getAnnotation(InjectLayout.class); // 获取类之上的@InjectLayout注解
      if (annotation != null) {
        int layoutId = annotation.value(); // 获取@InjectLayout注解的值

        Method method = clazz.getMethod("setContentView", int.class); // 通过反射获取setContentView()方法
        method.invoke(activity, layoutId); // 执行setContentView()方法
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 控件的注入
   */
  private static void injectViews(Activity activity) {
    try {
      Class<? extends Activity> clazz = activity.getClass(); // 获取类
      Field[] fields = clazz.getDeclaredFields(); // 获取类的所有属性
      for (Field field : fields) { // 遍历并处理每个属性
        InjectView annotation = field.getAnnotation(InjectView.class); // 获取属性之上的@InjectView注解
        if (annotation != null) {
          int viewId = annotation.value(); // 获取@InjectView注解的值

          //Method method = clazz.getMethod("findViewById", int.class); // 通过反射获取findViewById()方法
          //Object view = method.invoke(activity, viewId); // 执行findViewById()方法

          View view = activity.findViewById(viewId); // 通过viewId获取view
          field.setAccessible(true); // 将私有属性设置为可以访问，如：用private关键字修饰的属性
          field.set(activity, view);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 事件的注入
   */
  private static void injectEvents(Activity activity) {
    try {
      Class<? extends Activity> clazz = activity.getClass(); // 获取类
      Method[] methods = clazz.getDeclaredMethods(); // 获取类中所有的方法
      for (Method method : methods) { // 遍历方法
        Annotation[] annotations = method.getAnnotations(); // 获取方法之上的注解，一个方法之上可以有多个注解，所以返回值是数组
        for (Annotation annotation : annotations) { // 遍历每个方法的每个注解
          Class<? extends Annotation> annotationType = annotation.annotationType(); // 获取注解的类型
          if (annotationType != null) {
            EventBase eventBase = annotationType.getAnnotation(EventBase.class); // 获取@EventBase注解
            if (eventBase != null) {
              String listenerSetter = eventBase.listenerSetter(); // 获取@EventBase注解的listenerSetter值
              Class<?> listenerType = eventBase.listenerType(); // 获取@EventBase注解的listenerType值
              String listenerCallBack = eventBase.listenerCallBack(); // 获取@EventBase注解的listenerCallBack值

              Method valueMethod = annotationType.getDeclaredMethod("value"); // 获取@OnClick或@OnLongClick注解中的value()方法；
              int[] viewIds = (int[]) valueMethod.invoke(annotation); // 执行@OnClick或@OnLongClick注解中的value()方法，并获取值

              EventInvocationHandler invocationHandler = new EventInvocationHandler(activity);
              invocationHandler.addMethod(listenerCallBack, method);

              // 用代理的方式，去匹配对应的监听方法和回调方法（即：外面是什么注解，就匹配对应的监听方法和回调方法）
              Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[] { listenerType }, invocationHandler);

              for (int viewId : viewIds) { // 遍历每个控件的id
                View view = activity.findViewById(viewId);// 通过viewId获取view
                Method setter =
                    view.getClass().getMethod(listenerSetter, listenerType); // 获取setOnClickListener()或setOnLongClickListener()方法
                setter.invoke(view, listener);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
