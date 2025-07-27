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

### Arquitetura da infraestrutura

<div style="text-align: center;">
    <img 
        src="./docs/images/infra_kubernetes.png" 
        width="60%" 
        alt="Diagrama da arquitetura de infraestrutura Kubernetes com backend Spring Boot e banco de dados PostgreSQL."
    >
</div>

### Executando o app com o minikube localmente
> Comandos realizados utilizando o Windows + Powershell (administrador) e driver sendo o Docker Desktop

Primeiro precisamos instalar o minikube conforme o sistema operacional, para isso siga as [instruções de instalação na documentação](https://minikube.sigs.k8s.io/docs/start/?arch=%2Fmacos%2Farm64%2Fstable%2Fbinary+download)

#### Iniciar o software do Docker Desktop

#### Iniciar o Minikube com driver Docker

```sh
minikube start --driver=docker
```

#### Habilitar o Metrics Server (necessário para o HPA funcionar)

```sh
minikube addons enable metrics-server
```

#### Configurar o ambiente Docker para usar o daemon interno do Minikube

```sh
& minikube -p minikube docker-env | Invoke-Expression
```

#### Construir a imagem Docker da aplicação backend

```sh
docker build -t soat:latest ./backend/soat
```

#### Aplicar os manifests do Kubernetes na ordem correta
> Os comandos a seguir estão levando em conta a pasta raiz do projeto
#### Namespace

```sh
kubectl apply -f ./infra/namespace.yml
```

#### Volumes persistentes para o banco de dados PostgreSQL

```sh
kubectl apply -f ./infra/volumes/soat-postgres.yml
```

#### Configurações e segredos do banco de dados PostgreSQL

```sh
kubectl apply -f ./infra/secrets/soat-postgres.yml
kubectl apply -f ./infra/configmaps/soat-postgres.yml
```

#### Serviço e Deployment do banco de dados PostgreSQL

```sh
kubectl apply -f ./infra/services/soat-postgres.yml
kubectl apply -f ./infra/deployments/soat-postgres.yml
```

#### Espere o pod do postgres ficar READY

```sh
kubectl get pods -n soat -w
```

#### Configurações e segredos do backend

```sh
kubectl apply -f ./infra/secrets/soat-backend.yml
kubectl apply -f ./infra/configmaps/soat-backend.yml
```

#### Deployment e Service do backend

```sh
kubectl apply -f ./infra/deployments/soat-backend.yml
kubectl apply -f ./infra/services/soat-backend.yml
```

#### HPA para autoescalonamento do backend

```sh
kubectl apply -f ./infra/hpas/soat-backend.yml
```

#### Acessar o serviço exposto no Minikube

```sh
minikube service soat-backend -n soat
```

> No exemplo abaixo, o acesso a aplicação está liberada no endereço **http://127.0.0.1:54754**

<div style="text-align: center;">
    <img 
        src="docs/images/minikube_service.png" 
        width="60%"
        alt="Porta exposta pelo minikube"
    >
</div>