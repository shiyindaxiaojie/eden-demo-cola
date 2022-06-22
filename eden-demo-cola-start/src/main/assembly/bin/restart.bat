@echo off

echo restart application
call catalina.bat stop
call catalina.bat start
exit