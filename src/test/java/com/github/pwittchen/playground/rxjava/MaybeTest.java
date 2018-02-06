package com.github.pwittchen.playground.rxjava;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

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

  private Maybe<Object> createSuccessfulMaybe() {
    return Maybe.create(emitter -> {
      emitter.onSuccess(new Object());
      emitter.onComplete();
    });
  }

  private Maybe<Object> createMaybeWithError() {
    return Maybe.create(emitter -> {
      emitter.onError(new RuntimeException("Error in Maybe!"));
      emitter.onComplete();
    });
  }
}
