-- V3__group_chat_messages.sql

-- If you don't need the old table created by V2:
drop table if exists chat_messages cascade;

create table if not exists group_chat_messages
(
    id              bigserial primary key,
    group_id        varchar(64) not null references groups (id) on delete cascade,
    sender_user_id  varchar(64) not null references app_users (id),
    sender_username varchar(64) not null,
    content         text        not null,
    sent_at         timestamptz not null default now()
);

create index if not exists idx_group_chat_messages_group_time
    on group_chat_messages (group_id, sent_at desc);

create index if not exists idx_group_chat_messages_sender_time
    on group_chat_messages (sender_user_id, sent_at desc);