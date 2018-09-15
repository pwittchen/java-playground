package com.github.pwittchen.playground.core;

import io.reactivex.Completable;
import io.vavr.control.Try;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.google.common.truth.Truth.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * In this test case I'm evaluating different methods of testing exceptions
 */
public class ExceptionTest {

  private static final String EXCEPTION_MESSAGE = "test exception";

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void shouldTestExceptionWithTryCatch() {
    Exception caughtException = null;

    try {
      throwException();
    } catch (final Exception e) {
      caughtException = e;
    }

    assertThat(caughtException).hasMessageThat().isEqualTo(EXCEPTION_MESSAGE);
  }

  @Test(expected = RuntimeException.class)
  public void shouldTestExceptionWithExpectedAnnotation() throws RuntimeException {
    throwException();
  }

  @Test
  public void shouldTestExceptionWithRuleAnnotation() throws RuntimeException {
    expectedException.expect(RuntimeException.class);
    expectedException.expectMessage(EXCEPTION_MESSAGE);
    throwException();
  }

  @Test
  public void shouldTestExceptionWithAssertJ() throws RuntimeException {
    Throwable thrown = catchThrowable(this::throwException);

    org.assertj.core.api.Assertions
        .assertThat(thrown)
        .isInstanceOf(RuntimeException.class)
        .hasMessage(EXCEPTION_MESSAGE);
  }

  @Test
  public void shouldTestExceptionWithTryCatchAndVavr() {

    final Object object =
        Try
            .of(this::throwExceptionObject)
            .toOption()
            .getOrElse(new ErrorObject());

    assertThat(object).isInstanceOf(ErrorObject.class);
  }

  @Test
  public void shouldTestExceptionWithRxJava() {
    final Throwable throwable = toCompletable(this::throwException).blockingGet();
    assertThat(throwable).isInstanceOf(RuntimeException.class);
    assertThat(throwable.getMessage()).isEqualTo(EXCEPTION_MESSAGE);
  }

  private void throwException() {
    throw new RuntimeException(EXCEPTION_MESSAGE);
  }

  private Object throwExceptionObject() {
    throw new RuntimeException(EXCEPTION_MESSAGE);
  }

  private Completable toCompletable(final Runnable runnable) {
    return Completable.create(emitter -> {
      try {
        runnable.run();
        emitter.onComplete();
      } catch (final Exception e) {
        emitter.onError(e);
      }
    });
  }

  private class ErrorObject {
  }
}
