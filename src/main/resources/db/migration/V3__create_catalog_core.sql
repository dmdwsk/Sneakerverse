create table brands (
                        id uuid primary key,
                        name varchar(80) not null,
                        created_at timestamptz not null,
                        constraint uk_brands_name unique (name)
);

create table products (
                          id uuid primary key,
                          brand_id uuid not null,
                          name varchar(160) not null,
                          slug varchar(200) not null,
                          description varchar(5000),
                          gender varchar(16) not null,
                          base_price numeric(19,2) not null,
                          status varchar(16) not null,
                          created_at timestamptz not null,
                          updated_at timestamptz not null,
                          constraint fk_products_brand foreign key (brand_id) references brands(id),
                          constraint uk_products_slug unique (slug)
);

create index ix_products_brand_id on products(brand_id);
create index ix_products_status on products(status);
create index ix_products_slug on products(slug);

create table product_images (
                                id uuid primary key,
                                product_id uuid not null,
                                url varchar(1000) not null,
                                sort_order int not null,
                                is_main boolean not null,
                                created_at timestamptz not null,
                                constraint fk_product_images_product foreign key (product_id) references products(id)
);

create index ix_product_images_product_id on product_images(product_id);
create index ix_product_images_product_sort on product_images(product_id, sort_order);