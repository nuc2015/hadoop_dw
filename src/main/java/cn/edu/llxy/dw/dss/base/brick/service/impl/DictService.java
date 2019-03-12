package cn.edu.llxy.dw.dss.base.brick.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.llxy.dw.dss.base.brick.service.IDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.llxy.dw.dss.base.brick.vo.DictCodeMapVo;
import cn.edu.llxy.dw.dss.base.brick.vo.DictVo;
import cn.edu.llxy.dw.dss.brick.dao.IDictDao;
import cn.edu.llxy.dw.dss.po.brick.Dict;
import cn.edu.llxy.dw.dss.po.brick.DictCodeMap;

@Service(value="dictService")
@Transactional(readOnly = false)
public class DictService implements IDictService {
	@Autowired
	private IDictDao dictDao;
	
	@Override
	public DictVo getDict(String classCode) throws Exception {
		Dict dict = (Dict)dictDao.get(Dict.class, classCode);
		
		DictVo dv = new DictVo();
		BeanUtils.copyProperties(dict, dv);
		
		List<DictCodeMap> codeMaps = dict.getCodeMaps();
		List<DictCodeMapVo> codeMapVos = new ArrayList();
		for(DictCodeMap m:codeMaps){
			DictCodeMapVo mapVo = new DictCodeMapVo();
			BeanUtils.copyProperties(m, mapVo);
			
			codeMapVos.add(mapVo);
		}
		dv.setCodeMapVos(codeMapVos);
		return dv;
	}

	@Override
	public Map<String, String> getDictMap(String classCode) throws Exception {
		List<Object[]> classCodes = dictDao.queryByHql("select d.code,d.name from DictCodeMap d where d.classCode=?", classCode);
		Map<String,String> ret = new HashMap();
		
		for(Object[] o:classCodes){
			ret.put(o[0]+"", o[1]+"");
		}
		return ret;
	}

	@Override
	public List<Object[]> getOrderDict(String classCode) throws Exception {
		return dictDao.queryByHql("select d.code,d.name from DictCodeMap d where d.classCode=?", classCode);
	}
	
	public String getBizDomainPath(String code) throws Exception{
		List<Object[]> domains = dictDao.queryByHql("select d.classCode, d.code ,d.name from DictCodeMap d where d.code=?", code);
		if(domains.size() > 0){
			Object[] domain = domains.get(0);
			
			Dict dict = (Dict)dictDao.get(Dict.class, domain[0]+"");
			
			return "/" + dict.getName()+"/" + domain[2];
		}
		
		return null;
	}
	
	public Map<String, String> getDwCategoryInfo() throws Exception {
		List<Object[]> categories = dictDao.queryByHql("select d.code,d.name from DictCodeMap d where d.classCode=?", "dw.category");
		
		List<Object[]> subCates;
		
		Map<String, String> ret = new HashMap();
		for(Object[] category:categories){
			subCates = dictDao.queryByHql("select d.code,d.name from DictCodeMap d where d.classCode=?", category[0]);
			for(Object[] subCate:subCates){
				ret.put(category[1]+"."+subCate[1], subCate[0]+"");
			}
		}
		return ret;
	}
}
