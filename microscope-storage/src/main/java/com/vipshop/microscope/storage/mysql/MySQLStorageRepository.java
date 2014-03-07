package com.vipshop.microscope.storage.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.storage.mysql.factory.RepositoryFactory;

public class MySQLStorageRepository {

	private static final Logger logger = LoggerFactory.getLogger(MySQLStorageRepository.class);
	
	public void create() {
		Statement stmt;
		try {
			stmt = RepositoryFactory.getDataSource().getConnection().createStatement();
			List<String> sqlList = new ArrayList<String>();
			InputStream sqlFileIn = this.getClass().getClassLoader().getResourceAsStream("vipshop_microscope.sql");
			StringBuffer sqlSb = new StringBuffer();
			byte[] buff = new byte[sqlFileIn.available()];
			int byteRead = 0;
			while ((byteRead = sqlFileIn.read(buff)) != -1) {
				sqlSb.append(new String(buff, 0, byteRead));
			}
			// Windows/Linux
			String[] sqlArr = sqlSb.toString().split("(;\\s*\\r\\n)|(;\\s*\\n)");
			for (int i = 0; i < sqlArr.length; i++) {
				String sql = sqlArr[i].trim();
				if (!sql.equals("")) {
					sqlList.add(sql);
				}
			}
			for (String sql : sqlList) {
				logger.info("add sql [" + sql + "] to batch execute");
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drop() {
		
	}

}
