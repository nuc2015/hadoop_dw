package cn.edu.llxy.dw.dss.base.brick.service;

import java.util.List;
import java.util.Map;

import cn.edu.llxy.dw.dss.base.brick.vo.DictVo;

public interface IDictService {
	
	public DictVo getDict(String classCode) throws Exception;
	
	/**
	 * 
	 * @param classCode
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getDictMap(String classCode) throws Exception;
	
	/**
	 * 获取字典，带排序
	 * @param classCode
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getOrderDict(String classCode) throws Exception;
	
	public Map<String, String> getDwCategoryInfo() throws Exception;
	
	/**
	 * 获取业务分类路径
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String getBizDomainPath(String code) throws Exception;
}
