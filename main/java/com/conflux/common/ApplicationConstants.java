package com.conflux.common;

public class ApplicationConstants {

	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String UNAUTHORIZED = "unauthorized";
	public static final String HOME_PAGE = "home";

	/** Application constant enumeration **/
	public static enum USER_ROLE {
		ADMIN(1), REGULAR_USER(2), AUDITOR(3), JUNIOR_AUDITOR(3);
		private final int roleId;

		USER_ROLE(int roleId) {
			this.roleId = roleId;
		}

		public String toString() {
			return name().toString().replaceAll("_", " ");
		}

		public int getRoleId() {
			return roleId;
		}
	}

	public static enum EMPLOYEE_ROLE {
		BRANCH_MANAGER, LOAN_OFFICER, NON_LOAN_OFFICER, OTHER;

		public String toString() {
			return name().toString().replaceAll("_", " ");
		}

	}

	public static enum SEX {
		MALE, FEMALE;
		public String toString() {
			return name().toString().toLowerCase();
		}
	}

	public static enum ENTITY_TYPE {
		BRANCH_MANAGER(2), LOAN_OFFICER(1), CENTER(3);
		private final int entityId;

		ENTITY_TYPE(int entityId) {
			this.entityId = entityId;
		}

		public String toString() {
			return name().toString().replaceAll("_", " ");
		}

		public int getEntityId() {
			return entityId;
		}
	}

	public static enum RISK_RATING {
		LOW, HIGH, NORMAL;
		public String toString() {
			return name().toString().toLowerCase();
		}
	}

	public static enum CUSTOMER_TYPE {
		VILLAGE(1), CENTER(2), GROUP(3), CLIENT(4);
		private final int customerTypeId;

		CUSTOMER_TYPE(int customerTypeId) {
			this.customerTypeId = customerTypeId;
		}

		public String toString() {
			return name().toString();
		}

		public int getCustomerTypeId() {
			return customerTypeId;
		}
	}

	/*** Navigation related constants ***/
	public static enum GENERIC_NAVIGATION {
		ADMIN, SAVE_ATTENDANCE_SHEET, HOME, VIEW_EMPLOYEE, AUDITS_IN_PROGRESS, VIEW_CENTER, VIEW_CLIENT, VIEW_CENTER_MEETINGS, VIEW_LOAN, ATTACH_AN_AUDIT, AUDIT_PROCESS, CLOSED_AUDIT_PROCESS, PENDING_LUCS, SAVE_LUCS, VIEW_CLIENTS_PENDING_UPDATE;
		@Override
		public String toString() {
			return name().toString().toLowerCase();
		}
	}

	public static enum ADMIN_NAVIGATION {
		VIEW_ALL_AUDIT_TYPES, VIEW_AUDIT_TYPE, EDIT_AUDIT, CREATE_AUDIT;
		@Override
		public String toString() {
			return name().toString().toLowerCase();
		}
	}

	public static enum USER_NAVIGATION {
		VIEW_USER, CREATE_USER, EDIT_USER, VIEW_USERS;
		@Override
		public String toString() {
			return name().toString().toLowerCase();
		}
	}
	
	public static enum STABILITY_FACTOR {
		HIGH, MEDIUM, LOW;
		@Override
		public String toString(){
			return name().toString();
		}
	}
	
	public static enum LOAN_STATUS {
		LOAN_CLOSED;

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			
			return name().toString().replaceAll("_", " ");
		}
		
	}
	
	public static enum PENTAHO_REPORT {
		REPAYMENT_CAPACITY_REPORT("repaymentCapacityReport"), 
		REPAYMENT_CAPACITY_DETAIL_REPORT("repaymentCapacityDetailReport"),
		CENTERWISE_ATTENDACE_REPORT("centerWiseAttendaceReport"), 
		BRANCHWISE_ATTENDACE_REPORT("branchWiseAttendaceReport"),
		MONTHLY_BRANCHWISE_ATTENDACE_REPORT("monthlyBranchWiseAttendaceReport"),
		BRANCHWISE_LOAN_UTILISATION_SUMMARY_REPORT("branchwiseLoanUtilisationSummaryReport"),
		CENTER_ASSESMENT_REPORT("centerAssesmentReport"),
		INCOME_ASSESMENT_REPORT("incomeAssesmentReport"),
		LOAN_UTILISATION_REPORT("loanUtilisationReport"),
		MONTHLY_LOAN_UTILISATION_SUMMARY_REPORT("monthlyLoanUtilisationSummaryReport");
		
		private final String value;

		PENTAHO_REPORT(String value) {
			this.value = value;
		}

		public String toString() {
			return name().toString().toLowerCase();
		}

		public String getReportName() {
			return value;
		}
	}
}
