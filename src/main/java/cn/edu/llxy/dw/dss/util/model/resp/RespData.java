package cn.edu.llxy.dw.dss.util.model.resp;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;
import cn.edu.llxy.dw.dss.util.model.MetaData;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RespData")
public class RespData {
	@XStreamAlias("metadata")
	private MetaData metaData;

	private List<MetaData> list;

	public static RespData parseXml(Element dataElm) {
		RespData respData = new RespData();
		Element metaElm = dataElm.element("metadata");
		if (metaElm != null) {
			respData.setMetaData(MetaData.parseXml(metaElm));
		}
		return respData;
	}

	public static RespData parseListXml(Element dataElm) {
		RespData respData = new RespData();
		Element listElm = dataElm.element("list");
		if (listElm != null) {
			List<Element> metaElms = listElm.elements("metadata");
			if (metaElms != null) {
				List<MetaData> metaDatas = new ArrayList<MetaData>();
				for (Element metaElm : metaElms) {
					MetaData metaData = MetaData.parseXml(metaElm);
					metaDatas.add(metaData);
				}
				respData.setList(metaDatas);
			}
		}
		return respData;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public List<MetaData> getList() {
		return list;
	}

	public void setList(List<MetaData> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "RespData [metaData=" + metaData + ", list=" + list + "]";
	}

}
