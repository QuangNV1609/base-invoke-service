create table users
(
    id                bigserial
        constraint "PK_c62436add5ba01a5832c135ff25"
            primary key,
    department_code   varchar(50),
    hris_id           varchar(50),
    user_role         varchar(50),
    name              varchar(255),
    email             varchar(255)            not null,
    calculation_date  date      default now() not null,
    manager_name      varchar,
    manager_email     varchar,
    created_at        timestamp default now() not null,
    updated_at        timestamp default now() not null
);