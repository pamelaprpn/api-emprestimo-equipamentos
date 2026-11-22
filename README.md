# Sistema de Empréstimo de Equipamentos

API REST desenvolvida em **Java 25 e Spring Boot 4** para gerenciar equipamentos corporativos, colaboradores e empréstimos.

Projeto desenvolvido durante o **Bootcamp Java Full Stack ElasTech — SoulCode + PagBank**, com foco em programação orientada a objetos, arquitetura em camadas, persistência de dados e implementação de regras de negócio.

## Sobre o projeto

O sistema tem como objetivo substituir controles manuais de empréstimos de equipamentos por uma API que permite acompanhar a disponibilidade, os responsáveis e os prazos de devolução.

A aplicação contempla três entidades principais:

- **Equipamento:** notebooks, monitores e headsets disponíveis para empréstimo.
- **Colaborador:** pessoa responsável pelo equipamento emprestado.
- **Empréstimo:** registro da retirada, prazo e devolução de um equipamento.

## Tecnologias utilizadas

| Tecnologia | Utilização |
|---|---|
| Java 25 | Linguagem principal |
| Spring Boot 4 | Desenvolvimento da aplicação |
| Spring Web | Criação dos endpoints REST |
| Spring Data JPA | Persistência de dados |
| Hibernate | Mapeamento objeto-relacional |
| H2 Database | Banco de dados |
| Maven | Gerenciamento de dependências e build |
| JUnit 5 | Testes unitários |
| Swagger / Springdoc OpenAPI | Documentação interativa da API |
| Spring Boot Actuator | Monitoramento da aplicação |
| Docker | Containerização e deploy |
| Render | Hospedagem da API |

## Arquitetura

O projeto utiliza arquitetura em camadas, organizando as responsabilidades:

- **Controller:** recebe requisições HTTP e retorna respostas.
- **Service:** centraliza as regras de negócio.
- **Repository:** acessa o banco utilizando Spring Data JPA.
- **Model:** representa as entidades do domínio.
- **DTO:** controla os dados de entrada e saída.
- **Mapper:** converte entidades em DTOs e vice-versa.

A comunicação entre as camadas utiliza injeção de dependência pelo construtor.

## Funcionalidades

### Equipamentos

- [x] Cadastrar equipamentos
- [x] Listar equipamentos
- [x] Buscar equipamento por ID
- [x] Filtrar por tipo e disponibilidade
- [x] Atualizar nome e tipo
- [x] Validar patrimônio duplicado
- [x] Definir novos equipamentos como disponíveis
- [x] Impedir exclusão de equipamentos com histórico de empréstimos

### Colaboradores

- [x] Cadastrar colaboradores
- [x] Listar colaboradores
- [x] Buscar colaborador por ID
- [x] Validar e-mail duplicado
- [x] Consultar histórico de empréstimos

### Empréstimos

- [x] Registrar empréstimos
- [x] Impedir empréstimo de equipamento indisponível
- [x] Estabelecer prazo de devolução de 14 dias
- [x] Registrar devoluções
- [x] Atualizar disponibilidade do equipamento
- [x] Identificar empréstimos atrasados
- [x] Impedir devoluções duplicadas
- [x] Bloquear empréstimos para colaboradores com atrasos

## Regras de negócio

| Regra | Descrição |
|---|---|
| R1 | Não permitir equipamentos com patrimônio duplicado. |
| R2 | Todo equipamento novo deve estar disponível. |
| R3 | Não permitir colaboradores com e-mail duplicado. |
| R4 | Somente equipamentos disponíveis podem ser emprestados. |
| R5 | O empréstimo torna o equipamento indisponível e estabelece prazo de 14 dias. |
| R6 | Um empréstimo devolvido não pode ser devolvido novamente. |
| R7 | A devolução registra a data atual e libera o equipamento. |
| R8 | Empréstimos não devolvidos após o prazo são considerados atrasados. |
| R9 | Equipamentos com histórico de empréstimos não podem ser removidos. |
| R10 | Colaboradores com empréstimos atrasados não podem realizar novos empréstimos. |

## Endpoints da API

### Equipamentos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/equipamentos` | Listar equipamentos |
| GET | `/equipamentos/{id}` | Buscar equipamento por ID |
| POST | `/equipamentos` | Cadastrar equipamento |
| PUT | `/equipamentos/{id}` | Atualizar equipamento |
| DELETE | `/equipamentos/{id}` | Remover equipamento |

**Filtros opcionais:**

```http
GET /equipamentos?tipo=NOTEBOOK
GET /equipamentos?disponivel=true
GET /equipamentos?tipo=MONITOR&disponivel=false
```

### Colaboradores

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/colaboradores` | Cadastrar colaborador |
| GET | `/colaboradores` | Listar colaboradores |
| GET | `/colaboradores/{id}` | Buscar colaborador |
| GET | `/colaboradores/{id}/emprestimos` | Consultar histórico de empréstimos |

### Empréstimos

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/emprestimos?equipamentoId=1&colaboradorId=2` | Registrar empréstimo |
| GET | `/emprestimos` | Listar empréstimos |
| GET | `/emprestimos?atrasados=true` | Listar empréstimos atrasados |
| GET | `/emprestimos/{id}` | Buscar empréstimo por ID |
| PUT | `/emprestimos/{id}/devolucao` | Registrar devolução |

No cadastro de empréstimo, os IDs do equipamento e do colaborador são enviados como parâmetros da URL. As datas são definidas automaticamente pelo sistema.

## Documentação e monitoramento

### Swagger — OpenAPI

A documentação interativa permite visualizar os endpoints, parâmetros e respostas da API, além de executar requisições diretamente pelo navegador.

**Acesso:** [Swagger UI](https://api-emprestimo-equipamentos.onrender.com/swagger-ui.html)

### Actuator

O Spring Boot Actuator disponibiliza informações de saúde da aplicação.

**Endpoint:** [Health Check](https://api-emprestimo-equipamentos.onrender.com/actuator/health)

Resposta esperada:

```json
{
  "status": "UP"
}
```

## Banco de dados

O projeto utiliza **H2 Database** com Spring Data JPA para persistência das entidades.

O H2 é utilizado tanto no desenvolvimento quanto no ambiente publicado no Render. Por ser um banco em memória no ambiente configurado, os dados podem ser perdidos quando a aplicação reinicia ou passa por um novo deploy.

## Como executar localmente

### Pré-requisitos

- Java JDK 25
- Git

O projeto utiliza Maven Wrapper, portanto não é necessário ter o Maven instalado globalmente.

### 1. Clonar o repositório

```bash
git clone https://github.com/pamelaprpn/api-emprestimo-equipamentos.git
```

### 2. Acessar a pasta do projeto

```bash
cd api-emprestimo-equipamentos
```

### 3. Executar a aplicação

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A aplicação será iniciada na porta 8080.

### 4. Acessar a API e a documentação

- API: http://localhost:8080/equipamentos
- Swagger: http://localhost:8080/swagger-ui.html
- Actuator: http://localhost:8080/actuator/health

## Testes

O projeto possui testes unitários com JUnit 5 para validar os comportamentos das entidades.

**Cobertura implementada:**

- 3 testes unitários para Equipamento.
- 7 testes unitários para Empréstimo.

Os cenários contemplam empréstimo, disponibilidade, devolução, cálculo de prazo, bloqueio de devoluções duplicadas e identificação de atrasos.

Para executar os testes:

```bash
./mvnw test
```

No Windows:

```bash
.\mvnw.cmd test
```

## Deploy

A aplicação está publicada no Render utilizando Docker.

**URL da API:**

https://api-emprestimo-equipamentos.onrender.com

O Render realiza o deploy automaticamente após novos pushes na branch `main` do GitHub.

## Aprendizados aplicados

- Programação orientada a objetos.
- Encapsulamento e regras de domínio.
- Princípio da responsabilidade única (SRP).
- Injeção de dependência.
- Arquitetura em camadas.
- Desenvolvimento de APIs REST.
- Spring Data JPA.
- DTOs e Mappers.
- Persistência relacional.
- Testes unitários com JUnit 5.
- Documentação de APIs com Swagger.
- Monitoramento com Spring Boot Actuator.
- Containerização com Docker.
- Deploy de aplicações com Render.

---

**Desenvolvido como projeto de aprendizagem no Bootcamp Java Full Stack ElasTech — SoulCode + PagBank.**