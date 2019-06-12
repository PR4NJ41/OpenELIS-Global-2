/**
* The contents of this file are subject to the Mozilla Public License
* Version 1.1 (the "License"); you may not use this file except in
* compliance with the License. You may obtain a copy of the License at
* http://www.mozilla.org/MPL/
*
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
* License for the specific language governing rights and limitations under
* the License.
*
* The Original Code is OpenELIS code.
*
* Copyright (C) CIRG, University of Washington, Seattle WA.  All Rights Reserved.
*
*/
package us.mn.state.health.lims.dataexchange.aggregatereporting.daoimpl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.mn.state.health.lims.common.daoimpl.BaseDAOImpl;
import us.mn.state.health.lims.common.exception.LIMSRuntimeException;
import us.mn.state.health.lims.dataexchange.aggregatereporting.dao.ReportExternalImportDAO;
import us.mn.state.health.lims.dataexchange.aggregatereporting.valueholder.ReportExternalImport;

@Component
@Transactional
public class ReportExternalImportDAOImpl extends BaseDAOImpl<ReportExternalImport, String>
		implements ReportExternalImportDAO {

	public ReportExternalImportDAOImpl() {
		super(ReportExternalImport.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportExternalImport> getReportsInDateRangeSorted(Timestamp lower, Timestamp upper)
			throws LIMSRuntimeException {
		String sql = "from ReportExternalImport rq where rq.eventDate >= :lower and rq.eventDate <= :upper order by rq.sendingSite";

		try {
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			query.setTimestamp("lower", lower);
			query.setTimestamp("upper", upper);

			List<ReportExternalImport> reports = query.list();

			// closeSession(); // CSL remove old

			return reports;
		} catch (HibernateException e) {
			handleException(e, "getReportsInDateRangeSorted");
		}

		return null;
	}

//	@Override
//	public void insertReportExternalImport(ReportExternalImport report) throws LIMSRuntimeException {
//		try {
//			String id = (String) sessionFactory.getCurrentSession().save(report);
//			report.setId(id);
//			auditDAO.saveNewHistory(report, report.getSysUserId(), "REPORT_EXTERNAL_IMPORT");
//			// closeSession(); // CSL remove old
//		} catch (HibernateException e) {
//			handleException(e, "insertReportExternalImport");
//		}
//	}

//	@Override
//	public void updateReportExternalImport(ReportExternalImport report) throws LIMSRuntimeException {
//		ReportExternalImport oldData = readReportExternalImport(report.getId());
//
//		try {
//
//			auditDAO.saveHistory(report, oldData, report.getSysUserId(), IActionConstants.AUDIT_TRAIL_UPDATE,
//					"REPORT_EXTERNAL_IMPORT");
//
//			sessionFactory.getCurrentSession().merge(report);
//			// sessionFactory.getCurrentSession().flush(); // CSL remove old
//			// sessionFactory.getCurrentSession().clear(); // CSL remove old
//			// sessionFactory.getCurrentSession().evict // CSL remove old(report);
//			// sessionFactory.getCurrentSession().refresh // CSL remove old(report);
//		} catch (Exception e) {
//			handleException(e, "updateReportExternalImport");
//		}
//	}

	public ReportExternalImport readReportExternalImport(String idString) throws LIMSRuntimeException {

		try {
			ReportExternalImport data = sessionFactory.getCurrentSession().get(ReportExternalImport.class, idString);
			// closeSession(); // CSL remove old
			return data;
		} catch (HibernateException e) {
			handleException(e, "readReportExternalImport");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUniqueSites() throws LIMSRuntimeException {
		String sql = "select distinct sending_site from clinlims.report_external_import ";
		try {
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			List<String> sites = query.list();
			// closeSession(); // CSL remove old
			return sites;
		} catch (HibernateException e) {
			handleException(e, "getUniqueSites");
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportExternalImport> getReportsInDateRangeSortedForSite(Timestamp lower, Timestamp upper, String site)
			throws LIMSRuntimeException {
		String sql = "from ReportExternalImport rq where rq.eventDate >= :lower and rq.eventDate <= :upper and rq.sendingSite = :site order by rq.sendingSite";

		try {
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			query.setTimestamp("lower", lower);
			query.setTimestamp("upper", upper);
			query.setString("site", site);

			List<ReportExternalImport> reports = query.list();

			// closeSession(); // CSL remove old

			return reports;
		} catch (HibernateException e) {
			handleException(e, "getReportsInDateRangeSortedForSite");
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReportExternalImport getReportByEventDateSiteType(ReportExternalImport importReport)
			throws LIMSRuntimeException {
		String sql = "from ReportExternalImport rei where rei.eventDate = :eventDate and rei.sendingSite = :site and rei.reportType = :type";

		try {
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			query.setDate("eventDate", importReport.getEventDate());
			query.setString("site", importReport.getSendingSite());
			query.setString("type", importReport.getReportType());

			List<ReportExternalImport> reports = query.list();

			// closeSession(); // CSL remove old

			return reports.isEmpty() ? new ReportExternalImport() : reports.get(0);

		} catch (HibernateException e) {
			handleException(e, "getReportByEventDateSiteType");
		}

		return null;
	}

}
