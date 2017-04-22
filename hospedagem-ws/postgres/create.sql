CREATE TABLE hotel(
  id  serial not NULL,
  nome character varying(200) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE reserva(
  id  serial not NULL,
  cliente_id integer not null,
  hotel_id bigint not null,
  FOREIGN KEY (hotel_id) REFERENCES hotel(id),
  PRIMARY KEY (id)
);
