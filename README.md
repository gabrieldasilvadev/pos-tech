# Pós Tech

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

##### Crie um arquivo `.env` a partir do arquivo de exemplo em `pos-tech/backend/soat/contrib/env.example`

```sh
cp pos-tech/backend/soat/contrib/env.example pos-tech/backend/soat/.env
```

Feito isso, altere as envs conforme o seu ambiente.

##### Executar os testes unitários

```sh
export $(cat .env | xargs) && ./mvnw test
```

##### Swagger

```
http://localhost:8080/api/swagger-ui/index.html
```

##### Health Check

```
http://localhost:8080/api/health
```

### Utilizando o docker-compose

##### Criar o arquivo .env a partir do arquivo de exemplo pos-tech/backend/soat/contrib/env.example ou exportar as variáveis de ambiente abaixo:

```sh
export DB_URL=jdbc:postgresql://soat-postgres:5432/soat
export DB_USER=<DB_USER>
export DB_PASS=<DB_PASS>
```

##### Executar o docker-compose

```sh
docker-compose up -d
```

##### Para validar se todos os serviços estão up acesse:

```
http://0.0.0.0:8081/api/health
```

##### Api docs:

```
http://0.0.0.0:8081/api/swagger-ui/index.html
```
