# no longer needed now when the app is deployed to root
#sed -i 's/const val applicationRoot = ""/const val applicationRoot = "\/pogolitics"/g' \
#  ./src/main/kotlin/pogolitics/EnvVariables.kt

./gradlew clean build

cd ../PokeGoDex.github.io/  || { echo "PokeGoDex.github.io directory not found"; exit 1; }

if [[ `git status --porcelain` ]]; then
  echo "CAN'T PROCEED THERE ALREADY ARE UNCOMMITTED CHANGES"
  exit 0
fi

rm -r ./*
git checkout README.md # We don't want to lose README
cp -r ../pogolitics/build/distributions/* ./

git add -A
git commit -m "Release PokeGoDex"

git push

cd ../pogolitics/

#sed -i 's/const val applicationRoot = "\/pogolitics"/const val applicationRoot = ""/g' \
#  ./src/main/kotlin/pogolitcs/EnvVariables.kt
git checkout ./src/main/kotlin/pogolitics/EnvVariables.kt