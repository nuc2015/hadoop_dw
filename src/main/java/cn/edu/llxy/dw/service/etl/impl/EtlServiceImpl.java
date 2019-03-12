package cn.edu.llxy.dw.service.etl.impl;

import cn.edu.llxy.dw.dao.etl.EtlDao;
import cn.edu.llxy.dw.dss.database.EtlDataBaseFactory;
import cn.edu.llxy.dw.dss.database.EtlDataBaseInterface;
import cn.edu.llxy.dw.dss.po.dg.MetaCore;
import cn.edu.llxy.dw.dss.po.dg.MetaMeta;
import cn.edu.llxy.dw.dss.util.DateUtil;
import cn.edu.llxy.dw.dss.vo.cfg.EtlManDbResourceVo;
import cn.edu.llxy.dw.dss.vo.dg.MetaMetaVo;
import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.DataSource;
import cn.edu.llxy.dw.service.etl.EtlService;
import net.sf.json.JSONObject;
import org.pentaho.di.core.database.DatabaseMeta;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "etlService")
@Transactional
public class EtlServiceImpl implements EtlService {

    @Resource
    private EtlDao etlDao;

    @Autowired
    private EtlDataBaseFactory etlDataBaseFactory;

    /**
     * 根据tableId查询该表的数据
     * @param tableId
     * @param args
     * @return
     */
    @Override
    public List<Map<String, Object>> queryTableData(String tableId, String... args) {
        String[] split = tableId.split("\\.");
        String dsId = split[0];
        String dbName = split[1];
        String tbName = split[2];
        List<Map<String, Object>> tableData = new ArrayList<>();
        /*MetaCore dsM = (MetaCore)etlDao.get(MetaCore.class, dsId);
        if("mysql".equalsIgnoreCase(dsM.getType())){
            tableData = etlDao.getMysqlTableData(dbName, tbName);
        }else if("hive".equalsIgnoreCase(dsM.getType())){
            tableData = etlDao.getHiveTableData(dbName, tbName);
        }*/
        DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);
        EtlManDbResourceVo dbvo =  getEtlManDbResourceVo(ds);
        EtlDataBaseInterface database = null;
        try {
            database = etlDataBaseFactory.getDatabase(dbvo);
            database.connect();
            Connection connection = database.getConnection();
            PreparedStatement pst = null;
            String sql = "select * from "+tbName;
            pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                Map<String, Object> entity = new HashMap<String, Object>();
                for (int i = 1; i<=columnCount; i++){
                    entity.put(md.getColumnName(i), rs.getObject(i));
                }
                tableData.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(database != null){
                database.disconnect();
            }
        }
        return tableData;
    }


    /**
     * 获取转换后的元数据实例
     * @param cls
     * @param id
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public Object getMetaInst(Class cls, String id) {
        MetaCore cm = (MetaCore)etlDao.get(MetaCore.class, id);
        if(cm == null)
            return null;
        else{
            try {
                Object obj = cls.newInstance();

                BeanUtils.copyProperties(cm, obj);

                //处理RefObject
                if(cm.getRefObject() != null){
                    Object refInstance =  getMetaCore(cm.getRefObject());

                    Method setRefObjectMtd = getDeclaredMethod(obj, "setRefObject", new Class[] {AbstractBaseObject.class});
                    //		cls.getMethod("setRefObject", new Class[] {AbstractBasePackage.class});
                    if(setRefObjectMtd != null)
                        fillRefObj(obj,getDeclaredSpField(obj, "refObject") ,setRefObjectMtd,refInstance);
                }

                //处理json对象
                if(cm.getEntity()!= null && !cm.getEntity().equals("null")){
                    JSONObject jasonObject = JSONObject.fromObject(cm.getEntity());
                    Map<String, Object> map2 = (Map) jasonObject;

                    Field[] myDeclaredFields = cls.getFields();
                    Field field;Object value ;
                    for(int i=0; i<myDeclaredFields.length; i++){
                        field = myDeclaredFields[i];
                        String firstLetter = null;String setMethodName;Method method;
                        if(!field.getName().equals("META_MDL") && !field.getName().equals("MD_TYPE_NORMAL")
                                && !field.getName().equals("MD_TYPE_REFTABLE")
                                && !field.getName().equals("DS_TYPE_MYSQL")
                                && !field.getName().equals("MD_TYPE_DICT"))
                        {
                            field.setAccessible(true);
                            value = map2.get(field.getName());

                            firstLetter = field.getName().substring(0, 1).toUpperCase();
                            setMethodName = "set" + firstLetter
                                    + field.getName().substring(1);

                            method = cls.getMethod(setMethodName, new Class[] { field.getType() });
                            fill(obj,field,method,value+"");
                        }
                    }
                    //return obj;
                }
                return obj;
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 获取元数据实例
     * @param id
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Object getMetaCore(String id) {
        MetaCore cm = (MetaCore)etlDao.get(MetaCore.class, id);
        return wrapperObj(cm);
    }


    /**
     * 对象转换，将公告MetaCore对象转换为具体实体对象
     * @param cm
     * @return
     */
    private Object wrapperObj(MetaCore cm){
        MetaMetaVo mm = queryMetaMeta(cm.getMetaMdl());
        Class cls = null;
        try {
            cls = Class.forName(mm.getClz());
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if(cm == null)
            return null;
        else{
            try {
                Object obj = cls.newInstance();

                BeanUtils.copyProperties(cm, obj);

                //处理RefObject
                if(cm.getRefObject() != null){
                    Object refInstance =  getMetaCore(cm.getRefObject());

                    //Method setRefObjectMtd = cls.getMethod("setRefObject", new Class[] {AbstractBasePackage.class});
                    Method setRefObjectMtd = getDeclaredMethod(obj, "setRefObject", new Class[] {AbstractBaseObject.class});

                    if(setRefObjectMtd != null)
                        fillRefObj(obj,getDeclaredSpField(obj, "refObject"),setRefObjectMtd,refInstance);
                }

                //处理json对象
                if(cm.getEntity() != null && !cm.getEntity().equals("null")){
                    JSONObject jasonObject = JSONObject.fromObject(cm.getEntity());
                    Map<String, Object> map2 = (Map) jasonObject;

                    Field[] myDeclaredFields = cls.getFields();
                    Field field;Object value ;
                    for(int i=0; i<myDeclaredFields.length; i++){
                        field = myDeclaredFields[i];
                        String firstLetter = null;String setMethodName;Method method;
                        if(field!= null && !field.getName().equals("META_MDL") && !field.getName().equals("MD_TYPE_NORMAL")
                                && !field.getName().equals("MD_TYPE_REFTABLE") && !field.getName().equals("DS_TYPE_MYSQL")&& !field.getName().equals("MD_TYPE_DICT"))
                        {
                            field.setAccessible(true);
                            value = map2.get(field.getName());

                            firstLetter = field.getName().substring(0, 1).toUpperCase();
                            setMethodName = "set" + firstLetter
                                    + field.getName().substring(1);

                            method = cls.getMethod(setMethodName, new Class[] { field.getType() });
                            fill(obj,field,method,value+"");
                        }
                    }
                }
                return obj;
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     *
     * @param metaName
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MetaMetaVo queryMetaMeta(String metaName) {
        Object mm = etlDao.get(MetaMeta.class, metaName);
        if(mm != null){
            MetaMetaVo mmv = new MetaMetaVo();
            try {
                BeanUtils.copyProperties(mm, mmv);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return mmv;
        }
        else return null;
    }

    /**
     * 获取方法
     * @param object
     * @param methodName
     * @param parameterTypes
     * @return
     */
    private static Method getDeclaredMethod(Object object, String methodName,
                                            Class<?>... parameterTypes) {
        Method method = null;

        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
                .getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                if (method != null)
                    return method;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return method;
    }


    /**
     * 获取字段定义
     * @param object
     * @param fieldName
     * @return
     */
    private static Field getDeclaredSpField(Object object, String fieldName) {
        Field field = null;

        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
                .getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                if (field != null)
                    return field;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return field;
    }

    /**
     * 填充RefObject属性
     * @param bean
     * @param field
     * @param method
     * @param value
     */
    private static void fillRefObj(Object bean, Field field, Method method,
                                   Object value) {
        if (value == null)
            return;
        Object[] oo = new Object[1];

        oo[0] = value;
        try {
            method.invoke(bean, oo);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 将字符串值转换为合适的值填充到对象的指定域
     * @param bean 被填充的java bean
     * @param field 需要填充的域
     * @param value 字符串值
     */
    public static void fill(Object bean, Field field, Method method,
                            String value) {
        if (value == null || "null".equalsIgnoreCase(value))
            return;

        try {
            Object[] oo = new Object[1];

            String type = field.getType().getName();

            if ("java.lang.String".equals(type)) {
                oo[0] = value;
            } else if ("java.lang.Integer".equals(type) || "int".equals(type)) {
                if (value.length() > 0)
                    oo[0] = Integer.valueOf(value);
            } else if ("java.lang.Float".equals(type) || "float".equals(type)) {
                if (value.length() > 0)
                    oo[0] = Float.valueOf(value);
            } else if ("java.lang.Double".equals(type) || "double".equals(type)) {
                if (value.length() > 0)
                    oo[0] = Double.valueOf(value);
            } else if ("java.math.BigDecimal".equals(type)) {
                if (value.length() > 0)
                    oo[0] = new BigDecimal(value);
            } else if ("java.util.Date".equals(type)) {
                if (value.length() > 0)
                    oo[0] = DateUtil.parseDate(value, "yyyy-mm-dd hh:mi:ss")
                            ;
            } else if ("java.sql.Timestamp".equals(type)) {
                if (value.length() > 0)
                    oo[0] = DateUtil.parseDate(value, "yyyy-mm-dd hh:mi:ss");
            } else if ("java.lang.Boolean".equals(type) || "boolean".equals(type)) {
                if (value.length() > 0)
                    oo[0] = Boolean.valueOf(value);
            } else if ("java.lang.Long".equals(type)) {
                if (value.length() > 0)
                    oo[0] = Long.valueOf(value);
            }

            method.invoke(bean, oo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EtlManDbResourceVo getEtlManDbResourceVo(DataSource ds){
        EtlManDbResourceVo dbvo = new EtlManDbResourceVo();
        dbvo.setDbType(ds.getType());
        dbvo.setDbConType(DatabaseMeta.TYPE_ACCESS_NATIVE+"");
        dbvo.setDbHost(ds.getHost());
        dbvo.setDbName(ds.getServerName());
        dbvo.setDbPort(ds.getPort());
        dbvo.setDbUser(ds.getUser());
        dbvo.setDbPassword(ds.getPwd());
        dbvo.setKrb5ConfigPath(ds.getKrb5ConfigPath());
        dbvo.setHadoopHomeDir(ds.getHadoopHomeDir());
        dbvo.setKeytabPath(ds.getKeytabPath());
        dbvo.setPrincipal(ds.getPrincipal());
        return dbvo;
    }
}
