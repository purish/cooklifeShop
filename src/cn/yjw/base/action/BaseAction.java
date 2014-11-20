package cn.yjw.base.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author yangjunwei
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	private static final long serialVersionUID = 3833288688698579604L;

	public T getModel() {
		return null;
	}
}
