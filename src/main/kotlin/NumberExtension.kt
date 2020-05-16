fun Float.format(decimalPlaces: Int): String {
    val parts = this.toString().split(".")
    val part2 = if (parts.size >= 2) parts[1] else "0".repeat(decimalPlaces)
    return parts[0] + "." + part2.subSequence(0, decimalPlaces)
}