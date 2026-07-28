data class ApiKey(
    val key: String,
    val userId: String,
    val plan: PlanType,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long? = null,
    val isActive: Boolean = true,
    val usageCount: Int = 0
)

enum class PlanType {
    FREE, PREMIUM, ENTERPRISE
}
