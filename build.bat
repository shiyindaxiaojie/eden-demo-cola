@echo off

if exist "%JAVA_HOME%" (
  echo Java Path: %JAVA_HOME%
  echo Java Version: 
  java -version
)

if exist "%E3_HOME%" (
  echo Maven Path: %E3_HOME%
  echo Maven Version: 
  call mvn -version
)

set "skipTests="

:targetProfile
echo Please choose Profile. 1: Development, 2: Test, 3: Staging, 4: Production, 0: Exit
set /p selectProfile=Please press the above numbers to enter the options and press Enter to confirm:
if /i "%selectProfile%"=="" goto prod
if /i "%selectProfile%"=="1" goto dev
if /i "%selectProfile%"=="2" goto test
if /i "%selectProfile%"=="3" goto staging
if /i "%selectProfile%"=="4" goto prod
if /i "%selectProfile%"=="0" exit

:dev
echo Development environment selected
set "profile=dev"
goto targetTests

:test
echo Test environment selected
set "profile=test"
goto targetTests

:pre
echo Staging environment selected
set "profile=staging"
goto targetTests

:prod
echo Production environment selected
set "profile=prod"
goto targetTests

:targetTests
echo Please choose whether to skip the test before building. Y: Yes, N: No
set /p skipTests=Please press the above numbers to enter the options and press Enter to confirm:
if /i "%skipTests%"=="" goto skipTests 
if /i "%skipTests%"=="Y" goto skipTests 
if /i "%skipTests%"=="y" goto skipTests 
if /i "%skipTests%"=="N" goto doTests
if /i "%skipTests%"=="n" goto doTests

:skipTests 
echo Test skip selected
set "skipTests=-DskipTests=true"
goto build

:doTests 
echo Test exec selected
set "skipTests="
goto build

:build
echo Start building, please wait. . . . . .
echo Command: mvn clean compile package -e -P %profile% %skipTests%
call mvn clean compile package -e -P %profile% %skipTests%
echo.&echo The build is complete, you can copy executable program from %cd%/target, press any key to exit &pause>nul
exit


