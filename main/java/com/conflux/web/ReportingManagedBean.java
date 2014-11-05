package com.conflux.web;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceCreationException;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceKeyCreationException;
import org.pentaho.reporting.libraries.resourceloader.ResourceLoadingException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

import com.conflux.common.ApplicationConstants.PENTAHO_REPORT;
import com.conflux.common.ApplicationUtils;
import com.conflux.service.dto.CustomerDTO;

@ManagedBean(name = "reportBean")
@SessionScoped
public class ReportingManagedBean extends AbstractManagedBean implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private int branchId;
	private Integer[] selectedBranches;
	private int centerId;
	private Date date;
	private Integer year;
	private Map<String, Integer> centers = new LinkedHashMap<String, Integer>();
	
	public String createRepaymentCapacityReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.REPAYMENT_CAPACITY_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);

		// set the report parameters
		report.getParameterValues().put("office", new Long(this.branchId));
		report.getParameterValues().put("center", new Long(this.centerId));
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createRepaymentCapacityDetailReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.REPAYMENT_CAPACITY_DETAIL_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);

		// set the report parameters
		report.getParameterValues().put("office", new Long(this.branchId));
		report.getParameterValues().put("center", new Long(this.centerId));
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createCenterWiseAttendaceReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.CENTERWISE_ATTENDACE_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);
		
		// set the report parameters
		report.getParameterValues().put("selectBranch", new Integer(this.branchId));
		report.getParameterValues().put("date", date);
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createBranchWiseAttendaceReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.BRANCHWISE_ATTENDACE_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);
		
		// set the report parameters
		report.getParameterValues().put("selectBranch", this.selectedBranches);
		report.getParameterValues().put("date", this.date);
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createMonthlyBranchWiseAttendaceReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.MONTHLY_BRANCHWISE_ATTENDACE_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);
		
		// set the report parameters
		report.getParameterValues().put("selectBranch", this.branchId);
		report.getParameterValues().put("year", this.year);
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createBranchwiseLoanUtilisationSummaryReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.BRANCHWISE_LOAN_UTILISATION_SUMMARY_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);
		
		// set the report parameters
		report.getParameterValues().put("selectBranch", this.selectedBranches);
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createCenterAssesmentReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.CENTER_ASSESMENT_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);
		
		// set the report parameters
		report.getParameterValues().put("select_office", new Integer(this.branchId));
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createIncomeAssesmentReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.INCOME_ASSESMENT_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);
		
		// set the report parameters
		report.getParameterValues().put("select_office", new Integer(this.branchId));
		report.getParameterValues().put("select_center", new Integer(this.centerId));
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createLoanUtilisationReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.LOAN_UTILISATION_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);
		
		// set the report parameters
		report.getParameterValues().put("selectBranch", new Integer(this.branchId));
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	public String createMonthlyLoanUtilisationSummaryReport() throws Exception {
		// Load the report definition
		final String reportName = PENTAHO_REPORT.MONTHLY_LOAN_UTILISATION_SUMMARY_REPORT.getReportName();
		MasterReport report = loadReportFromFileSystem(reportName);
		
		// set the report parameters
		report.getParameterValues().put("selectBranch", this.selectedBranches);
		report.getParameterValues().put("date", this.date);
		
		/** Execute the report and send response back to the client **/
		generateResponse(report, reportName);
		return null;
	}
	
	private void generateResponse(MasterReport report, final String reportName) throws IOException {

		// set the response
		HttpServletResponse response = null;
		response = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		// reset the response object
		response.reset();
		generatePDFResponse(report, response, reportName);
		response.flushBuffer();
		FacesContext.getCurrentInstance().responseComplete();
	}

	private void generatePDFResponse(MasterReport report,
			HttpServletResponse response, final String reportName) throws IOException {
		// set relevant metadata
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename="
                + reportName + ".pdf");
		// write the generated pdf to outputstream
		PdfReportUtil.createPDF(report, response.getOutputStream());
	}

	private MasterReport loadReportFromFileSystem(String reportName)
			throws ResourceLoadingException, ResourceCreationException,
			ResourceKeyCreationException, MalformedURLException,
			ResourceException {
		ResourceManager manager = new ResourceManager();
		manager.registerDefaults();

		/** getting the report from file system **/
		// set the report path
		String reportPath = ApplicationUtils.REPORTS_PATH_NAME + reportName
				+ ".prpt";
		// load report from file system
		Resource resource = manager.createDirectly(reportPath,
				MasterReport.class);
		MasterReport report = (MasterReport) resource.getResource();
		return report;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void populateCentersListByBranchId(){
		List<CustomerDTO> centerList = getCustomerService().getCentersByBranchId(this.branchId);
		if(centerList == null || centerList.isEmpty()){
			centers = new LinkedHashMap<String, Integer>();
		}
		for (CustomerDTO center : centerList) {
			centers.put(center.getDisplayName(), center.getCustomerId());
		}
	}

	
	
	/**
	 * @return the branchId
	 */
	public int getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the centerId
	 */
	public int getCenterId() {
		return centerId;
	}

	/**
	 * @param centerId the centerId to set
	 */
	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	/**
	 * @return the centers
	 */
	public Map<String, Integer> getCenters() {
		return centers;
	}

	/**
	 * @param centers the centers to set
	 */
	public void setCenters(Map<String, Integer> centers) {
		this.centers = centers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer[] getSelectedBranches() {
		return selectedBranches;
	}

	public void setSelectedBranches(Integer[] selectedBranches) {
		this.selectedBranches = selectedBranches;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
		
}