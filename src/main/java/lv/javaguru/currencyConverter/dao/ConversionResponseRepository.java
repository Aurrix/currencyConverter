package lv.javaguru.currencyConverter.dao;

import lv.javaguru.currencyConverter.entities.ConversionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface ConversionResponseRepository extends JpaRepository<ConversionResponse,Long> {

}
