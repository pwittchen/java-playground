package com.github.pwittchen.playground.core;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class StreamsTest {
  @Test
  public void shouldProduceMappedStreamWithDuplicatedValues() {

    // given
    final Stream<Integer> stream = Stream.of(1, 2, 3, 4, 4, 4, 5);
    final Map<Integer, String> map =
        ImmutableMap.of(
            1, "one",
            2, "two",
            3, "three",
            4, "four",
            5, "five");

    // when
    final List<String> list = stream
        .map(map::get)
        .collect(Collectors.toList());

    // then
    assertThat(list.size()).isEqualTo(7);
  }
}
