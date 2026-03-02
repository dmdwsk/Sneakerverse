create table carts (
                       id uuid primary key,
                       user_id uuid,
                       status varchar(16) not null,
                       created_at timestamptz not null,
                       updated_at timestamptz not null,
                       constraint fk_carts_user foreign key (user_id) references users(id)
);

create index ix_carts_user_id on carts(user_id);
create index ix_carts_status on carts(status);

create table cart_items (
                            id uuid primary key,
                            cart_id uuid not null,
                            variant_id uuid not null,
                            quantity int not null,
                            price_at_time numeric(19,2) not null,
                            created_at timestamptz not null,
                            updated_at timestamptz not null,
                            constraint fk_cart_items_cart foreign key (cart_id) references carts(id),
                            constraint fk_cart_items_variant foreign key (variant_id) references product_variants(id),
                            constraint uk_cart_items_cart_variant unique (cart_id, variant_id)
);

create index ix_cart_items_cart_id on cart_items(cart_id);
create index ix_cart_items_variant_id on cart_items(variant_id);