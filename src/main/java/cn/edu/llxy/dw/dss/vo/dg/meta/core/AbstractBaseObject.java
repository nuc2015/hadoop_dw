package cn.edu.llxy.dw.dss.vo.dg.meta.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public abstract class AbstractBaseObject implements Serializable {
	public static String META_MDL = "core.AbstractBaseObject";
	
	//唯一ID
	private String id;
	//名称
	private String name;
	//中文名
	private String chName;
	//不同实体，类型不同，如表对应：宽表、维度表等，如字段则对应字段具体数值类型
	private String type;
	//元模型
	//private String metaMdl;
	//父节点
	private AbstractBaseObject parent;
	//子节点
	private List<AbstractBaseObject> children;
	//描述
	private String cmt;
	//创建时间
	private Date crtDate;
	//最后修改时间
	private Date lastUpdate;
	//所属业务域
	private String bizDomain;
	//业务编号
	private String bizNo;
	//命名空间
	private String namespace;
	//引用对象，如业务宽表会引用关系型中的表
	private AbstractBaseObject refObject;
	//非核心对象外属性
	private String personalProperties;
	//实体对应时间周期
	private String cycle;
	//实体版本信息
	private String version;
	//租户信息
	private String tenantid;
	//元数据管理类型
	private String mgrType;
	
	//源表(视图)-抽取\接口表-加载
	private String useTable;
	//文件名
	private String fileName;
	//文件头
	private String fileHeader;
	//层级
	private String lvel;
	//创建者
	public String crtUser;
	
	//数据类型
	private String dataType;
	
	//数据类型长度
	private Integer length;
	
	//数据精度
	private Integer precision;

	//数据格式化规则
	private String fmtRule;
	
	//缺省展现组建
	private String defaultView;
	//对齐方式
	private String align;
	//标题
	private String title;
	//显示长度
	private Integer showLength;
	
	//功能分层
	private String layer;
	
	private String remarks;
	
	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 将专有属性封装为json格式
	 * 元数据采用通用的结构保存，专有属性以长字符串的方式保存到数据库CLOB字段
	 * @param obj
	 */
	public void wrapperPersonalProperties(Object obj){
		Field[] myDeclaredFields = this.getClass().getFields();
		Field field;
		StringBuffer otherPropertiesBuffer = new StringBuffer();
		otherPropertiesBuffer.append("{");
		for(int i=0; i<myDeclaredFields.length; i++){
			field = myDeclaredFields[i];
			 
			if(!field.getName().equals("META_MDL") && !field.getName().equals("MD_TYPE_NORMAL")
					&& !field.getName().equals("MD_TYPE_REFTABLE"))
			{
				field.setAccessible(true);
				
				try {
					Object fieldValue = field.get(obj);
					if(fieldValue == null)
						fieldValue = "";
					
					//if(obj instanceof InterfaceDef){
					if(false){
						if(field.getName().equals("columns") || field.getName().equals("paras")){
							otherPropertiesBuffer.append("\"").append(field.getName()).append("\":")
							.append(fieldValue).append(",");
						}else{
							otherPropertiesBuffer.append("\"").append(field.getName()).append("\":\"")
							.append(fieldValue).append("\",");
						}
					}else
						otherPropertiesBuffer.append("\"").append(field.getName()).append("\":\"")
						.append(fieldValue).append("\",");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(otherPropertiesBuffer.charAt(otherPropertiesBuffer.length()-1) == ','){
			otherPropertiesBuffer.deleteCharAt(otherPropertiesBuffer.length()-1);
		}
		
		otherPropertiesBuffer.append("}");
		
		this.setPersonalProperties(otherPropertiesBuffer.toString());
		
		//System.out.println();
	}
	
	public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){  
        Method method = null ;  
        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;  
                return method ;
            } catch (Exception e) {
            	e.printStackTrace();
            }  
        }  
        return null;  
    }
	
	public static Field getDeclaredField(Object object, String fieldName){  
        Field field = null ;  
          
        Class<?> clazz = object.getClass() ;  
          
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                field = clazz.getDeclaredField(fieldName) ;  
                return field ;  
            } catch (Exception e) {
            	e.printStackTrace();
            }   
        }  
      
        return null;  
    }
	
	public String getPersonalProperties() {
		return personalProperties;
	}

	public void setPersonalProperties(String personalProperties) {
		this.personalProperties = personalProperties;
	}

	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public AbstractBaseObject getParent() {
		return parent;
	}
	public void setParent(AbstractBaseObject parent) {
		this.parent = parent;
	}
	public List<AbstractBaseObject> getChildren() {
		return children;
	}
	public void setChildren(List<AbstractBaseObject> children) {
		this.children = children;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getBizDomain() {
		return bizDomain;
	}
	public void setBizDomain(String bizDomain) {
		this.bizDomain = bizDomain;
	}
	public String getBizNo() {
		return bizNo;
	}
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	public AbstractBaseObject getRefObject() {
		return refObject;
	}
	public void setRefObject(AbstractBaseObject refObject) {
		this.refObject = refObject;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUseTable() {
		return useTable;
	}

	public void setUseTable(String useTable) {
		this.useTable = useTable;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileHeader() {
		return fileHeader;
	}

	public void setFileHeader(String fileHeader) {
		this.fileHeader = fileHeader;
	}

	public String getLvel() {
		return lvel;
	}

	public void setLvel(String lvel) {
		this.lvel = lvel;
	}

	public String getCrtUser() {
		return crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getMgrType() {
		return mgrType;
	}

	public void setMgrType(String mgrType) {
		this.mgrType = mgrType;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getFmtRule() {
		return fmtRule;
	}

	public void setFmtRule(String fmtRule) {
		this.fmtRule = fmtRule;
	}

	public String getDefaultView() {
		return defaultView;
	}

	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getShowLength() {
		return showLength;
	}

	public void setShowLength(Integer showLength) {
		this.showLength = showLength;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}
}
