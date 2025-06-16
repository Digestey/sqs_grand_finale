create table if not exists book
(
    id    bigint       not null auto_increment,
    title varchar(255) not null default '',
    lent  boolean               default false,

    primary key (id)
)