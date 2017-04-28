CREATE TABLE empresa(
  id  serial not NULL,
  nome character varying(200) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE reserva(
  id  serial not NULL,
  cliente_id integer not null,
  empresa_id bigint not null,
  FOREIGN KEY (empresa_id) REFERENCES empresa(id),
  PRIMARY KEY (id)
);
