create table if not exists reward_accounts (
    id uuid primary key,
    card_number varchar(255) not null unique,
    balance numeric(19, 2) not null,
    processed_transactions integer not null,
    last_transaction_at timestamp,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists reward_operations (
    id uuid primary key,
    transaction_id uuid not null,
    card_number varchar(255) not null,
    restaurant_code varchar(255) not null,
    amount numeric(19, 2) not null,
    reward_amount numeric(19, 2) not null,
    status varchar(50) not null,
    message varchar(255) not null,
    processed_at timestamp not null
);