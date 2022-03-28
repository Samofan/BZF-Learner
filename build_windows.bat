@echo off

SET APP_NAME=%1
SET APP_VERSION=%2
SET APP_DESCRIPTION=%3
SET OUTPUT_NAME=%4

CD target
MKDIR jpackage
MOVE %APP_NAME% jpackage
MOVE lib jpackage

jpackage --input jpackage ^
--dest ./ ^
--name BZF-Learner ^
--main-jar %APP_NAME% ^
--main-class com.floriandinter.BzfLearner ^
--type msi ^
--app-version %APP_VERSION% ^
--vendor "Florian Dinter" ^
--copyright "2022 Florian Dinter" ^
--description %APP_DESCRIPTION% ^
--icon "../src/main/resources/images/headset32.ico" ^
--win-shortcut ^
--win-dir-chooser

RMDIR /S /Q "jpackage"