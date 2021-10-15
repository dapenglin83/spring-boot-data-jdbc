package test.springboot.datajdbc.repo;

import org.springframework.data.repository.CrudRepository;
import test.springboot.datajdbc.data.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
