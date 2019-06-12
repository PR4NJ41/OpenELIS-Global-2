package spring.service.dataexchange.aggregatereporting;

import java.sql.Timestamp;
import java.util.List;

import spring.service.common.BaseObjectService;
import us.mn.state.health.lims.dataexchange.aggregatereporting.valueholder.ReportExternalImport;

public interface ReportExternalImportService extends BaseObjectService<ReportExternalImport, String> {
	List<ReportExternalImport> getReportsInDateRangeSortedForSite(Timestamp beginning, Timestamp end, String site);

	List<ReportExternalImport> getReportsInDateRangeSorted(Timestamp lower, Timestamp upper);

	ReportExternalImport getReportByEventDateSiteType(ReportExternalImport importReport);

	List<String> getUniqueSites();

	void updateReports(List<ReportExternalImport> insertableImportReports,
			List<ReportExternalImport> updatableImportReports);
}
