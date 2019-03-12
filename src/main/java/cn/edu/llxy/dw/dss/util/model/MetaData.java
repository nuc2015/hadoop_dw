package cn.edu.llxy.dw.dss.util.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.dom4j.Element;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("metadata")
public class MetaData {
	private String id; // 元数据ID
	@XStreamOmitField
	private String path; // 元数据路径
	private String modelId; // 元模型ID
	private String name; // 元数据名称
	private String displayName; // 元数据显示名
	private String parentId; // 元数据父节点ID
	@XStreamOmitField
	private String parentPath; // 元数据父节点路径
	@XStreamAlias("attributeList")
	private List<MetaAttribute> attributeList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public List<MetaAttribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<MetaAttribute> attributeList) {
		this.attributeList = attributeList;
	}

	public void setAttributeList(Map<String, String> attributeMap) {

		List<MetaAttribute> attributeList = new ArrayList<MetaAttribute>();
		if (attributeMap != null && !attributeMap.isEmpty()) {
			for (Entry<String, String> kv : attributeMap.entrySet()) {
				MetaAttribute attr = new MetaAttribute();
				attr.setName(kv.getKey());
				attr.setValue(kv.getValue());
				attributeList.add(attr);
			}
		}
		this.attributeList = attributeList;
	}

	@Override
	public String toString() {
		return "MetaData [id=" + id + ", path=" + path + ", modelId=" + modelId + ", name=" + name + ", displayName=" + displayName + ", parentId=" + parentId + ", parentPath=" + parentPath
				+ ", attributeList=" + attributeList + "]";
	}

	public static MetaData parseXml(Element metaElm) {
		MetaData metaData = new MetaData();
		metaData.setDisplayName(metaElm.elementTextTrim("displayName"));
		metaData.setId(metaElm.elementTextTrim("id"));
		metaData.setModelId(metaElm.elementTextTrim("modelId"));
		metaData.setName(metaElm.elementTextTrim("name"));
		metaData.setParentId(metaElm.elementTextTrim("parentId"));
		metaData.setPath(metaElm.elementTextTrim("path"));

		Element attrsElm = metaElm.element("attributeList");
		if (attrsElm != null) {
			List<MetaAttribute> attributeList = new ArrayList<MetaAttribute>();
			List<Element> attrEs = attrsElm.elements("attribute");
			for (Element attrE : attrEs) {
				MetaAttribute attr = new MetaAttribute();
				attr.setName(attrE.elementTextTrim("name"));
				attr.setValue(attrE.elementTextTrim("value"));
				attributeList.add(attr);
			}
			metaData.setAttributeList(attributeList);
		}

		return metaData;
	}
}
