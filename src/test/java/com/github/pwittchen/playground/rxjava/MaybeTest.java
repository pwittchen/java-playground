package com.github.pwittchen.playground.rxjava;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Conclusions:
 * - Maybe should return success, error or it should just complete when it's empty
 * - Only ONE terminal state is allowed at the end of the flow
 * - When Maybe invokes onError, then it shouldn't invoke onComplete
 * - When Maybe invokes onSuccess, then it shouldn't invoke onComplete
 * - When Maybe invokes onComplete, then it shouldn't invoke onSuccess or onError
 */
public class MaybeTest {

  @Test
  public void shouldReturnValueFromMaybe() {
    // given
    Maybe<Object> maybe = createSuccessfulMaybe();

    // when
    Object object = maybe
        .subscribeOn(Schedulers.io())
        .blockingGet();

    // then
    assertThat(object).isNotNull();
  }

  @Test(expected = RuntimeException.class)
  public void shouldReturnErrorFromMaybe() {
    // given
    Maybe<Object> maybe = createMaybeWithError();

    // when
    maybe
        .subscribeOn(Schedulers.io())
        .blockingGet();

    // then throw an error
  }

  @Test
  public void shouldReturnEmptyMaybe() throws InterruptedException {
    // given
    Maybe<Object> maybe = createEmptyMaybe();
    final boolean[] isCompleted = {false};
    final boolean[] isSubscribed = {false};
    final boolean[] isSuccess = {false};
    final boolean[] isError = {false};

    // when
    maybe
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .subscribe(new MaybeObserver<Object>() {
          @Override public void onSubscribe(Disposable d) {
            System.out.println("subscribed");
            isSubscribed[0] = true;
          }

          @Override public void onSuccess(Object o) {
            System.out.println("success");
            isSuccess[0] = true;
          }

          @Override public void onError(Throwable e) {
            System.out.println("error");
            isError[0] = true;
          }

          @Override public void onComplete() {
            System.out.println("completed");
            isCompleted[0] = true;
          }
        });

    // let subscription finish
    Thread.sleep(3_000);

    // then
    assertThat(isSubscribed[0]).isTrue();
    assertThat(isCompleted[0]).isTrue();
    assertThat(isSuccess[0]).isFalse();
    assertThat(isError[0]).isFalse();
  }

  @Test
  public void shouldReturnMaybeJustOneValue() throws InterruptedException {
    // given
    final boolean[] isCompleted = {false};
    final boolean[] isSubscribed = {false};
    final boolean[] isSuccess = {false};
    final boolean[] isError = {false};

    // when
    Maybe
        .just(1)
        .observeOn(Schedulers.single())
        .subscribe(new MaybeObserver<Integer>() {
          @Override public void onSubscribe(Disposable d) {
            System.out.println("subscribed");
            isSubscribed[0] = true;
          }

          @Override public void onSuccess(Integer o) {
            System.out.println("success");
            isSuccess[0] = true;
          }

          @Override public void onError(Throwable e) {
            System.out.println("error");
            isError[0] = true;
          }

          @Override public void onComplete() {
            System.out.println("completed");
            isCompleted[0] = true;
          }
        });

    // let subscription finish
    Thread.sleep(3_000);

    // then
    assertThat(isSubscribed[0]).isTrue();
    assertThat(isCompleted[0]).isFalse();
    assertThat(isSuccess[0]).isTrue();
    assertThat(isError[0]).isFalse();
  }

  private Maybe<Object> createSuccessfulMaybe() {
    return Maybe.create(emitter -> {
      emitter.onSuccess(new Object());
    });
  }

  private Maybe<Object> createMaybeWithError() {
    return Maybe.create(emitter -> {
      emitter.onError(new RuntimeException("Error in Maybe!"));
    });
  }

  private Maybe<Object> createEmptyMaybe() {
    return Maybe.empty();
  }
}
