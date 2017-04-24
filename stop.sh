docker-compose down
docker rmi -f natarajan/db-cliente
docker rmi -f natarajan/cliente
docker volume remove ativ2microservices_postgres-volume-cliente
docker rmi -f natarajan/db-hospedagem
docker rmi -f natarajan/hospedagem
docker volume remove ativ2microservices_postgres-volume-hospedagem
