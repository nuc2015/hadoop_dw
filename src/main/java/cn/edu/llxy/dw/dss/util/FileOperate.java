package cn.edu.llxy.dw.dss.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import jxl.*;
import jxl.write.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.commons.lang.math.NumberUtils;


import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;

public class FileOperate{
	static final int BUFFER = 8192;
	
public void createXML(List list){
		  Document document = DocumentHelper.createDocument();
		       Element tableElement = document.addElement("table");
		       Element bodyElement =  tableElement.addElement("tablebody");
		       for(int i=0;i<list.size();i++){
		    	   Map map = new HashMap(); 
					 map=(Map)list.get(i);
					 Element trElement =  bodyElement.addElement("tr");
					 trElement.addAttribute("code", map.get("REGION_CODE").toString());
					 trElement.addAttribute("desc", map.get("REGION_DESC").toString());
					 //trElement.setText("Marcello");
		       }
		      try{
		      XMLWriter output = new XMLWriter(
		              new FileWriter( new File("d:/temp/region.xml") ));
		          output.write( document );
		          output.close();
		          }
		       catch(IOException e){System.out.println(e.getMessage());}
	  }

		public static void createXML(Document document,File file){
			try{
				XMLWriter output = new XMLWriter(new FileOutputStream( file ));
				//XMLWriter xmlWriter = new XMLWriter( new FileWriter(file));

				output.write( document );
				output.close();
			}catch(IOException e){e.printStackTrace();}
		}
		public static void createXML(String path,String roolEle){
			try{
				Document document = DocumentHelper.createDocument();
				document.setXMLEncoding("UTF-8");
				document.addElement(roolEle);
				OutputFormat format = OutputFormat.createPrettyPrint();  
				format.setEncoding("UTF-8"); 
				XMLWriter output = new XMLWriter(new  FileOutputStream( new File(path)) , format);
		        output.write( document );
		        output.close();
			}catch(IOException e){e.printStackTrace();}
		}
	  
	  public static void getXML(String path){
		    try{
		    	  SAXReader saxReader = new SAXReader();
					Document document = saxReader.read(new File(path));
					Element tableE=document.getRootElement();
					List tr=tableE.selectNodes("//tablebody/tr");
					for(int i=0;i<tr.size();i++){
					Element trE=(Element)tr.get(i);
					//System.out.println(trE.attributeCount());
					//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
					}
		    }catch(Exception e){System.out.println(e.getMessage());}
	  }
	  
	  //path:文件路径，nodeStr:要寻找的节点，attributeVal：不为空则取属性，否则取内容
	  public static String getXML(String path,String nodeStr,String attributeVal){
		  	String xmlStr="";
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element tableE=document.getRootElement();
				List tr=tableE.selectNodes(nodeStr);
				for(int i=0;i<tr.size();i++){
					Element element=(Element)tr.get(i);
					if(!attributeVal.equals("")){
						xmlStr=element.attributeValue(attributeVal);
					}else{
						xmlStr=element.getText();
					}
					//System.out.println(xmlStr);
					//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
				}
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return xmlStr;
	  }
	  
	//path:文件路径，nodeStr:要寻找的节点
	  public static String getXML(String path,String nodeStr){
		  	String xmlStr="";
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element tableE=document.getRootElement();
				List tr=tableE.selectNodes(nodeStr);
				for(int i=0;i<tr.size();i++){
					Element element=(Element)tr.get(i);
					xmlStr=element.getText();
					//System.out.println(xmlStr);
					//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
				}
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return xmlStr;
	  }
	  
	  public static List getXMLforList(String path,String nodeStr,String attributeVal){
		  	List xmlStr=new ArrayList();
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element tableE=document.getRootElement();
				List tr=tableE.selectNodes(nodeStr);
				for(int i=0;i<tr.size();i++){
					Element element=(Element)tr.get(i);
					if(!attributeVal.equals("")){
						xmlStr.add(element.attributeValue(attributeVal));
					}else{
						xmlStr.add(element.getText());
					}
					//System.out.println(xmlStr);
					//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
				}
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return xmlStr;
	  }
	  
	  public static List getXMLforList(String path,String nodeStr,String[] attributeVals){
		  	List xmlStr=new ArrayList();
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element tableE=document.getRootElement();
				List tr=tableE.selectNodes(nodeStr);
				for(int i=0;i<tr.size();i++){
					Element element=(Element)tr.get(i);
					Map map=new HashMap();
					for(int j=0;j<attributeVals.length;j++){
						String attributeVal=attributeVals[j];
						//System.out.println(element.getText());

							List childelist=element.selectNodes(attributeVal);
							if(childelist!=null && childelist.size()>0){
								Element element_child=(Element)childelist.get(0);
								map.put(attributeVal,element_child.getText());
							}
						if(element.getText().equals("") ){
						}else{
							//map.put(attributeVal,element.getText());
						}
						
					}
					xmlStr.add(map);
					//System.out.println(xmlStr);
					//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
				}
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return xmlStr;
	  }
	  
	  public static List appendXml(String path,String nodeStr,Map sel){
		  	List xmlStr=new ArrayList();
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element root=document.getRootElement();
				Element node=root.addElement(nodeStr);
				Iterator iterator=sel.keySet().iterator(); 
				while(iterator.hasNext()){ 
					Object	key=iterator.next(); 
					Object  value=sel.get(key)==null ? "" : sel.get(key); 
					Element child=node.addElement(key.toString().toLowerCase());
					child.setText(value.toString());
				} 
				
				OutputFormat format = OutputFormat.createPrettyPrint();  
			    format.setEncoding("UTF-8"); 
				XMLWriter output = new XMLWriter(new  FileOutputStream( new File(path)) , format);
	        	output.write( document );
	          	output.close();
				
		    }catch(Exception e){
		    	e.printStackTrace();
		    	System.out.println(e.getMessage());}
		    return xmlStr;
	  }
	  
	  public static List updateXml(String path,String nodeStr,String code,Map sel){
		  	List xmlStr=new ArrayList();
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element root=document.getRootElement();
				if(nodeStr.equals("")){
					Node node=root.selectSingleNode(code);
					//System.out.println(node.getText()+":"+node.getName());
					if(node.getName().equals(code)){
						node.setText(sel.get(code).toString());
					}
				}else{
					List nodes=root.selectNodes(nodeStr);
					for(int i=0;i<nodes.size();i++){
						Element element=(Element)nodes.get(i);
						Node node=element.selectSingleNode(code);
						//System.out.println(node.getText()+":"+node.getName());
						if(node.getText().equals(sel.get(code))){
							Iterator iterator=sel.keySet().iterator(); 
							while(iterator.hasNext()){ 
								Object	key=iterator.next(); 
								Object  value=sel.get(key); 
								Node chileNode=element.selectSingleNode(key.toString());
								chileNode.setText(value.toString());
							} 
						}
						//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
					}
				}
				OutputFormat format = OutputFormat.createPrettyPrint();  
			    format.setEncoding("UTF-8"); 
				XMLWriter output = new XMLWriter(new  FileOutputStream( new File(path)) , format);
	        	output.write( document );
	          	output.close();
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return xmlStr;
	  }
	  
	  public static List updateForInsertXml(String path,String nodeStr,String code,Map sel){
		  	List xmlStr=new ArrayList();
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element root=document.getRootElement();
				if(nodeStr.equals("")){
					Node node=root.selectSingleNode(code);
					//System.out.println(node.getText()+":"+node.getName());
					if(node.getName().equals(code)){
						node.setText(sel.get(code).toString());
					}
				}else{
					List nodes=root.selectNodes(nodeStr);
					boolean flag=false; //如果没有该记录就进行添加
					
					for(int i=0;i<nodes.size();i++){
						Element element=(Element)nodes.get(i);
						Node node=element.selectSingleNode(code);
						//System.out.println(node.getText()+":"+node.getName());
						if(node.getText().equals(sel.get(code))){
							Iterator iterator=sel.keySet().iterator(); 
							while(iterator.hasNext()){ 
								Object	key=iterator.next(); 
								Object  value=sel.get(key); 
								Node chileNode=element.selectSingleNode(key.toString());
								chileNode.setText(value.toString());
							} 
							flag=true;
						}
					}
					
					if(!flag){
						Element node=root.addElement(nodeStr);
						Iterator iterator=sel.keySet().iterator(); 
						while(iterator.hasNext()){ 
							Object	key=iterator.next(); 
							Object  value=sel.get(key); 
							Element child=node.addElement(key.toString());
							child.setText(value.toString());
						} 
					}
				}
				OutputFormat format = OutputFormat.createPrettyPrint();  
			    format.setEncoding("UTF-8"); 
				XMLWriter output = new XMLWriter(new  FileOutputStream( new File(path)) , format);
	        	output.write( document );
	          	output.close();
		    }catch(Exception e){
		    	
				
				
		    }
		    return xmlStr;
	  }
	  
	  public static String copyXml(String path,String newpath){
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				OutputFormat format = OutputFormat.createPrettyPrint();  
			    format.setEncoding("UTF-8"); 
				XMLWriter output = new XMLWriter(new  FileOutputStream( new File(newpath)) , format);
	        	output.write( document );
	          	output.close();
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return newpath;
	  }
	  
	  public static List removeXml(String path,String nodeStr,String code,Map sel){
		  	List xmlStr=new ArrayList();
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element root=document.getRootElement();
				List nodes=root.selectNodes(nodeStr);
				for(int i=0;i<nodes.size();i++){
					Element element=(Element)nodes.get(i);
					Node node=element.selectSingleNode(code);
					//System.out.println(node.getText()+":"+node.getName());
					if(node.getText().equals(sel.get(code))){
						root.remove(element);
					}
					//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
				}
				OutputFormat format = OutputFormat.createPrettyPrint();  
			    format.setEncoding("UTF-8"); 
				XMLWriter output = new XMLWriter(new  FileOutputStream( new File(path)) , format);
	        	output.write( document );
	          	output.close();
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return xmlStr;
	  }
	  
	  public static List saxXml(String path){
		  	List list=new ArrayList();
		    try{
		    	  SAXReader saxReader = new SAXReader();
					Document document = saxReader.read(new File(path));
					Element tableE=document.getRootElement();
					List tr=tableE.selectNodes("//tablebody/tr");
					for(int i=0;i<tr.size();i++){
						Element trE=(Element)tr.get(i);
						Map map=new HashMap();
						for(int j=0;j<trE.attributeCount();j++){
							map.put(trE.attribute(j).getName(), trE.attribute(j).getText());
						}
						list.add(map);
					}
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return list;
	  }
	  
	  public static Map getXMLforMap(String path){
		  	Map xmlMap=new HashMap();
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(path));
				Element root=document.getRootElement();
				List tr=root.elements();
				for(int i=0;i<tr.size();i++){
					Element element=(Element)tr.get(i);
					
//					System.out.println(element.getName()+":"+element.getText());
					xmlMap.put(element.getName(), element.getText());	
					//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
				}
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return xmlMap;
	  }
	 
	  //压缩一个文件
	  public static void compress(File srcdir,File zipFile) {   
	        if (!srcdir.exists())  {
	        	System.out.println("文件不存在");
	        	return ;
	        }
	            
	          
	        Project prj = new Project();  
	        Zip zip = new Zip();  
	        zip.setProject(prj);  
	        zip.setDestFile(zipFile);  
	        FileSet fileSet = new FileSet();  
	        fileSet.setProject(prj);  
	        fileSet.setFile(srcdir);  
	        //fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");  
	        //fileSet.setExcludes(...); 排除哪些文件或文件夹  
	        zip.addFileset(fileSet);  
	          
	        zip.execute();  
	    } 
	  
	  public String getProperties(String str){
		  Properties pros = new Properties();
		  try {
			InputStream in = this.getClass().getResourceAsStream("/struts.properties"); 
			pros.load(in);
			str=pros.getProperty(str);  
		  } catch (Exception e) {
				e.printStackTrace();
		  } 
		  return str;
	  }
	  
	  public String excelDownload(List list,String filename,String columns){
		  String path=getProperties("downDir");
		  String random=(DateUtil.getCurrentTime1()+System.currentTimeMillis()+new Random().nextInt());
//		  filename=filename+".xls";
		  String filepath=path+filename+".xls";
		  File file=new File(filepath);
		  WritableWorkbook workbook;
		  try {
			  workbook = Workbook.createWorkbook(file);
			  WritableSheet sheet = workbook.createSheet(filename, 0); 
			  //一些临时变量，用于写到excel中
			  Label l=null;
			  jxl.write.Number n=null;
			  DateTime d=null;
			  //预定义的一些字体和格式，同一个Excel中最好不要有太多格式
			   
			  WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false); 
			  WritableCellFormat headerFormat = new WritableCellFormat (headerFont); 
			   
			  WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false); 
			  WritableCellFormat titleFormat = new WritableCellFormat (titleFont); 
			   
			  WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
			  WritableCellFormat detFormat = new WritableCellFormat (detFont); 
			   
			  NumberFormat nf=new NumberFormat("0.00000"); //用于Number的格式
			  WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf); 
			   
			  DateFormat df=new DateFormat("yyyy-MM-dd");//用于日期的
			  WritableCellFormat dateFormat = new WritableCellFormat (detFont, df); 
	
			  String[] coldata=columns.split(",");
			   
			  //add Title
			  for(int k=0;coldata !=null && k<coldata.length;k++)
			  {
			  	l=new Label(k,0,coldata[k],headerFormat);
			  	sheet.addCell(l);
			  }
			  //add detail
			  for(int i=0;list!=null && i<list.size();i++){
				  Object[] data=(Object[])list.get(i);
				   for(int j=0 ; data!=null && j<data.length;j++){
					   if(data[j]!=null){
						   //System.out.println(data[j].toString().trim());
						   l=new Label(j, i+1, data[j].toString().trim(),detFormat);
						   sheet.addCell(l);
						   sheet.setColumnView(j, 25);
					   }
				   }
			  }
			  workbook.write();
			  workbook.close();
			  filename=random+".zip";
			     //压缩
			  filepath=path+filename;
			  File zip=new File(filepath);
			  compress(file,zip);
			  file.delete();
		  } catch (Exception e) {
			e.printStackTrace();
			filepath="";
		  } 
		  return filename;
	  }
	  
	  public String excelDownloadForHashMap(List list,String filename,String columns,String columnDescs){
		  String path=getProperties("downDir");
		  String random=(DateUtil.getCurrentTime1()+System.currentTimeMillis()+new Random().nextInt());
//		  filename=filename+".xls";
		  String filepath=path+filename+".xls";
		  File file=new File(filepath);
		  WritableWorkbook workbook;
		  try {
			  workbook = Workbook.createWorkbook(file);
			  WritableSheet sheet = workbook.createSheet(filename, 0); 
			  //一些临时变量，用于写到excel中
			  Label l=null;
			  jxl.write.Number n=null;
			  DateTime d=null;
			  //预定义的一些字体和格式，同一个Excel中最好不要有太多格式
			   
			  WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false); 
			  WritableCellFormat headerFormat = new WritableCellFormat (headerFont); 
			   
			  WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false); 
			  WritableCellFormat titleFormat = new WritableCellFormat (titleFont); 
			   
			  WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
			  WritableCellFormat detFormat = new WritableCellFormat (detFont); 
			   
			  NumberFormat nf=new NumberFormat("0.00000"); //用于Number的格式
			  WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf); 
			   
			  DateFormat df=new DateFormat("yyyy-MM-dd");//用于日期的
			  WritableCellFormat dateFormat = new WritableCellFormat (detFont, df); 
	
			  String[] coldata=columns.split(",");
			  String[] colDescdata=columnDescs.split(",");
			   
			  //add Title
			  for(int k=0;colDescdata !=null && k<colDescdata.length;k++)
			  {
			  	l=new Label(k,0,colDescdata[k],headerFormat);
			  	sheet.addCell(l);
			  }
			  //add detail
			  for(int i=0;list!=null && i<list.size();i++){
				  Map data=(HashMap)list.get(i);
				   for(int j=0 ; data!=null && j<coldata.length;j++){
					   if(data!=null){
						   //System.out.println(data[j].toString().trim());
						   l=new Label(j, i+1, data.get(coldata[j]).toString().trim(),detFormat);
						   sheet.addCell(l);
						   sheet.setColumnView(j, 25);
					   }
				   }
			  }
			  workbook.write();
			  workbook.close();
			  filename=random+".zip";
			     //压缩
			  filepath=path+filename;
			  File zip=new File(filepath);
			  compress(file,zip);
			  file.delete();
		  } catch (Exception e) {
			e.printStackTrace();
			filepath="";
		  } 
		  return filename;
	  }
	  
	  public String excelDownloadForHashMap(List list,String path,String filename,String columns,String columnDescs){
		  String random=(DateUtil.getCurrentTime1()+System.currentTimeMillis()+new Random().nextInt());
//		  filename=filename+".xls";
		  String filepath=path+filename+".xls";
		  File file=new File(filepath);
		  WritableWorkbook workbook;
		  try {
			  workbook = Workbook.createWorkbook(file);
			  WritableSheet sheet = workbook.createSheet(filename, 0); 
			  //一些临时变量，用于写到excel中
			  Label l=null;
			  jxl.write.Number n=null;
			  DateTime d=null;
			  //预定义的一些字体和格式，同一个Excel中最好不要有太多格式
			   
			  WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false); 
			  WritableCellFormat headerFormat = new WritableCellFormat (headerFont); 
			   
			  WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false); 
			  WritableCellFormat titleFormat = new WritableCellFormat (titleFont); 
			   
			  WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
			  WritableCellFormat detFormat = new WritableCellFormat (detFont); 
			   
			  NumberFormat nf=new NumberFormat("0.00000"); //用于Number的格式
			  WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf); 
			   
			  DateFormat df=new DateFormat("yyyy-MM-dd");//用于日期的
			  WritableCellFormat dateFormat = new WritableCellFormat (detFont, df); 
	
			  String[] coldata=columns.split(",");
			  String[] colDescdata=columnDescs.split(",");
			   
			  //add Title
			  for(int k=0;colDescdata !=null && k<colDescdata.length;k++)
			  {
			  	l=new Label(k,0,colDescdata[k],headerFormat);
			  	sheet.addCell(l);
			  }
			  //add detail
			  for(int i=0;list!=null && i<list.size();i++){
				  Map data=(HashMap)list.get(i);
				   for(int j=0 ; data!=null && j<coldata.length;j++){
					   if(data!=null){
						   //System.out.println(data[j].toString().trim());
						   l=new Label(j, i+1, data.get(coldata[j]).toString().trim(),detFormat);
						   sheet.addCell(l);
						   sheet.setColumnView(j, 25);
					   }
				   }
			  }
			  workbook.write();
			  workbook.close();
			  filename=random+".zip";
			     //压缩
			  filepath=path+filename;
			  File zip=new File(filepath);
			  compress(file,zip);
			  file.delete();
		  } catch (Exception e) {
			e.printStackTrace();
			filepath="";
		  } 
		  return filename;
	  }
	  
	  public String zipDownloadForHashMap(List list,String path,String filename,String zipname,String columns,String columnDescs){
		  String random=(DateUtil.getCurrentTime1()+System.currentTimeMillis()+new Random().nextInt());
//		  filename=filename+".xls";
		  String filepath=path+filename+".xls";
		  File file=new File(filepath);
		  WritableWorkbook workbook;
		  try {
			  workbook = Workbook.createWorkbook(file);
			  WritableSheet sheet = workbook.createSheet(filename, 0); 
			  //一些临时变量，用于写到excel中
			  Label l=null;
			  jxl.write.Number n=null;
			  DateTime d=null;
			  //预定义的一些字体和格式，同一个Excel中最好不要有太多格式
			   
			  WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false); 
			  WritableCellFormat headerFormat = new WritableCellFormat (headerFont); 
			   
			  WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false); 
			  WritableCellFormat titleFormat = new WritableCellFormat (titleFont); 
			   
			  WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
			  WritableCellFormat detFormat = new WritableCellFormat (detFont); 
			   
			  NumberFormat nf=new NumberFormat("0.00000"); //用于Number的格式
			  WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf); 
			   
			  DateFormat df=new DateFormat("yyyy-MM-dd");//用于日期的
			  WritableCellFormat dateFormat = new WritableCellFormat (detFont, df); 
	
			  String[] coldata=columns.split(",");
			  String[] colDescdata=columnDescs.split(",");
			  //add Title
			  for(int k=0;colDescdata !=null && k<colDescdata.length;k++)
			  {
			  	l=new Label(k,0,colDescdata[k],headerFormat);
			  	sheet.addCell(l);
			  }
			  
			  int listSize=list.size();
			  if(listSize>50000){
				  listSize=50000;
			  }
			  //add detail
			  for(int i=0;list!=null && i<listSize;i++){
				  Map data=(HashMap)list.get(i);
				   for(int j=0 ; coldata!=null && coldata.length>0 && j<coldata.length;j++){
					   if(data!=null){
						   String dd=data.get(coldata[j])==null ? "" : data.get(coldata[j]).toString().trim();
						   l=new Label(j, i+1, dd,detFormat);
						   sheet.addCell(l);
						   sheet.setColumnView(j, 25);
					   }
				   }
			  }
			  workbook.write();
			  workbook.close();
			     //压缩
			  zipname=zipname+".zip";
			  filepath=path+zipname;
			  File zip=new File(filepath);
			  compress(file,zip);
			  file.delete();
		  } catch (Exception e) {
			e.printStackTrace();
			zipname="";
		  } 
		  return zipname;
	  }
	  
	  public String txtDownload(List list,String filename,String columns){
		  String path=getProperties("downDir");
		  String splitStr="||";
		  String random=(DateUtil.getCurrentTime1()+System.currentTimeMillis()+new Random().nextInt());
		  
		  String filepath=path+filename+".txt";
		  File file=new File(filepath);
		  BufferedWriter writer = null;
		  try{
			   writer = new BufferedWriter(new FileWriter(file));
			   String[] coldata=columns.split(",");
			   String title="";
			   for(int k=0;coldata !=null && k<coldata.length;k++){
				   if(coldata[k]!=null){
					   title+=coldata[k]+splitStr;
				   }
			   }
			   writer.write(title+"\r\n");
			   
			   for(int i=0;list!=null && i<list.size();i++){
				   Object[] data=(Object[])list.get(i);
				   String row="";
				   for(int j=0 ; data!=null && j<data.length;j++){
					   if(data[j]!=null){
						   row+=data[j].toString().trim()+splitStr;
					   }else{
						   row+="null"+splitStr;
					   }
					   
				   }
					 writer.write(row+"\r\n");
			   	}
			     writer.close();
			     }catch(Exception e){
			    	e.printStackTrace();
			     }
			     filename=random+".zip";
			     //压缩
			     filepath=path+filename;
			     File zip=new File(filepath);
			     compress(file,zip);
			     file.delete();
		  return filename;
	  }
	  
	  public static void writeArrayText(String path,List list,String table){
		  BufferedWriter writer = null;
		  try{
			   writer = new BufferedWriter(new FileWriter(new File(path)));
			   for(int i=0;i<list.size();i++){
					 Map map = new HashMap(); 
					 map=(Map)list.get(i);
					 
					 String cname="(";
					 String values="(";
					 Iterator   iterator=map.keySet().iterator(); 
					 while(iterator.hasNext()){ 
					 	Object key=iterator.next(); 
					 	Object value=map.get(key); 
					 	cname=cname+key+",";
					 	if(key.toString().indexOf("VALUE")>-1 || key.toString().indexOf("_ZF")>-1){
					 		values=values+value.toString().trim()+",".replaceAll("--", "0");
					 	}else{
					 		values=values+"'"+value.toString().trim()+"',";
					 	}
					 } 
					 cname=cname.substring(0, cname.length()-1);
					 values=values.substring(0, values.length()-1);
					 cname+=")";
					 values+=")";
					 writer.write("insert into "+table+cname+" values "+values+";\r\n");
			   	}
			     writer.close();
			     }catch(Exception e){
			    	e.printStackTrace();
			     }
		  
	  }
	  
	  public static void readText(String fileName,String table){
		  File file = new File(fileName);
		  BufferedReader reader = null;
		  try {
		  System.out.println("以行为单位读取文件内容，一次读一整行：");
		  reader = new BufferedReader(new FileReader(file));
		  String tempString = null;
		  int line = 1;
		  //一次读入一行，直到读入null为文件结束
		  while ((tempString = reader.readLine()) != null){
		  //显示行号
//		  System.out.println("line " + line + ": " + tempString); 
		  line++;
		  }
//		  System.out.println("line="+line);
		  reader.close();
		  } catch (IOException e) {
		  e.printStackTrace();
		  } finally {
		  if (reader != null){
		  try {
		  reader.close();
		  } catch (IOException e1) {
		  }
		  }
		  }
		  
	  }
	  
	  public static boolean checkExist(String filepath){
		  boolean isExist=false;
		  File file=new File(filepath);
		  if (file.exists()) {
			  isExist=true;
		  }
		  return isExist;
	  }
	  public static boolean deleteFile(String filepath){
		  boolean isExist=false;
		  File file=new File(filepath);
			  if (file.exists()) {
				  file.delete();
				  isExist=true;
			  }
		  return isExist;
	  }
	  public static boolean deleteFile(String filepath,String rpt){
		  boolean isExist=false;
		  File file=new File(filepath);
		  if(file.isDirectory()){
			  File[] files=file.listFiles();
			  for(File f:files){
				  if(f.getName().indexOf(rpt)==0){
					  System.out.println(f.getName()+"被删除了!");
					  f.delete();
					  isExist=true;
				  }
			  }
		  }else{
			  filepath=filepath.substring(0, filepath.indexOf(rpt));
			  File directory=new File(filepath);
			  if (directory.isDirectory()) {
				  File[] files=directory.listFiles();
				  for(File f:files){
					  if(f.getName().indexOf(rpt)>=0){
						  System.out.println(f.getName()+"被删除了!");
						  f.delete();
						  isExist=true;
					  }
				  }
			  }else{isExist=false;}
		  }
		  return isExist;
	  }
	  public static boolean createDirs(String filepath){
		  boolean isExist=false;
		  File file=new File(filepath);
		  if (file.exists()) {
			  isExist=true;
		  }else{
			  file.mkdirs();
		  }
		  return isExist;
	  }
	  
	  public static String getFileName(Map map){
		  String fileName=map.get("fileName").toString();
		  String region="";
		  String city="";
		  String b1="";
		  String b2="";
		  String b3="";
		  String b4="";
		  String b5="";
		  String b6="";
		  String b7="";
		  String b8="";
		  Iterator iterator=map.keySet().iterator(); 
			 while(iterator.hasNext()){ 
			 	Object key=iterator.next(); 
			 	Object value=map.get(key); 
			 	if("REGION_CODE".equals(key)){
			 		region=value.toString();
			 	}else if("CITY_CODE".equals(key)){
			 		city=value.toString();
			 	}
			 } 
		  fileName+="-"+region+"-"+city+"-"+b1+"-"+b2+"-"+b3+"-"+b4+"-"+b5+"-"+b6+"-"+b7+"-"+b8+map.get("fileSuffix").toString();  
		  return fileName;
	  }
	  
	  public static String getParameter(HttpServletRequest request,String str){
		  try {
			str=request.getParameter(str)==null ? "" : request.getParameter(str).toString();
			//str=new String(str.getBytes("ISO8859_1"), "UTF-8");
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		  return str;
	  }
	  
	  public static String getAttribute(HttpServletRequest request,String str){
		  try {
				str=request.getAttribute(str)==null ? "" : request.getAttribute(str).toString();
				str=new String(str.getBytes("ISO8859_1"), "UTF-8");
			  } catch (Exception e) {
				e.printStackTrace();
			  }
		  return str;
	  }
	  
	  public static HSSFCell createHSSFCell(HSSFRow row, int colIndex, String colContent){
		  	HSSFCell cell = row.createCell((short)colIndex);
		  	try{
				if (NumberUtils.isNumber(colContent))
					cell.setCellValue(NumberUtils.createDouble(colContent).doubleValue());
				else
					cell.setCellValue(colContent);
		  	}catch(Exception ex){
		  		cell.setCellValue(colContent);
		  	}
			return cell;
	  }
	  
	  public String createTxtFile(List list,String filename,String columnCodes,String columnDescs) {
		  String path=getProperties("downDir")+"/";
		  String splitStr="||";
		  String random=(DateUtil.getCurrentTime1()+System.currentTimeMillis()+new Random().nextInt());
		  String filepath="";
		  
		  BufferedWriter writer = null;
		  OutputStreamWriter oswriter =null;
		  try{
			  filepath=path+filename;//new String(filename.getBytes("ISO8859_1"), "UTF-8");
			  File file=new File(filepath);
			  oswriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
			  // writer = new BufferedWriter(new FileWriter(file));
			  writer = new BufferedWriter(oswriter);
			   String[] coldata=columnCodes.split(",");
			   String[] colNamedata=columnDescs.split(",");
			   String title="";
			   for(int k=0;colNamedata !=null && k<colNamedata.length;k++){
				   if(colNamedata[k]!=null){
					   title+=colNamedata[k]+splitStr;
				   }
			   }
			   //writer.write(title+"\r\n");
			   System.out.println("======"+title);
			   writer.write(title);
			   writer.newLine();
			   for(int i=0;list!=null && i<list.size();i++){
				   Map data=(HashMap)list.get(i);
				   String row="";
				   for(int j=0 ; coldata!=null && j<coldata.length;j++){
					   if(data.get(coldata[j])!=null){
						   row+=data.get(coldata[j]).toString().trim()+splitStr;
					   }else{
						   row+="null"+splitStr;
					   }
					   
				   }
					// writer.write(row+"\r\n");
				   writer.write(row);
				   writer.newLine();
				   writer.flush();
			   	}
			   System.out.println("==write txt end==");
			     writer.close();
			     }catch(Exception e){
			    	e.printStackTrace();
			     }finally{
			    	 try{
			    		 oswriter.close();
			    		 writer.close();
			    	 }catch(Exception ex){}
			     }
		  return filepath;
	  }
	  
	  public String createCsvFile(List list,String filename,String columnCodes,String columnDescs) {
		  String path=getProperties("downDir")+"/";
		  String splitStr=",";
		  String random=(DateUtil.getCurrentTime1()+System.currentTimeMillis()+new Random().nextInt());
		  String filepath="";
		  
		  BufferedWriter writer = null;
		  OutputStreamWriter oswriter =null;
		  try{
			  filepath=path+filename;//new String(filename.getBytes("ISO8859_1"), "UTF-8");
			  File file=new File(filepath);
			  	oswriter = new OutputStreamWriter(new FileOutputStream(file),"GB2312");
//			  	writer = new BufferedWriter(new FileWriter(file));
			  	 writer = new BufferedWriter(oswriter);
			   String[] coldata=columnCodes.split(",");
			   String[] colNamedata=columnDescs.split(",");
			   String title="";
			   for(int k=0;colNamedata !=null && k<colNamedata.length;k++){
				   if(colNamedata[k]!=null){
					   title+=colNamedata[k]+splitStr;
				   }
			   }
			   //writer.write(title+"\r\n");
			   System.out.println("csv title======"+title);
			   writer.write(title);
			   writer.newLine();
			   for(int i=0;list!=null && i<list.size();i++){
				   Map data=(HashMap)list.get(i);
				   String row="";
				   for(int j=0 ; coldata!=null && j<coldata.length;j++){
					   if(data.get(coldata[j])!=null){
						   row+=data.get(coldata[j]).toString().trim()+splitStr;
					   }else{
						   row+="null"+splitStr;
					   }
					   
				   }
					// writer.write(row+"\r\n");
				   writer.write(row);
				   writer.newLine();
				   writer.flush();
			   	}
			   System.out.println("==write csv end==");
			   
			     writer.close();
			     }catch(Exception e){
			    	e.printStackTrace();
			     }finally{
			    	 try{
			    		 oswriter.close();
			    		 writer.close();
			    	 }catch(Exception ex){}
			     }
		  return filepath;
	  }
	  
	  
	//根据查询结果生成ＸＭＬ文件 ,path 包含文件的路径名称，dimentionCode 维度 ，measureCode 指标 ，list 结果集  ,sql 查询语句,pageNum 总记录数
	  public String  createXmlFile(List list,String fileName,String  cCode,String  cName ){
		  String path="";
		  if(list.isEmpty()||list.size()==0||list.equals(null)){
			  return path;
		  }
		  Document document = DocumentHelper.createDocument();
	      Element RootElement = document.addElement("root");
	      Element listElement =  RootElement.addElement("listElement");
	      String[]  codes=cCode.split(",");
	      String[] names=cName.split(",");
	      for(int i=0;i<list.size();i++){
	    	  Map map = new HashMap(); 
	    	  map=(Map)list.get(i);
	    	  Element trElement =  listElement.addElement("tr");
	    	  for(int k=0;k<codes.length;k++){
	    		  trElement.addAttribute(codes[k], map.get(codes[k])==null ? "" : map.get(codes[k]).toString());
	    	  }
	      }
	      try{
	    	  path=getProperties("downDir")+fileName;
	    	  OutputFormat format = OutputFormat.createPrettyPrint();  
	    	  format.setEncoding("gbk"); 
	    	  XMLWriter output = new XMLWriter(	 new FileWriter( new File(path) ),format);
	          output.write( document );
	          output.close();
	          }
	      catch(IOException e){
	    	  System.out.println(e.getMessage());
	      }
	      return path;
	  }
	  
	  public static void createHSSFSheet(List list,HSSFWorkbook workbook,String sheetName,String columnCodes,String columnDescs){
		//产生Excel表头
		  	
		  	try{
		  	int pageSize=50000;
		  	String sName=sheetName;
			int init=0;
			int listSize=list.size();
			int forNum=1;
			if(listSize>=pageSize){
				forNum=listSize/pageSize+1;
				
			}
			for(int f=0;f<forNum;f++){
				if(forNum>1){
					listSize=pageSize*(f+1);
					sheetName=sName+"_"+f;
					init=f*pageSize;
				}
				HSSFSheet sheet = workbook.createSheet(sheetName);
				HSSFRow header = sheet.createRow(0);
				
				String[] coldata=columnCodes.split(",");
				String[] colDescdata=columnDescs.split(",");
				
				//产生标题列
				if(colDescdata.length >= 1){
					for(int i=0;i<colDescdata.length;i++){
						sheet.setColumnWidth(i, 3500);
						createHSSFCell(header,i,colDescdata[i]);
					}
				}
				int rowNum = 1;
				//填充数据
				for(int i=init;list!=null && i<listSize;i++){
					Map data=(HashMap)list.get(i);
					HSSFRow hssfrow = sheet.createRow(rowNum++);
					for(int j=0; data!=null && j<coldata.length;j++){
						String val = data.get(coldata[j])==null? "" : data.get(coldata[j]).toString().trim();
						createHSSFCell(hssfrow,j,val);
					}
			    }
			}
		  	}catch(IndexOutOfBoundsException ex){}catch(Exception ex){ex.printStackTrace();}
	  }
	  
	  //判断字符串是否存在与List中
	  public static boolean checkList(List list,String str,String val){
		  try{
			  for(int i=0;i<list.size();i++){
				  Map map =(HashMap)list.get(i);
				  if(str.equals(map.get(val))){
					  return true;
				  }
			  }
		  }catch(Exception ex){}
		  return false;
	  }
	  
	
	  //根据查询结果生成ＸＭＬ文件 ,path 包含文件的路径名称，dimentionCode 维度 ，measureCode 指标 ，list 结果集  ,sql 查询语句,pageNum 总记录数
	  public static void  resultListToXml(String path,String  dimentionCode,String  measureCode,List list ,String sql ,String pageNum){
		  if(list.isEmpty()||list.size()==0||list.equals(null)){
			  return   ;
		  }
		  Document document = DocumentHelper.createDocument();
	       Element RootElement = document.addElement("root");
	       Element sqlElement =  RootElement.addElement("sql");
	       sqlElement.addAttribute("sql", sql);
	       Element pageNumElement =  RootElement.addElement("pageNum");
	       pageNumElement.addAttribute("pageNum", pageNum);
	       Element dimentionCodeElement =  RootElement.addElement("dimentionCode");
	       dimentionCodeElement.addAttribute("dimentionCode", dimentionCode);
	       Element measureCodeElement =  RootElement.addElement("measureCode");
	       measureCodeElement.addAttribute("measureCode", measureCode);
	       Element listElement =  RootElement.addElement("listElement");
	   	String[]  dimentions=dimentionCode.split(",");
	   	String[] measures=measureCode.split(",");
	   	//System.out.println("------FileOperate--------dimentionCode:"+dimentionCode);
	   	//System.out.println("------FileOperate--------measureCode:"+measureCode);
	   	//System.out.println("------FileOperate--------list.size():"+list.size());
	       for(int i=0;i<list.size();i++){
	    	   Map map = new HashMap(); 
				 map=(Map)list.get(i);
				 Element trElement =  listElement.addElement("tr");
		
				 for(int k=0;k<dimentions.length;k++){
					 trElement.addAttribute(dimentions[k], map.get(dimentions[k])==null ? "" : map.get(dimentions[k]).toString());
				 }

				 for(int j=0;j<measures.length;j++){
					 trElement.addAttribute(measures[j], map.get(measures[j])==null ? "" : map.get(measures[j]).toString());
				 }
				 
			
	       }
	      try{
	     OutputFormat format = OutputFormat.createPrettyPrint();  
			    format.setEncoding("gbk"); 
	      XMLWriter output = new XMLWriter(	 new FileWriter( new File(path) ),format);
	          output.write( document );
	          output.close();
	          }
	       catch(IOException e){
	    	   System.out.println(e.getMessage());
	    	   
	       }
		   
		 
	  }
	  
	 //读取XML文件生成List数据
	  public static  List  getXMLFileToListResult(String  path){
		  List list =new  ArrayList();		
		 
 
			try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read( new File(path));
				Element rootE=document.getRootElement();
				String rootName =rootE.getName();
				String dimentionCodeE =rootE.element("dimentionCode").attributeValue("dimentionCode"); //获取dimentionCode 维度
				
				String measureCodeE =rootE.element("measureCode").attributeValue("measureCode");  //获取measureCode指标
				String allStr  ="" ;  //维度和指标的合体 方便遍历listElement中的值

				String[] dimentionCodes =dimentionCodeE.split(",");
				String[] measureCodes =measureCodeE.split(",");
				
				for(int k=0;k<dimentionCodes.length;k++){
					allStr +=dimentionCodes[k]+",";
				}
				for(int t=0;t<measureCodes.length;t++){
					allStr +=measureCodes[t]+",";
				}
			
			String[]	attributeVals =allStr.split(",");
			Element listElement=rootE.element("listElement");
			
			List  tr =listElement.selectNodes("tr");
			
				for(int i=0;i<tr.size();i++){
					Element element=(Element)tr.get(i);
					Map map=new HashMap();
					for(int j=0;j<attributeVals.length;j++){
						String attributeVal=attributeVals[j];		
					   map.put(attributeVal,element.attributeValue(attributeVal));

					}
					list.add(map);
					//System.out.println(list);
					//System.out.println(trE.attributeValue("code")+trE.attributeValue("desc"));
				}
		    }catch(Exception e){System.out.println(e.getMessage());}
		  
		  
		  return  list ;
	  }
	  
	  public static String getXMLattributeValue(String path,String element,String attributeValue){
		  	String xmlStr="";
		    try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read( new File(path));
				Element rootE=document.getRootElement();
				xmlStr =rootE.element(element).attributeValue(attributeValue); //获取dimentionCode 维度
		    }catch(Exception e){System.out.println(e.getMessage());}
		    return xmlStr;
	  }
	  
	  //Ftp文件
	  public void  getFtpFiles(List list,String clientId,String clientName,String path,String userid,List keys){
		  Properties prop = new Properties(); 
		  try 
		  { 
		  InputStream inputStream = this.getClass().getResourceAsStream("/init.properties"); 
		  prop.load(inputStream); 
		  String ftpDir = prop.getProperty("ftpDir"); //上传文件，ftp服务器目录
		  String ftpIp  = prop.getProperty("ftpIp"); 
		  int ftpPort = Integer.parseInt(prop.getProperty("ftpPort")); 
		  String ftpUsername = prop.getProperty("ftpUsername"); 
		  String ftpPassword = prop.getProperty("ftpPassword"); 
		  String downDir = prop.getProperty("downDir"); 
		  // String file_dir = cInfo.getFileDir();	//	
		  if("".equals(path)){
			  path=downDir;
		  }
		  	 
		  FtpApche fa = new FtpApche();    	 
		  boolean isSuccess = fa.connect(ftpIp,ftpPort, ftpUsername,ftpPassword, ftpDir);
		  if(isSuccess){   	
					   		//上传控制文件 
			  String  file_=DateUtil.getToday("yyyyMMdd")+Long.toString(System.currentTimeMillis());	
			  String file_dat=path+"/"+file_+".dat";
				  	File file = new File(file_dat);
				  	BufferedWriter writer = null;
				  	writer = new BufferedWriter(new FileWriter(file));
					String str_=file_+".txt|"+clientId+"|"+"|"+"|"+clientName+"|"+userid;
					writer.write(str_+"\r\n");
					writer.close();
				  	if(file.exists()){
				  		FileInputStream in = new FileInputStream(file);
				  		boolean flag = fa.uploadFile(ftpDir, file.getName(), in);
				    	System.out.println("FTP上传文件成功:"+file_dat);
				    	file.delete();
				    }else{
				    	System.out.println("FTP上传文件不存在："+file_dat);
				    }   
				    		//上传数据文件文件 
				  	String file_txt=path+"/"+file_+".txt";
					file = new File(file_txt);
					writer = null;
				  	writer = new BufferedWriter(new FileWriter(file));
				  	for(int i=0;list!=null && i<list.size();i++){
				  		Map map=(HashMap)list.get(i);
				  		String phone="";
				  		for(int j=0;keys!=null && j<keys.size();j++){
				  			phone=map.get(keys.get(j)).toString()+"|";
				  		}
				  		phone=phone.substring(0,phone.length()-1);
						writer.write(phone+"\r\n");
				  	}
					
					writer.close();
					
					if(file.exists()){
						FileInputStream in = new FileInputStream(file);
						boolean flag = fa.uploadFile(ftpDir, file.getName(), in);
						System.out.println("FTP上传文件成功:"+file_txt);
						file.delete();
	   		    	}else{
					    System.out.println("FTP上传文件不存在："+file_txt);
					}    	    		 

				   	fa.ftpClosed();

				   	}else{
				   		System.out.println("FTP服务器连接失败...");		   		
				   	}
		  }catch(IOException e) { 
			  System.out.println("IO读取出错,找不到application.properties!");
			  e.printStackTrace();
		  }catch (Exception e) { 
		      e.printStackTrace(); 
		  }	
		  
	  }	
	  
	  //首页生成ＸＭＬ文件，参数为路径　path ,指标字段 measureCode
	  public  static  void  createHomeXml(String  path,String measureCode ,List  list){
		  if(list.isEmpty()||list.size()==0||list.equals(null)){
			  return   ;
		  }
		   Document document = DocumentHelper.createDocument();
	       Element RootElement = document.addElement("root");
	       Element measureCodeElement =  RootElement.addElement("measureCode");
	       measureCodeElement.addAttribute("measureCode", measureCode);
	       Element listElement =  RootElement.addElement("listElement");
	   
	   	String[] measures=measureCode.split(",");
	       for(int i=0;i<list.size();i++){
	    	   Map map = new HashMap(); 
				 map=(Map)list.get(i);
				 Element trElement =  listElement.addElement("tr");
				 for(int j=0;j<measures.length;j++){
					 trElement.addAttribute(measures[j], map.get(measures[j]).toString());
				 }	
	       }
	      try{
	     OutputFormat format = OutputFormat.createPrettyPrint();  
			    format.setEncoding("gbk"); 
	      XMLWriter output = new XMLWriter(	 new FileWriter( new File(path) ),format);
	          output.write( document );
	          output.close();
	          }
	       catch(IOException e){
	    	   System.out.println(e.getMessage());
	    	   
	       }
		  
	  }
	  //首页读取XML文件生成List
	public  static  List  homeXmlToList(String  path){
		  List list =new  ArrayList();		
			 
		  
			try{
		    	SAXReader saxReader = new SAXReader();
				Document document = saxReader.read( new File(path));
				Element rootE=document.getRootElement();
				String rootName =rootE.getName();
				
				String measureCodeE =rootE.element("measureCode").attributeValue("measureCode");  //获取measureCode指标
				String allStr  ="" ;  //维度和指标的合体 方便遍历listElement中的值
				String[] measureCodes =measureCodeE.split(",");
				
				for(int t=0;t<measureCodes.length;t++){
					allStr +=measureCodes[t]+",";
				}
			
			String[]	attributeVals =allStr.split(",");
			Element listElement=rootE.element("listElement");
			
			List  tr =listElement.selectNodes("tr");
			
				for(int i=0;i<tr.size();i++){
					Element element=(Element)tr.get(i);
					Map map=new HashMap();
					for(int j=0;j<attributeVals.length;j++){
						String attributeVal=attributeVals[j];		
					   map.put(attributeVal,element.attributeValue(attributeVal));

					}
					list.add(map);
					//System.out.println(list);
				}
		    }catch(Exception e){System.out.println(e.getMessage());}
		  
		  
		  return  list ;
	  }
	
		public String mergeFile(String newpath,List files){
				BufferedWriter writer = null;
		        BufferedReader reader = null;  
		        OutputStreamWriter oswrite=null;
		        InputStreamReader isr=null;
		        try {  
		        	oswrite = new OutputStreamWriter(new FileOutputStream(new File(newpath)),"GB2312"); 
		        	writer = new BufferedWriter(oswrite);
		        	//writer = new BufferedWriter(new FileWriter(new File(newpath)));
		        	for(int i=0;i<files.size();i++){
		        		isr = new InputStreamReader(new FileInputStream(new File(files.get(i).toString())), "GB2312");
			            reader = new BufferedReader(isr); 
			            StringBuffer buffer = new StringBuffer("");  
			            String content = reader.readLine();  
			            int size=0;
			            while (content != null) {  
			            	if(i!=0 && size==0){
			            		System.out.println(i+"-----");
			            	}else{
			            		buffer.append(content).append(System.getProperty("line.separator"));  
			            	}
			                content = reader.readLine();
			                size++;
			            }  
			            writer.write(buffer.toString());
		                writer.flush();
		        	}
		        	
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        } finally {  
		            try {  
		            	if(oswrite!=null)oswrite.close();
				        if(isr!=null)isr.close();
		                if (reader != null)reader.close();  
		                if (writer != null)writer.close(); 
				        
		              //删除生成的txt文件
						for(int p=0;files!=null && p<files.size();p++){
				        	File srcdir=new File(files.get(p).toString());
				        	try{
				        		srcdir.delete();
				        	}catch(Exception ex){}
				        }
		            } catch (Exception e) {  
		                e.printStackTrace();  
		            }  
		        }  
			return newpath;
		}
		
		public String createDbfFile(List list,String filename,String columnCodes,String columnDescs) {
			  String path=getProperties("downDir")+"/";
			  String splitStr="||";
			  String random=(DateUtil.getCurrentTime1()+System.currentTimeMillis()+new Random().nextInt());
			  String filepath=path+filename;//new String(filename.getBytes("ISO8859_1"), "UTF-8");
			 
				   
				  OutputStream fos = null;
				  try  
				  {   
					  File file=new File(filepath);
				      //定义DBF文件字段   
					  String colstr="统计日期,归属县市,服务品牌,停开机状,服务号码,用户状态";
					  //colstr="aa,bb,vv,dd,ssd,fsfs,df";
					  String [] cols=colstr.split(",");
					  
//				      DBFField[] fields = new DBFField[cols.length]; 
//				      //分别定义各个字段信息，setFieldName和setName作用相同， 
//				      //只是setFieldName已经不建议使用   
//				      for(int i=0;i<cols.length;i++){
//					      fields[i] = new DBFField();   
//					      //fields[0].setFieldName("emp_code"); 
//					      fields[i].setName(cols[i]);   
//					      fields[i].setDataType(DBFField.FIELD_TYPE_C);   
//					      fields[i].setFieldLength(10);   
//				      }
					  DBFField[] fields = new DBFField[2]; 
					    fields[0] = new DBFField();
					    fields[0].setName( "aa");
					    fields[0].setDataType( DBFField.FIELD_TYPE_C);
					    fields[0].setFieldLength( 10);

					    fields[1] = new DBFField();
					    fields[1].setName("emp_name");
					    fields[1].setDataType( DBFField.FIELD_TYPE_C);
					    fields[1].setFieldLength( 20);

				      //DBFWriter writer = new DBFWriter(new File(path));   

				      //定义DBFWriter实例用来写DBF文件   
				      DBFWriter writer = new DBFWriter(file); 
				      writer.setCharactersetName("GBK");
				      //把字段信息写入DBFWriter实例，即定义表结构  
				      writer.setFields(fields);   
				      //一条条的写入记录   
				      
				      for(int i=0;i<100;i++){
				    	  Object[] rowData = new Object[2];
				    	  for(int r=0;r<2;r++){
				    		  rowData[r] = "国家"+i+r;    
				    	  }
				    	  //rowData[22] = "1";   
					      writer.addRecord(rowData); 
					      System.out.println(i);
				      }

				      //定义输出流，并关联的一个文件   
				      fos = new FileOutputStream(filename);
				      //写入数据   
				      writer.write(fos);   

				      //writer.write();  
				  }catch(Exception e)   
				  {   
				      e.printStackTrace();   
				  }   
				  finally  
				  {   
				      try{   
				      fos.close();
				      }catch(Exception e){}
				  }

			  return filepath;
		  }
	  
	  public static void main(String [] str){
		  String path="D:/work_bigdata/paas-parent/paas-web/src/main/webapp/WebRoot/reportModel/metaManager/data/reportConfig.xml";
		  String nodeStr="busn";
		  String xml=FileOperate.getXML(path, nodeStr);
//		  Properties pros = new Properties();
//		  InputStream in = FileOperate.class.getResourceAsStream("init.properties"); 
//		  try {
//			pros.load(in);
//			path=pros.getProperty("downDir"); 
//			String filename=path+"2012071618150313424337035982088403858.xls";
//			File file=new File(filename);
//			File zipFile=new File(path+"2012071618150313424337035982088403858.zip");
//			FileOperate.compress(file,zipFile);
//		  Enumeration<?> names = pros.propertyNames();  
//	        while (names.hasMoreElements()) {  
//	            String name = (String) names.nextElement();  
//	            System.out.println(name + "=" + pros.getProperty(name));  
//	        }  
//		  } catch (Exception e) {
//				e.printStackTrace();
//		  } 
	  }
	  
}