package lv.javaguru.currency.converter.dao.repositories;

import lv.javaguru.currency.converter.entities.Commissions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "commissions", collectionResourceRel = "commissions")
public interface CommissionsRepository extends CrudRepository<Commissions, Long> {

  Commissions findByCurrencyPair(String currencyPair);
}
