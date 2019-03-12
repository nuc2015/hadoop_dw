package cn.edu.llxy.dw.dss.util;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

public class FusionChartTool {
	public static final String Column2D="Column2D";
	public static final String StackedColumn2D="StackedColumn2D";
	public static final String MSCombiDY2D="MSCombiDY2D";
	
	public static final String[] chartColor={"1D8BD1","F6BD0F","2AD62A","F1683C","8BBA00","DBDC25","AFD8F8","e438e6","7e38e6","e6e438","98dd3e","d623d8","26d062"};
	
	public static String checkSingleChart(String chart){
		if(chart.equals("Line")){
			return "single";
		}else if(chart.equals("Area2D")){
			return "single";
		}else if(chart.equals("Pie3D")){
			return "single";
		}else if(chart.equals("Pie2D")){
			return "single";
		}else if(chart.equals("Doughnut3D")){
			return "single";
		}else if(chart.equals("Doughnut2D")){
			return "single";
		}else if(chart.equals("Bar2D")){
			return "single";
		}else if(chart.equals("Column3D")){
			return "single";
		}else if(chart.equals("Column2D")){
			return "single";
		}else if(chart.equals("AngularGauge")){
			return "AngularGauge";
		}else if(chart.equals("Scatter")){
			return "Scatter";
		}
		return "double";
    }
	
	public static String createChartXml(List list,String chartType,String caption,String xAxisName,String yAxisName){
		if("single".equals(checkSingleChart(chartType))){
			return getSingleChart(list,caption,xAxisName,yAxisName);
		}else if("double".equals(checkSingleChart(chartType))){
			return getDoubleChart(list,caption,xAxisName,yAxisName);
		}
		return "";
	}
	public static String createChartXml(List list,String chartType,String caption,String xAxisName,String yAxisName,String seriesName1,String seriesName2){
		if("single".equals(checkSingleChart(chartType))){
			return getSingleChart(list,caption,xAxisName,yAxisName);
		}else if("double".equals(checkSingleChart(chartType))){
			//return getDoubleChart(list,caption,xAxisName,yAxisName);
			return getDoubleChart(list,caption,xAxisName,yAxisName,seriesName1,seriesName2);
		}else{
			return getDoubleChart(list,caption,xAxisName,yAxisName,seriesName1,seriesName2);
		}
	}
	
	
	public static String getSingleChart(List list,String caption,String xAxisName,String yAxisName){
		StringBuffer chartxml=new StringBuffer();
		chartxml.append("<chart chartRightMargin='30' rotateNames='0' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' chartLeftMargin='0'  chartBottomMargin='0' showValues='0' decimals='0' formatNumberScale='0'  canvasBorderThickness ='0' canvasBorderColor ='ffffff' canvasBgColor ='ffffff' bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff' >");
		for(int i=0;list !=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
			String value_=map.get("VALUE_")==null ? "" : map.get("VALUE_").toString();
			chartxml.append("<set label='"+label_.trim()+"' value='"+value_+"' /> ");
		}
		chartxml.append("</chart>");
		return chartxml.toString();
	}
	
	public static String getSingleChart(List list,String caption,String xAxisName,String yAxisName,String step){
		StringBuffer chartxml=new StringBuffer();
		chartxml.append("<chart labelStep='"+step+"' chartRightMargin='30' rotateNames='0' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' chartLeftMargin='0'  chartBottomMargin='0' showValues='0' decimals='0' formatNumberScale='0'  canvasBorderThickness='0' canvasBorderColor ='ffffff' canvasBgColor ='ffffff' bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff' >");
		for(int i=0;list !=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
			String value_=map.get("VALUE_")==null ? "" : map.get("VALUE_").toString();
			chartxml.append("<set label='"+label_.trim()+"' value='"+value_+"' /> ");
		}
		chartxml.append("</chart>");
		return chartxml.toString();
	}
	//是否有边框
	public static String getSingleNoBorderChart(List list,String caption,String xAxisName,String yAxisName){
		StringBuffer chartxml=new StringBuffer();
		chartxml.append("<chart rotateNames='0' palette='2' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' chartRightMargin='30' chartLeftMargin='0' chartBottomMargin='0' showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' canvasBgAlpha ='0' canvasBorderColor='ffffff' canvasBgColor='ffffff' canvasBorderThickness='0' bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff'>");
		for(int i=0;list !=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			String link_str="";
			if(map.get("code")!=null){
				link_str="link='javascript:fusionChartFunction("+map.get("CODE").toString()+");'";
			}
			String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
			String value_=map.get("VALUE_")==null ? "" : map.get("VALUE_").toString();
			chartxml.append("<set label='"+label_.trim()+"' value='"+value_+"' "+link_str+" /> ");
		}
		chartxml.append("</chart>");
		return chartxml.toString();
	}
	
	//是否有边框无背景
			public static String getSingleNoBorderStepChart(List list,String caption,String xAxisName,String yAxisName,String step){
				StringBuffer chartxml=new StringBuffer();
				chartxml.append("<chart formatNumberScale='1' rotateNames='0' palette='2' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' chartRightMargin='30' chartLeftMargin='0' chartBottomMargin='0' showValues='0' decimals='0' useRoundEdges='1' canvasBgAlpha ='0' canvasBorderColor='ffffff' canvasBgColor='ffffff' canvasBorderThickness='0' bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff' labelStep='"+step+"' >");
				for(int i=0;list !=null && i<list.size();i++){
					Map map=(HashMap)list.get(i);
					String link_str="";
					if(map.get("code")!=null){
						link_str="link='javascript:fusionChartFunction("+map.get("CODE").toString()+");'";
					}
					String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
					String value_=map.get("VALUE_")==null ? "" : map.get("VALUE_").toString();
					chartxml.append("<set label='"+label_.trim()+"' value='"+value_+"' "+link_str+" /> ");
				}
				chartxml.append("</chart>");
				return chartxml.toString();
			}
	
	//是否有边框无背景
		public static String getSingleNoBorderNumdivlinesChart(List list,String caption,String xAxisName,String yAxisName,String step){
			StringBuffer chartxml=new StringBuffer();
			chartxml.append("<chart showLimits ='0' chartRightMargin='30' rotateNames='0' numdivlines='0' palette='2' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' labelStep='"+step+"'  showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' canvasBgAlpha='0' canvasBorderColor='ffffff' canvasBgColor='ffffff' canvasBorderThickness='0'  bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff'>");
			for(int i=0;list !=null && i<list.size();i++){
				Map map=(HashMap)list.get(i);
				String link_str="";
				if(map.get("code")!=null){
					link_str="link='javascript:fusionChartFunction("+map.get("CODE").toString()+");'";
				}
				String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
				String value_=map.get("VALUE_")==null ? "" : map.get("VALUE_").toString();
				chartxml.append("<set label='"+label_.trim()+"' value='"+value_+"' "+link_str+" /> ");
			}
			chartxml.append("</chart>");
			return chartxml.toString();
		}
	
	//无边框并设置开始值
		public static String getSingleNoBorderChangeMinChart(List list,String caption,String xAxisName,String yAxisName,String yAxisMinValue,String yAxisMaxValue){
			StringBuffer chartxml=new StringBuffer();
			//chartRightMargin='40' chartTopMargin='40'
			chartxml.append("<chart chartRightMargin='30' rotateNames='0' palette='2' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' yAxisMinValue='"+yAxisMinValue+"' yAxisMaxValue='"+yAxisMaxValue+"' chartLeftMargin='0'  chartBottomMargin='0' showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' canvasBgAlpha ='0' canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness ='0'  bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff'>");
			for(int i=0;list !=null && i<list.size();i++){
				Map map=(HashMap)list.get(i);
				String link_str="";
				if(map.get("code")!=null){
					link_str="link='javascript:fusionChartFunction("+map.get("CODE").toString()+");'";
				}
				String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
				String value_=map.get("VALUE_")==null ? "" : map.get("VALUE_").toString();
				chartxml.append("<set label='"+label_.trim()+"' value='"+value_+"' "+link_str+" /> ");
			}
			chartxml.append("</chart>");
			return chartxml.toString();
		}
		
		//无边框并设置开始值设置步长
				public static String getSingleNoBorderChangeMinSetStepChart(List list,String caption,String xAxisName,String yAxisName,String yAxisMinValue,String yAxisMaxValue,String step){
					StringBuffer chartxml=new StringBuffer();
					//chartRightMargin='40' chartTopMargin='40'
					chartxml.append("<chart chartRightMargin='30' rotateNames='0' palette='2' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' yAxisValuesStep='"+step+"' yAxisMinValue='"+yAxisMinValue+"' yAxisMaxValue='"+yAxisMaxValue+"' chartLeftMargin='0'  chartBottomMargin='0' showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' canvasBgAlpha='0' canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness='0' bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff'>");
					for(int i=0;list !=null && i<list.size();i++){
						Map map=(HashMap)list.get(i);
						String link_str="";
						if(map.get("code")!=null){
							link_str="link='javascript:fusionChartFunction("+map.get("CODE").toString()+");'";
						}
						String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
						String value_=map.get("VALUE_")==null ? "" : map.get("VALUE_").toString();
						chartxml.append("<set label='"+label_.trim()+"' value='"+value_+"' "+link_str+" /> ");
					}
					chartxml.append("</chart>");
					return chartxml.toString();
				}

	public static boolean contrastList(Map m,List<Map> list,String str){
		try{
		for(Map newm:list){
			if(m.get(str).equals(newm.get(str))){
				return true;
			}
		}
		}catch(Exception ex){}
		return false;
	}
	
	public static String getValue_(List<Map> list,Map map_1,Map map_2){
		String setVal="0";
		for(Map map_val:list){
			try{
				if(map_1.get("LABEL_1").equals(map_val.get("LABEL_1")) && map_2.get("LABEL_2").equals(map_val.get("LABEL_2")) ){
					setVal=map_val.get("VALUE_").toString();
				}
			}catch(Exception ex){}
		}
		return setVal;
	}
	public static String getDoubleChart(List<Map> list,String caption,String xAxisName,String yAxisName){
		StringBuffer chartxml=new StringBuffer();
		String categories="<categories>";
		String dataset="";
		String setVal="";
		
		List<Map> label_1=new ArrayList();
		List<Map> label_2=new ArrayList();
		for(Map m : list){
			if(!contrastList(m,label_1,"LABEL_1")){
				label_1.add(m);
			}
		}
		for(Map m : list){
			if(!contrastList(m,label_2,"LABEL_2")){
				label_2.add(m);
			}
		}
		
		String format="shownames='1' showvalues='0'   decimals='0' useRoundEdges='1'";
		chartxml.append("<chart palette='2' caption='"+caption.trim()+"' "+format+">");
		for(Map map_1 : label_1){
			try{
				categories+="<category label='"+map_1.get("LABEL_1").toString().trim()+"' />";
			}catch(Exception ex){}
		}
		
		for(int i=0;i<label_2.size();i++){
			try{
			Map map_2=label_2.get(i);
			String color="";
			try{color=chartColor[i];}catch(Exception ex){ex.printStackTrace();}
			dataset+="<dataset seriesName='"+map_2.get("LABEL_2")+"' color='"+color+"' showValues='0'>";
			setVal="";
			for(Map map_1:label_1){
				setVal+="<set value='"+getValue_(list,map_1,map_2)+"'/>";
			}
			}catch(Exception ex){}
			dataset+=setVal+"</dataset>";
		}
		
		categories+="</categories>";
		chartxml.append(categories+dataset+"</chart>");
		System.out.println("--------------------------------chartXML---------------------------------------------------------------------");
		System.out.println(chartxml.toString());
		return chartxml.toString();
	}
	
	public static String getDoubleChart(List list,String caption,String xAxisName,String yAxisName,String seriesName1,String seriesName2){
		StringBuffer chartxml=new StringBuffer();
		String categories="<categories>";
		String dataset1="<dataset seriesName='"+seriesName1.trim()+"' color='528cb6' anchorRadius='2' >";
		String dataset2="<dataset seriesName='"+seriesName2.trim()+"' color='c63100' anchorRadius='2' renderAs='Line' parentYAxis='P'>";
		
		chartxml.append("<chart rotateNames='0' palette='2' caption='"+caption.trim()+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' 	canvasBgColor ='ffffff'  bgcolor='ffffff' borderColor='a6a6a3' divLineDecimalPrecision='1' limitsDecimalPrecision='1'  numberPrefix='' formatNumberScale='0'>");
		for(int i=0;list!=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
			categories+="<category label='"+label_.trim()+"' />";
			dataset1+="<set value='"+map.get("VALUE_1").toString().trim()+"' />";
			dataset2+="<set value='"+map.get("VALUE_2").toString().trim()+"' />";
		}
		categories+="</categories>";
		dataset1+="</dataset>";
		dataset2+="</dataset>";
		chartxml.append(categories+dataset1+dataset2+"</chart>");
		return chartxml.toString();
	}
	
	public static String getDoubleNoBorderChart(List list,String caption,String xAxisName,String yAxisName,String seriesName1,String seriesName2){
		StringBuffer chartxml=new StringBuffer();
		String categories="<categories>";
		String dataset1="<dataset seriesName='"+seriesName1.trim()+"' color='528cb6' anchorRadius='2' >";
		String dataset2="<dataset seriesName='"+seriesName2.trim()+"' color='c63100' anchorRadius='2' renderAs='Line' parentYAxis='P'>";
		
		chartxml.append("<chart rotateNames='0' palette='2' caption='"+caption.trim()+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness ='0'  bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff' borderColor='a6a6a3' divLineDecimalPrecision='1' limitsDecimalPrecision='1'  numberPrefix='' formatNumberScale='0'>");
		for(int i=0;list!=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
			categories+="<category label='"+label_.trim()+"' />";
			dataset1+="<set value='"+map.get("VALUE_1").toString().trim()+"' />";
			dataset2+="<set value='"+map.get("VALUE_2").toString().trim()+"' />";
		}
		categories+="</categories>";
		dataset1+="</dataset>";
		dataset2+="</dataset>";
		chartxml.append(categories+dataset1+dataset2+"</chart>");
		return chartxml.toString();
	}
	
	//柱和线的双坐标图,seriesName设置 name 每个区间的seriesName属性,还有renderAs和parentYAxis属性,renderAs可设 Line,Area,column等;parentYAxis可设p或S
	public static String getDoubleCombiDYChart(List list,String caption,String syAxisName,String pyAxisName,List seriesName){
		StringBuffer chartxml=new StringBuffer();
		String categories="<categories>";
		List dataset=new ArrayList();
		for(int i=0; seriesName!=null && i<seriesName.size(); i++){
			String str_="";
			Map map=(HashMap)seriesName.get(i);
			try{
				str_="<dataset seriesName='"+map.get("name").toString()+"' color='"+chartColor[i]+"' anchorRadius='2' ";
				//如果有renderAs
				if(map.get("renderAs")!=null && !map.get("renderAs").equals("")){
					str_+=map.get("renderAs").toString();//" renderAs='Line' ";
				}
				if(map.get("parentYAxis")!=null && !map.get("parentYAxis").equals("")){
					str_+=map.get("parentYAxis");//" parentYAxis='P' ";
				}
				str_+=">";
				
				for(int j=0; list!=null && j<list.size(); j++){
					map=(HashMap)list.get(j);
					if(i==0){
						String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
						categories+="<category label='"+label_.trim()+"' />";
					}
					str_+="<set value='"+map.get("VALUE_"+(i+1)).toString().trim()+"' />";
				}
				str_+="</dataset>";
				dataset.add(str_);
			}catch(Exception ex){}
		}
		categories+="</categories>";
		
		chartxml.append("<chart rotateNames='0' palette='2' caption='"+caption.trim()+"' PYAxisName='"+pyAxisName+"' SYAxisName='"+syAxisName+"' showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' 	canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness ='0'  bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff' borderColor='a6a6a3' divLineDecimalPrecision='1' limitsDecimalPrecision='1'  numberPrefix='' formatNumberScale='0'>");
		
		
		chartxml.append(categories);
		for(int i=0; seriesName!=null && i<seriesName.size(); i++){
			chartxml.append(dataset.get(i));
		}
		chartxml.append("</chart>");
		return chartxml.toString();
	}
	
	//柱和线的双坐标图,seriesName设置 name 每个区间的seriesName属性,还有renderAs和parentYAxis属性,renderAs可设 Line,Area,column等;parentYAxis可设p或S
		public static String getDoubleCombiDYChangeMinChart(List list,String caption,String syAxisName,String pyAxisName,List seriesName,String yAxisMinValue,String yAxisMaxValue){
			StringBuffer chartxml=new StringBuffer();
			String categories="<categories>";
			List dataset=new ArrayList();
			for(int i=0; seriesName!=null && i<seriesName.size(); i++){
				String str_="";
				Map map=(HashMap)seriesName.get(i);
				try{
					str_="<dataset seriesName='"+map.get("name").toString()+"' color='"+chartColor[i]+"' anchorRadius='2' ";
					//如果有renderAs
					if(map.get("renderAs")!=null && !map.get("renderAs").equals("")){
						str_+=map.get("renderAs").toString();//" renderAs='Line' ";
					}
					if(map.get("parentYAxis")!=null && !map.get("parentYAxis").equals("")){
						str_+=map.get("parentYAxis");//" parentYAxis='P' ";
					}
					str_+=">";
					
					for(int j=0; list!=null && j<list.size(); j++){
						map=(HashMap)list.get(j);
						if(i==0){
							String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
							categories+="<category label='"+label_.trim()+"' />";
						}
						str_+="<set value='"+map.get("VALUE_"+(i+1)).toString().trim()+"' />";
					}
					str_+="</dataset>";
					dataset.add(str_);
				}catch(Exception ex){}
			}
			categories+="</categories>";
			
			chartxml.append("<chart rotateNames='0' palette='2' caption='"+caption.trim()+"' PYAxisName='"+pyAxisName+"' SYAxisName='"+syAxisName+"' PYAxisMinValue='"+yAxisMinValue+"' PYAxisMaxValue='"+yAxisMaxValue+"' showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' 	canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness ='0'  bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff' borderColor='a6a6a3' divLineDecimalPrecision='1' limitsDecimalPrecision='1'  numberPrefix='' formatNumberScale='0'>");
			
			
			chartxml.append(categories);
			for(int i=0; seriesName!=null && i<seriesName.size(); i++){
				chartxml.append(dataset.get(i));
			}
			chartxml.append("</chart>");
			return chartxml.toString();
		}
	
	public static String getAngularGauge(Map map){
		int minVal=0;
		int val=0;
		int maxVal=0;
		String min="";
		String max="";
		String value="";
			min=map.get("MIN")==null ? "0" : map.get("MIN").toString();
			max=map.get("MAX")==null ? "0" : map.get("MAX").toString();
			value=map.get("VAL")==null ? "0" : map.get("VAL").toString();
		try{
			minVal=Integer.parseInt(min);
			maxVal=Integer.parseInt(max);
			val=maxVal+(maxVal*100);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		String xml=" <Chart bgColor='FFFFFF' upperLimit='100' lowerLimit='0' baseFontColor='000000' majorTMNumber='11' majorTMColor='000000'  majorTMHeight='10' minorTMNumber='5'  "
		+" minorTMColor='000000' minorTMHeight='3' toolTipBorderColor='FFFFFF' toolTipBgColor='ffffff' gaugeScaleAngle='270' "
		+" placeValuesInside='1' annRenderDelay='0' gaugeFillMix='' pivotRadius='12' showPivotBorder='0' pivotFillMix='{CCCCCC},{333333}' pivotFillRatio='45,45' showShadow='1'> "
		+" <colorRange>	"
		+" 	<color minValue='0' maxValue='"+minVal+"' code='FF0000' alpha='40'/> 	"
		+" 	<color minValue='"+minVal+"' maxValue='"+maxVal+"' code='FFFF00' alpha='40'/>"
		+" 	<color minValue='"+maxVal+"' maxValue='"+val+"' code='008000' alpha='40'/>"
		+" </colorRange> "
		+" <dials> 	"
		+" 	<dial value='"+value+"' borderColor='FFFFFF' bgColor='FC0000,da4440,F00000' borderAlpha='0' baseWidth='15'/>"
		+" </dials>	"
		+" <annotations>"
		+" 	<annotationGroup xPos='68' yPos='68' showBelow='1'>		"
		+" 		<annotation type='circle' xPos='0' yPos='0' radius='60' startAngle='0' endAngle='360' fillColor='FFFFFF,FFFFFF'  fillPattern='linear'   fillRatio='70,70' fillAnge='100'/>"
		+" 		<annotation type='circle' xPos='0' yPos='0' radius='65' startAngle='0' endAngle='360' fillColor='FFFFFF,FFFFFF'  fillPattern='linear'   fillRatio='70,70' fillAngle='0'/>"
		+" 	</annotationGroup> "
		+" </annotations>  "
		+" </Chart> ";
		return xml;
	}

	/**
	 * 圈图
	 */
	public static String getDoughnutChart(List list) {
		
		StringBuffer chartxml=new StringBuffer();
		chartxml.append("<chart palette='2'  showValues='0' decimals='0' formatNumberScale='0'  canvasBgAlpha ='0' canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness ='0'  bgcolor='ffffff'  borderColor='ffffff'>");
		for(int i=0;list !=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
			chartxml.append("<set label='"+label_.trim()+"' value='"+map.get("VALUE_").toString()+"' /> ");
		}
		chartxml.append("</chart>");
		return chartxml.toString();
	}

	/**
	 * 横向块状图
	 */
	public static String getBarChart(List list, String caption, String axisNameX, String axisNameY,String numberSuffix,String showValues,String decimals) {
		StringBuffer chartxml=new StringBuffer();
		chartxml.append("<chart caption='"+caption+"' xAxisName='"+axisNameX+"' yAxisName='"+axisNameY+"' numberSuffix='"+numberSuffix+"' showValues='"+showValues+"' decimals='"+decimals+"' " +
				"formatNumberScale='0'  canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness ='0' " +
				"bgcolor='ffffff'  borderColor='ffffff'>");
		for(int i=0;list !=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
			String value_=map.get("VALUE_")==null ? "" : map.get("VALUE_").toString();
			chartxml.append("<set label='"+label_.trim()+"' value='"+value_+"' /> ");
		}
		chartxml.append("</chart>");
		return chartxml.toString();
	}
	
	/**
	 * 盘图
	 */
	public static String getPieChart(List list) {
		
		StringBuffer chartxml=new StringBuffer();
		chartxml.append("<chart palette='4'  showValues='0' decimals='0' formatNumberScale='0'  canvasBgAlpha ='0' canvasBorderColor ='ffffff' " +
				"canvasBgColor ='ffffff' canvasBorderThickness ='0'  bgcolor='ffffff'  borderColor='ffffff'>");
		for(int i=0;list !=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			chartxml.append("<set label='"+map.get("LABEL_").toString().trim()+"' value='"+map.get("VALUE_").toString()+"' /> ");
		}
		chartxml.append("</chart>");
		return chartxml.toString();
	}

	/**
	 * 生成没有边框一个柱多个产品和多线的组合图表的Xml
	 * @return
	 */
	public static String getStColLineChart(List list, String caption,String axisNameX, String axisNameY, List columnNames ,List linesNames ,String sNumberSuffix) {
		
		StringBuffer chartxml=new StringBuffer();
		chartxml.append("<chart caption='' subcaption='' xaxisname='' PYaxisname='"+axisNameX+"' SYAxisName='"+axisNameY+"' decimals='' numberPrefix='' numberSuffix='' " +
				" setAdaptiveSYMin='1' showPlotBorder='1' palette='3' useRoundEdges='1' canvasBgAlpha ='0' " +
				"canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness ='0'  bgcolor='ffffff'  borderColor='ffffff' sNumberSuffix='"+sNumberSuffix+"'>");
		chartxml.append("<categories font='Arial' fontSize='12' fontColor='000000'>");
		for(int i=0;list !=null && i<list.size();i++){
			Map map=(HashMap)list.get(i);
			chartxml.append("<category label='"+map.get("LABEL_").toString().trim()+"'/> ");
		}
		chartxml.append("</categories>");
		chartxml.append("<dataset>");
		for(int i=0;columnNames !=null && i<columnNames.size();i++){
			Map map=(HashMap)columnNames.get(i);
			chartxml.append("<dataSet seriesName='"+map.get("name").toString()+"' color='"+chartColor[i]+"' showValues='0'>");
			for(int j=0;list !=null && j<list.size();j++){
				Map mapL=(HashMap)list.get(j);
				chartxml.append("<set value='"+mapL.get("VALUE_1_"+(i+1)).toString().trim()+"' />");
			}
			chartxml.append("</dataSet>");			
		}
		chartxml.append("</dataset>");
		
		
		for(int i=0;linesNames !=null && i<linesNames.size();i++){
			Map map=(HashMap)linesNames.get(i);
			chartxml.append("<lineSet seriesname='"+map.get("name").toString()+"' showValues='0' lineThickness='4'>");	
			for(int j=0;list !=null && j<list.size();j++){
				Map mapL=(HashMap)list.get(j);
				chartxml.append("<set value='"+mapL.get("VALUE_2_"+(i+1)).toString().trim()+"' />");
			}
			chartxml.append("</lineSet>");
		}
		
		chartxml.append("</chart>");
		return chartxml.toString();
	}
	
	/**
	 * 是否有边框
	 */
		public static String getSingleNoBorderChart(List list,String caption,String xAxisName,String yAxisName,String numberSuffix,String showValues,String decimals,String isDate) throws ParseException{
			StringBuffer chartxml=new StringBuffer();
			chartxml.append("<chart palette='2' caption='"+caption+"' xAxisName='"+xAxisName+"' yAxisName='"+yAxisName+"' numberSuffix='"+numberSuffix+"' showValues='"+showValues+"' decimals='"+decimals+"' formatNumberScale='0' useRoundEdges='1' canvasBgAlpha ='0' canvasBorderColor ='ffffff' canvasBgColor ='ffffff' canvasBorderThickness ='0'  bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff'>");
			
			//判断X坐标是否为日期
			if(isDate.equals("1"))
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
				Map mapDate=(HashMap)list.get(list.size()-1);
				String dateS =  mapDate.get("LABEL_").toString().trim();
				Date dateD = df.parse(dateS);

				for(int j=11 ;j>=0;j--)
				{
					int isMatch = 0;
					Calendar cal=Calendar.getInstance();
					cal.setTime(dateD);
					cal.add(Calendar.MONTH, -j);
					Date lastMonth = cal.getTime();
					String lastMonthS = df.format(lastMonth);
					for(int i=0;list !=null && i<list.size();i++){
						Map map=(HashMap)list.get(i);
						if(map.get("LABEL_").toString().trim().equals(lastMonthS))
						{
							isMatch = 1;
							String link_str="";
							if(map.get("code")!=null){
								link_str="link='javascript:fusionChartFunction("+map.get("code").toString()+");'";
							}
							chartxml.append("<set label='"+map.get("LABEL_").toString().trim()+"' value='"+map.get("VALUE_").toString()+"' "+link_str+" /> ");
						}				
					}
					if(isMatch ==0)
					{
						chartxml.append("<set label='"+lastMonthS+"' value='0'/> ");
					}		
				}
			}
			else
			{
				for(int i=0;list !=null && i<list.size();i++){
					Map map=(HashMap)list.get(i);
					String link_str="";
					if(map.get("code")!=null){
						link_str="link='javascript:fusionChartFunction("+map.get("code").toString()+");'";
					}
					chartxml.append("<set label='"+map.get("LABEL_").toString().trim()+"' value='"+map.get("VALUE_").toString()+"' "+link_str+" /> ");
				}
			}
			
			chartxml.append("</chart>");
			return chartxml.toString();
		}
		
		
		//带百分比符号
		//柱和线的双坐标图,seriesName设置 name 每个区间的seriesName属性,还有renderAs和parentYAxis属性,renderAs可设 Line,Area,column等;parentYAxis可设p或S
		public static String getDoubleCombiDYChart(List list,String caption,String syAxisName,String pyAxisName,List seriesName,String sNumberSuffix){
			StringBuffer chartxml=new StringBuffer();
			String categories="<categories>";
			List dataset=new ArrayList();
			for(int i=0; seriesName!=null && i<seriesName.size(); i++){
				String str_="";
				Map map=(HashMap)seriesName.get(i);
				try{
					str_="<dataset seriesName='"+map.get("name").toString()+"' color='"+chartColor[i]+"' anchorRadius='2' ";
					//如果有renderAs
					if(map.get("renderAs")!=null && !map.get("renderAs").equals("")){
						str_+=map.get("renderAs").toString();//" renderAs='Line' ";
					}
					if(map.get("parentYAxis")!=null && !map.get("parentYAxis").equals("")){
						str_+=map.get("parentYAxis");//" parentYAxis='P' ";
					}
					str_+=">";
					
					for(int j=0; list!=null && j<list.size(); j++){
						map=(HashMap)list.get(j);
						if(i==0){
							String label_=map.get("LABEL_")==null ? "" : map.get("LABEL_").toString();
							categories+="<category label='"+label_.trim()+"' />";
						}
						str_+="<set value='"+map.get("VALUE_"+(i+1)).toString().trim()+"' />";
					}
					str_+="</dataset>";
					dataset.add(str_);
				}catch(Exception ex){}
			}
			categories+="</categories>";
			
			chartxml.append("<chart palette='2' caption='"+caption.trim()+"' PYAxisName='"+pyAxisName+"' SYAxisName='"+syAxisName+"' " +
					"showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' 	canvasBorderColor ='ffffff' canvasBgColor ='ffffff' " +
					"canvasBorderThickness ='0'  bgcolor='ffffff' lineColor='FCB541' borderColor='ffffff' borderColor='a6a6a3' " +
					"divLineDecimalPrecision='1' limitsDecimalPrecision='1'  numberPrefix='' formatNumberScale='0' sNumberSuffix='"+sNumberSuffix+"'>");
			
			
			chartxml.append(categories);
			for(int i=0; seriesName!=null && i<seriesName.size(); i++){
				chartxml.append(dataset.get(i));
			}
			chartxml.append("</chart>");
			return chartxml.toString();
		}

	
}
