### Comandos úteis

1. Listar containers iniciados

  `docker ps`
  
2. Visualizar os logs de um container em específico em um comando:

  `docker logs -f $(docker ps -q -f name=NOME_DO_CONTAINER)`

  A opção `docker logs -f` permite a tela ficar presa do fluxo do log acompanhando assim que outras informações forem adiconadas. Se fornecermos apenas `docker logs`, o docker vai apenas mostrar o último *status* to log e voltar à linha de comando.

  A segunda parte (`$(docker ps -q -f name=NOME_DO_CONTAINER)`) pode ser explicada da seguinte forma:

  - `docker ps` para listar os containers inicializados;

  - opção `-q` para mostrar apenas os IDs dos containers;

  - opção `-f name=` para **filtrar** os containers pelo nome.


  No caso do trabalho em questão, podemos acompanhar o log do container `ativ2-app-hospedagem` (que contém os recursos hotel e reserva de hotel) com o seguinte comando:

  `docker logs -f $(docker ps -q -f name=ativ2-app-hospedagem)`
