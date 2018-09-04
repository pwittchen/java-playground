package com.github.pwittchen.playground.libraries.vavr;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;
import static org.junit.Assert.fail;

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
  public void shouldConsumeOptionForEach() {
    // given
    final Option<Object> option = Option.of(new Object());

    // when
    option.forEach(object -> {
      // then
      if (option.isDefined()) {
        assertThat(object).isNotNull();
      } else {
        fail();
      }
    });
  }

  @Test
  public void shouldConsumeOptionPeek() {
    // given
    final Option<Object> option = Option.of(new Object());

    // when, then
    option
        .peek(object -> assertThat(object).isNotNull())
        .onEmpty(Assert::fail);
  }

  @Test
  public void shouldConsumeOptionWithGetOrElse() {
    // given
    final Option<Object> option = Option.of(new Object());

    // when
    final Object returnedValue = option.getOrElse(() -> StringUtils.EMPTY);

    // then
    assertThat(returnedValue.toString()).contains("@");
  }

  @Test
  public void shouldConsumeOptionWithGetOrElseEmpty() {
    // given
    final Option<Object> option = Option.none();

    // when
    final Object returnedValue = option.getOrElse(() -> StringUtils.EMPTY);

    // then
    assertThat(returnedValue.toString()).isEmpty();
  }

  @Test
  public void shouldProcessCollectionOfOptions() {
    // given
    final List<Option<Object>> options = Arrays.asList(
        Option.of(new Object()),
        Option.none(),
        Option.of(new Object())
    );

    // when
    final List<Object> returnedResult = Stream.ofAll(options)
        .flatMap(Option::toStream)
        .collect(Collectors.toList());

    // then
    assertThat(returnedResult.size()).isEqualTo(2);
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

  @Test
  public void shouldPerformPatternMatching() {
    // given
    final Option<String> option = Option.of("my string");

    // when
    final String result = Match(option).of(
        Case($Some($()), String::toUpperCase),
        Case($None(), () -> StringUtils.EMPTY)
    );

    // then
    assertThat(result).isEqualTo("MY STRING");
  }

  @Test
  public void shouldPerformPatternMatchingForNone() {
    // given
    final Option<String> option = Option.none();

    // when
    final String result = Match(option).of(
        Case($Some($()), String::toUpperCase),
        Case($None(), () -> StringUtils.EMPTY)
    );

    // then
    assertThat(result).isEmpty();
  }
}
