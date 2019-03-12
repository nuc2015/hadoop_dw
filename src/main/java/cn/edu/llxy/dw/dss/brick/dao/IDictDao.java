package cn.edu.llxy.dw.dss.brick.dao;

import cn.edu.llxy.dw.dss.po.brick.Dict;
import cn.edu.llxy.dw.dssframework.hibernate4.dao.IBaseDao;

public interface IDictDao extends IBaseDao {
	Dict getDict(String classCode);
}