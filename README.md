# Fast Food Tech Challenge - Sistema de Gestão

Bem-vindo ao repositório do **Fast Food Tech Challenge**, um sistema moderno para gestão de pedidos, clientes e operações de uma rede de fast food. Este projeto foi desenvolvido com foco em escalabilidade, performance e experiência do usuário, utilizando as melhores práticas de engenharia de software.

## Sobre o Projeto

O **Fast Food Tech** é uma solução completa para digitalização e automação de processos em estabelecimentos de alimentação rápida. O sistema contempla funcionalidades como cadastro de produtos, gerenciamento de pedidos, acompanhamento de status em tempo real, integração com meios de pagamento e relatórios gerenciais.

O objetivo é proporcionar uma plataforma robusta, intuitiva e eficiente, facilitando o dia a dia de operadores, gerentes e clientes.

## Principais Funcionalidades

- Cadastro e gerenciamento de produtos e categorias
- Registro e acompanhamento de pedidos
- Controle de status dos pedidos (em preparo, pronto, entregue)
- Relatórios de vendas e desempenho
- Interface amigável para operadores e clientes

## Tecnologias Utilizadas

O projeto adota tecnologias, práticas e ferramentas alinhadas com o Pós Tech em Arquitetura de Software da FIAP, incluindo:

- **Microsserviços** e arquitetura orientada a serviços
- **Containers** com Docker e orquestração com Kubernetes
- **Arquitetura Hexagonal** e **Clean Architecture**
- **Domain-Driven Design (DDD)**
- **Serverless** (ex: AWS Lambda, API Gateway)
- **Integração e entrega contínua (CI/CD)** com GitHub Actions e Terraform (IaC)
- **Banco de dados relacionais e NoSQL**: PostgreSQL, MongoDB, Redis, Cassandra, Neo4j
- **Monitoramento e observabilidade**: OpenTelemetry, EFK (Elasticsearch, Fluentd, Kibana)
- **Testes automatizados**: TDD, BDD, testes de integração e performance
- **Segurança**: OWASP, práticas de desenvolvimento seguro, LGPD
- **DevOps** e práticas de engenharia de software moderna

## Referência: [Pós Tech em Software Architecture - FIAP+Alura](https://postech.fiap.com.br/curso/software-architecture/)

## Participantes

[![brunocesaromax](https://github.com/brunocesaromax.png?size=100)](https://github.com/brunocesaromax)
[![philipphahmann](https://github.com/philipphahmann.png?size=100)](https://github.com/philipphahmann)
[![shandersonvieira](https://github.com/shandersonvieira.png?size=100)](https://github.com/shandersonvieira)
[![vinicius-ma](https://github.com/vinicius-ma.png?size=100)](https://github.com/vinicius-ma)
[![gabrieldasilvadev](https://github.com/gabrieldasilvadev.png?size=100)](https://github.com/gabrieldasilvadev)

---

### Instalação

##### Instale o gerenciador de versões para Java [sdkman](https://sdkman.io/install/)

```sh
curl -s "https://get.sdkman.io" | bash
```

Feche o terminal e abra um novo

##### Execute o comando abaixo para testar a instalação

```sh
sdk version
```

##### Instalação do Java 21

```sh
sdk install java 21-zulu
```

##### Para validar a instalação do Java execute o comando:

```sh
java --version
```

##### Para iniciar o app execute:

```sh
mvn spring-boot:run
```

##### Executar os testes unitários

```sh
export $(cat .env | xargs) && ./mvnw test
```

##### Swagger

```
http://localhost:8080/swagger-ui/index.html
```

##### Health Check

```
http://localhost:8080/health
```

### Utilizando o docker compose

##### Criar o arquivo .env a partir do arquivo de exemplo pos-tech/backend/soat/contrib/env.example ou exportar as variáveis de ambiente abaixo:

```sh
export DB_URL=jdbc:postgresql://soat-postgres:5432/soat
export DB_USER=<DB_USER>
export DB_PASSWORD=<DB_PASSWORD>
```

##### Executar o docker compose

```sh
docker compose up -d
```

##### Para validar se todos os serviços estão up acesse:

```
http://0.0.0.0:8080/health
```

##### Api docs:

```
http://0.0.0.0:8080/swagger-ui/index.html
```

### Executando o app com o minikube localmente

Primeiro precisamos instalar o minikube conforme o sistema operacional, para isso siga as [instruções de instalação na documentação](https://minikube.sigs.k8s.io/docs/start/?arch=%2Fmacos%2Farm64%2Fstable%2Fbinary+download)

##### Feito isso, inicie o minikube

```sh
minikube start
```

##### Também podemos habilitar as métricas do minikube, mas esse passo é opcional

```sh
minikube addons enable metrics-server
```

##### Agora precisamos fazer o build atualizado da última versão da aplicação

```sh
docker build -t soat ./backend/soat
```

##### O minikube, por padrão, não consegue acessar as imagens no nosso host, então precisamos carrega-las dentro do minikube

```sh
minikube image load soat:latest
```

##### Feito isso, podemos carregar todos os arquivos de configuração de uma só vez com o comando abaixo

```sh
kubectl apply -R -f ./infra/
```

##### Para expor uma porta do minikube para o nosso host e possibilitar o acesso, executamos o comando abaixo

```sh
minikube service soat-backend -n soat
```

> No exemplo abaixo o acesso a aplicação está liberada no endereço **http://127.0.0.1:50189**

<div align="center">
    <img src="./docs/images/minikube_service.png"width="60%">
</div>

##### Opcionalmente também podemos fazer um redirecionamento de porta.

```sh
kubectl port-forward service/soat-backend 8080:8080 -n soat
```

##### Para forçar a aplicação escalar o número de pods, podemos usar o comando abaixo

while true; do curl -X 'GET' 'http://127.0.0.1:8080/products' -H 'accept: application/json'; sleep 0.2; done
