package com.github.pwittchen.playground.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
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

  @Test
  public void shouldInvokeTimingProxy() {
    final Map mapProxyInstance = createTimingProxyInstance();
    final CharSequence csProxyInstance = createCharSequenceProxyInstance();

    mapProxyInstance.put("hello", "world");
    csProxyInstance.length();

    // watch console logs to see outputs for this test
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

  private Map createTimingProxyInstance() {
    return (Map) Proxy.newProxyInstance(
        DynamicProxyTest.class.getClassLoader(), new Class[] {Map.class},
        new TimingDynamicInvocationHandler(new HashMap<>()));
  }

  private CharSequence createCharSequenceProxyInstance() {
    return (CharSequence) Proxy.newProxyInstance(
        DynamicProxyTest.class.getClassLoader(),
        new Class[] {CharSequence.class},
        new TimingDynamicInvocationHandler("Hello World"));
  }

  class DynamicInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) {
      System.out.println(String.format("Invoked method: %s", method.getName()));
      return 42;
    }
  }

  class TimingDynamicInvocationHandler implements InvocationHandler {

    private final Map<String, Method> methods = new HashMap<>();
    private Object target;

    public TimingDynamicInvocationHandler(final Object target) {
      this.target = target;

      for (final Method method : target.getClass().getDeclaredMethods()) {
        this.methods.put(method.getName(), method);
      }
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args)
        throws Throwable {
      final long start = System.nanoTime();
      final Object result = methods.get(method.getName()).invoke(target, args);
      final long elapsed = System.nanoTime() - start;
      System.out.println(
          String.format("Executing %s finished in %s ns", method.getName(), elapsed));
      return result;
    }
  }
}
