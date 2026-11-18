# Sistema de Empréstimo de Equipamentos

API REST desenvolvida em **Java e Spring Boot** para gerenciar equipamentos corporativos, colaboradores e empréstimos.

Projeto desenvolvido durante o **Bootcamp Java Full Stack ElasTech — SoulCode + PagBank**, com foco em programação orientada a objetos, arquitetura em camadas, persistência de dados e implementação de regras de negócio.

## Sobre o projeto

O sistema tem como objetivo substituir controles manuais de empréstimos de equipamentos por uma API que permita acompanhar a disponibilidade, os responsáveis e os prazos de devolução.

A aplicação contempla três entidades principais:

- **Equipamento:** notebooks, monitores e headsets disponíveis para empréstimo.
- **Colaborador:** pessoa responsável pelo equipamento emprestado.
- **Empréstimo:** registro da retirada, prazo e devolução de um equipamento.

## Tecnologias

| Tecnologia | Utilização |
|---|---|
| Java | Linguagem principal |
| Spring Boot | Desenvolvimento da API REST |
| Spring Web | Endpoints HTTP |
| Spring Data JPA | Persistência de dados |
| Hibernate | Mapeamento objeto-relacional |
| H2 Database | Desenvolvimento e testes |
| PostgreSQL | Banco de dados de produção (planejado) |
| Jakarta Validation | Validação dos DTOs |
| JUnit 5 | Testes unitários |





A estrutura representa a organização planejada, incluindo classes ainda em desenvolvimento.

### Responsabilidades

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

- [ ] Cadastrar colaboradores
- [ ] Listar colaboradores
- [ ] Buscar colaborador por ID
- [ ] Validar e-mail duplicado
- [ ] Consultar histórico de empréstimos

### Empréstimos

- [ ] Registrar empréstimos
- [ ] Impedir empréstimo de equipamento indisponível
- [ ] Estabelecer prazo de devolução de 14 dias
- [ ] Registrar devoluções
- [ ] Atualizar disponibilidade do equipamento
- [ ] Identificar empréstimos atrasados
- [ ] Impedir devoluções duplicadas
- [ ] Bloquear empréstimos para colaboradores com atrasos

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

## Endpoints

### Equipamentos — implementados

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| GET | `/equipamentos` | Listar equipamentos | 200 |
| GET | `/equipamentos/{id}` | Buscar por ID | 200 |
| POST | `/equipamentos` | Cadastrar equipamento | 201 |
| PUT | `/equipamentos/{id}` | Atualizar equipamento | 200 |
| DELETE | `/equipamentos/{id}` | Remover equipamento | 204 |

**Filtros opcionais:**

```http
GET /equipamentos?tipo=NOTEBOOK
GET /equipamentos?disponivel=true
GET /equipamentos?tipo=MONITOR&disponivel=false
```

### Colaboradores — em desenvolvimento

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/colaboradores` | Cadastrar colaborador |
| GET | `/colaboradores` | Listar colaboradores |
| GET | `/colaboradores/{id}` | Buscar colaborador |
| GET | `/colaboradores/{id}/emprestimos` | Consultar histórico |

### Empréstimos — em desenvolvimento

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/emprestimos?equipamentoId=1&colaboradorId=2` | Registrar empréstimo |
| GET | `/emprestimos` | Listar empréstimos |
| GET | `/emprestimos?atrasados=true` | Listar atrasados |
| GET | `/emprestimos/{id}` | Buscar empréstimo |
| PUT | `/emprestimos/{id}/devolucao` | Registrar devolução |

## Exemplo de utilização

### Cadastrar equipamento

**POST `/equipamentos`**

Requisição:

```json
{
  "patrimonio": "PAT-001",
  "nome": "Notebook Dell",
  "tipo": "NOTEBOOK"
}
```

Resposta esperada — `201 Created`:

```json
{
  "id": 1,
  "patrimonio": "PAT-001",
  "nome": "Notebook Dell",
  "tipo": "NOTEBOOK",
  "disponivel": true
}
```

O campo `disponivel` é definido automaticamente como `true` pelo Service.

## Banco de dados

A aplicação utiliza Spring Data JPA para persistência das entidades.

### Desenvolvimento e testes

O banco **H2** é utilizado inicialmente para facilitar o desenvolvimento e a execução local.


## Como executar localmente

### Pré-requisitos

- Java JDK compatível com o projeto
- Maven
- Git

### 1. Clonar o repositório

```bash
git clone https://github.com/pamelaprpn/api-emprestimo-equipamentos.git
```

### 2. Acessar o projeto

Entre na pasta que contém o arquivo `pom.xml`.

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

Com a aplicação iniciada na porta 8080, acesse:

```text
http://localhost:8080/equipamentos
```

## Testes

O projeto prevê testes unitários com JUnit 5 para validar os comportamentos das entidades.

Os cenários incluem:

- Empréstimo de equipamento disponível
- Bloqueio de empréstimo de equipamento indisponível
- Devolução de equipamento
- Cálculo do prazo de 14 dias
- Bloqueio de devoluções duplicadas
- Identificação de empréstimos atrasados
- Verificação de empréstimos dentro do prazo
- Verificação de empréstimos já devolvidos

Após a implementação, os testes poderão ser executados com:

```bash
mvn test
```

## Aprendizados aplicados

O desenvolvimento deste projeto envolve:

- Programação orientada a objetos
- Encapsulamento e regras de domínio
- Princípio da responsabilidade única (SRP)
- Injeção de dependência
- Arquitetura em camadas
- APIs REST
- Spring Data JPA
- DTOs e Mappers
- Validação de dados
- Persistência relacional
- Testes unitários

---

**Desenvolvido como projeto de aprendizagem no Bootcamp Java Full Stack ElasTech — SoulCode + PagBank.**
