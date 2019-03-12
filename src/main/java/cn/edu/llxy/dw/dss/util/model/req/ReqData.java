package cn.edu.llxy.dw.dss.util.model.req;

import cn.edu.llxy.dw.dss.util.model.MetaData;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ReqData")
public class ReqData {
	private MetaData metadata;

	public MetaData getMetadata() {
		return metadata;
	}

	public void setMetadata(MetaData metadata) {
		this.metadata = metadata;
	}
}
