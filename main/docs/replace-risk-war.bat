G:
cd G:\apache-tomcat-7.0.21\webapps
rmdir /s /Q risk_management
del risk_management.war
copy G:\otherprojects\chaitanya\risk-management\target\risk_management-1.0.war .
rem rename files
rename risk_management-1.0.war risk_management.war