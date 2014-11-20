package cn.yjw.utils.page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjunwei
 */
public class Page<T> {
	private List<T> list = new ArrayList<T>();// 每页显示的记录集合
	private int startIndex;// 当前页第一条记录在所有记录中的位置
	private int pageSize;// 每页显示的记录数
	private int totalCount;// 总记录数
	private int totalPage;// 总页数
	private int pageNumber;// 当前页的页码
	private String url;// 分页处理的路径

	public Page(int pageSize, int pageNumber) {
		
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;

		// 通过 "每页记录数" 和 "当前页页码" 计算出 "新页的第一条记录在总记录中的编号"
		this.startIndex = (pageNumber - 1) * pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		// 通过 "每页记录数" 和 "当前页页码" 重新计算出 "新页的第一条记录在总记录中的编号"
		this.startIndex = (pageNumber - 1) * pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {

		int mod = totalCount % pageSize;
		if (mod == 0) {
			this.totalPage = totalCount / pageSize;
		} else {
			this.totalPage = totalCount / pageSize + 1;
		}

		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		// 通过 "每页记录数" 和 "当前页页码" 重新计算出 "新页的第一条记录在总记录中的编号"
		this.startIndex = (pageNumber - 1) * pageSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Page [list=" + list + ", pageNumber=" + pageNumber
				+ ", pageSize=" + pageSize + ", startIndex=" + startIndex
				+ ", totalCount=" + totalCount + ", totalPage=" + totalPage
				+ ", url=" + url + "]";
	}
}
