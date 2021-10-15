package test.springboot.datajdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.springboot.datajdbc.data.CustomerAccount;
import test.springboot.datajdbc.repo.CustomerAccountRepository;
import test.springboot.datajdbc.repo.CustomerRepository;

@RestController
@RequestMapping("/customer/{id}/account")
public class CustomerAccountController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerAccountRepository customerAccountRepository;

    @GetMapping
    public Iterable<CustomerAccount> getAccounts(@PathVariable Long id) {
        return customerAccountRepository.findByCustomerAccountIdCustomerId(id);
    }

    @PostMapping
    public CustomerAccount addAccount(@PathVariable Long id, @RequestBody CustomerAccount account) {
        return customerRepository.findById(id).map(customer -> {
            account.getCustomerAccountId().setCustomerId(id);
            return customerAccountRepository.save(account);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
    }

}
