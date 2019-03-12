package cn.edu.llxy.dw.dss.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfFileWriter {
	
	private String fileName;
	private char delimiter;
	private Document doc;
	
	public PdfFileWriter(String fileName, char delimiter) throws IOException{
		this.fileName = fileName;
		this.delimiter = delimiter;
 
		// 创建pdf文件
		String tempFile = fileName.replace(".pdf", "_bak.pdf");
		File file = new File(tempFile);
		// 设置纸张
		doc = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			//设置pdf文件生成路径
			PdfWriter.getInstance(doc, new FileOutputStream(file));
			//打开pdf文件
			doc.open();
			doc.setMargins(30, 30, 0, 0);	
		}catch(Exception e){
			e.printStackTrace();
		}
 
	}
	
	public void writeRecord(Object[] values) throws Exception {
		if (values != null && values.length > 0) {			
			StringBuffer strb = new StringBuffer();
			int lastIdx = values.length - 1;
			for(int i=0; i < values.length; i++) {
				strb.append(values[i]);
				if(i == lastIdx){
					//实例化Paragraph 获取写入pdf文件的内容，调用支持中文的方法
					doc.add(new Paragraph(strb.toString(), setChineseFont()));
				}else{
					strb.append(delimiter);
				}
			}
		} 
		
	}
	
 
	public void addWatermark(String waterMarkName) {
		PdfReader pdfReader = null;
		PdfStamper pdfStamper = null;
		PdfContentByte overContent = null;
		BaseFont base = null;
		Rectangle pageRect = null;		
		String tempFile = fileName.replace(".pdf", "_bak.pdf");
		try {
		    pdfReader = new PdfReader(tempFile);		
		    pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(this.fileName));
		    if(waterMarkName==null || waterMarkName.equals("")){
		    	waterMarkName = "内部文档,注意保密";
		    }
			// 设置字体
		    base = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			// 设置透明度为0.4
		    PdfGState gs = new PdfGState();
			gs.setFillOpacity(0.2f);
			gs.setStrokeOpacity(0.2f);
			int toPage = pdfStamper.getReader().getNumberOfPages();
			for (int i = 1; i <= toPage; i++) {
				pageRect = pdfStamper.getReader().getPageSizeWithRotation(i);
				// 计算水印X,Y坐标
				float x = pageRect.getWidth() / 2;
				float y = pageRect.getHeight() / 2;
				//获得PDF最顶层
				overContent = pdfStamper.getOverContent(i);
				overContent.saveState();
				// set Transparency
				overContent.setGState(gs);
				overContent.beginText();
				overContent.setColorFill(BaseColor.GRAY);
				overContent.setFontAndSize(base, 60);
				//水印文字成45度角倾斜
				overContent.showTextAligned(Element.ALIGN_CENTER, waterMarkName, x,y, 45);
				overContent.endText(); 
				overContent.restoreState();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			overContent = null;
			base = null;
			pageRect = null;
			
			if(pdfStamper!=null){
			    try {
					pdfStamper.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(pdfReader!=null){
				pdfReader.close();
			}		
		}
	}
	
	public void close() {
		//关闭pdf文件
		if(doc!=null){
	       doc.close();
		}
	}
	
	private static Font setChineseFont() {
		BaseFont base = null;
		Font fontChinese = null;
		try {
			base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
			fontChinese = new Font(base, 12, Font.NORMAL);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fontChinese;
	}

}
