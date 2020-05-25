enum class PokemonType() {
    BUG {
        override val strongAgainst get() = setOf(DARK,GRASS,PSYCHIC)
        override val weakAgainst get() = setOf(FIGHTING,FIRE,FLYING,FAIRY,GHOST,POISON,STEEL)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    DARK {
        override val strongAgainst get() = setOf(GHOST,PSYCHIC)
        override val weakAgainst get() = setOf(DARK,FIGHTING,FAIRY)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    DRAGON {
        override val strongAgainst get() = setOf(DRAGON)
        override val weakAgainst get() = setOf(STEEL)
        override val superWeakAgainst get() = setOf(FAIRY)
    },
    ELECTRIC {
        override val strongAgainst get() = setOf(FLYING,WATER)
        override val weakAgainst get() = setOf(DRAGON,ELECTRIC,GRASS)
        override val superWeakAgainst get() = setOf(GROUND)
    },
    FAIRY {
        override val strongAgainst get() = setOf(DARK,DRAGON,FIGHTING)
        override val weakAgainst get() = setOf(FIRE,POISON,STEEL)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    FIGHTING {
        override val strongAgainst get() = setOf(DARK,ICE,NORMAL,ROCK,STEEL)
        override val weakAgainst get() = setOf(BUG,FAIRY,FLYING,POISON,PSYCHIC)
        override val superWeakAgainst get() = setOf(GHOST)
    },
    FIRE {
        override val strongAgainst get() = setOf(BUG,GRASS,ICE,STEEL)
        override val weakAgainst get() = setOf(DRAGON,FIRE,ROCK,WATER)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    FLYING {
        override val strongAgainst get() = setOf(BUG,FIGHTING,GRASS)
        override val weakAgainst get() = setOf(ELECTRIC,ROCK,STEEL)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    GHOST {
        override val strongAgainst get() = setOf(GHOST,PSYCHIC)
        override val weakAgainst get() = setOf(DARK)
        override val superWeakAgainst get() = setOf(NORMAL)
    },
    GRASS {
        override val strongAgainst get() = setOf(GROUND,ROCK,WATER)
        override val weakAgainst get() = setOf(BUG,DRAGON,FIRE,FLYING,GRASS,POISON,STEEL)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    GROUND {
        override val strongAgainst get() = setOf(ELECTRIC,FIRE,POISON,ROCK,STEEL)
        override val weakAgainst get() = setOf(BUG,GRASS)
        override val superWeakAgainst get() = setOf(FLYING)
    },
    ICE {
        override val strongAgainst get() = setOf(DRAGON,FLYING,GRASS,GROUND)
        override val weakAgainst get() = setOf(FIRE,ICE,STEEL,WATER)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    NORMAL {
        override val strongAgainst get() = setOf<PokemonType>()
        override val weakAgainst get() = setOf(ROCK,STEEL)
        override val superWeakAgainst get() = setOf(GHOST)
    },
    POISON {
        override val strongAgainst get() = setOf(GRASS,FAIRY)
        override val weakAgainst get() = setOf(GHOST,GROUND,POISON,ROCK)
        override val superWeakAgainst get() = setOf(STEEL)
    },
    PSYCHIC {
        override val strongAgainst get() = setOf(FIGHTING,POISON)
        override val weakAgainst get() = setOf(PSYCHIC,STEEL)
        override val superWeakAgainst get() = setOf(DARK)
    },
    ROCK {
        override val strongAgainst get() = setOf(BUG,FIRE,FLYING,ICE)
        override val weakAgainst get() = setOf(FIGHTING,GROUND,STEEL)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    STEEL {
        override val strongAgainst get() = setOf(FAIRY,ICE,ROCK)
        override val weakAgainst get() = setOf(ELECTRIC,FIRE,STEEL,WATER)
        override val superWeakAgainst get() = setOf<PokemonType>()
    },
    WATER {
        override val strongAgainst get() = setOf(FIRE,GROUND,ROCK)
        override val weakAgainst get() = setOf(DRAGON,GRASS,WATER)
        override val superWeakAgainst get() = setOf<PokemonType>()
    };

    fun against(type: PokemonType): Effectiveness {
        return when {
            strongAgainst.contains(type) -> Effectiveness.STRONG
            weakAgainst.contains(type) -> Effectiveness.WEAK
            superWeakAgainst.contains(type) -> Effectiveness.SUPER_WEAK
            else -> Effectiveness.REGULAR
        }
    }

    abstract val strongAgainst: Set<PokemonType>
    abstract val weakAgainst: Set<PokemonType>
    abstract val superWeakAgainst: Set<PokemonType>

    val displayName get() = name.toLowerCase().capitalize()

    companion object {
        fun fromString(value: String): PokemonType = valueOf(value.toUpperCase())
    }

    enum class Effectiveness {
        STRONG, REGULAR, WEAK, SUPER_WEAK;
    }
}