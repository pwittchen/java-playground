package com.github.pwittchen.playground.vavr;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Exploratory Test Case case to verify work of Option type from Vavr
 * See this article: https://softwaremill.com/do-we-have-better-option-here/
 * And this one: https://dev.to/koenighotze/in-praise-of-vavrs-option
 * Also don't forget to check the docs: http://www.vavr.io/vavr-docs/#_option
 */
public class OptionTest {

	@Test
	public void shouldBeDefined() {
		// given
		final Option<Object> option = Option.of(new Object());

		// when
		final boolean defined = option.isDefined();

		// then
		assertThat(defined).isTrue();
	}

	@Test
	public void shouldNotBeDefined() {
		// given
		final Option<Object> option = Option.none();

		// when
		final boolean defined = option.isDefined();

		// then
		assertThat(defined).isFalse();
	}

	@Test
	public void shouldNotBeDefinedWhenCreatingFromNull() {
		// given
		final Option<Object> option = Option.of(null);

		// when
		final boolean defined = option.isDefined();

		// then
		assertThat(defined).isFalse();
	}

	@Test
	public void shouldConvertExceptionToOption() {

		// when
		final Object object = Try.of(this::getObject)
								 .toOption()
								 .getOrElse(new Object());

		// then
		assertThat(object).isNotNull();
		// and no exception is thrown here!
	}

	private Object getObject() {
		throw new NullPointerException("Surprise! There's no Object!");
	}
}
