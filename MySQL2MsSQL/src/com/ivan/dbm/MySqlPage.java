package com.ivan.dbm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlPage {
	private long maxRow;
	private long currentRow;
	private long tableSize;
	private String querySql;
	private boolean hasNext = true;
	private String table;
	public MySqlPage(long startRow, long maxRow,DMJob job,Connection conn)
	{
		this.maxRow = maxRow;
		this.currentRow = startRow;
		this.table = job.getFromTable();
		String sql = "select count(*) as count from " + table;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			this.tableSize = rs.getLong("count");
			rs.close();
			pstmt.close();
			if(tableSize == 0)
				hasNext = false;
		}
		catch (Exception e) {
			DMLogger.getLogger().error(e.getMessage());
			e.printStackTrace();
		}
		this.querySql = "select ";
		for(int i = 0; i < job.getFields().size(); i++)
		{
			this.querySql = this.querySql + (job.getFields().get(i).getColumn()).replaceAll("\\[", "").replaceAll("\\]", "");
			if(i < job.getFields().size() - 1)
			{
				this.querySql = this.querySql + ",";
			}
		}
		this.querySql = this.querySql + " from " + table;
	}
	
	public ResultSet getNextPage(Connection con ) throws SQLException
	{
		//select * from user where (UserID >= (select UserID from user order by UserID limit 21010,1)) limit 10
		//long startTime = System.currentTimeMillis();
		//System.out.print((int)(((float)currentRow/(float)tableSize) * 100)+"%");
		PreparedStatement pstmt = con.prepareStatement(querySql+" limit " + currentRow +"," + maxRow,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		currentRow = currentRow + maxRow;
		if(currentRow >= tableSize)
		{
			hasNext = false;
			//System.out.print("100%");
		}
		ResultSet rs = pstmt.executeQuery();
		
		//System.out.println(System.currentTimeMillis() - startTime);
		return rs;
	}
	public ResultSet getNextPage(Connection con, String keyColumn) throws SQLException
	{
		//select * from user where (UserID >= (select UserID from user order by UserID limit 21010,1)) limit 10
		//long startTime = System.currentTimeMillis();
		//System.out.print(((int)((float)currentRow/(float)tableSize) * 100)+"%");
		String sql = querySql + " where (" + keyColumn + ">= (select " + keyColumn + " from " + table + " order by " + keyColumn + " asc limit " + currentRow + ",1)) limit " + maxRow;
		PreparedStatement pstmt = con.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		currentRow = currentRow + maxRow;
		if(currentRow >= tableSize)
		{
			hasNext = false;
			//System.out.print("100%");
		}
		ResultSet rs = pstmt.executeQuery();
		//System.out.println(System.currentTimeMillis() - startTime);
		//System.out.println("next page");
		return rs;
	}
	public long getCurrentRow()
	{
		return currentRow;
	}
	public long getTableSize()
	{
		return tableSize;
	}
	public boolean hasNext()
	{
		return hasNext;
	}
}
