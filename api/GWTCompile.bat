@echo off

cd C:\Users\tanaka\git\KIfU\GWTUMLAPI

call ant jar

copy .\build\dist\gwt-umlapi.jar ..\GWTUMLDrawer\war\WEB-INF\lib\gwt-umlapi.jar

cd  ..\GWTUMLDrawer\
call ant  war

del C:\pleiades4-4\tomcat\7\webapps\KIfU4.war
copy KIfU4.war C:\pleiades4-4\tomcat\7\webapps\
rd /s /q C:\pleiades4-4\tomcat\7\webapps\KIfU4

cd ..\GWTUMLAPI
echo %time:~0,2%:%time:~3,2%:%time:~6,2%

exit /B %errorlevel%
