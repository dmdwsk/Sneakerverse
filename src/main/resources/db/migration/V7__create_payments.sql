create table payments (
                          id uuid primary key,
                          order_id uuid not null,
                          provider varchar(24) not null,
                          status varchar(24) not null,
                          amount numeric(19,2) not null,
                          currency varchar(8) not null,
                          provider_payment_id varchar(128),
                          checkout_url varchar(1000),
                          created_at timestamptz not null,
                          updated_at timestamptz not null,
                          constraint fk_payments_order foreign key (order_id) references orders(id)
);

create index ix_payments_order_id on payments(order_id);
create index ix_payments_provider_payment_id on payments(provider_payment_id);
create index ix_payments_status on payments(status);

create table payment_events (
                                id uuid primary key,
                                payment_id uuid not null,
                                type varchar(48) not null,
                                payload varchar(10000) not null,
                                created_at timestamptz not null,
                                constraint fk_payment_events_payment foreign key (payment_id) references payments(id)
);

create index ix_payment_events_payment_id on payment_events(payment_id);
create index ix_payment_events_created_at on payment_events(created_at);