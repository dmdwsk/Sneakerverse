create table roles (
                       id uuid primary key,
                       name varchar(32) not null unique
);

create table user_roles (
                            id uuid primary key,
                            user_id uuid not null,
                            role_id uuid not null,
                            created_at timestamptz not null,
                            constraint fk_user_roles_user foreign key (user_id) references users(id),
                            constraint fk_user_roles_role foreign key (role_id) references roles(id),
                            constraint uk_user_roles_user_role unique (user_id, role_id)
);

create index ix_user_roles_user_id on user_roles(user_id);
create index ix_user_roles_role_id on user_roles(role_id);

-- seed
insert into roles (id, name) values (gen_random_uuid(), 'CUSTOMER');
insert into roles (id, name) values (gen_random_uuid(), 'ADMIN');