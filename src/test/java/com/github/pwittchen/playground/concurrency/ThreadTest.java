package com.github.pwittchen.playground.concurrency;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ThreadTest {

  @Test
  public void shouldCreateRegularThread() throws InterruptedException {

    final Thread thread = new Thread(() -> {
      System.out.println("executed: " + Thread.currentThread().toString());
      System.out.println("is daemon: " + Thread.currentThread().isDaemon());
    });

    thread.start();
    Thread.sleep(2000);

    assertThat(thread.isDaemon()).isFalse();
    assertThat(thread.isAlive()).isFalse();
  }

  @Test
  public void shouldCreateDaemonThread() throws InterruptedException {

    // note: daemon threads should be used for house-keeping tasks or tasks,
    // which can be interrupted without consequences - e.g. periodic checks, etc.
    // daemon tasks have low priority and JVM can kill them anytime
    // no matter if they're finished or not

    final Thread thread = new Thread(() -> {
      System.out.println("executed: " + Thread.currentThread().toString());
      System.out.println("is daemon: " + Thread.currentThread().isDaemon());
    });
    thread.setDaemon(true);
    thread.start();
    Thread.sleep(2000);

    assertThat(thread.isDaemon()).isTrue();
    assertThat(thread.isAlive()).isFalse();
  }

  @Test
  public void shouldCreateThreadLocal() {

    // note: ThreadLocal has value which is 'internal'
    // for a given thread and won't be shared among different threads

    final Thread thread = new Thread(() -> {
      final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);
      threadLocal.set(threadLocal.get() + 1);
      System.out.println(threadLocal.get());
    });

    thread.run();
    thread.run();

  }
}
