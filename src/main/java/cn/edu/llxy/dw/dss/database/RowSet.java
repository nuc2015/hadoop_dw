package cn.edu.llxy.dw.dss.database;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import cn.edu.llxy.dw.dss.cfg.Const;

public class RowSet implements Comparable<RowSet>{

	/**
	 * 行数据模型，记录了这个rowset里面存放的rowData的数据的具体数据模型
	 */
	private RowModel rowMeta;
	
	/**
	 * 一个由数组支持的有界阻塞队列。此队列按 FIFO（先进先出）原则对元素进行排序
	 * 该队列存储的对象为RowData数据数组，是核心的数据交换缓存对象
	 */
	private BlockingQueue<RowData> queArray;
    /**
     * 标识这个缓存对象已经结束了数据的接收
     */
    private AtomicBoolean		    done;
    /**
     * 数据来源的step名称
     */
    private String    			    originStepName;
    /**
     * 如果是1—N 的step数据分发方式，记录这个rowset是第几份数据copy
     */
    private AtomicInteger		    originStepCopy;
    /**
     * 数据目标的step名称
     */
    private String    			    destinationStepName;
    /**
     * 如果是N-1的step数据分发方式，记录这个rowset是第几份数据copy的来源
     */
    private AtomicInteger		    destinationStepCopy;
    
    /**init RowBufferCache 
     * @param maxSize
     */
    public RowSet(int maxSize){
    	queArray=new ArrayBlockingQueue<RowData>(maxSize,false);
    	done=new AtomicBoolean(false);
    	originStepCopy=new AtomicInteger(0);
    	destinationStepCopy=new AtomicInteger(0);
    }
    
    
	@Override
	public int compareTo(RowSet rowbuffer) {
		String target = destinationStepName+"."+destinationStepCopy.intValue();
		String comp   = rowbuffer.destinationStepName+"."+rowbuffer.destinationStepCopy.intValue();
		return target.compareTo(comp);
	}
	
	public boolean equals(RowSet RowBufferCache) {
		return compareTo(RowBufferCache) == 0;
	}

    
	public boolean putRow(RowModel rowMeta, RowData rowData)
    {
    	//System.out.println("put--------");
		return putRowWait(rowMeta, rowData, Const.TIMEOUT_PUT_MILLIS, TimeUnit.MILLISECONDS);
    }
    
    
    public boolean putRowImmediate(RowModel rowMeta, RowData rowData){
    	this.rowMeta = rowMeta;
    	try {             		
    		return queArray.offer(rowData);             
    	}
    	catch (NullPointerException e)
	    {
    		return false;
	    }    	
    }
    
    public boolean putRowWait(RowModel rowMeta, RowData rowData, long time, TimeUnit tu) {
    	this.rowMeta = rowMeta;
    	try{
    		
    		return queArray.offer(rowData, time, tu);
    	}
    	catch (InterruptedException e)
	    {
    		return false;
	    }
    	catch (NullPointerException e)
	    {
    		return false;
	    }    	
    	
    }
    
    
    /**获取一行，定义的等待时间为50ms
     * @return
     */
    public Object getRow(){
    	return getRowWait(Const.TIMEOUT_GET_MILLIS, TimeUnit.MILLISECONDS);
    }
    
    
    /**
     * 从容器中立即获取一行
     * @return Row or null
     */       
    public RowData getRowImmediate(){

    	return queArray.poll();	    	
    }
    
    /**
     * 获取一行，获取并移除此队列的头部，在指定的等待时间前等待可用的元素（如果有必要）
     * @return Row or null
     */
    public RowData getRowWait(long timeout, TimeUnit tu){

    	try{
    		return queArray.poll(timeout, tu);
    	}
    	catch(InterruptedException e){
    		return null;
    	}
    }

    /**
     * Wait forever until successfully receive a row.
     * This method should be use only at the beginning of
     * transformation. All the step in the beginning can wait
     * until other steps finish producing data.
     * This method can block your thread forever.
     */
    public Object getRowUnitlSuccess(){
    	
    	try{
    		return queArray.take();
    	}
    	catch(InterruptedException e){
    		return null;
    	}
    }
    
    /**
     * @return Set indication that there is no more input
     */
    public void setDone()
    {
    	done.set(true);
    	    	
    }
    
    /**
     * @return 返回这个容器是否down
     */
    public boolean isDone()
    {
    	return done.get();
    }
    
    /**
     * @return 返回这个来源数据的组件名称.
     */
    public String getOriginStepName()
    {
    	synchronized(originStepName){
    		return originStepName;
    	}
        
    }
    
    /**
     * @return Returns the originStepCopy.
     */
    public int getOriginStepCopy()
    {
        return originStepCopy.get();
    }
    
    /**
     * @return Returns the destinationStepName.
     */
    public String getDestinationStepName()
    {
        return destinationStepName;
    }
    
    /**
     * @return Returns the destinationStepCopy.
     */
    public int getDestinationStepCopy()
    {
    	return destinationStepCopy.get();    	
    }

    
    public String getName()
    {
        return toString();
    }
    
    /**
     * 
     * @return 返回 RowBufferCache 的最大容量
     */
    public int size()
    {
    	return queArray.size();
    }

    /**
     * This method is used only in Trans.java 
     * when created RowBufferCache at line 333. Don't need
     * any synchronization on this method
     *     
     */
    public void setThreadNameFromToCopy(String from, int from_copy, String to, int to_copy)
    {
    	if (originStepName == null)
    		originStepName = from;
    	else{
    		synchronized(originStepName){
        		originStepName= from;
        	}
    	}
    	
    	originStepCopy.set(from_copy);  
    	
    	if (destinationStepName == null)
    		destinationStepName = to;
    	else{
    		synchronized(destinationStepName){
    			destinationStepName = to;    	
    		}
    	}
    		
    	destinationStepCopy.set(to_copy);
    }
    
    public String toString()
    {
    	StringBuffer str;
    	synchronized(originStepName){
    		str = new StringBuffer(originStepName);
    	}    	
    	str.append(".");
    	synchronized(originStepCopy){
    		str.append(originStepCopy);
    	}
    	str.append(" - ");
    	synchronized(destinationStepName){
    		str.append(destinationStepName);
    	}
    	str.append(".");
    	synchronized(destinationStepCopy){
    		str.append(destinationStepCopy);
    	}
        return str.toString();
    }

	/**
	 * @return the rowMeta
	 */
	public RowModel getRowMeta() {
		return rowMeta;
	}

	/**
	 * @param rowMeta the rowMeta to set
	 */
	public void setRowMeta(RowModel rowMeta) {
		this.rowMeta = rowMeta;
	}

}