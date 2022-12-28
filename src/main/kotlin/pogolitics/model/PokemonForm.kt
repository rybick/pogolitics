package pogolitics.model

class PokemonForm(val code: String) {
    val prettyName get() =
        if (code.startsWith("COPY_")) {
            code.substring(5)
        } else {
            code
        }.lowercase()
            .replaceFirstChar { it.uppercase() }
            .replace("_", " ")

    companion object {
        fun ofNullable(code: String?): PokemonForm? = code?.let(::PokemonForm)
    }
}