echo off
set source_folder=W:\Workspaces\risk_module\risk_management
set bo_folder=%source_folder%\src\main\java\com\conflux\dal\bo
set dto_folder=%source_folder%\src\main\java\com\conflux\service\dto
w:
cd %bo_folder% 
for %%i in (*.java) do (
Call :populateDefaultContent %%i
)
GOTO:EOF

:remove
set filename=%1
set classname=%filename:.java=%
set dtoname=%classname%DTO.java
echo creating DTO %dtoname% in %dto_folder% 
findstr /v "@ javax ( ) hbm2 Hibernate *  / this ,  }"  %1 > %dto_folder%\%dtoname%
ECHO } >>%dto_folder%\%dtoname%
GOTO:EOF

:populateDefaultContent
set filename=%1
set classname=%filename:.java=%
set dtoname=%classname%DTO.java
echo creating DTO %dtoname% in %dto_folder% 
findstr /v "@ javax ( ) hbm2 Hibernate *  / this ,  }"  %1 > %dto_folder%\%dtoname%
ECHO } >>%dto_folder%\%dtoname%
GOTO:EOF

:replaceDummyContent
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java com.conflux.dal.bo com.conflux.service.dto
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Integer int
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Float float
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Boolean boolean
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java implements " "
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java java.io.Serializable " "
rem Changing all references DTO's to DAO's
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Asset AssetDTO 
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java AssetSubType AssetSubTypeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java AssetType AssetTypeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Audit AuditDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java AuditQuestion AuditQuestionDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java AuditType AuditTypeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java AuditTypeScoring AuditTypeScoringDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java AuditTypeScoringId AuditTypeScoringIdDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Customer CustomerDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java CustomerFamilyMember CustomerFamilyMemberDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java CustomerFamilyOccupation CustomerFamilyOccupationDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java CustomerType CustomerTypeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Employee EmployeeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java ExternalLoan ExternalLoanDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java ExternalOrganization ExternalOrganizationDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java LoanAccount LoanAccountDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java LoanProduct LoanProductDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java LoanPurpose LoanPurposeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java LoanStatus LoanStatusDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java LoanUtilizationCheck LoanUtilizationCheckDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java MailSender MailSenderDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Meeting MeetingDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Occupation OccupationDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java OccupationSubType OccupationSubTypeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java OccupationType OccupationTypeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Office OfficeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java OfficeType OfficeTypeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java OrgEntity OrgEntityDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java PortalUser PortalUserDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java Question QuestionDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java RatingBucket RatingBucketDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java RelationshipType RelationshipTypeDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java RiskRatingHistory RiskRatingHistoryDTO  
C:\Users\dv6\Desktop\fart.exe -w -- %dto_folder%\*.java UserRole UserRoleDTO 
GOTO:EOF

