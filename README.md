**Instituto Federal de Educação, Ciência e Tecnologia da Paraíba**

**Campus Cajazeiras**

**Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas**

**Programação Orientada a Serviços**

**Professor: Ricardo de Sousa Job**

<h3 align="center">
  Atividade Avaliativa - Microservices RESTful
</h3>

Implemente uma aplicação que dispõem quatro microservices: **hotel**, **passagem**, **agência** e **clientes**.

No serviço de **clientes** deve ser implementado um serviço que mantém um cadastro compartilhado de cliente. Cada entidade cliente deve possuir um identificador, nome, cpf e renda. O serviço deve disponibilizar uma forma de acesso aos dados da entidade cliente. Este serviço será utilizado para realizar as reservas nos demais serviços.

Nos demais serviços deve ser possível realizar uma reserva para um cliente. Os serviços devem possuir no mínimo duas entidades e não deve persistir uma entidade cliente. Deve ser possível, em todos os serviços, realizar as operações de CRUD das entidades.

No serviço de **agência** deve fornecer um recurso que permite solicitar um pacote. O pacote deve possuir uma passagem e uma reserva em um hotel.


**Observações:**

Forma de entrega: um repositório na [organization](https://github.com/ifpb-disciplinas-2016-2).

O projeto deve ser **implantado com o Docker** e deve conter **um roteiro** de como realizar a implantação da aplicação.

Após a implementação, **encaminhar** um email com o link do repositório para o email sousajob@gmail.com.
Prazo para entrega: **2 de maio de 2017, às 23h**.

<hr/>


#### ROTEIRO PARA IMPLANTAR E UTILIZAR A APLICAÇÃO


1. Certifique-se que o seu serviço **Docker** esteja iniciado.

2. No terminal de linha de comando, execute `docker network create cliente_default_ntw`. Este comando é necessário para criarmos a rede docker que será usada para comunicação entre os containers da aplicação e do banco de dados.

3. No seu terminal, navegue até a pasta raiz do projeto `ativ2microservices`.

4. Para iniciar os containers do projeto, execute `sh ./run.sh` (\*\*). A partir deste passo, a aplicação já deve estar disponível para uso, logo após os containers terem inicializado é claro :)

5. Se desejar parar todos os containers e remover os volumes de persistência de dados, pode ser executado `sh ./stop.sh`(\*\*).

  \*\* Os scripts `run.sh` e `stop.sh` são válidos para sistemas unix-like.

  Os passos acima devem ser suficientes para iniciar os containers com serviço restful, bem como aqueles responsáveis pela persistência.


Os recursos estão disponíveis da seguinte forma:

| Recurso | URI |
|----|-----|
| Cliente | http://localhost:8081/cliente-ws/cliente |
| Hotel | http://localhost:8082/hospedagem-ws/hotel |
| Reseva de Hotel | http://localhost:8082/hospedagem-ws/reserva |
