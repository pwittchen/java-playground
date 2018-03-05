package com.github.pwittchen.playground.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import org.junit.Test;

/**
 * Read more at: http://www.baeldung.com/java-dynamic-proxies
 */
public class DynamicProxyTest {

  @Test
  public void shouldInvokePutMethodViaProxy() {

    final Map proxyInstance = (Map) Proxy.newProxyInstance(
        DynamicProxyTest.class.getClassLoader(),
        new Class[] {Map.class},
        new DynamicInvocationHandler()
    );

    proxyInstance.put("hello", "world");
  }

  @Test
  public void shouldInvokeProxyViaLambda() {
    final Map proxyInstance = (Map) Proxy.newProxyInstance(
        DynamicProxyTest.class.getClassLoader(),
        new Class[] {Map.class},
        (proxy, method, methodArgs) -> {
          if (method.getName().equals("get")) {
            System.out.println(String.format("Invoked method: %s", method.getName()));
            return 42;
          } else {
            throw new UnsupportedOperationException(
                "Unsupported method: " + method.getName());
          }
        });

    proxyInstance.get("hello");
  }

  public class DynamicInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) {
      System.out.println(String.format("Invoked method: %s", method.getName()));
      return 42;
    }
  }
}
