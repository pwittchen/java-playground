package com.github.pwittchen.playground.rxjava;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class MapTest {

  @Test
  public void shouldExecuteSwitchMapOperator() throws InterruptedException {

    // please note the difference from flatMap operator
    // 'internal' stream is being cancelled stops emitting values
    // when next value from 'external' stream enters the stream

    Flowable
        .intervalRange(1, 10, 0, 1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .switchMap(tick -> Flowable.intervalRange(20, 10, 0, 1, TimeUnit.SECONDS))
        .subscribe(System.out::println);

    Thread.sleep(10_000);
  }

  @Test
  public void shouldExecuteFlatMapOperator() throws InterruptedException {

    // please note the difference from switchMap operator
    // 'internal' stream is NOT being cancelled and keeps emitting values

    Flowable
        .intervalRange(1, 10, 0, 1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .flatMap(tick -> Flowable.intervalRange(20, 10, 0, 1, TimeUnit.SECONDS))
        .subscribe(System.out::println);

    Thread.sleep(10_000);
  }
}
