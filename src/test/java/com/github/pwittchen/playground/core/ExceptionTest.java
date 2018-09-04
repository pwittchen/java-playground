package com.github.pwittchen.playground.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.google.common.truth.Truth.assertThat;

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

  private void throwException() {
    throw new RuntimeException(EXCEPTION_MESSAGE);
  }
}
