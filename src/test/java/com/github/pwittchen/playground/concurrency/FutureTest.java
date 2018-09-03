package com.github.pwittchen.playground.concurrency;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class FutureTest {

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  @Test(expected = TimeoutException.class)
  public void shouldThrowTimeoutException()
      throws ExecutionException, InterruptedException, TimeoutException {
    final Future<String> future = createFuture();
    final String futureValue = future.get(1, TimeUnit.SECONDS);

    assertThat(future.isDone()).isTrue();
    assertThat(futureValue).isEqualTo("futureValue");
  }

  @Test
  public void shouldGetFutureValue()
      throws ExecutionException, InterruptedException {
    final Future<String> future = createFuture();

    final String futureValue = future.get();

    assertThat(future.isDone()).isTrue();
    assertThat(futureValue).isEqualTo("futureValue");
  }

  @Test
  public void shouldGetFutureValueWithProgressMonitor()
      throws ExecutionException, InterruptedException {
    final Future<String> future = createFuture();

    int counter = 0;

    while (!future.isDone()) {
      counter++;
      System.out.println("calculating #".concat(String.valueOf(counter)));
      Thread.sleep(1_000);
    }

    final String futureValue = future.get();
    System.out.println("calculated value: ".concat(futureValue));

    assertThat(future.isDone()).isTrue();
    assertThat(future.isCancelled()).isFalse();
    assertThat(futureValue).isEqualTo("futureValue");
  }

  @Test
  public void shouldCancelFuture() {
    final Future<String> future = createFuture();

    future.cancel(true);

    assertThat(future.isDone()).isTrue();
    assertThat(future.isCancelled()).isTrue();
  }

  @Test(expected = CancellationException.class)
  public void shouldThrowCancellationException() throws ExecutionException, InterruptedException {
    final Future<String> future = createFuture();

    future.cancel(true);
    future.get();
  }

  private Future<String> createFuture() {
    return executorService.submit(() -> {
      Thread.sleep(3_000);
      return "futureValue";
    });
  }
}
