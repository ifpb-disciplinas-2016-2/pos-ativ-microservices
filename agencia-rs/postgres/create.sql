CREATE TABLE reserva(
  id  serial not NULL,
  cliente_id integer not null,
  reserva_hotel_id integer not null,
  reserva_passagem_id integer not null,
  PRIMARY KEY (id)
);
