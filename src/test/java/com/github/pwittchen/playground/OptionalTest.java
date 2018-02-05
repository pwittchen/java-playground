package com.github.pwittchen.playground;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

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

	@Test
	public void shouldBePresentViaIfPresent() {
		// given
		final Optional<Object> optional = Optional.of(new Object());

		// when, then
		optional.ifPresent(object -> assertThat(object).isNotNull());
	}

	@Test
	public void shouldBePresentViaIfPresentOrElse() {
		// given
		final Optional<Object> optional = Optional.of(new Object());

		// when then
		optional.ifPresentOrElse(object -> assertThat(object).isNotNull(), Assert::fail);
	}

	@Test
	public void shouldNotBePresentViaIfPresentOrElse() {
		// given
		final Optional<Object> optional = Optional.empty();

		// when then
		optional.ifPresentOrElse(object -> fail(), () -> assertThat(optional.isPresent()).isFalse());
	}

	//TODO #0 add more tests with Optional
	//TODO #1 add test with map
	//TODO #2 add test with Guava
	//TODO #3 add test with Vavr
}
