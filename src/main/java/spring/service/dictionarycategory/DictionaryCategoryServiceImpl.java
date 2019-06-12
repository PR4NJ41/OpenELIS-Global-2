package spring.service.dictionarycategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.service.common.BaseObjectServiceImpl;
import us.mn.state.health.lims.common.exception.LIMSDuplicateRecordException;
import us.mn.state.health.lims.dictionarycategory.dao.DictionaryCategoryDAO;
import us.mn.state.health.lims.dictionarycategory.valueholder.DictionaryCategory;

@Service
public class DictionaryCategoryServiceImpl extends BaseObjectServiceImpl<DictionaryCategory, String>
		implements DictionaryCategoryService {
	@Autowired
	protected DictionaryCategoryDAO baseObjectDAO;

	DictionaryCategoryServiceImpl() {
		super(DictionaryCategory.class);
	}

	@Override
	protected DictionaryCategoryDAO getBaseObjectDAO() {
		return baseObjectDAO;
	}

	@Override
	public String insert(DictionaryCategory dictionaryCategory) {
		if (getBaseObjectDAO().duplicateDictionaryCategoryExists(dictionaryCategory)) {
			throw new LIMSDuplicateRecordException(
					"Duplicate record exists for " + dictionaryCategory.getCategoryName());
		}
		return super.insert(dictionaryCategory);
	}

	@Override
	public DictionaryCategory save(DictionaryCategory dictionaryCategory) {
		if (getBaseObjectDAO().duplicateDictionaryCategoryExists(dictionaryCategory)) {
			throw new LIMSDuplicateRecordException(
					"Duplicate record exists for " + dictionaryCategory.getCategoryName());
		}
		return super.save(dictionaryCategory);
	}

	@Override
	public DictionaryCategory update(DictionaryCategory dictionaryCategory) {
		if (getBaseObjectDAO().duplicateDictionaryCategoryExists(dictionaryCategory)) {
			throw new LIMSDuplicateRecordException(
					"Duplicate record exists for " + dictionaryCategory.getCategoryName());
		}
		return super.update(dictionaryCategory);
	}

	@Override
	public DictionaryCategory getDictionaryCategoryByName(String name) {
		return getMatch("categoryName", name).orElse(null);
	}

}
