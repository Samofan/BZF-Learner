#!/bin/zsh

APP_NAME=$1
APP_VERSION=$2
APP_DESCRIPTION=$3
OUTPUT_NAME=$4

cd target
mkdir jpackage
mv $APP_NAME jpackage
mv lib jpackage

jpackage --input jpackage \
--dest ./ \
--name BZF-Learner \
--main-jar $APP_NAME \
--main-class com.floriandinter.BzfLearner \
--type dmg \
--app-version $APP_VERSION \
--vendor "Florian Dinter" \
--copyright "2022 Florian Dinter" \
--description $APP_DESCRIPTION \
--mac-package-name "$OUTPUT_NAME" \
--icon "../src/main/resources/images/headset.icns"

rm -r jpackage