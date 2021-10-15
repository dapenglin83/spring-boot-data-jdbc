package test.springboot.datajdbc.repo;

import org.springframework.data.repository.CrudRepository;
import test.springboot.datajdbc.data.CustomerAccount;

public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Long> {
    Iterable<CustomerAccount> findByCustomerAccountIdCustomerId(Long id);
}
