/* Clients. */
CREATE SEQUENCE test.client_id_sequence START WITH 1;

CREATE TABLE test.clients (
  client_id   bigint   NOT NULL   DEFAULT nextval('client_id_sequence')   PRIMARY KEY,
  full_name   text     NOT NULL,
  address     text     NOT NULL,
  birthday    date     NOT NULL
);


/* Accounts. */
CREATE SEQUENCE test.account_id_sequence START WITH 1;

CREATE TABLE test.accounts (
  account_id      bigint        NOT NULL   DEFAULT nextval('account_id_sequence')   PRIMARY KEY,
  name            text          NOT NULL,
  creation_time   timestamptz   NOT NULL,
  amount          numeric       NOT NULL,
  currency        text          NOT NULL,
  client_id       bigint        NOT NULL,
  FOREIGN KEY (client_id) REFERENCES test.clients (client_id)
);


/* Operations. */
CREATE SEQUENCE test.operation_id_sequence START WITH 1;

CREATE DOMAIN test.operation_type AS TEXT
CHECK (VALUE IN ('PAYMENT', 'RECEIPT', 'TRANSFER'));

CREATE TABLE test.operations (
  operation_id        bigint                NOT NULL   DEFAULT nextval('operation_id_sequence')   PRIMARY KEY,
  operation_type      test.operation_type   NOT NULL,
  time                timestamptz           NOT NULL,
  amount              numeric               NOT NULL,
  currency            text                  NOT NULL,
  source_account_id   bigint,
  target_account_id   bigint,
  FOREIGN KEY (source_account_id) REFERENCES test.accounts (account_id),
  FOREIGN KEY (target_account_id) REFERENCES test.accounts (account_id)
);
