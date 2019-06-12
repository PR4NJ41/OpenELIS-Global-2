package spring.service.sampleproject;

import java.sql.Date;
import java.util.List;

import spring.service.common.BaseObjectService;
import us.mn.state.health.lims.sampleproject.valueholder.SampleProject;

public interface SampleProjectService extends BaseObjectService<SampleProject, String> {
	void getData(SampleProject sampleProj);

	List getSampleProjectsByProjId(String projId);

	SampleProject getSampleProjectBySampleId(String id);

	List<SampleProject> getByOrganizationProjectAndReceivedOnRange(String organizationId, String projectName,
			Date lowDate, Date highDate);
}
