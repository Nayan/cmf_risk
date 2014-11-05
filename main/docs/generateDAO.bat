@echo off
set source_folder=W:\Workspaces\risk_module\risk_management
set dao_folder=%source_folder%\src\main\java\com\conflux\dal\dao
set bo_folder=%source_folder%\src\main\java\com\conflux\dal\bo
w:
xcopy /I %dao_folder% %dao_folder%\impl
cd %dao_folder%\impl
for %%i in (*.java) do (
Call :populateDefaultContent %%i
)
GOTO:EOF

:populateDefaultContent
set oldfilename=%1
echo Old File Name: %oldfilename%
set newfilename=%oldfilename%
set newfilename=%newfilename:~1%
set classname=%newfilename:.java=%
set interfacename=%oldfilename:.java=%
set businessobjectname=%newfilename:DAO.java=%
set tempvariablename=%newfilename:DAO.java=%
set firstalphabet=%tempvariablename:~0,1%
echo Old File Name: %oldfilename%
echo New File Name: %newfilename%
echo Class: %classname%
echo Interface: %interfacename%
echo BusinessObjectName: %businessobjectname%
echo Variable: %variablename%

ren "%oldfilename%" "%newfilename%" 

CALL :LoCase firstalphabet
set firstalphabet
set variablename=%firstalphabet%%tempvariablename:~1%
ECHO package com.conflux.dal.dao.impl; >%newfilename%
ECHO.>>%newfilename%
ECHO import java.util.List;>>%newfilename%
ECHO import org.springframework.stereotype.Repository;>>%newfilename%
ECHO import com.conflux.dal.bo.%newfilename:DAO.java=%;>>%newfilename%
ECHO import com.conflux.dal.dao.%oldfilename:.java=%;>>%newfilename%
ECHO @Repository >>%newfilename%
ECHO public class %classname% extends BaseDAO implements %interfacename% { >>%newfilename%
ECHO.>>%newfilename%
ECHO 	@Override >>%newfilename%
ECHO 	public void persist(%businessobjectname% %variablename%) { >>%newfilename%
ECHO 		getSessionFactory().getCurrentSession().persist(%variablename%);>>%newfilename%
ECHO 	}>>%newfilename%
ECHO.>>%newfilename%
ECHO 	@Override >>%newfilename%
ECHO 	public void merge(%businessobjectname% %variablename%) { >>%newfilename%
ECHO 		getSessionFactory().getCurrentSession().merge(%variablename%); >>%newfilename%
ECHO.>>%newfilename%
ECHO 	}>>%newfilename%
ECHO.>>%newfilename%
ECHO 	@Override>>%newfilename%
ECHO 	public void delete(%businessobjectname% %variablename%) {>>%newfilename%
ECHO 		getSessionFactory().getCurrentSession().delete(%variablename%);>>%newfilename%
ECHO 	}>>%newfilename%
ECHO.>>%newfilename%
ECHO 	@Override>>%newfilename%
ECHO 	public %businessobjectname% findById(int %variablename%Id) {>>%newfilename%
ECHO 		%newfilename:DAO.java=% %variablename% = (%businessobjectname%) getSessionFactory().getCurrentSession().get(>>%newfilename%
ECHO 				"com.conflux.dal.bo.%newfilename:DAO.java=%", %variablename%Id);>>%newfilename%
ECHO 		return %variablename%;>>%newfilename%
ECHO 	}>>%newfilename%
ECHO.>>%newfilename%
ECHO 	@Override>>%newfilename%
ECHO 	public List^<%businessobjectname%^> findAll() {>>%newfilename%
ECHO 		List^<%businessobjectname%^> %variablename%s = (List^<%newfilename:DAO.java=%^>) getSessionFactory()>>%newfilename%
ECHO 				.getCurrentSession()>>%newfilename%
ECHO 				.createQuery("from com.conflux.dal.bo.%businessobjectname%").list();>>%newfilename%
ECHO 		return %variablename%s;>>%newfilename%
ECHO 	}>>%newfilename%
ECHO }>>%newfilename%
GOTO:EOF

:LoCase
:: Subroutine to convert a variable VALUE to all lower case.
:: The argument for this subroutine is the variable NAME.
echo start
FOR %%i IN ("A=a" "B=b" "C=c" "D=d" "E=e" "F=f" "G=g" "H=h" "I=i" "J=j" "K=k" "L=l" "M=m" "N=n" "O=o" "P=p" "Q=q" "R=r" "S=s" "T=t" "U=u" "V=v" "W=w" "X=x" "Y=y" "Z=z") DO CALL SET "%1=%%%1:%%~i%%"
echo end
GOTO:EOF


