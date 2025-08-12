# Java Data Processor

![Java](https://img.shields.io/badge/Java-17-blue?style=flat-square&logo=java)
![Gradle](https://img.shields.io/badge/Gradle-Build-green?style=flat-square&logo=gradle)
![Status](https://img.shields.io/badge/Status-Finalizado-brightgreen?style=flat-square)

> Projeto para processamento eficiente de arquivos JSON contendo dados complexos de usuários, times, projetos e logs.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Gradle
- SQLite (para perfil dev)
- PostgreSQL (para perfil test)
- Docker para serviço de banco de dados PostgreSQL

---

## Estrutura do Projeto

```plaintext
> controller
> dto
> entities
> mapper
> repository
> service
```

---

## Funcionalidades

- Leitura de arquivos JSON com estrutura aninhada
- Persistência eficiente com Spring Data JPA
- Associação entre usuários, times, projetos e logs
- Identificação de líderes de equipe
- Otimizações com cache e uso de `saveAll()`
- Tempo de execução rápido mesmo com grandes volumes

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

## Performance

- Teste com processamento de 1 mil e 100 mil usuários
- Uso de cache para evitar consultas repetidas ao banco
- Evita exceções de transientes com ordenação correta de persistência

---

Desenvolvido por Matheus Valdevino



