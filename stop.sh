# PARANDO CONTAINERS
docker-compose down

# REMOVENDO IMAGENS
docker rmi -f natarajan/db-cliente
docker rmi -f natarajan/cliente

docker rmi -f natarajan/db-hospedagem
docker rmi -f natarajan/hospedagem

docker rmi -f natarajan/db-passagem
docker rmi -f natarajan/passagem

docker rmi -f natarajan/db-agencia
docker rmi -f natarajan/agencia


# REMOVENDO VOLUMES
docker volume remove ativ2microservicesrs_postgres-volume-cliente
docker volume remove ativ2microservicesrs_postgres-volume-hospedagem
docker volume remove ativ2microservicesrs_postgres-volume-passagem
docker volume remove ativ2microservicesrs_postgres-volume-agencia
