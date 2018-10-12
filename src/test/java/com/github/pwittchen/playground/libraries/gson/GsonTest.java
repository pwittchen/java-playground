package com.github.pwittchen.playground.libraries.gson;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class GsonTest {

  private Gson gson;

  @Before
  public void setUp() {
    gson = new Gson();
  }

  @Test
  public void shouldCreateJson() {
    XempBigDecimal object = new XempBigDecimal(new m_Volume(0, new byte[] {4, 3, 102, -101, 40}));

    String json = encode(object);

    assertThat(json).isEqualTo(
        "{\"m_Volume\":{\"m_Exponent\":0,\"m_Mantissa\":[4,3,102,-101,40]}}"
    );
  }

  public String encode(Object object) {
    return gson.toJson(object);
  }

  public String encodeType(XempBigDecimal object) {
    return gson.toJson(object);
  }

  private class XempBigDecimal {
    public XempBigDecimal(GsonTest.m_Volume m_Volume) {
      this.m_Volume = m_Volume;
    }

    private m_Volume m_Volume;
  }

  private class m_Volume {
    public m_Volume(int m_Exponent, byte[] m_Mantissa) {
      this.m_Exponent = m_Exponent;
      this.m_Mantissa = m_Mantissa;
    }

    private int m_Exponent;
    private byte[] m_Mantissa;
  }
}
