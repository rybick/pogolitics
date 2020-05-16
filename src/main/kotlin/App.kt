import react.*

class App: RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        child(MovesetsTable::class) {
            attrs.values = listOf(
                Moveset(
                    fastAttack = Attack(PokemonType.STEEL, "Metal Claw"),
                    chargedAttack = Attack(PokemonType.ICE, "Blizzard"),
                    dps = 13.245F,
                    timeToFirstAttack = 5.00F
                ),
                Moveset(
                    fastAttack = Attack(PokemonType.PSYCHIC, "Confuse"),
                    chargedAttack = Attack(PokemonType.PSYCHIC, "Psystrike"),
                    dps = 12.345F,
                    timeToFirstAttack = 8.00F
                ),
                Moveset(
                    fastAttack = Attack(PokemonType.NORMAL, "Yawn"),
                    chargedAttack = Attack(PokemonType.ROCK, "Ancient Power"),
                    dps = 2.345F,
                    timeToFirstAttack = 5.50F
                )
            )
        }
    }
}