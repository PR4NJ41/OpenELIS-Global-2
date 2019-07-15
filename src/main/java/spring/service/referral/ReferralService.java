package spring.service.referral;

import java.sql.Date;
import java.util.List;

import spring.service.common.BaseObjectService;
import us.mn.state.health.lims.referral.valueholder.Referral;

public interface ReferralService extends BaseObjectService<Referral, String> {

	Referral getReferralById(String referralId);

	Referral getReferralByAnalysisId(String analysisId);

	List<Referral> getAllReferralsBySampleId(String id);

	List<Referral> getAllUncanceledOpenReferrals();

	List<Referral> getAllReferralsByOrganization(String organizationId, Date lowDate, Date highDate);
}