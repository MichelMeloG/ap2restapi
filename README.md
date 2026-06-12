# API Gestão da Copa do Mundo ⚽🌍

Este é o projeto **AP2** da disciplina de TAP (Tópicos Avançados de Programação). Consiste em uma **API RESTful** desenvolvida em **Java + Spring Boot**, projetada para realizar a gestão das Seleções, Jogadores e Partidas de uma Copa do Mundo.

O projeto foi inteiramente refatorado seguindo rigorosos padrões de **Engenharia de Software**, englobando Clean Code, Princípios SOLID e Design Patterns.

---

## 🎯 Objetivo do Projeto
Oferecer uma arquitetura moderna e coesa para operações CRUD completas no contexto da Copa do Mundo (Minimundo escolhido), utilizando um Banco de Dados Relacional em Memória (H2), além de documentação limpa e iterativa gerada pelo **Swagger**.

## 🛠️ Stack Tecnológica
* **Java 17 / 24** (Compilável no JDK mais recente)
* **Spring Boot 3.3.x**
  * Spring Web (Camada REST)
  * Spring Data JPA (Persistência)
  * Spring Validation (Regras de entrada)
* **H2 Database** (Banco Relacional em Memória)
* **Lombok** (Versão 1.18.38 - Compatível com Java 24)
* **Swagger / OpenAPI** (Documentação Automática)
* **Maven** (Gerenciador de Dependências)

---

## 🧩 Arquitetura & Boas Práticas

### 📂 Estrutura de Pacotes (SRP e Clean Code)
* `controller/`: Interceptores HTTP que chamam as regras de negócio.
* `service/`: Onde toda a lógica do minimundo acontece.
* `repository/`: Interfaces do Spring Data JPA.
* `entity/`: Classes espelho das tabelas do banco de dados relacional.
* `dto/`: Objetos "anêmicos" que filtram o que entra e o que sai da API.
* `config/`: Configurações de CORS e outras diretivas Web.
* `exception/`: Tratamento central de erros HTTP (`@RestControllerAdvice`).

### 📐 Design Patterns Implementados
1. **Builder (Criacional)**: Anotações `@Builder` facilitam instanciar entidades complexas.
2. **DTO (Estrutural)**: Evita acoplamento entre camada de Banco de Dados e Front-End.
3. **Factory Method (Criacional)**: `SelecaoFactory` automatiza e centraliza a conversão de Entidades para ResponseDTOs.

### 🗄️ Modelagem JPA e Relacionamentos
* **1:N (Um para Muitos)**: Uma Seleção tem Vários Jogadores.
* **N:N (Muitos para Muitos)**: Várias Seleções participam de Várias Partidas.

---

## 🚀 Como Executar o Projeto

1. Clone o repositório ou faça o download da pasta do projeto.
2. Abra seu terminal na raiz da pasta `ap2restapi`.
3. Certifique-se de ter o **Java instalado**.
4. Rode a aplicação com o Maven Wrapper:

```bash
# Windows
.\mvnw spring-boot:run

# Linux / Mac
./mvnw spring-boot:run
```

A aplicação subirá na porta padrão **8080**.

---

## 🌐 Como Acessar a Aplicação

A aplicação está hospedada no Google Cloud Run e também pode ser executada localmente.

### ☁️ Acesso em Nuvem (Google Cloud Run)
* **Swagger UI:** 👉 **[https://ap2restapi-564386776684.us-central1.run.app/swagger-ui/index.html](https://ap2restapi-564386776684.us-central1.run.app/swagger-ui/index.html)**
* **Console H2:** 👉 **[https://ap2restapi-564386776684.us-central1.run.app/h2-console](https://ap2restapi-564386776684.us-central1.run.app/h2-console)**

### 💻 Acesso Local

#### 📖 Documentação do Swagger (Testar Endpoints)
Para testar todos os endpoints `GET`, `POST`, `PUT` e `DELETE` sem precisar do Postman:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

> **Dica:** O projeto possui um roteamento na rota raiz (`/`), ou seja, se você abrir só `http://localhost:8080/` no navegador, será redirecionado para o Swagger automaticamente.

#### 💾 Console do Banco H2
Para ver as tabelas reais, relacionamentos e fazer queries SQL:
👉 **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**

**Credenciais para Login:**
* **JDBC URL:** `jdbc:h2:mem:copadb` *(Exatamente como está aqui)*
* **User Name:** `sa`
* **Password:** *(deixe em branco)*
* Clique no botão **Connect**.

---

## 📌 Rotas Disponíveis (Endpoints)

| Entidade | Método | Rota | Descrição |
| :--- | :--- | :--- | :--- |
| **Seleções** | POST | `/api/selecoes` | Cadastra país participante |
| | GET | `/api/selecoes` | Retorna todos os países |
| | GET | `/api/selecoes/{id}` | Busca país específico |
| | PUT | `/api/selecoes/{id}` | Atualiza ranking e técnico |
| | DELETE | `/api/selecoes/{id}` | Deleta país |
| | GET | `/api/selecoes/{id}/jogadores` | Traz apenas o elenco do país |
| **Jogadores** | POST | `/api/jogadores` | Cadastra jogador (requer `selecaoId`) |
| | GET | `/api/jogadores` | Traz todos os jogadores |
| | GET | `/api/jogadores/{id}` | Busca por ID |
| | PUT | `/api/jogadores/{id}` | Atualiza dados |
| | DELETE | `/api/jogadores/{id}` | Remove do sistema |
| **Partidas** | POST | `/api/partidas` | Cadastra jogo (sem vínculo inicial) |
| | POST | `/api/partidas/{id}/selecoes/{idSelecao}` | Vincula Seleções N:N à partida |
| | GET | `/api/partidas` | Traz o calendário de jogos completo |
| | GET | `/api/partidas/{id}` | Retorna um jogo |
| | PUT | `/api/partidas/{id}` | Atualiza data, estádio, placar |
| | DELETE | `/api/partidas/{id}` | Remove do sistema |
| | DELETE | `/api/partidas/{id}/selecoes/{idSelecao}` | Desfaz o vínculo da partida |

---

## ✅ Tratamento de Exceções 
O sistema trata graciosamente erros lançando objetos HTTP corretos. Se buscar por um `ID` que não existe no Banco, a API retornará código **404 NOT FOUND** formatado (graças ao `GlobalExceptionHandler`). Se faltarem dados obrigatórios nos POSTs, retornará um **400 BAD REQUEST**.
