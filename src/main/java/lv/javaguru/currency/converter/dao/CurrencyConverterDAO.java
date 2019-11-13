package lv.javaguru.currency.converter.dao;

import lv.javaguru.currency.converter.entities.ConversionRequest;
import lv.javaguru.currency.converter.entities.ConversionResponse;

public interface CurrencyConverterDAO {

  ConversionResponse convert(ConversionRequest request);

}
