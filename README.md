# Gestão Escolar API - Avaliação AP2 (TAP)

Esta é uma REST API minimalista desenvolvida em **Spring Boot (Java 17)** para a avaliação AP2. 

link api online: `https://ap2restapi-564386776684.us-central1.run.app/swagger-ui/index.html#/`

## 🎯 Objetivo do Projeto
O objetivo principal é entregar 100% dos requisitos solicitados na prova com o código mais simples e direto ao ponto possível. Para isso, o projeto removeu camadas extras (como Services) e concentrou-se apenas no que é essencial.

## 🏗️ Estrutura (Apenas 2 Pacotes)
1. **`models`**: Onde fica o "Coração" do sistema. Estão aqui as 3 Entidades (`Aluno`, `Professor`, `Turma`), os 3 Repositories (que conversam com o banco de dados via JPA) e a Fábrica.
2. **`controllers`**: Onde ficam as rotas web (Endpoints). Eles recebem as requisições HTTP e repassam a lógica de salvar/atualizar direto para o banco.

## 🧠 Padrões de Projeto (Design Patterns)
* **Builder Pattern**: Implementado via Lombok (`@Builder`) dentro de todas as entidades. Substitui a necessidade de construtores confusos com vários parâmetros, permitindo montar objetos dinamicamente: `Aluno.builder().matricula("x").build()`.
* **Factory Method Pattern**: Implementado na classe `EntidadeFactory`. A regra é simples: os Controllers são proibidos de dar um "new" ou de usar os builders diretamente. Eles pedem o objeto pronto para a Fábrica.

## 🗄️ Banco de Dados (H2 em Memória)
Os dados ficam salvos exclusivamente na **Memória RAM** da máquina (H2 Database).
* **Por quê?** Para facilitar a vida de quem for avaliar. O professor só precisa dar o "Play" no projeto, sem se preocupar em instalar MySQL/Postgres ou configurar senhas. Parou a aplicação, os dados somem.
* **Console do H2:** Pode ser acessado visualmente via `http://localhost:8080/h2-console` usando a JDBC URL gerada no log do console ao dar o Play.

## 🛡️ Validações e Regras de Identificação
Foi utilizada a biblioteca de Validações do Spring (`spring-boot-starter-validation`) para garantir regras rígidas na criação de dados (evitando Erros 500 no banco de dados, barrando-os logo na entrada com Erro 400 - Bad Request):
* **Aluno**: Chave primária é a `matricula`. A matrícula agora é **gerada automaticamente** pelo sistema, recebendo um sequencial de 8 dígitos (ex: 00000001). O `nome` é obrigatório (`@NotBlank`).
* **Professor**: Chave primária é a `matricula`. A matrícula agora é **gerada automaticamente** pelo sistema, recebendo um sequencial de 5 dígitos (ex: 00001). O `nome` é obrigatório.
* **Turma**: Chave primária é o `codigo`. É obrigatório, junto com o `nomeDisciplina`.

## 🚀 Como testar a API localmente (Swagger)
Todo o CRUD e a documentação dos Endpoints estão expostos visualmente na interface do OpenAPI/Swagger:

1. Dê o Play (`Ap2restapiApplication`) no seu IntelliJ.
2. Acesse no navegador: `http://localhost:8080/swagger-ui/index.html`

> **Atenção nos POSTs:** Para os endpoints de `Aluno` e `Professor`, o sistema gera a matrícula automaticamente. Portanto, não é necessário enviar o campo `matricula` no JSON (se enviar, ele será ignorado/substituído pelo gerado no backend). Para a `Turma`, o `codigo` ainda precisa ser enviado!
