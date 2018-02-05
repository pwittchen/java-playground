package com.github.pwittchen.playground;

import org.junit.Test;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

public class OptionalTest {

	@Test
	public void shouldBePresent() {
		// given
		final Optional<Object> optional = Optional.of(new Object());

		// when
		final boolean isPresent = optional.isPresent();

		// then
		assertThat(isPresent).isTrue();
	}

	@Test
	public void shouldBeEmpty() {
		// given
		final Optional<Object> optional = Optional.empty();

		// when
		final boolean isPresent = optional.isPresent();

		// then
		assertThat(isPresent).isFalse();
	}
}
