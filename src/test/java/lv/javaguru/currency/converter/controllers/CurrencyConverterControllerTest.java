package lv.javaguru.currency.converter.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lv.javaguru.currency.converter.CurrencyConverterApp;
import lv.javaguru.currency.converter.entities.ConversionRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyConverterApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CurrencyConverterControllerTest {

  @Autowired
  private CurrencyConverterController currencyConverterController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(currencyConverterController).build();
  }

  @Test
  public void convertAmountIsToTrueTest() throws Exception {
    mockMvc
        .perform(
            post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    asJsonString(
                        new ConversionRequest(
                            "EUR",
                            "USD",
                            new BigDecimal(100.00).setScale(2, RoundingMode.HALF_EVEN),
                            false))))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.amountFrom", is(100.00)))
        .andExpect(jsonPath("$.amountTo").exists())
        .andExpect(jsonPath("$.curFrom", is("EUR")))
        .andExpect(jsonPath("$.curTo", is("USD")))
        .andExpect(jsonPath("$.commission").exists())
        .andExpect(jsonPath("$.amountCharged").exists())
        .andExpect(jsonPath("$.rate").exists())
        .andExpect(jsonPath("$.timeStamp").exists());
  }

  @Test
  public void convertAmountIsToFalseTest() throws Exception {
    mockMvc
        .perform(
            post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    asJsonString(
                        new ConversionRequest(
                            "EUR",
                            "USD",
                            new BigDecimal(100.00).setScale(2, RoundingMode.HALF_EVEN),
                            true))))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.amountFrom").exists())
        .andExpect(jsonPath("$.amountTo", is(100.00)))
        .andExpect(jsonPath("$.curFrom", is("EUR")))
        .andExpect(jsonPath("$.curTo", is("USD")))
        .andExpect(jsonPath("$.commission").exists())
        .andExpect(jsonPath("$.amountCharged").exists())
        .andExpect(jsonPath("$.rate").exists())
        .andExpect(jsonPath("$.timeStamp").exists());
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
