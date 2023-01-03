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

    companion object {
        fun ofNullable(code: String?): PokemonForm = code?.let(::PokemonForm) ?: DEFAULT

        private val DEFAULT = PokemonForm(null)
    }
}