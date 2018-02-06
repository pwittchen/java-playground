package com.github.pwittchen.playground;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/**
 * Exploratory Test Case case to verify work of Optional from Java 8 and 9
 */
public class OptionalTest {

  @Test
  public void shouldBePresent() {
    // given
    final Optional<Object> optional = Optional.of(new Object());

    // when
    final boolean isPresent = optional.isPresent();

    // then
    if (optional.isPresent()) {
      assertThat(optional.get()).isNotNull();
    }

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

  @Test(expected = NoSuchElementException.class)
  public void shouldThrowNoSuchElementException() {
    // when
    final Optional<Object> optional = Optional.empty();

    // then
    optional.get();
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

  /**
   * testing feature #1 of Java 9 -> case #1
   */
  @Test
  public void shouldBePresentViaIfPresentOrElse() {
    // given
    final Optional<Object> optional = Optional.of(new Object());

    // when then (available since Java 9)
    optional.ifPresentOrElse(object -> assertThat(object).isNotNull(), Assert::fail);
  }

  /**
   * testing feature #1 of Java 9 -> case #2
   */
  @Test
  public void shouldNotBePresentViaIfPresentOrElse() {
    // given
    final Optional<Object> optional = Optional.empty();

    // when then (available since Java 9)
    optional.ifPresentOrElse(object -> fail(), () -> assertThat(optional.isPresent()).isFalse());
  }

  /**
   * testing feature #2 of Java 9 -> case #1
   */
  @Test
  public void shouldNotBePresentViaOr() {
    // given
    final Optional<Object> optional = Optional.empty();

    // when then (available since Java 9)
    final Optional<Object> returnedOptionalValue = optional.or(() -> Optional.of(new Object()));

    // then
    assertThat(returnedOptionalValue.isPresent()).isTrue();
    assertThat(returnedOptionalValue.get()).isNotNull();
  }

  /**
   * testing feature #3 of Java 9 -> case #1
   */
  @Test
  public void shouldProcessCollectionOfOptionals() {
    // given
    final List<Optional<Object>> optionals =
        Arrays.asList(
            Optional.of(new Object()),
            Optional.empty(),
            Optional.of(new Object())
        );

    // when
    final List<Object> outPutList = optionals
        .stream()
        .flatMap(Optional::stream)
        .collect(Collectors.toList());

    // then
    assertThat(outPutList.size()).isEqualTo(2);
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
    final String value = optional
        .map(o -> o.toString())
        .orElse(StringUtils.EMPTY);

    // then
    assertThat(value).isEmpty();
  }

  @Test
  public void shouldReturnEmptyStringWhenObjectIsEmptyWithMethodReference() {
    // given
    final Optional<Object> optional = Optional.empty();

    // when
    final String value = optional
        .map(Object::toString)
        .orElse(StringUtils.EMPTY);

    // then
    assertThat(value.toString()).isEmpty();
  }

  @Test
  public void shouldBePresentFromNullable() {
    // given
    final Optional optional = Optional.ofNullable(new Object());

    // when
    final boolean present = optional.isPresent();

    // then
    assertThat(present).isTrue();
  }

  @Test
  public void shouldBeEmptyFromNullable() {
    // given
    final Optional optional = Optional.ofNullable(null);

    // when
    final boolean present = optional.isPresent();

    // then
    assertThat(present).isFalse();
  }
}
