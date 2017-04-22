CREATE TABLE cliente(
  id  serial not NULL,
  nome character varying(80) NOT NULL,
  cpf character varying(14) NOT NULL,
  renda decimal(15,2),
  PRIMARY KEY (id)
)
