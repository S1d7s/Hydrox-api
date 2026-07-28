# Hydrox-api
🔐 Sistema de Gerenciamento de Chaves API

Sistema completo para geração, validação e gerenciamento de chaves de API com suporte a múltiplos planos, rate limiting e controle de acesso.

https://img.shields.io/badge/Kotlin-1.9.0-7F52FF.svg?logo=kotlin
https://img.shields.io/badge/License-MIT-blue.svg
https://img.shields.io/badge/Build-Passing-brightgreen.svg
https://img.shields.io/badge/Coverage-85%25-green.svg

---

📋 Índice

· ✨ Visão Geral
· 🚀 Funcionalidades
· 🏗️ Arquitetura
· 📦 Instalação
· ⚙️ Configuração
· 💻 Uso
· 🔗 Endpoints
· 📊 Planos
· 🛡️ Segurança
· 🧪 Testes
· 🤝 Contribuição
· 📄 Licença
· 📞 Suporte

---

✨ Visão Geral

Sistema robusto para gerenciamento de chaves API desenvolvido em Kotlin, ideal para:

· 🏢 SaaS e plataformas com múltiplos clientes
· 🔧 Microserviços com necessidade de controle de acesso
· 🌐 APIs públicas com diferentes níveis de serviço
· 📊 Sistemas que requerem rate limiting por usuário

---

🚀 Funcionalidades

Funcionalidade Descrição Status
🔑 Geração Segura Chaves com assinatura HMAC-SHA256 ✅
✅ Validação Real-time Verificação de integridade e status ✅
📊 Múltiplos Planos FREE, PREMIUM, ENTERPRISE ✅
⏱️ Rate Limiting Controle por plano e usuário ✅
⏰ Expiração Automática Chaves com validade configurável ✅
🔒 Revogação Bloqueio imediato de chaves ✅
📈 Monitoramento Estatísticas de uso ✅
🔄 Thread-safe Suporte a alta concorrência ✅

---

🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                    🌐 Cliente/Serviço                   │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                  🔐 API Key Service                    │
├─────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐   │
│  │  🔑 Key     │  │  ⏱️ Rate    │  │  ✅ Validation│   │
│  │  Generator  │  │  Limiter    │  │  Manager    │   │
│  └─────────────┘  └─────────────┘  └─────────────┘   │
└─────────────────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│              💾 Storage (In-Memory/Cache)              │
└─────────────────────────────────────────────────────────┘
```

📦 Componentes

1. 🔑 Key Generator: Criação de chaves com HMAC-SHA256
2. ⏱️ Rate Limiter: Controle de requisições por plano
3. ✅ Validation Manager: Validação de integridade
4. 🎯 Service Layer: Orquestração das operações

---

📦 Instalação

📋 Pré-requisitos

· ☕ JDK 17+
· 🟣 Kotlin 1.9+
· 📦 Gradle 8.0+

🚀 Passos

1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/api-key-manager.git
cd api-key-manager
```

2. Configure as dependências (build.gradle.kts)

```kotlin
plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("org.jetbrains.dokka") version "1.9.0"
}

group = "com.seuapp"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // 📦 Core
    implementation(kotlin("stdlib"))
    
    // 📝 Logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    
    // 🔧 Utilitários
    implementation("com.google.guava:guava:32.1.3-jre")
    
    // 🧪 Testes
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("io.mockk:mockk:1.13.5")
}

application {
    mainClass.set("com.seuapp.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
```

3. Compile o projeto

```bash
./gradlew build
./gradlew run
```

---

⚙️ Configuração

📄 Arquivo de configuração (application.conf)

```hocon
api {
  security {
    # 🗝️ Chave secreta para HMAC (mude em produção!)
    secretKey = ${?API_SECRET_KEY}
    secretKey = "seu_segredo_super_seguro_aqui"
    
    # ⏰ Tempo de expiração padrão (dias)
    defaultExpirationDays = 30
    
    # 🔐 Algoritmo de assinatura
    algorithm = "HmacSHA256"
  }
  
  rateLimiting {
    # 📊 Requisições por minuto por plano
    limits {
      FREE = ${?API_LIMIT_FREE}
      FREE = 10
      PREMIUM = ${?API_LIMIT_PREMIUM}
      PREMIUM = 100
      ENTERPRISE = ${?API_LIMIT_ENTERPRISE}
      ENTERPRISE = 1000
    }
  }
  
  storage {
    # 💾 Tipo: memory, redis, database
    type = "memory"
  }
}
```

🌍 Variáveis de ambiente

```bash
# .env
API_SECRET_KEY=seu_segredo_super_seguro
API_DEFAULT_EXPIRATION=30
API_LIMIT_FREE=10
API_LIMIT_PREMIUM=100
API_LIMIT_ENTERPRISE=1000
```

---

💻 Uso

🎯 Exemplo básico

```kotlin
import com.seuapp.api.*

fun main() {
    // 🔧 Inicializar serviço
    val service = ApiKeyService()
    
    // 1️⃣ Criar uma chave para um usuário
    val apiKey = service.registerKey(
        userId = "user_123",
        plan = PlanType.PREMIUM
    )
    println("🔑 Chave gerada: $apiKey")
    
    // 2️⃣ Validar a chave em cada requisição
    val result = service.checkKey(apiKey)
    
    when (result) {
        is CheckResult.Success -> {
            println("✅ Acesso permitido!")
            println("   👤 Usuário: ${result.userId}")
            println("   📊 Plano: ${result.plan}")
        }
        is CheckResult.Failure -> {
            println("❌ Acesso negado: ${result.reason}")
        }
        is CheckResult.RateLimited -> {
            println("⏳ ${result.message}")
        }
    }
}
```

🚀 Uso com Ktor

```kotlin
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.module() {
    val apiKeyService = ApiKeyService()
    
    install(Authentication) {
        apiKey {
            validate { credentials ->
                val result = apiKeyService.checkKey(credentials)
                when (result) {
                    is CheckResult.Success -> UserIdPrincipal(result.userId)
                    else -> null
                }
            }
        }
    }
    
    routing {
        authenticate {
            get("/protected") {
                val userId = call.principal<UserIdPrincipal>()?.name
                call.respondText("✅ Bem-vindo, $userId!")
            }
        }
    }
}
```

🌱 Uso com Spring Boot

```kotlin
@RestController
@RequestMapping("/api")
class ApiController(
    private val apiKeyService: ApiKeyService
) {
    @GetMapping("/dados")
    fun getData(@RequestHeader("X-API-Key") apiKey: String): ResponseEntity<Any> {
        val result = apiKeyService.checkKey(apiKey)
        
        return when (result) {
            is CheckResult.Success -> ResponseEntity.ok(
                mapOf(
                    "status" to "success",
                    "userId" to result.userId,
                    "plan" to result.plan.name,
                    "timestamp" to System.currentTimeMillis()
                )
            )
            is CheckResult.Failure -> ResponseEntity.status(401).body(
                mapOf(
                    "status" to "error",
                    "error" to result.reason
                )
            )
            is CheckResult.RateLimited -> ResponseEntity.status(429).body(
                mapOf(
                    "status" to "error",
                    "error" to result.message,
                    "retryAfter" to 60
                )
            )
        }
    }
}
```

---

🔗 Endpoints

📋 API REST

Método Endpoint Descrição Auth
POST /api/keys Gerar nova chave Admin
GET /api/keys/{key}/validate Validar chave Public
POST /api/keys/{key}/revoke Revogar chave Admin
GET /api/keys/{key}/usage Estatísticas Admin
PUT /api/keys/{key} Atualizar plano Admin
GET /api/keys/{key}/status Status da chave Public

📝 Exemplos de requisições

```bash
# 🔑 Gerar chave
curl -X POST http://localhost:8080/api/keys \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {admin_token}" \
  -d '{
    "userId": "user_123",
    "plan": "PREMIUM",
    "expirationDays": 60
  }'

# ✅ Validar chave
curl -X GET http://localhost:8080/api/keys/{key}/validate \
  -H "X-API-Key: {key}"

# 🔒 Revogar chave
curl -X POST http://localhost:8080/api/keys/{key}/revoke \
  -H "Authorization: Bearer {admin_token}"

# 📊 Obter uso
curl -X GET http://localhost:8080/api/keys/{key}/usage \
  -H "Authorization: Bearer {admin_token}"
```

📤 Respostas

✅ Sucesso (200)

```json
{
  "status": "success",
  "data": {
    "userId": "user_123",
    "plan": "PREMIUM",
    "valid": true,
    "usageCount": 42,
    "expiresAt": "2026-12-31T23:59:59Z"
  }
}
```

❌ Erro (401)

```json
{
  "status": "error",
  "error": "Chave API inválida ou expirada",
  "timestamp": "2026-07-28T10:30:00Z"
}
```

⏳ Rate Limit (429)

```json
{
  "status": "error",
  "error": "Limite de requisições excedido",
  "retryAfter": 45,
  "limit": 100,
  "current": 100
}
```

---

📊 Planos

Plano 🚀 Requisições/min ⏰ Expiração 🎯 Features 💰 Preço
FREE 10 30 dias Básico 🆓 Grátis
PREMIUM 100 90 dias Prioritário + Suporte 💵 $29/mês
ENTERPRISE 1000 365 dias VIP + Suporte 24/7 💰 Custom

🎨 Comparação

```
FREE      ████████░░░░░░░░░░░░  (10 req/min)
PREMIUM   ██████████████████░░  (100 req/min)
ENTERPRISE████████████████████  (1000 req/min)
```

---

🛡️ Segurança

🔒 Boas práticas implementadas

1. 🔐 HMAC-SHA256: Chaves assinadas contra falsificação
2. 🔄 Rotação automática: Renovação periódica recomendada
3. ✅ Validação multinível:
   · Integridade da chave
   · Status ativo/inativo
   · Expiração
   · Rate limit
4. 👤 Isolamento de dados: Chaves únicas por usuário
5. 📝 Logs de auditoria: Todas as requisições registradas

⚠️ Recomendações

```kotlin
// ✅ SEMPRE use variáveis de ambiente
val secretKey = System.getenv("API_SECRET_KEY") 
    ?: throw IllegalStateException("Chave secreta não configurada")

// ❌ NUNCA hardcode chaves
// val secretKey = "chave_fixa_aqui" // 🚫 NUNCA FAÇA ISSO!
```

🛡️ Checklist de segurança

☐ Chave secreta em variável de ambiente
☐ HTTPS em produção
☐ Rate limiting ativo
☐ Logs de acesso
☐ Rotação periódica de chaves
☐ Monitoramento de uso suspeito

---

🧪 Testes

▶️ Executar testes

```bash
# Todos os testes
./gradlew test

# Com cobertura
./gradlew test jacocoTestReport

# Teste específico
./gradlew test --tests "ApiKeyServiceTest"
```

📝 Exemplo de teste

```kotlin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import kotlin.test.*

class ApiKeyServiceTest {
    private lateinit var service: ApiKeyService
    
    @BeforeEach
    fun setup() {
        service = ApiKeyService()
    }
    
    @Test
    fun `deve gerar e validar chave corretamente`() {
        // Given
        val userId = "test_user"
        
        // When
        val key = service.registerKey(userId)
        val result = service.checkKey(key)
        
        // Then
        assertTrue(result is CheckResult.Success)
        assertEquals(userId, (result as CheckResult.Success).userId)
    }
    
    @Test
    fun `deve bloquear rate limit excedido`() {
        // Given
        val key = service.registerKey("test_user", PlanType.FREE)
        
        // When
        repeat(11) { service.checkKey(key) }
        val result = service.checkKey(key)
        
        // Then
        assertTrue(result is CheckResult.RateLimited)
    }
    
    @Test
    fun `deve invalidar chave revogada`() {
        // Given
        val key = service.registerKey("test_user")
        
        // When
        service.revokeKey(key)
        val result = service.checkKey(key)
        
        // Then
        assertTrue(result is CheckResult.Failure)
        assertTrue((result as CheckResult.Failure).reason.contains("desativada"))
    }
}
```

---

🤝 Contribuição

📋 Como contribuir

1. 🍴 Fork o projeto
2. 🌿 Crie sua branch (git checkout -b feature/nova-feature)
3. 💻 Commit suas mudanças (git commit -m '✨ Adiciona nova feature')
4. 📤 Push para a branch (git push origin feature/nova-feature)
5. 🔄 Abra um Pull Request

📝 Padrões de código

· Siga o Kotlin Coding Conventions
· ✍️ Escreva testes para novas funcionalidades
· 📚 Documente funções públicas com KDoc

🎯 Exemplo de commit

```bash
# ✨ Nova feature
git commit -m "✨ Adiciona suporte a Redis"

# 🐛 Bug fix
git commit -m "🐛 Corrige validação de chave expirada"

# 📚 Documentação
git commit -m "📚 Atualiza README com exemplos"

# 🔧 Configuração
git commit -m "🔧 Atualiza dependências"
```

---

📄 Licença

MIT License - veja o arquivo LICENSE para detalhes.

```
MIT License

Copyright (c) 2026 Seu Nome

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions...
```

---

📞 Suporte

Canal Detalhes
📧 Email suporte@seudominio.com
🐛 Issues GitHub Issues
📚 Docs Documentação Completa
💬 Discord Servidor da Comunidade
🐦 Twitter @seuapp

---

📈 Roadmap

🚀 Próximas features

☐ 🗄️ Integração com Redis
☐ 📊 Dashboard administrativo
☐ 🔔 Webhooks para eventos
☐ 📑 Exportação de relatórios
☐ 📦 SDKs para outras linguagens
☐ 🔐 Autenticação 2FA
☐ 📈 Analytics avançado

---

⭐ Agradecimentos

Agradecimentos especiais a:

· 🟣 Kotlin Community - Pela linguagem incrível
· 🚀 Ktor Team - Pelo framework poderoso
· 🌱 Spring Boot Team - Pela excelente integração
· 👥 Contribuidores - Por tornarem isso possível

---

📊 Status do Projeto

https://repobeats.axiom.co/api/embed/example.svg

---

🏆 Badges

https://img.shields.io/badge/Kotlin-1.9.0-7F52FF.svg?logo=kotlin
https://img.shields.io/badge/License-MIT-blue.svg
https://img.shields.io/badge/Build-Passing-brightgreen.svg
https://img.shields.io/badge/Coverage-85%25-green.svg
https://img.shields.io/badge/PRs-welcome-brightgreen.svg
https://img.shields.io/badge/Maintained%3F-yes-green.svg
https://img.shields.io/github/last-commit/seu-usuario/api-key-manager.svg

---

Feito com ❤️ pela equipe de desenvolvimento

⭐ Se você gostou, dê uma estrela no GitHub! ⭐
