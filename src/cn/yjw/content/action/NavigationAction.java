package cn.yjw.content.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.yjw.content.bean.Navigation;
import cn.yjw.content.service.NavigationService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author yangjunwei
 */
public class NavigationAction extends ActionSupport implements ModelDriven<Navigation> {

	private static final long serialVersionUID = 2652489609845331569L;
	
	private NavigationService navService;
	
	private Navigation nav = new Navigation();
	
	public NavigationService getNavService() {
		return navService;
	}
	public void setNavService(NavigationService navService) {
		this.navService = navService;
	}

	public Navigation getNav() {
		return nav;
	}
	public void setNav(Navigation nav) {
		this.nav = nav;
	}
	
	/**
	 * 获取JavaBean模型对象
	 */
	public Navigation getModel() {
		return this.nav;
	}
	
	/**
	 * 初始化编辑导航页面
	 * @return
	 */
	public String initSaveNavigationUi() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取文章分类对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)) {
			Long id = Long.valueOf(idStr);
			this.nav = navService.getNavigationById(id);
		}
		
		// 将操作类型加入值栈
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		
		return "initSaveNavUiSuccess";
	}
	
	/**
	 * 保存导航信息
	 * @return
	 */
	public String saveNavigation() {
		
		navService.saveNavigation(nav);
		
		return "saveNavSuccess";
	}
	
	/**
	 * 根据自定条件查询导航信息
	 * @return
	 */
	public String getNavigationsByCondition() {
		
		List<Navigation> navs = navService.getAllNavigations();
		
		// 将商品分类信息加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("navs", navs);
		
		return "getNavsByConditionSuccess";
	}
	
	/**
	 * 根据ID集合批量删除导航信息
	 */
	public void deleteNavigationsByIds() {
		
		
		
	}
	
}
