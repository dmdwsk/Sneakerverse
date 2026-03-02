create table orders (
                        id uuid primary key,
                        order_number varchar(32) not null,
                        user_id uuid not null,
                        status varchar(24) not null,
                        currency varchar(8) not null,
                        total_amount numeric(19,2) not null,
                        shipping_address_snapshot varchar(4000) not null,
                        created_at timestamptz not null,
                        updated_at timestamptz not null,
                        constraint fk_orders_user foreign key (user_id) references users(id),
                        constraint uk_orders_order_number unique (order_number)
);

create index ix_orders_user_id on orders(user_id);
create index ix_orders_status on orders(status);
create index ix_orders_created_at on orders(created_at);

create table order_items (
                             id uuid primary key,
                             order_id uuid not null,
                             variant_id uuid,
                             product_name_snapshot varchar(200) not null,
                             size_snapshot varchar(16) not null,
                             color_snapshot varchar(40) not null,
                             unit_price numeric(19,2) not null,
                             quantity int not null,
                             line_total numeric(19,2) not null,
                             constraint fk_order_items_order foreign key (order_id) references orders(id),
                             constraint fk_order_items_variant foreign key (variant_id) references product_variants(id)
);

create index ix_order_items_order_id on order_items(order_id);
create index ix_order_items_variant_id on order_items(variant_id);