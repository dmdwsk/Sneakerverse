create table product_variants (
                                  id uuid primary key,
                                  product_id uuid not null,
                                  sku varchar(64) not null,
                                  size varchar(16) not null,
                                  color varchar(40) not null,
                                  price numeric(19,2),
                                  status varchar(20) not null,
                                  created_at timestamptz not null,
                                  updated_at timestamptz not null,
                                  constraint fk_product_variants_product foreign key (product_id) references products(id),
                                  constraint uk_product_variants_sku unique (sku)
);

create index ix_product_variants_product_id on product_variants(product_id);
create index ix_product_variants_status on product_variants(status);

create table inventory (
                           variant_id uuid primary key,
                           quantity_available int not null,
                           updated_at timestamptz not null,
                           constraint fk_inventory_variant foreign key (variant_id) references product_variants(id)
);