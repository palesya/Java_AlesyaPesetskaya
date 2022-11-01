create table if not exists roles
(
    id   bigint generated by default as identity
        primary key,
    name varchar(255)
);

create table if not exists dogs
(
    id          bigint generated by default as identity
        primary key,
    age         integer,
    base64image oid,
    name        varchar(255),
    sex         integer,
    type        varchar(255)
);

create table if not exists addresses
(
    id                  bigint generated by default as identity
        primary key,
    city                varchar(255),
    house_number_nearby integer,
    street              varchar(255)
);

create table if not exists users
(
    id          bigint generated by default as identity
        primary key,
    age         integer,
    base64image oid,
    created     timestamp,
    is_deleted  boolean not null,
    login       varchar(255),
    password    varchar(255),
    updated     timestamp,
    version     bigint  not null,
    dog_id      bigint,
    constraint fk_dog
        FOREIGN KEY (dog_id)
            references dogs (id)
);

create table if not exists users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    constraint fk_user
        FOREIGN KEY (role_id)
            references roles (id)
);

create table if not exists places
(
    address_id                 bigint not null ,
    base64image        oid,
    fence              boolean not null,
    training_eqyipment boolean not null,
    transport_stop     varchar(255),
    primary key (address_id)
);

create table if not exists appointments
(
    id             bigint generated by default as identity
        primary key,
    date           date,
    time           time,
    description    varchar(255),
    number_of_people integer

);

create table if not exists user_appointment
(
    user_id bigint not null,
    appointment_id bigint not null,
    constraint fk_user
        FOREIGN KEY (appointment_id)
            references appointments (id)
);

create table if not exists place_appointment
(
    place_id bigint not null,
    appointment_id bigint not null,
    constraint fk_place
        FOREIGN KEY (appointment_id)
            references appointments (id)
);


