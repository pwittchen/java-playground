package com.github.pwittchen.playground;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/**
 * Exploratory Test Case case to verify work of Optional from Java 8, 9 and Guava, Option type from Vavr and Null Object Pattern
 */
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
	public void shouldReturnAnotherValue() {
		// given
		final Optional<Object> optional = Optional.empty();

		// when
		final Object returnedValue = optional.orElse(StringUtils.EMPTY);

		// then
		assertThat(returnedValue).isInstanceOf(String.class);
	}

	@Test
	public void shouldNotReturnAnotherValue() {
		// given
		final Optional<Object> optional = Optional.of(new Object());

		// when
		final Object returnedValue = optional.orElse(StringUtils.EMPTY);

		// then
		assertThat(returnedValue).isNotNull();
		assertThat(returnedValue).isNotInstanceOf(String.class);
	}

	@Test
	public void shouldBePresentViaIfPresentOrElse() {
		// given
		final Optional<Object> optional = Optional.of(new Object());

		// when then (available since Java 9)
		optional.ifPresentOrElse(object -> assertThat(object).isNotNull(), Assert::fail);
	}

	@Test
	public void shouldNotBePresentViaIfPresentOrElse() {
		// given
		final Optional<Object> optional = Optional.empty();

		// when then (available since Java 9)
		optional.ifPresentOrElse(object -> fail(), () -> assertThat(optional.isPresent()).isFalse());
	}

	@Test
	public void shouldVerifyNullObjectPatternFromApacheCommons() {
		// when
		final ObjectUtils.Null nullObject = ObjectUtils.NULL;

		// then
		assertThat(nullObject).isInstanceOf(ObjectUtils.NULL.getClass());
	}

	@Test
	public void shouldReturnDefaultIfNull() {
		// given
		final Object object = new Object();

		// when
		final Object returnedValue = ObjectUtils.defaultIfNull(null, object);

		// then
		assertThat(returnedValue).isEqualTo(object);
	}

	@Test
	public void shouldConvertObjectToStringWhenItIsNotEmpty() {
		// given
		final Optional<Object> optional = Optional.of(new Object());

		// when
		final String value = optional.map(o -> o.toString())
									 .orElse(StringUtils.EMPTY);

		// then
		assertThat(value).contains("@");
	}

	@Test
	public void shouldReturnEmptyStringWhenObjectIsEmpty() {
		// given
		final Optional<Object> optional = Optional.empty();

		// when
		final String value = optional.map(o -> o.toString())
									 .orElse(StringUtils.EMPTY);

		// then
		assertThat(value).isEmpty();
	}

	@Test
	public void shouldReturnEmptyStringWhenObjectIsEmptyWithMethodReference() {
		// given
		final Optional<Object> optional = Optional.empty();

		// when
		final String value = optional.map(Object::toString)
									 .orElse(StringUtils.EMPTY);

		// then
		assertThat(value).isEmpty();
	}

	//TODO #0 add more tests with Optional
	//TODO #2 add test with Guava
	//TODO #3 add test with Vavr
}
