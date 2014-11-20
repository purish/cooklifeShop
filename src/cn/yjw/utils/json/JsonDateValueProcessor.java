package cn.yjw.utils.json;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * @author yangjunwei
 * JSON日期格式处理器
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

	private String datePattern = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 默认构造方法
	 */
	public JsonDateValueProcessor() {
		super();
	}
	
	/**
	 * 设置日期格式的构造方法
	 * @param format
	 */
	public JsonDateValueProcessor(String format) {
		super();
		this.datePattern = format;
	}
	
	public String getDatePattern() {
		return datePattern;
	}
	
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	
	
	/**
	 * 将日期类型转换为制定格式的字符串
	 * @param value
	 * @return
	 */
	private Object process(Object value){
		try {
			if(value instanceof Timestamp){
				SimpleDateFormat sdf = new SimpleDateFormat(datePattern,Locale.UK);
				return sdf.format(value);
			}
			return value==null?"":value.toString();
			
		} catch (Exception e) {
			//log:JavaBean转换为JSON出现错误
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 当调用方法：JSONArray.fromObject(bean)——转换到指定的类型时，
	 * JSON框架会调用此方法
	 */
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		
		return process(value);
	}

	/**
	 * 当调用方法：JSONObject.fromObject(bean)——转换到指定的类型时，
	 * JSON框架会调用此方法
	 */
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		
		return process(value);
	}
	
}