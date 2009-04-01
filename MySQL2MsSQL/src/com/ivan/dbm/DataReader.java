package com.ivan.dbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.dom4j.DocumentException;

public class DataReader implements Runnable {
	private Connection con = null;
	private Connection toConn = null;
	private ResultSet rs = null;
	private int pagesize = 0;
	private int retryTimes = 1;
	private long retryInterval = 1000;
	public final String fileName = "temp";

	public DataReader() {

		init();
	}

	public void init() {
		try {

			String fromDbType = DMProperties.getInstance()
					.getProps("reader.db");
			String toDbType = DMProperties.getInstance().getProps("writer.db");
			retryTimes = Integer.parseInt(DMProperties.getInstance().getProps(
					"job.retry")) + 1;
			retryInterval = Long.parseLong(DMProperties.getInstance().getProps(
					"job.retryInterval"));
			if (!fromDbType.equalsIgnoreCase("mysql")
					|| !toDbType.equalsIgnoreCase("sql server")) {
				throw new RuntimeException(
						"Unsupport database type, please provide the correct database type.");
			}

			Class.forName(DMProperties.getInstance().getProps("reader.driver"));
			con = DriverManager.getConnection(DMProperties.getInstance()
					.getProps("reader.url"), DMProperties.getInstance()
					.getProps("reader.user"), DMProperties.getInstance()
					.getProps("reader.password"));
			
			Class.forName(DMProperties.getInstance().getProps("writer.driver"));
			toConn = DriverManager.getConnection(DMProperties.getInstance()
					.getProps("writer.url"), DMProperties.getInstance()
					.getProps("writer.user"), DMProperties.getInstance()
					.getProps("writer.password"));
			

			DMLogger.getLogger().info("Start to read data");
			pagesize = Integer.parseInt(DMProperties.getInstance().getProps(
					"page.size"));
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			DMLogger.getLogger().error(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			DMLogger.getLogger().error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			
			
			while (retryTimes > 0) {
				MysqlStructure.createJob(con, DMProperties.getInstance().getProps("job.conf"));
				
				ArrayList<DMJob> joblist = DMJobFactory.configure(DMProperties
						.getInstance().getProps("job.conf"));

				checkJobs(joblist);

				String clearTable = DMProperties.getInstance().getProps(
						"job.clearTable");
				

				if (clearTable.equals("true")) {
					clearTable(joblist);
				}
				
				toConn.setAutoCommit(false);
				DMJob job = null;
				for (int i = 0; i < joblist.size(); i++) {
					job = (DMJob) joblist.get(i);
					if (job.getSize() > 0) {
						long startTime = System.currentTimeMillis();
						DMLogger.getLogger().info("start job(" + i + "): from ["
								+ job.getFromTable() + "] to ["
								+ job.getToTable() + "]");
						readData(job, i);
						DMLogger.getLogger().info("cost " + (System.currentTimeMillis() - startTime) + "ms");
					} else {
						DMLogger.getLogger().warn("No data: job(" + i + ") from ["+ 
								job.getFromTable() + "] to ["+ job.getToTable() + "]");
					}
				}
				if (validate(joblist))
					break;
				else {
					toConn.close();
					con.close();
					DMLogger.getLogger().info(
							"Retry in " + retryInterval + "ms");
					Thread.sleep(retryInterval);
					DMLogger.getLogger().info("Retry");
					init();
					retryTimes--;
				}

			}
			con.close();
			toConn.close();
		}
		catch (DocumentException e) {
			DMLogger.getLogger().error(e.getMessage());
			e.printStackTrace();
		}
		catch (SQLException e) {
			DMLogger.getLogger().error(e.getMessage());
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			DMLogger.getLogger().error(e.getMessage());
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			DMLogger.getLogger().error(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				con.close();
				toConn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void readData(DMJob job, int jobindex) {
		try {
			MySqlPage page;
			PreparedStatement psmt;
			page = new MySqlPage(0, pagesize, job, con);
			while (page.hasNext()) {
				System.out.println(page.getCurrentRow() + "/" + page.getTableSize());
				
				psmt = toConn.prepareStatement(job.getExecuteSql());
				if(job.getKeyList() != null && job.getKeyList().size() > 0)
				{
					rs = page.getNextPage(con,job.getKeyList().get(0).getColumn());
				}
				else
				{
					rs = page.getNextPage(con);
				}
				while (rs.next()) {
					for (int i = 0; i < job.getFields().size(); i++) {
						if(job.getFields().get(i).getColumn().equals("CName") && (job.getFromTable().equalsIgnoreCase("application") || job.getFromTable().equalsIgnoreCase("voter")))
						{
							psmt.setObject(i + 1,rs.getString(i + 1));
						}
						else
						{
							psmt.setObject(i + 1, rs.getObject(i + 1));
						}
					}
					psmt.addBatch();
				}
				psmt.executeBatch();
				psmt.close();
				toConn.commit();
				rs.close();
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			DMLogger.getLogger().error(e.getMessage());
		}
	}

	public void clearTable(ArrayList<DMJob> joblist) {
		try {
			Class.forName(DMProperties.getInstance().getProps("writer.driver"));

			Connection conn = DriverManager.getConnection(DMProperties
					.getInstance().getProps("writer.url"), DMProperties
					.getInstance().getProps("writer.user"), DMProperties
					.getInstance().getProps("writer.password"));
			Statement smt;
			String toTable;
			DMJob job;
			String sql;
			for (int i = 0; i < joblist.size(); i++) {
				job = (DMJob) joblist.get(i);
				toTable = job.getToTable();
				DMLogger.getLogger().info("clear table :" + toTable);
				sql = "delete from " + "[" + toTable + "]";
				smt = conn.createStatement();
				smt.execute(sql);
				smt.close();
			}
			conn.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			DMLogger.getLogger().error(e.getMessage());
		}
		catch (SQLException e) {
			e.printStackTrace();
			DMLogger.getLogger().error(e.getMessage());
		}
	}

	public void checkJobs(ArrayList<DMJob> joblist) {
		try {
			Statement smt;
			String fromTable;
			DMJob job;
			String sql;
			ResultSet rs;
			long size;
			for (int i = 0; i < joblist.size(); i++) {
				job = (DMJob) joblist.get(i);
				fromTable = job.getFromTable();
				// DMLogger.getLogger().info("clear table :" + toTable);
				sql = "select count(*) from " + fromTable;
				smt = con.createStatement();
				rs = smt.executeQuery(sql);
				rs.next();
				size = rs.getLong(1);
				job.setSize(size);
				rs.close();
				
				Statement smtTo = toConn.createStatement();
				try{
					DMLogger.getLogger().info("drop table " + job.getToTable());
					smtTo.execute("drop table " + "[" + job.getToTable() + "]");
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				try{
					DMLogger.getLogger().info("create table " + job.getToTable());
					smtTo.execute(job.getCreateTableSql());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				smtTo.close();
			}
		}
		catch (SQLException e) {
			DMLogger.getLogger().error("Check table size error");
			e.printStackTrace();
			DMLogger.getLogger().error(e.getMessage());
		}
	}

	public boolean validate(ArrayList<DMJob> joblist)
			throws ClassNotFoundException, SQLException {
		Class.forName(DMProperties.getInstance().getProps("writer.driver"));

		Connection conn = DriverManager.getConnection(DMProperties
				.getInstance().getProps("writer.url"), DMProperties
				.getInstance().getProps("writer.user"), DMProperties
				.getInstance().getProps("writer.password"));

		long maxTimeOut = 60000;
		long waitTime = 0;
		ArrayList<Long> fromSizes = new ArrayList<Long>();
		ArrayList<Long> toSizes = new ArrayList<Long>();
		Statement smt;
		String fromTable;
		String toTable;
		DMJob job;
		ResultSet rs;
		boolean check = false;

		try {

			for (int i = 0; i < joblist.size(); i++) {
				job = (DMJob) joblist.get(i);
				fromTable = job.getFromTable();
				smt = con.createStatement();
				rs = smt.executeQuery("select count(*) from " + fromTable);
				rs.next();
				fromSizes.add(new Long(rs.getLong(1)));
				rs.close();
			}

			while (waitTime < maxTimeOut && check == false) {
				Thread.sleep(1000);
				toSizes.clear();
				for (int i = 0; i < joblist.size(); i++) {
					job = (DMJob) joblist.get(i);
					toTable = job.getToTable();
					smt = conn.createStatement();
					rs = smt.executeQuery("select count(*) from " + "[" + toTable + "]");
					rs.next();
					toSizes.add(new Long(rs.getLong(1)));
					rs.close();
				}
				if (fromSizes.equals(toSizes)) {
					check = true;
				}
				waitTime += 1000;
			}
			if (check == true) {
				DMLogger.getLogger().info("done success");
				return true;
			} else {
				DMLogger.getLogger().error("fail because of unequal row");
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			DMLogger.getLogger().error(e.getMessage());
		}
		return false;
	}
}
