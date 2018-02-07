package com.github.pwittchen.playground.guava;

import com.google.common.base.Optional;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class GuavaOptionalTest {

  @Test
  public void shouldBePresent() {
    // given
    final Optional<Object> optional = Optional.of(new Object());

    // when
    final boolean isPresent = optional.isPresent();

    // then
    if (optional.isPresent()) {
      Object notNull = optional.get();
    }

    assertThat(isPresent).isTrue();
  }

  @Test
  public void shouldBeAbsent() {
    // given
    final Optional<Object> optional = Optional.absent();

    // when
    final boolean isPresent = optional.isPresent();

    // then
    assertThat(isPresent).isFalse();
  }

  @Test
  public void shouldBePresentNullable() {
    // given
    final Optional<Object> optional = Optional.fromNullable(new Object());

    // when
    final boolean isPresent = optional.isPresent();

    // then
    if (optional.isPresent()) {
      Object notNull = optional.get();
    }

    assertThat(isPresent).isTrue();
  }

  @Test
  public void shouldBeAbsentNullable() {
    // given
    final Optional<Object> optional = Optional.fromNullable(null);

    // when
    final boolean isPresent = optional.isPresent();

    // then
    assertThat(isPresent).isFalse();
  }
}
