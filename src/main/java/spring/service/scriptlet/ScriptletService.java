package spring.service.scriptlet;

import java.util.List;

import spring.service.common.BaseObjectService;
import us.mn.state.health.lims.scriptlet.valueholder.Scriptlet;

public interface ScriptletService extends BaseObjectService<Scriptlet, String> {
	void getData(Scriptlet scriptlet);

	List getPreviousScriptletRecord(String id);

	Integer getTotalScriptletCount();

	List getPageOfScriptlets(int startingRecNo);

	Scriptlet getScriptletByName(Scriptlet scriptlet);

	List getNextScriptletRecord(String id);

	Scriptlet getScriptletById(String scriptletId);

	List getScriptlets(String filter);

	List getAllScriptlets();
}
