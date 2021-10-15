package test.springboot.datajdbc.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "CUSTOMER_ACCOUNT")
public class CustomerAccount {

    @Id
    Long id;

    @Embedded.Nullable
    CustomerAccountId customerAccountId;
    AccountType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerAccountId getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(CustomerAccountId customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
