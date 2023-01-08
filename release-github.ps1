(Get-Content ./src/main/kotlin/pogolitcs/EnvVariables.kt).replace('const val applicationRoot = ""', 'const val applicationRoot = "/pogolitics"') | Set-Content ./src/main/kotlin/pogolitcs/EnvVariables.kt

git checkout ./src/main/kotlin/pogolitcs/EnvVariables.kt
#(Get-Content ./src/main/kotlin/pogolitcs/EnvVariables.kt).replace('const val applicationRoot = "/pogolitics"', 'const val applicationRoot = ""') | Set-Content ./src/main/kotlin/pogolitcs/EnvVariables.kt