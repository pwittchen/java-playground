package com.github.pwittchen.playground.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Read more at: http://www.baeldung.com/java-dynamic-proxies
 */
public class DynamicProxyTest {

  @Test
  public void shouldInvokePutMethodViaProxy() {

    // given
    final Map proxyInstance = createProxyInstance();

    // when
    final Integer returnedValue = (Integer) proxyInstance.put("hello", "world");

    // then
    assertThat(returnedValue).isEqualTo(42);
  }

  @Test
  public void shouldInvokeGetFromProxyViaLambda() {

    // given
    final Map proxyInstance = createLambdaProxyInstance();

    // when
    final Integer returnedValue = (Integer) proxyInstance.get("hello");

    // then
    assertThat(returnedValue).isEqualTo(42);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldInvokePutFromProxyViaLambdaAndThrowException() {

    // given
    final Map proxyInstance = createLambdaProxyInstance();

    // when
    proxyInstance.put("hello", "world");

    // then an exception is thrown
  }

  private Map createProxyInstance() {
    return (Map) Proxy.newProxyInstance(
        DynamicProxyTest.class.getClassLoader(),
        new Class[] {Map.class},
        new DynamicInvocationHandler()
    );
  }

  private Map createLambdaProxyInstance() {
    return (Map) Proxy.newProxyInstance(
        DynamicProxyTest.class.getClassLoader(),
        new Class[] {Map.class},
        (proxy, method, methodArgs) -> {
          if (method.getName().equals("get")) {
            System.out.println(String.format("Invoked method: %s", method.getName()));
            return 42;
          } else {
            throw new UnsupportedOperationException("Unsupported method: " + method.getName());
          }
        });
  }

  class DynamicInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) {
      System.out.println(String.format("Invoked method: %s", method.getName()));
      return 42;
    }
  }
}
