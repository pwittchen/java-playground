package com.github.pwittchen.playground.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.google.common.truth.Truth.assertThat;

/**
 * In this test case I'm evaluating different methods of testing exceptions
 */
public class ExceptionTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void shouldTestExceptionWithTryCatch() {

    final Exception caughtException;

    try {
      throw new RuntimeException("test exception");
    } catch (final Exception e) {
      caughtException = e;
    }

    assertThat(caughtException).hasMessageThat().isEqualTo("test exception");
  }

  @Test(expected = RuntimeException.class)
  public void shouldTestExceptionWithExpectedAnnotation() throws RuntimeException {
    throw new RuntimeException("test exception");
  }

  @Test
  public void shouldTestExceptionWithRuleAnnotation() throws RuntimeException {

    expectedException.expect(RuntimeException.class);
    expectedException.expectMessage("test exception");

    throw new RuntimeException("test exception");
  }
}
