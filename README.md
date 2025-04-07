# Park API

API para gerenciamento de estacionamento, incluindo usuários, veículos e vagas. Ainda em desenvolvimento.

## 🚀 Tecnologias

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- MySql
- Swagger (OpenAPI 3)
- JUnit & Mockito

## 📌 Pré-requisitos

- Java 17+
- Maven
- MySql

## ⚙️ Configuração

1. Configure um banco MySql local
2. Altere o `application.properties` para apontar para seu banco

## 📖 Endpoints da API

### 🔑 **Autenticação**

| Método | Endpoint       | Descrição          | Permissão    |
|--------|--------------|------------------|--------------|
| POST   | `/api/v1/auth` | Login na API | Público |
| POST   | `/api/v1/usuarios` | Registro de usuário | Público |

### 👤 **Usuários**

| Método | Endpoint | Descrição | Permissão |
|--------|----------|------------|------------|
| GET | `/api/v1/usuarios` | Lista todos os usuários | ADMIN |
| GET | `/api/v1/usuarios/{id}` | Busca um usuário por ID | ADMIN |

### 🚗 **Estacionamentos**

| Método | Endpoint | Descrição | Permissão |
|--------|----------|------------|------------|
| POST | `/api/v1/estacionamentos/check-in` | Aluguel da vaga | ADMIN |
| GET | `/api/v1/estacionamentos/check-in/{recibo}` | Buscar registro de estacionamento pelo recibo | ADMIN |
| PUT | `/api/v1/estacionamentos/check-out/{recibo}` | Compravante de sainda | ADMIN |
| GET | `/api/v1/estacionamentos/cpf/{cpf}` | Buscar os registros de estacionamento do cliente por CPF | ADMIN |


### 🅿️ **Vagas**

| Método | Endpoint | Descrição | Permissão |
|--------|----------|------------|------------|
| POST | `/api/v1/vagas` | Cadastra uma nova vaga | ADMIN |
| GET | `/api/v1/vagas/{codigo}` | Busca uma vaga pelo código | ADMIN |

---

### **Swagger**
http://localhost:8080/swagger-ui/index.html#/

## 📂 Coleção Postman

Para facilitar os testes a coleção do postman esta no diretorio docs

## 🤝 Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Fork este repositório
2. Crie uma branch (`feature/minha-feature`)
3. Commit suas mudanças (`git commit -m 'Minha nova feature'`)
4. Envie um PR

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.


EM DESENVOLVIMENTO


