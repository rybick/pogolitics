# powershell.exe -ExecutionPolicy Bypass -Command ./release-github.ps1
./gradlew clean build

try {
    cd ../PokeGoDex.github.io/
} catch {
    echo "PokeGoDex.github.io directory not found"; exit 1;
}

if (git status --porcelain) {
    echo "CAN'T PROCEED THERE ALREADY ARE UNCOMMITTED CHANGES"
    exit 0
}

Remove-Item -Recurse ./*
git checkout README.md # We don't want to lose README
cp -r ../pogolitics/build/distributions/* ./

git add -A
git commit -m "Release PokeGoDex"

git push

cd ../pogolitics/

git checkout ./src/main/kotlin/pogolitics/EnvVariables.kt