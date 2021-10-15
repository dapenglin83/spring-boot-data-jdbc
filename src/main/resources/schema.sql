CREATE TABLE IF NOT EXISTS customer (
    id IDENTITY PRIMARY KEY,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    version INT
);

CREATE TABLE IF NOT EXISTS transaction (
    id IDENTITY PRIMARY KEY,
    amount DOUBLE,
    account VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS customer_account (
    id IDENTITY PRIMARY KEY,
    customer_id BIGINT,
    account VARCHAR(32),
    type VARCHAR(10),
    FOREIGN KEY (customer_id) references customer(id)
);