package student.util;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
			// 保存当前页的数据的集合
			private List<T> data;
			// 保存分页前的总的记录数
			private int totalRecords;
			// 保存当前页号
			private int pageNo;
			// 保存页大小
			private int pageSize;
			
			public List<T> getData() {
				return data;
			}
			public void setData(List<T> data) {
				this.data = data;
			}
			
			//获取记录总数
			public int getTotalRecords() {
				/**
				 * 判断记录总数和页大小取余数，
				 * 余数如果为零得到的就是总页数
				 *如果不为零必须在取得的页数上加1才能存放多出来的记录
				 */
				return (this.totalRecords + this.pageSize - 1) / this.pageSize;//获取总页数
			}
			
			
			public void setTotalRecords(int totalRecords) {
				this.totalRecords = totalRecords;
			}
			public int getPageNo() {
				return pageNo;
			}
			public void setPageNo(int pageNo) {
				this.pageNo = pageNo;
			}
			public int getPageSize() {
				return pageSize;
			}
			public void setPageSize(int pageSize) {
				this.pageSize = pageSize;
			}
			
			
			//为了实现首页、末叶、前一页、后一页分页工具栏
			//必须提供四个GET方法以便于使用EL表达式启动
			//首页：
			//${pageBean.firstPage}
			public int getFirstPage() {
				return 1;//返回第一页
			}
			
			//最后一页
			public int getLastPage() {
				if (this.totalRecords == 0) {
					//如果记录总数为0则只有第一页
					return 1;
				}
				return this.getTotalRecords();//一般记录总数就是最后一页的页号
			}
			
			//获取前一页
			public int getPreviousPage() {
				if (this.pageNo == 1) {
					//如果就是第一页则不可能再减一得到第0页
					return 1;//直接返回第一页
				}
				return this.pageNo - 1;
			}
			
			//获取后一页
			public int getNextPage() {
				if (this.pageNo == this.getLastPage()) {
					//如果已经是最后一页无法再往后增加
					return this.pageNo;//直接返回最后一页的页号
				}
				return this.pageNo + 1;		//后一页就是增加一页
			}
			
			
			public PageBean() {
				super();
				// TODO Auto-generated constructor stub
			}
			@Override
			public String toString() {
				return "PageBean [data=" + data + ", totalRecords=" + totalRecords
						+ ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
			}
}
