package us.mn.state.health.lims.testconfiguration.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import us.mn.state.health.lims.common.action.BaseAction;
import us.mn.state.health.lims.common.services.DisplayListService;
import spring.service.localization.LocalizationServiceImpl;
//import spring.service.test.TestSectionServiceImpl;
import spring.service.unitofmeasure.UnitOfMeasureServiceImpl;
import us.mn.state.health.lims.common.util.ConfigurationProperties;
// import us.mn.state.health.lims.test.valueholder.TestSection;
import us.mn.state.health.lims.unitofmeasure.valueholder.UnitOfMeasure;

public class UomCreateAction extends BaseAction {
    public static final String NAME_SEPARATOR = "$";
    
    @Override
    protected ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ((DynaValidatorForm)form).initialize(mapping);
        PropertyUtils.setProperty(form, "existingUomList", DisplayListService.getInstance().getList(DisplayListService.ListType.UNIT_OF_MEASURE));
        PropertyUtils.setProperty(form, "inactiveUomList", DisplayListService.getInstance().getList(DisplayListService.ListType.UNIT_OF_MEASURE_INACTIVE));
        List<UnitOfMeasure> uoms = UnitOfMeasureServiceImpl.getInstance().getAllUnitOfMeasures();
        PropertyUtils.setProperty(form, "existingEnglishNames", getExistingUomNames(uoms, ConfigurationProperties.LOCALE.ENGLISH));
        PropertyUtils.setProperty(form, "existingFrenchNames", getExistingUomNames(uoms, ConfigurationProperties.LOCALE.FRENCH));

        return mapping.findForward(FWD_SUCCESS);
    }

    
    private String getExistingUomNames(List<UnitOfMeasure> uoms, ConfigurationProperties.LOCALE locale) {
        StringBuilder builder = new StringBuilder(NAME_SEPARATOR);

        for( UnitOfMeasure uom : uoms){
            builder.append(LocalizationServiceImpl.getLocalizationValueByLocal(locale, uom.getLocalization()));
            builder.append(NAME_SEPARATOR);
        }

        return builder.toString();
    }
    


    @Override
    protected String getPageTitleKey() {
        return null;
    }

    @Override
    protected String getPageSubtitleKey() {
        return null;
    }
}
