-- 3) Make email required
alter table app_users
    alter column email set not null;

-- 4) Ensure unique (simple unique index)
drop index if exists ux_app_users_email;

create unique index ux_app_users_email
    on app_users(email);