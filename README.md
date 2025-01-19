# Forum Hub API: Uma Solução Java para Gerenciamento de Fórum

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

A **Forum Hub API** é uma aplicação backend desenvolvida em Java com Spring Boot, com foco no gerenciamento de um fórum de discussões. A API oferece funcionalidades de autenticação, gerenciamento de usuários, postagens, comentários, e muito mais, usando um banco de dados MySQL.

---

## **Recursos**

- **Autenticação de Usuários**:
    - Login via JWT para garantir segurança e controle de acesso.
    - Registro de novos usuários com validação de dados.

- **Gestão de Postagens**:
    - Criar, editar e excluir postagens.
    - Visualizar postagens e listar por categoria.

- **Comentários**:
    - Criar, editar e excluir comentários nas postagens.
    - Listar comentários por postagem.

- **Gestão de Categorias**:
    - Criar, editar e excluir categorias para as postagens.

- **API de Documentação**:
    - Documentação da API gerada automaticamente com o Swagger.

- **Validação de Dados**:
    - Validação robusta de entradas de dados usando annotations do Spring Validation.

---

## **Tecnologias Utilizadas**

- **Java 21**: Linguagem principal da aplicação.
- **Spring Boot 3.4**: Framework principal para desenvolvimento do backend.
- **Spring Security**: Para autenticação e autorização com JWT.
- **Spring Data JPA**: Para manipulação de dados com o banco de dados MySQL.
- **MySQL 8**: Banco de dados para persistência de dados.
- **Flyway**: Para migrações de banco de dados.
- **Swagger (Springdoc)**: Para geração da documentação da API.
- **Lombok**: Para reduzir boilerplate de código com anotações como @Getter, @Setter, @NoArgsConstructor, etc.

---

## **Pré-requisitos**

Antes de começar, certifique-se de ter os seguintes pré-requisitos instalados:

1. **Java 21**
2. **MySQL 8** (ou superior)
3. **Maven 4.0.0 ou superior**
4. **Git** (opcional, para clonar o repositório)

As dependências do projeto estão definidas no `pom.xml` e serão baixadas automaticamente via Maven.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-mysql</artifactId>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>com.auth0</groupId>
        <artifactId>java-jwt</artifactId>
        <version>4.4.0</version>
    </dependency>
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.7.0</version>
    </dependency>

</dependencies>
```

---

## **Instalação e Configuração**

### Passo 1: Clonar Repositório

```bash
git clone https://github.com/Allex-Goncalves/forumhub
cd forumhub
```

### Passo 2: Instalar Dependências com Maven

```bash
mvn clean install
```

### Passo 3: Instalar o MYSQL e Crie o banco de dados

```bash
CREATE DATABASE forum_hub;
```
### Configurar o banco de dados no seu application.properties.

```
spring.datasource.url = ${DATASOURCE_URL}
spring.datasource.username = ${DATASOURCE_USERNAME}
spring.datasource.password = ${DATASOURCE_PASSWORD}
```

# Documentação da API

Quando a aplicação estiver em execução, você pode interagir com a API usando ferramentas como o Postman ou Swagger.

A documentação da API gerada automaticamente está disponível em:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Endpoints principais

### POST /login
Realiza login e gera um token JWT.
A senha precisa ter no mínimo 8 caracteres, sendo pelo menos
uma letra Maiúscula, uma minúscula, um número e um caractere especial.

## Rotas para Users são acessadas somente por administradores
### POST /user
Cadastra um novo usuário. São 3 tipos de usuários, ADMIN, MANAGER e USER.

### GET /user
Lista os usuários ativos.

### GET /user{id}
Busca um único usuário.

### PUT /user
Atualiza os dados do usuário.

### PUT /user{id}
Atualiza o status do usuário.

## Rotas para cadastro e atualização dos cursos 

### POST /course
Cadastra um novo curso.

### GET /course
Lista os cursos ativos.

### GET /course{id}
Busca um único curso.

### PUT /course
Atualiza os dados do curso.

### PUT /course/{id}
Edita o status do curso.

## Rotas para os tópicos. Não é permitido criar tópicos com o mesmo titulo e pergunta.

### POST /topic
Adiciona um novo tópico.

### GET /topic/open
Lista todos os tópicos abertos, ordenados por data de criação.

### GET /topic/closed
Lista todos os tópicos fechados, ordenados por data de criação.

### GET /topic?course=
Lista todos os tópicos por curso.

### GET /topic/{id}
Detalha um tópico.

### PUT /topic/{id}
Atualiza o status do tópico, somente usuários MANAGER podem realizar esta operação.

### DELETE /topic/{id}
Deleta um tópico e respostas associadas, somente usuários ADIMIN podem realizar esta operação.

## Rotas para as respostas aos tópicos. Não é permitido criar uma resposta com um mesmo conteúdo de outra resposta.

### POST /response
Adiciona uma nova resposta a um tópico.

### PUT /response/{id}
Marca como solução do tópico, somente usuários MANAGER e o criador do tópico podem realizar esta operação.
Ao marcar a resposta como solução, o tópico referente e encerrado.

Para um melhor entendimento de imput e output nas rotas da api, utilize
a documentação da API gerada automaticamente após rodar a aplicação, disponível em:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)



## Licença
Este projeto está licenciado sob a Licença MIT. Consulte o arquivo LICENSE para mais detalhes.