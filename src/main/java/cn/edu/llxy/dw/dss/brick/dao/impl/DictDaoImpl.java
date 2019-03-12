package cn.edu.llxy.dw.dss.brick.dao.impl;

import cn.edu.llxy.dw.dss.brick.dao.IDictDao;
import cn.edu.llxy.dw.dss.po.brick.Dict;
import cn.edu.llxy.dw.dssframework.hibernate4.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DictDaoImpl  extends BaseDaoImpl implements IDictDao {
	@Override
	public Dict getDict(String classCode) {
		return (Dict)this.get(Dict.class, classCode);
	}
}
