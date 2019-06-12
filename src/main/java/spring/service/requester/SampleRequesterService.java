package spring.service.requester;

import java.util.List;

import spring.service.common.BaseObjectService;
import us.mn.state.health.lims.requester.valueholder.SampleRequester;

public interface SampleRequesterService extends BaseObjectService<SampleRequester, String> {

	List<SampleRequester> getRequestersForSampleId(String sampleId);
}
