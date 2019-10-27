package lv.javaguru.currency.converter.dao.repositories;

import lv.javaguru.currency.converter.entities.ConversionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ConversionResponseRepository extends JpaRepository<ConversionResponse, Long> {

}
