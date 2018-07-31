package com.github.pwittchen.playground.concurrency;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ThreadTest {

  @Test
  public void shouldCreateRegularThread() throws InterruptedException {

    Thread thread = new Thread(() -> {
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
    DaemonThread thread = new DaemonThread();
    thread.run();
    Thread.sleep(2000);

    assertThat(thread.isDaemon()).isTrue();
    assertThat(thread.isAlive()).isFalse();
  }

  private class DaemonThread extends Thread {

    public DaemonThread() {
      setDaemon(true);
    }

    @Override public void run() {
      super.run();
      System.out.println("executed: " + Thread.currentThread().toString());
      System.out.println("is daemon: " + isDaemon());
    }
  }
}
