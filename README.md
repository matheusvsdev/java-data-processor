# Java Data Processor

![Java](https://img.shields.io/badge/Java-17-blue?style=flat-square&logo=java)
![Gradle](https://img.shields.io/badge/Gradle-Build-green?style=flat-square&logo=gradle)
![Status](https://img.shields.io/badge/Status-Finalizado-brightgreen?style=flat-square)

> Projeto para processamento eficiente de arquivos JSON contendo dados complexos de usuários, times, projetos e logs. Focado em performance, consistência transacional e estrutura de dados relacional.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Gradle
- SQLite (perfil dev)
- PostgreSQL (perfil test)
- Docker para serviço de banco de dados PostgreSQL

---

## Estrutura do Projeto

```plaintext
src/
├── controller
├── dto
├── entities
├── mapper
├── repository
└── service
```

---

## Funcionalidades

- Leitura de arquivos JSON com estrutura aninhada
- Conversão de DTOs em entidades persistentes
- Associação entre usuários, times, projetos e logs
- Identificação automática de líderes de equipe
- Persistência otimizada com uso de saveAll()
- Cache interno para evitar duplicações e acelerar o processamento
- Execução rápida mesmo com grandes volumes de dados

---

## Como funciona o `ProcessorService`

O serviço principal realiza:

1. **Leitura do JSON** via `ObjectMapper`
2. **Identificação de times e projetos únicos**
3. **Persistência de novos times e projetos**
4. **Conversão de DTOs para entidades com `UserMapper`**
5. **Associação de usuários aos times e projetos**
6. **Persistência de logs de ação**
7. **Definição de líderes de equipe**
8. **Uso de `saveAll()` para reduzir transações e melhorar performance**

Tudo isso dentro de uma transação única com `@Transactional`, garantindo consistência e performance.

---

## Como Executar

1. Clone o repositório:

```bash
git clone git@github.com:matheusvsdev/java-data-processor.git
cd java-data-processor
```

1. Rode o projeto

2. Acesse a aplicação utilizando Postman em:
```
http://localhost:8080/api/v1/processor
```

3. Envie o caminho do arquivo JSON no body:

Exemplo:

POST
```
{
    "path": "/.../.../.../.../usuarios_1000.json"
}
```

Exemplo de arquivo JSON utilizado:

```
[
  {
    "id": "ee8f2662-c8b5-4189-9b66-d6af64fe02be",
    "name": "Ana Sophia Araújo",
    "age": 19,
    "score": 859,
    "active": true,
    "country": "Índia",
    "team": {
      "name": "Frontend Avengers",
      "leader": false,
      "projects": [
        {
          "name": "Mobile App",
          "completed": false
        }
      ]
    },
    "logs": [
      {
        "date": "2025-03-25",
        "action": "logout"
      },
      {
        "date": "2025-03-26",
        "action": "login"
      },
      {
        "date": "2025-03-29",
        "action": "logout"
      },
      {
        "date": "2025-03-26",
        "action": "login"
      }
    ]
  },
```

## Dados processados e salvos no banco

### tb_users

| id                                   | name                 | age | score | country | active |
|--------------------------------------|----------------------|-----|-------|---------|--------|
| ee8f2662-c8b5-4189-9b66-ddaf64fe02be | Ana Sophia Araújo    | 19  | 859   | Índia   | true   |
| 1e46c60d-96a4-4bc6-a792-923a0078f3f9 | Sra. Elisa das Neves | 44  | 325   | Japão   | true   |
| f1064a79-c486-4672-801c-e688a5f1902d | Vicente Cavalcanti   | 40  | 718   | Brasil  | true   |
| 32ccb15e-acfa-48c4-b9df-6f416864c737 | Sarah Viana          | 42  | 384   | Canadá  | true   |
| 6feaeed1-9be0-44f4-bba4-d96a747503f7 | Nicolas Pereira      | 58  | 804   | Canadá  | true   |

### tb_teams

| id                                   | name              |
|--------------------------------------|-------------------|
| 13f515f0-b091-4366-8d66-0332f840aeb0 | UX Wizards        |
| bef323aa-6ae6-4e75-9a5f-4f84faf726ac | Backend Ninjas    |
| 94a360b4-ab9b-44c7-8670-503337ed2f27 | DevOps Masters    |
| 26c29db2-e545-4765-864d-bcda672a05a7 | Frontend Avengers |
| 4960671f-b1fa-4528-afd5-96bc26880818 | Fullstack Force   |

### tb_projects

| id                                   | name            |
|--------------------------------------|-----------------|
| e0151355-57a8-479b-a2cd-f1e7a7031159 | Sistema Interno |
| a55313c9-5e3e-4a5b-a4d5-96df25109c08 | Dashboard       |
| 75ae4e08-57e9-4287-a50d-1ffcbdd71785 | Landing Page    |
| b2ca4113-6359-49c0-9dfb-ad5500343821 | Mobile App      |
| 36397865-a78b-4e30-85a7-a3d1eaa31192 | API Pública     |

### tb_user_project

| id                                   | project_id                           | user_id                              | completed |
|--------------------------------------|--------------------------------------|--------------------------------------|-----------|
| 3a1e5e3d-xxxx-xxxx-xxxx-xxxxxxxxxxxx | e0151355-57a8-479b-a2cd-f1e7a7031159 | f1064a79-c486-4672-801c-e688a5f1902d | true      |
| 7b2c9f8a-xxxx-xxxx-xxxx-xxxxxxxxxxxx | a55313c9-5e3e-4a5b-a4d5-96df25109c08 | 1e46c60d-96a4-4bc6-a792-923a0078f3f9 | true      |
| 9c3d7e6f-xxxx-xxxx-xxxx-xxxxxxxxxxxx | 75ae4e08-57e9-4287-a50d-1ffcbdd71785 | f1064a79-c486-4672-801c-e688a5f1902d | true      |
| 2d4f8a9b-xxxx-xxxx-xxxx-xxxxxxxxxxxx | b2ca4113-6359-49c0-9dfb-ad5500343821 | ee8f2662-c8b5-4189-9b66-ddaf64fe02be | false     |
| 5e6a1c2d-xxxx-xxxx-xxxx-xxxxxxxxxxxx | 36397865-a78b-4e30-85a7-a3d1eaa31192 | f1064a79-c486-4672-801c-e688a5f1902d | false     |

### 👥 tb_team_leaders

| team_id                              | user_id                              |
|--------------------------------------|--------------------------------------|
| 13f515f0-b091-4366-86d6-0332f840aeb0 | 1c301304-4102-4a47-8b7e-6c3f6c3c3c3c |
| 13f515f0-b091-4366-86d6-0332f840aeb0 | 2d7f9e2a-5f8e-4b9a-9d3a-7e2f7e2f7e2f |
| 13f515f0-b091-4366-86d6-0332f840aeb0 | 3e8a1b3b-6c9f-4d8b-8e4b-8f3e8f3e8f3e |
| 13f515f0-b091-4366-86d6-0332f840aeb0 | 4f9b2c4c-7d0a-5e9c-9f5c-9g4f9g4f9g4f |
| 13f515f0-b091-4366-86d6-0332f840aeb0 | 5g0c3d5d-8e1b-6f0d-af6d-ah5g0h5g0h5g |

### tb_logs

| id                                   | action  | date       | user_id                              |
|--------------------------------------|---------|------------|--------------------------------------|
| f7cf7702-7d72-491f-xxxx-xxxxxxxxxxxx | logout  | 2025-03-25 | ee8f2662-c8b5-4189-9b66-ddaf64fe02be |
| 674c2b84-f533-4449-xxxx-xxxxxxxxxxxx | login   | 2025-03-26 | ee8f2662-c8b5-4189-9b66-ddaf64fe02be |
| 0fded33c-ae15-4b46-xxxx-xxxxxxxxxxxx | logout  | 2025-03-29 | ee8f2662-c8b5-4189-9b66-ddaf64fe02be |
| 8f4efa56-874e-406a-xxxx-xxxxxxxxxxxx | login   | 2025-03-26 | ee8f2662-c8b5-4189-9b66-ddaf64fe02be |
| cead9d90-e758-4659-xxxx-xxxxxxxxxxxx | logout  | 2025-03-27 | 1e46c60d-96a4-4bc6-a792-923a0078f3f9 |

---

## Performance

- Teste com processamento de 1 mil e 100 mil usuários
- Uso de cache para evitar consultas repetidas ao banco
- Evita exceções de transientes com ordenação correta de persistência

---

Desenvolvido por Matheus Valdevino



