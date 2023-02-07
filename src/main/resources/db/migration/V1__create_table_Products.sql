CREATE TABLE "products"
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50)   NOT NULL,
    price       DECIMAL(6, 2) NOT NULL,
    description VARCHAR(50),
    date        DATE          NOT NULL
);

CREATE TABLE users
(
    id              SERIAL PRIMARY KEY,
    user_name       VARCHAR(50) UNIQUE NOT NULL,
    hashed_password VARCHAR(150)       NOT NULL,
    salt            VARCHAR(50)        NOT NULL,
    role            VARCHAR(15)        NOT NULL
)