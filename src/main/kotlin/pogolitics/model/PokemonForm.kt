package pogolitics.model

class PokemonForm private constructor(val code: String?) {
    val prettyName get(): String? = code?.let {
        if (code.startsWith("COPY_")) {
            code.substring(5)
        } else {
            code
        }.lowercase()
            .replaceFirstChar { it.uppercase() }
            .replace("_", " ")
    }

    fun isDefault() = code == null

    override fun equals(other: Any?): Boolean =
        if (other == null || other !is PokemonForm) {
            false
        } else {
            code == other.code
        }

    override fun hashCode(): Int = code.hashCode()

    override fun toString(): String = "Form($code)"

    companion object {
        fun ofNullable(code: String?): PokemonForm = code?.let(::PokemonForm) ?: DEFAULT

        val DEFAULT = PokemonForm(null)
    }
}