🔐 API Key Manager

Sistema completo para gerenciamento de chaves API em Kotlin

https://img.shields.io/badge/Kotlin-1.9.0-7F52FF.svg?logo=kotlin
https://img.shields.io/badge/License-MIT-blue.svg
https://img.shields.io/badge/Build-Passing-brightgreen.svg

---

🚀 Funcionalidades

· 🔑 Geração com HMAC-SHA256
· ✅ Validação em tempo real
· 📊 Planos FREE/PREMIUM/ENTERPRISE
· ⏱️ Rate limiting por plano
· ⏰ Expiração automática
· 🔒 Revogação de chaves
· 📈 Monitoramento de uso

---

📦 Instalação

```bash
git clone https://github.com/seu-usuario/api-key-manager.git
cd api-key-manager
./gradlew build
```

Dependências (build.gradle.kts)

```kotlin
dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}
```

---

⚙️ Configuração

.env

```bash
API_SECRET_KEY=seu_segredo_aqui
API_LIMIT_FREE=10
API_LIMIT_PREMIUM=100
API_LIMIT_ENTERPRISE=1000
```

---

💻 Uso Rápido

```kotlin
fun main() {
    val service = ApiKeyService()
    
    // Gerar chave
    val key = service.registerKey("user_123", PlanType.PREMIUM)
    println("🔑 Chave: $key")
    
    // Validar
    when (val result = service.checkKey(key)) {
        is CheckResult.Success -> println("✅ Acesso liberado para ${result.userId}")
        is CheckResult.Failure -> println("❌ ${result.reason}")
        is CheckResult.RateLimited -> println("⏳ Limite excedido")
    }
}
```

Com Spring Boot

```kotlin
@RestController
class ApiController(val service: ApiKeyService) {
    @GetMapping("/dados")
    fun getData(@RequestHeader("X-API-Key") key: String) =
        when (val result = service.checkKey(key)) {
            is CheckResult.Success -> ResponseEntity.ok(mapOf("user" to result.userId))
            is CheckResult.Failure -> ResponseEntity.status(401).body(mapOf("error" to result.reason))
            is CheckResult.RateLimited -> ResponseEntity.status(429).body(mapOf("error" to result.message))
        }
}
```

---

📊 Planos

Plano Req/min Expiração
🆓 FREE 10 30 dias
💎 PREMIUM 100 90 dias
👑 ENTERPRISE 1000 365 dias

---

🔗 Endpoints

```bash
# Gerar chave
POST /api/keys
{
  "userId": "user_123",
  "plan": "PREMIUM"
}

# Validar
GET /api/keys/{key}/validate

# Revogar
POST /api/keys/{key}/revoke
```

---

🛡️ Segurança

· ✅ HMAC-SHA256
· ✅ Variáveis de ambiente
· ✅ Rate limiting
· ✅ Logs de auditoria

```kotlin
// ✅ Correto
val secret = System.getenv("API_SECRET_KEY")

// ❌ NUNCA faça isso
val secret = "chave_fixa_aqui"
```

---

🧪 Testes

```bash
./gradlew test
```

---

🤝 Contribuição

1. Fork o projeto
2. Crie sua branch (git checkout -b feature/nova)
3. Commit (git commit -m '✨ Adiciona feature')
4. Push (git push origin feature/nova)
5. Abra um Pull Request

---

📄 Licença

MIT © 2026

---

📞 Suporte

· 📧 Email: suporte@cooming soon
· 🐛 Issues: GitHub Issues

---

⭐ Se gostou, deixe uma estrela!
