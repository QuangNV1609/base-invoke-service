CREATE TABLE users
(
    id                      BIGSERIAL PRIMARY KEY,
    username                VARCHAR(50)             NOT NULL UNIQUE,
    password                VARCHAR(100)            NOT NULL,
    full_name               VARCHAR(100)            NOT NULL,
    phone                   VARCHAR(15),
    account_non_expired     BOOLEAN                 NOT NULL,
    account_non_locked      BOOLEAN                 NOT NULL,
    credentials_non_expired BOOLEAN                 NOT NULL,
    enabled                 BOOLEAN                 NOT NULL,
    status                  VARCHAR(255), -- Enum kiểu chuỗi
    role                    VARCHAR(20)             NOT NULL,
    created_at              timestamp default now() not null,
    updated_at              timestamp default now() not null,
    created_by              VARCHAR(50),
    updated_by              VARCHAR(50)
);