@echo off

rem  
rem Setup env PATH for command line (win32)
rem

rem setup maven
set ETC_HOME=%~dp0
set MAVEN_HOME=%ETC_HOME%..\maven

rem setup java 
set JAVA_HOME=d:\devl\tools\java\64\jdk1.8.0_66

rem setup path
set PATH=%MAVEN_HOME%\bin;%PATH%
set PATH=%JAVA_HOME%\bin;%PATH%

