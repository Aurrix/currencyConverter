package lv.javaguru.currency.converter.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatesMap {

  @JsonProperty
  private Map<String, Double> rates;
}
