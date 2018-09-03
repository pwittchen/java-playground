package com.github.pwittchen.playground.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class ExecutorServiceTest {

  @Test
  public void shouldRunThreadWithSingleThreadExecutor() {
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
  }

  @Test
  public void shouldRunThreadsWithFixedThreadPool() {
    final ExecutorService executorService = Executors.newFixedThreadPool(4);
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
  }

  @Test
  public void shouldRunThreadsWithCachedThreadPool() {
    final ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
  }

  @Test
  public void shouldRunThreadsWithStealingThreadPool() {
    // it creates ForkJoinPool
    final ExecutorService executorService = Executors.newWorkStealingPool();
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
  }

  @Test
  public void shouldRunThreadsWithSingleThreadExecutor() {
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
  }

  @Test
  public void shouldRunThreadsWithScheduledThreadPool() {
    final ExecutorService executorService = Executors.newScheduledThreadPool(2);
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
  }

  @Test
  public void shouldSubmitMultipleTasksToExecutor() {
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      System.out.println("task 1: ".concat(Thread.currentThread().getName()));
    });
    executorService.submit(() -> {
      System.out.println("task 2: ".concat(Thread.currentThread().getName()));
    });
  }

  @Test
  public void shouldShutDownExecutor() {
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      System.out.println(Thread.currentThread().getName());
      System.out.println("task started");
      try {
        Thread.sleep(3000);
        System.out.println("task completed");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    try {
      System.out.println("attempting to shutdown executor");
      executorService.shutdown();
      executorService.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("tasks interrupted");
    } finally {
      if (!executorService.isTerminated()) {
        System.out.println("cancelling not finished tasks");
        executorService.shutdownNow();
      } else {
        System.out.println("executor service is terminated");
      }

      System.out.println("shutdown completed");
    }
  }
}
