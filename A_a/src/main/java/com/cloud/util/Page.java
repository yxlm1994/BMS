package com.cloud.util;

public class Page {

	private int currentPageNo = 1; // ��ǰҳ��

	private int pageSize = Constants.PAGESIZE; // ÿҳ��ʾ����Ŀ��

	private int totalCount; // �ܵļ�¼����

	private int totalPageCount; // �ܵ�ҳ��

	@SuppressWarnings("unused")
	private int startPos; // ��ҳ�ĳ�ʼλ��

	@SuppressWarnings("unused")
	private boolean hasFirst;// �Ƿ�����ҳ

	@SuppressWarnings("unused")
	private boolean hasPre;// �Ƿ�����һҳ

	@SuppressWarnings("unused")
	private boolean hasNext;// �Ƿ�����һҳ

	@SuppressWarnings("unused")
	private boolean hasLast;// �Ƿ������һҳ

	// ȡ����ҳ������ҳ��=�ܼ�¼��/ÿҳ����
	public int getTotalPageCount() {
		totalPageCount = getTotalCount() / getPageSize();
		return (totalCount % pageSize == 0) ? totalPageCount : totalPageCount + 1;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * ȡ��ѡ���¼�ĳ�ʼλ��
	 * 
	 * @return
	 */
	public int getStartPos() {
		return (currentPageNo - 1) * pageSize;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	/**
	 * �Ƿ��ǵ�һҳ
	 * 
	 * @return
	 */
	public boolean isHasFirst() {
		return (currentPageNo == 1) ? false : true;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}

	/**
	 * �Ƿ�����ҳ
	 * 
	 * @return
	 */
	public boolean isHasPre() {
		// �������ҳ����ǰһҳ����Ϊ����ҳ�Ͳ��ǵ�һҳ
		return isHasFirst() ? true : false;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	/**
	 * �Ƿ�����һҳ
	 * 
	 * @return
	 */
	public boolean isHasNext() {
		// �����βҳ������һҳ����Ϊ��βҳ�����������һҳ
		return isHasLast() ? true : false;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	/**
	 * �Ƿ���βҳ
	 * 
	 * @return
	 */
	public boolean isHasLast() {
		// ����������һҳ����βҳ
		return (currentPageNo == getTotalCount()) ? false : true;
	}

	public void setHasLast(boolean hasLast) {
		this.hasLast = hasLast;
	}
}