package test.springboot.datajdbc.repo;

import org.springframework.data.repository.CrudRepository;
import test.springboot.datajdbc.data.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
