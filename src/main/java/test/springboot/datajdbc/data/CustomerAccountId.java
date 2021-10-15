package test.springboot.datajdbc.data;

import org.springframework.data.relational.core.mapping.Column;

public class CustomerAccountId {

    @Column("CUSTOMER_ID")
    Long customerId;
    String account;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
