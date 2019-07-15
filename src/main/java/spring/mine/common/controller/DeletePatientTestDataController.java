package spring.mine.common.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import spring.mine.common.service.DatabaseCleanService;
import spring.service.history.HistoryService;
import us.mn.state.health.lims.audittrail.valueholder.History;
import us.mn.state.health.lims.common.exception.LIMSRuntimeException;
import us.mn.state.health.lims.common.log.LogEvent;
import us.mn.state.health.lims.common.util.ConfigurationProperties;
import us.mn.state.health.lims.patient.util.PatientUtil;

@Controller
public class DeletePatientTestDataController extends BaseController {

	@Autowired
	private DatabaseCleanService databaseCleanService;
	@Autowired
	private HistoryService historyService;

	@GetMapping(value = "/DatabaseCleaningRequest")
	public String confirmCleanSamplePatientDatabaseEntries() {
		return findForward(FWD_SUCCESS);
	}

	@PostMapping(value = "/DatabaseCleaningRequest")
	public String cleanSamplePatientDatabaseEntries(HttpServletRequest request) {

		if (!"true".equals(ConfigurationProperties.getInstance().getPropertyValueLowerCase(ConfigurationProperties.Property.TrainingInstallation))) {
			return findForward(FWD_FAIL_DELETE);
		}
		databaseCleanService.cleanDatabase();

		try {
			History history = new History();
			history.setActivity("T");
			history.setTimestamp( new Timestamp(System.currentTimeMillis()));

			history.setNameKey("Database");
			history.setReferenceId("0");
			history.setReferenceTable("0");
			history.setSysUserId(getSysUserId(request));
			historyService.save(history);

			PatientUtil.invalidateUnknownPatients();
		} catch (HibernateException e) {
			LogEvent.logError("DeletePatientTestData","performAction()",e.toString());
			throw new LIMSRuntimeException("Error in DeletePatientTestData performAction()", e);
		}


		return findForward(FWD_SUCCESS_DELETE);
	}

	@Override
	protected String findLocalForward(String forward) {

		if (FWD_SUCCESS_DELETE.equals(forward)) {
			return "masterListsPageDefinition";
		} else if (FWD_SUCCESS.equals(forward)) {
			return "databaseCleaningDefinition";
		}
		return "redirect:/Home.do";
	}

	@Override
	protected String getPageTitleKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPageSubtitleKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
