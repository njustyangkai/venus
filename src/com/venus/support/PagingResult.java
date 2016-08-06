package com.venus.support;

public class PagingResult
{
	private int total;

	private Object list;

	public int getTotal()
	{

		return total;
	}

	public void setTotal(int total)
	{

		this.total = total;
	}

	public Object getList()
	{

		return list;
	}

	public void setList(Object list)
	{

		this.list = list;
	}

	public static PagingResult getResult(int total, Object list)
	{

		PagingResult pagingResult = new PagingResult();
		pagingResult.setTotal(total);
		pagingResult.setList(list);

		return pagingResult;

	}

}
