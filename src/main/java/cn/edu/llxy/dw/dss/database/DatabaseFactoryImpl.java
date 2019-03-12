package cn.edu.llxy.dw.dss.database;

import cn.edu.llxy.dw.dss.vo.cfg.EtlManDbResourceVo;
import org.pentaho.di.core.exception.KettlePluginException;
import org.pentaho.di.core.plugins.DatabasePluginType;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.springframework.stereotype.Component;


@Component("_DatabaseFactory")
public class DatabaseFactoryImpl implements EtlDataBaseFactory{

	//private IDbPwdService dbPwdService;
	
	public DatabaseFactoryImpl() throws Exception {
		PluginRegistry.addPluginType(DatabasePluginType.getInstance());
		try {
			PluginRegistry.init();
		} catch (KettlePluginException e) {
			e.printStackTrace();
		} catch (Exception ee){
			ee.printStackTrace();
			throw ee;
		}
		System.out.println("aaaaaaaaaaaaaaaaaaaa");
	}

	public EtlDataBaseInterface getDatabase(EtlManDbResourceVo dbResource) throws Exception {
		/*	if(!StringUtil.isEmpty(dbResource.getDbUserPsdLab())){
				List<String> list = dbPwdService.getDbPwd(dbResource.getDbUserPsdLab());
				dbResource.setDbUser(list.get(0));
				dbResource.setDbPassword(list.get(1));
			}*/
			return new EtlDataBase(dbResource);
	}
	
/*	public IDbPwdService getDbPwdService() {
		return dbPwdService;
	}

	public void setDbPwdService(IDbPwdService dbPwdService) {
		this.dbPwdService = dbPwdService;
	}*/

}
