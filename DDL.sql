DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS board CASCADE;
DROP TABLE IF EXISTS element CASCADE;

CREATE TABLE account
(
    id       uuid primary key,
    login    varchar(255),
    email varchar(26),
    password varchar(255)
);


CREATE TABLE board
(
    id uuid primary key,
    creator_id uuid references account(id)
);

CREATE TABLE element
(
    id uuid primary key,
    creator_id uuid references account(id),
    board_id uuid references board(id),
    type varchar(255),
    size double precision,
    value text,
    color varchar(255)
);