create table if not exists transactions (
    id uuid primary key,
    amount numeric(19, 2) not null,
    card_number varchar(255) not null,
    restaurant_code varchar(255) not null,
    date_time timestamp not null,
    created_at timestamp not null
);