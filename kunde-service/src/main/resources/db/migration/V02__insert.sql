insert into adresse (id, strasse, hausnummer, plz, wohnort) values (
    '531e4cdd-bb78-4769-a0c7-cb948a9f1238',
    'Musterstrasse', '17a',
    '12345', 'Musterstadt'
);

insert into kunde (id, nachname, vorname, lieferadresse_id, rechnungsadresse_id) values (
    'bf73ce21-f91b-4619-8891-1b4b471db3fd',
    'Mustermann', 'Max',
    '531e4cdd-bb78-4769-a0c7-cb948a9f1238',
    '531e4cdd-bb78-4769-a0c7-cb948a9f1238'
);
