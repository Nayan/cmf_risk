G:
cd G:\apache-tomcat-7.0.21\webapps\risk_management

rmdir /s /Q admin
rmdir /s /Q pages
rmdir /s /Q reports
rmdir /s /Q resources
rmdir /s /Q sections
rmdir /s /Q templates
del accessDenied.xhtml
del error.xhtml
del login.xhtml


set source_folder=G:\otherprojects\chaitanya\risk-management\src\main\webapp\

rem copy all files
copy %source_folder%accessDenied.xhtml .
copy %source_folder%error.xhtml .
copy %source_folder%login.xhtml .

rem copy all folders
mkdir admin
cd admin
xcopy %source_folder%admin  /e
mkdir ..\pages
cd ..\pages
xcopy %source_folder%pages  /e
mkdir ..\reports
cd ..\reports
xcopy %source_folder%reports  /e
mkdir ..\resources
cd ..\resources
xcopy %source_folder%resources  /e
mkdir ..\sections
cd ..\sections
xcopy %source_folder%sections  /e
mkdir ..\templates
cd ..\templates
xcopy %source_folder%templates  /e





