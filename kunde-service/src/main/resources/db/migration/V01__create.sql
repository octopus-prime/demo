create table if not exists adresse
(
    id         uuid not null
        constraint adresse_pkey
            primary key,
    hausnummer varchar(255),
    plz        varchar(255),
    strasse    varchar(255),
    wohnort    varchar(255)
);

alter table adresse
    owner to username;

create table kunde
(
    id                  uuid not null
        constraint kunde_pkey
            primary key,
    nachname            varchar(255),
    vorname             varchar(255),
    lieferadresse_id    uuid
        constraint fk6svreqyglic3ske2mxitq0qw1
            references adresse,
    rechnungsadresse_id uuid
        constraint fka8rcueu13ix0myjf6dxx0ygyt
            references adresse
);

alter table kunde
    owner to username;
