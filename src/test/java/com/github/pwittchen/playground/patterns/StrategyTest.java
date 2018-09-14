package com.github.pwittchen.playground.patterns;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class StrategyTest {
  @Test
  public void shouldUseRectangleStrategy() {
    // given
    final Figure figure = new Rectangle(2, 4);

    // when
    final int area = figure.calculateArea();

    // then
    assertThat(area).isEqualTo(8);
  }

  @Test
  public void shouldUseSquareStrategy() {
    // given
    final Figure figure = new Square(2);

    // when
    final int area = figure.calculateArea();

    // then
    assertThat(area).isEqualTo(4);
  }

  private interface Figure {
    int calculateArea();
  }

  class Rectangle implements Figure {
    private final int edgeOne;
    private final int edgeTwo;

    Rectangle(final int edgeOne, final int edgeTwo) {
      this.edgeOne = edgeOne;
      this.edgeTwo = edgeTwo;
    }

    @Override public int calculateArea() {
      return edgeOne * edgeTwo;
    }
  }

  class Square implements Figure {
    private final int edgeOne;

    Square(final int edgeOne) {
      this.edgeOne = edgeOne;
    }

    @Override public int calculateArea() {
      return edgeOne * edgeOne;
    }
  }
}
