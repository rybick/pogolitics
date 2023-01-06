# FAQ

## What does DPS, TTFA, MTBA, PvP and Pve mean?
- **DPS** - **D**amage **P**er **S**econd - this is how much HP an attacked pokémon loses in each second. 
More specifically Poke-Go-Dex assumes that an attacker performs fast attack as often as possible and
a charged attack whenever enough energy is gathered. With this assumption DPS is calculated as an
avaraged value that would be dealt over an unlimited amount of time.
- **TTFA** - **T**ime **T**o **F**irst **A**ttack - The time it takes until a pokémon is able to perform a 
charged attack (assuming it was executing fast attacks as often as possible)
- **MTBA** - **M**ean **T**ime **B**etween **A**ttacks - The avarage time that it takes between two charged
attacks can be perfomed. It is often not the same as **TTFA**, because after first charged attack is done, some
energy may be left which caused the next one to be performed sooner. 
E.g. if fast attacks takes one second and generates 10 energy, while charged attack costs 45 energy, 
TTFA could be 5s (it takes 5 attacks to gather enough energy), but MTBA would be 4.5s - after 9s you are
able to do 2 charged attacks. This example is simplified as the charge attack also takes time which makes it 
more complicated.
- **PvP** - **P**layer **v**s **P**layer - fights between two players - in this case Go Battle League.
**PvP** and **PvE** battles have different rules and hence different values of DPS, TTFA, MTBA and so on for the same
pokémon. Fights with Team GO Rocket Grunts and similar follow the same rules as Player vs Player and hence they are
regarded as **PvP** for the sake of this tool.
- **PvE** - **P**layer **v**s **E**nvironment - fights where only one player participates. This mostly means raids
but also fighting other people's pokémon on gyms.

## Why is DPS shown in Poké-Go-Dex different from the one in app X
There is a number of factors. First of all the DPS is different for PvE and PvP. 
Make sure you're comparing the same ones. Secondly, except for your pokémon level and stats, 
DPS depends on the pokémon that it is being attacked. 
The calculations on the pokémon page are done assuming the enemy is a hypothetical pokemon with
defense stat equal to 100.

Having those things in mind these variables should not cause one set of attacks 
(fast + charged attack) to be stronger or weaker than another one when they change. 
This is the case though - in other sources I've seen different results regarding, which attack set
is different. I've checked it many times, and I'm quite possitive that the calculations are right.
This is an open source project, so you can check them out yourself 
[here](https://github.com/rybick/pogolitics/blob/master/src/main/kotlin/pogolitics/controller/MoveSetStatsCalculator.kt) -
even if you don't know anything about programming, you should be able to read it just like math equations.
If you find an error please let me know!
You can also take a look at [this test](https://github.com/rybick/pogolitics/blob/master/src/test/kotlin/MoveSetStatsCalculatorTest.kt#L60)
that compares the DPS calculated by the `MoveSetStatsCalculator` (linked above) with damage simulated in a number of attacks
and divided by the time it would take to execute them.

## I found a bug or an error. Can you fix it?
This is a project I do in my free time, but I try to fix all bugs that I know of. 
Please report your findings as [an issue in github](https://github.com/rybick/pogolitics/issues), or, 
since this is an open source project, you can try to fix it yourself and create a pull request. :)
See how to contribute [here](./CONTRIBUTING.md).

## Are you planning to add functionality Y?
Maybe. I only have limited free time that I can spend here, but I'm slowly adding new things.
If you want to have something particular implemented you can also try doing it yourself. See the point above. :)

## The data on the page seems outdated. What is it based on?
The data is based on the great work of [PokeMiners](https://github.com/PokeMiners), 
most notably [game masters files](https://github.com/PokeMiners/game_masters/tree/master/latest).
all it takes to update the data is to run "./gradlew updateData" in the root of the project using a console (a.k.a. terminal).
It should work no matter what OS you use.
I try to do it every now and then, but again feel free to do it yourself and [create a pull requests](./CONTRIBUTING.md).


