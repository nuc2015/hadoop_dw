package cn.edu.llxy.dw.dss.vo.cfg;

import java.util.ArrayList;
import java.util.List;

public class TimeSpliterVo {

	private List<TimeVo> timeVoList;

	public TimeSpliterVo() {
		timeVoList = new ArrayList<TimeVo>();
	}

	public void add(TimeVo vo){
		timeVoList.add(vo);
	}
	
	public List<TimeVo> getTimeVoList() {
		return timeVoList;
	}

	public void setTimeVoList(List<TimeVo> timeVoList) {
		this.timeVoList = timeVoList;
	}
}
