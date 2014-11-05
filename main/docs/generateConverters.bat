@echo off
set source_folder=W:\Workspaces\risk_module\risk_management
set dto_folder=%source_folder%\src\main\java\com\conflux\service\dto
set bo_folder=%source_folder%\src\main\java\com\conflux\dal\bo
set converter_file=%source_folder%\src\main\java\com\conflux\service\Converter.java
w:
cd %dto_folder%
for %%i in (*.java) do (
Call :populateDefaultContent %%i
)
GOTO:EOF

:populateDefaultContent
set dtofilename=%1
echo DTO File Name: %dtofilename%
set newfilename=%dtofilename%
set dtoname=%newfilename:.java=%
set boname=%newfilename:DTO.java=%
set dtovariablename=%dtoname%
set bovariablename=%boname%
echo DTO File Name: %dtofilename%
echo DTO Object: %dtoname%
echo DTO variable:%dtovariablename%
echo Business Object: %boname%
echo Business Object Variable: %bovariablename%

ren "%dtofilename%" "%newfilename%" 



set firstalphabetofdtovariablename=%dtovariablename:~0,1%
CALL :LoCase firstalphabetofdtovariablename
set firstalphabetofdtovariablename
set dtovariablename=%firstalphabetofdtovariablename%%dtovariablename:~1%

set firstalphabetofbovariablename=%bovariablename:~0,1%
CALL :LoCase firstalphabetofbovariablename
set firstalphabetofbovariablename
set bovariablename=%firstalphabetofbovariablename%%bovariablename:~1%



ECHO public %boname% create%boname%(%dtoname% %dtovariablename%) { >>%converter_file%
ECHO		%boname% %bovariablename% = new %boname%();>>%converter_file%
ECHO		BeanUtils.copyProperties(%dtovariablename%, %bovariablename%);>>%converter_file%
ECHO.>>%converter_file%
ECHO		// ensure id is set properly >>%converter_file%
ECHO		if (%dtovariablename%.get%boname%Id() == 0) { >>%converter_file%
ECHO			%bovariablename%.set%boname%Id(null); >>%converter_file%
ECHO		} >>%converter_file%
ECHO.>>%converter_file%
ECHO		return %bovariablename%; >>%converter_file%
ECHO }>>%converter_file%
ECHO.>>%converter_file%	
ECHO  public %dtoname% create%boname%DTO(%boname% %bovariablename%) {>>%converter_file%
ECHO		%dtoname% %dtovariablename% = new %dtoname%();>>%converter_file%
ECHO		BeanUtils.copyProperties(%bovariablename%, %dtovariablename%);>>%converter_file%
ECHO		return %dtovariablename%;>>%converter_file%
ECHO }>>%converter_file%

GOTO:EOF

:LoCase
:: Subroutine to convert a variable VALUE to all lower case.
:: The argument for this subroutine is the variable NAME.
echo start
FOR %%i IN ("A=a" "B=b" "C=c" "D=d" "E=e" "F=f" "G=g" "H=h" "I=i" "J=j" "K=k" "L=l" "M=m" "N=n" "O=o" "P=p" "Q=q" "R=r" "S=s" "T=t" "U=u" "V=v" "W=w" "X=x" "Y=y" "Z=z") DO CALL SET "%1=%%%1:%%~i%%"
echo end
GOTO:EOF


