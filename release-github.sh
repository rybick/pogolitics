sed -i 's/const val applicationRoot = ""/const val applicationRoot = "\/pogolitics"/g' \
  ./src/main/kotlin/pogolitics/EnvVariables.kt

./gradlew build


cd ../rybick.github.io/  || { echo "rybick.github.io directory not found"; exit 1; }

if [[ `git status --porcelain` ]]; then
  echo "CAN'T PROCEED THERE ALREADY ARE UNCOMMITTED CHANGES"
  exit 0
fi

rm -rf ./pogolitics/*
cp -r ../pogolitics/build/distributions/* ./pogolitics/

git add -A
git commit -m "Release pogolitcs"

git push

cd ../pogolitics/

#sed -i 's/const val applicationRoot = "\/pogolitics"/const val applicationRoot = ""/g' \
#  ./src/main/kotlin/pogolitcs/EnvVariables.kt
git checkout ./src/main/kotlin/pogolitics/EnvVariables.kt