create table addresses (
                           id uuid primary key,
                           user_id uuid not null,
                           type varchar(16) not null,
                           country varchar(64) not null,
                           city varchar(64) not null,
                           postal_code varchar(16),
                           street varchar(128) not null,
                           building varchar(32) not null,
                           apartment varchar(32),
                           is_default boolean not null,
                           created_at timestamptz not null,
                           updated_at timestamptz not null,
                           constraint fk_addresses_user foreign key (user_id) references users(id)
);

create index ix_addresses_user_id on addresses(user_id);
create index ix_addresses_user_type on addresses(user_id, type);