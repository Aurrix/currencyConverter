package lv.javaguru.currencyConverter.dao;

import lv.javaguru.currencyConverter.entities.Commissions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "commissions", collectionResourceRel = "commissions")
public interface CommissionsRepository extends CrudRepository<Commissions,Long> {
Commissions findByCurrencyPair(String CurrencyPair);
}
