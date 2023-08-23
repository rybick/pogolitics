# no longer needed now when the app is deployed to root
#sed -i 's/const val applicationRoot = ""/const val applicationRoot = "\/pogolitics"/g' \
#  ./src/main/kotlin/pogolitics/EnvVariables.kt

if [ -z ${1+x} ]; then
  DEPLOY_REPO_PATH=../PokeGoDex.github.io/
else
  DEPLOY_REPO_PATH=$1
fi

SOURCES_REPO_PATH=`pwd`

echo "sources repository path: $SOURCES_REPO_PATH"
echo "deploy repository path: $DEPLOY_REPO_PATH"

./gradlew clean build

cd "$DEPLOY_REPO_PATH"  || { echo "$DEPLOY_REPO_PATH directory not found"; exit 1; }

if [[ `git status --porcelain` ]]; then
  echo "CAN'T PROCEED THERE ALREADY ARE UNCOMMITTED CHANGES"
  exit 0
fi

rm -r ./*
git checkout README.md # We don't want to lose README
cp -r ${SOURCES_REPO_PATH}/build/distributions/* ./

git add -A
git commit -m "Release PokeGoDex TMP"

git push

cd ${SOURCES_REPO_PATH}

#sed -i 's/const val applicationRoot = "\/pogolitics"/const val applicationRoot = ""/g' \
#  ./src/main/kotlin/pogolitcs/EnvVariables.kt
git checkout ./src/main/kotlin/pogolitics/EnvVariables.kt