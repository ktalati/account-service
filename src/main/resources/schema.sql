DROP TABLE IF EXISTS "ACCOUNT";
DROP TABLE IF EXISTS "ACCOUNT_TYPE";
DROP TABLE IF EXISTS "CUSTOMER";

CREATE TABLE ACCOUNT
(
    "ID"              DECIMAL(19, 2) NOT NULL,
    "ACCOUNT_TYPE_ID" DECIMAL(19, 2),
    "CUSTOMER_ID"     DECIMAL(19, 2)
);


CREATE TABLE ACCOUNT_TYPE
(
    "ID"           DECIMAL(19, 2) NOT NULL,
    "ACCOUNT_TYPE" VARCHAR(255)
);


CREATE TABLE CUSTOMER
(
    "ID"          DECIMAL(19, 2) NOT NULL,
    "FIRST_NAME"  VARCHAR(255),
    "LAST_NAME"   VARCHAR(255),
    "MIDDLE_NAME" VARCHAR(255)
);