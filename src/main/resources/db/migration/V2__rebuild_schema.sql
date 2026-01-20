-- V2__rebuild_schema.sql
-- WARNING: this drops the old V1 table. Use only if you don't need its data.

drop table if exists test cascade;

-- ===== AUTH =====
create table if not exists app_users
(
    id            varchar(64) primary key,
    username      varchar(64)  not null unique,
    password_hash varchar(255) not null,
    email         varchar(255),
    display_name  varchar(256),
    avatar_url    varchar(256),
    created_at    timestamptz  not null default now()
);

create unique index if not exists ux_app_users_email
    on app_users (email)
    where email is not null;

create table if not exists refresh_tokens
(
    id         bigserial primary key,
    user_id    varchar(64)  not null references app_users (id) on delete cascade,
    token_hash varchar(255) not null unique,
    expires_at timestamptz  not null,
    revoked    boolean      not null default false,
    created_at timestamptz  not null default now()
);

create index if not exists idx_refresh_tokens_user_id
    on refresh_tokens (user_id);

create index if not exists idx_refresh_tokens_expires_at
    on refresh_tokens (expires_at);

-- ===== GROUPS =====
create table if not exists groups
(
    id         varchar(64) primary key,
    name       varchar(255) not null,
    created_by varchar(64)  not null references app_users (id),
    created_at timestamptz  not null default now()
);

create index if not exists idx_groups_created_by
    on groups (created_by);

create table if not exists group_members
(
    id        bigserial primary key,
    group_id  varchar(64) not null references groups (id) on delete cascade,
    user_id   varchar(64) not null references app_users (id) on delete cascade,
    role      varchar(32) not null default 'MEMBER',
    joined_at timestamptz not null default now(),
    unique (group_id, user_id)
);

create index if not exists idx_group_members_group_id
    on group_members (group_id);

create index if not exists idx_group_members_user_id
    on group_members (user_id);

-- ===== CHAT =====
create table if not exists chat_messages
(
    id             bigserial primary key,
    group_id       varchar(64) not null references groups (id) on delete cascade,
    sender_user_id varchar(64) not null references app_users (id),
    content        text        not null,
    sent_at        timestamptz not null default now()
);

create index if not exists idx_chat_messages_group_time
    on chat_messages (group_id, sent_at desc);

create index if not exists idx_chat_messages_sender_time
    on chat_messages (sender_user_id, sent_at desc);