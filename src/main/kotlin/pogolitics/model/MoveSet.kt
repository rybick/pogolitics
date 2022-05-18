package pogolitics.model

import kotlin.time.Duration

data class MoveSet(
    val quickAttack: Attack,
    val chargedAttack: Attack,
    val dps: Float,
    val timeToFirstAttack: Duration,
    val meanTimeBetweenAttacks: Duration
)