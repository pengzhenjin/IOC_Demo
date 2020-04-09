package com.pzj.ioc.library;

import android.util.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Author: PengZhenjin
 * @Date: 2019/9/9 21:57
 * @Description:
 */
public class EventInvocationHandler implements InvocationHandler {

  private final static long QUICK_EVENT_TIME_SPAN = 300; // 毫秒
  private long lastClickTime;

  // 拦截对象，如：MainActivity中本应该执行的事件的onClick;
  private Object target;

  // 存储方法的HashMap，key：onClick； value：实际调用的方法（即：开发者自定义的方法）
  private HashMap<String, Method> methodHashMap = new HashMap<>();

  public EventInvocationHandler(Object target) {
    this.target = target;
  }

  @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (target != null) {
      String methodName = method.getName();
      method = methodHashMap.get(methodName); // 如果集合中有需要执行的方法，就替换

      long timeSpan = System.currentTimeMillis() - lastClickTime;
      if (timeSpan < QUICK_EVENT_TIME_SPAN) {
        Log.e("点击阻塞，防止误点", String.valueOf(timeSpan));
        return null;
      }
      lastClickTime = System.currentTimeMillis();

      if (method != null) {
        if (method.getGenericParameterTypes().length == 0) {
          return method.invoke(target);
        }
        return method.invoke(target, args);
      }
    }
    return null;
  }

  /**
   * 拦截的方法，和替换执行的方法
   *
   * @param methodName 拦截本应该执行的onClick回调方法
   * @param method 执行自定义的方法
   */
  public void addMethod(String methodName, Method method) {
    methodHashMap.put(methodName, method);
  }
}
