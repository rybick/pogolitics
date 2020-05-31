import kotlin.time.*

fun Float.format(decimalPlaces: Int): String = format(this.toString(), decimalPlaces)
fun Double.format(decimalPlaces: Int): String = format(this.toString(), decimalPlaces)

private fun format(numberAsString: String, decimalPlaces: Int): String {
    val parts = numberAsString.split(".")
    val part2 = if (parts.size >= 2) parts[1] else "0".repeat(decimalPlaces)
    return parts[0] + "." + part2.subSequence(0, decimalPlaces)
}

@ExperimentalTime
val Int.pvpTurns get(): Duration = (0.5 * this).seconds
