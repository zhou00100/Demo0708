package student.util;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
			// ���浱ǰҳ�����ݵļ���
			private List<T> data;
			// �����ҳǰ���ܵļ�¼��
			private int totalRecords;
			// ���浱ǰҳ��
			private int pageNo;
			// ����ҳ��С
			private int pageSize;
			
			public List<T> getData() {
				return data;
			}
			public void setData(List<T> data) {
				this.data = data;
			}
			
			//��ȡ��¼����
			public int getTotalRecords() {
				/**
				 * �жϼ�¼������ҳ��Сȡ������
				 * �������Ϊ��õ��ľ�����ҳ��
				 *�����Ϊ�������ȡ�õ�ҳ���ϼ�1���ܴ�Ŷ�����ļ�¼
				 */
				return (this.totalRecords + this.pageSize - 1) / this.pageSize;//��ȡ��ҳ��
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
			
			
			//Ϊ��ʵ����ҳ��ĩҶ��ǰһҳ����һҳ��ҳ������
			//�����ṩ�ĸ�GET�����Ա���ʹ��EL���ʽ����
			//��ҳ��
			//${pageBean.firstPage}
			public int getFirstPage() {
				return 1;//���ص�һҳ
			}
			
			//���һҳ
			public int getLastPage() {
				if (this.totalRecords == 0) {
					//�����¼����Ϊ0��ֻ�е�һҳ
					return 1;
				}
				return this.getTotalRecords();//һ���¼�����������һҳ��ҳ��
			}
			
			//��ȡǰһҳ
			public int getPreviousPage() {
				if (this.pageNo == 1) {
					//������ǵ�һҳ�򲻿����ټ�һ�õ���0ҳ
					return 1;//ֱ�ӷ��ص�һҳ
				}
				return this.pageNo - 1;
			}
			
			//��ȡ��һҳ
			public int getNextPage() {
				if (this.pageNo == this.getLastPage()) {
					//����Ѿ������һҳ�޷�����������
					return this.pageNo;//ֱ�ӷ������һҳ��ҳ��
				}
				return this.pageNo + 1;		//��һҳ��������һҳ
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
