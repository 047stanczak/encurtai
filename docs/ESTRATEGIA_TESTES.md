# Documento de estratégia de testes — Encurtai

**Projeto:** API de encurtamento de URLs com cadastro e login de usuário.

---

## 1. Autenticação (cadastro, login, logout)

### Regras de negócio

- **Cadastro:** email válido e único; senha com no mínimo 6 caracteres; senha armazenada criptografada; não pode cadastrar o mesmo email duas vezes.
- **Login:** precisa existir usuário com aquele email e a senha deve conferir; se der certo, gera token (e cookie no navegador, conforme a API).
- **Logout:** limpa o cookie do token.

### Casos de teste

| ID  | Cenário | Expectativa | Tipo |
|-----|---------|-------------|------|
| **A1** | Positivo: cadastrar com email e senha válidos e novos | Resposta **201** e mensagem de sucesso | Integração (ex.: MockMvc pela API) |
| **A2** | Negativo: login com senha errada para usuário que existe | Erro **401** | Unitário (serviço de usuário com mocks, sem subir toda a web) |

---

## 2. Encurtamento de URL (criar link curto)

### Regras de negócio

- Só usuário logado pode encurtar (`POST /api/url`).
- A URL não pode vir vazia.
- A URL é normalizada (ex.: adicionar `https` se faltar) e precisa ser válida.
- URL bloqueada (lista do sistema) não pode ser encurtada.
- O mesmo usuário não pode encurtar de novo a mesma URL já cadastrada para ele (conflito).
- Se passar em tudo: gera um código (hash), salva e devolve na resposta **201**.

### Casos de teste

| ID  | Cenário | Expectativa | Tipo |
|-----|---------|-------------|------|
| **B1** | Positivo: usuário logado envia URL válida e liberada | **201** e receber o hash | Integração (controller + serviços, muitas vezes com mock do banco) |
| **B2** | Negativo: encurtar de novo a mesma URL para o mesmo usuário | Erro **409** | Unitário (regra de duplicidade no verificador / serviço com mock) |

---

## 3. Redirecionamento (abrir o link curto)

### Regras de negócio

- Qualquer pessoa pode acessar `GET /{codigo}` sem estar logada.
- Se o código existir: resposta **302** redirecionando para a URL original.
- Conta mais uma visualização na URL quando alguém acessa.
- Se o código não existir: resposta **404** com mensagem de não encontrado.

### Casos de teste

| ID  | Cenário | Expectativa | Tipo |
|-----|---------|-------------|------|
| **C1** | Positivo: acessar código que existe | **302** e cabeçalho `Location` com a URL correta | Integração (pela API) |
| **C2** | Negativo: acessar código que não existe | **404** | Integração (pela API) |
