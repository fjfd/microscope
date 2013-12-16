package com.vipshop.microscope.report.sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.report.factory.MySQLFactory;
 
public class SqlScriptExecutor {
	
	public static Logger logger = LoggerFactory.getLogger(SqlScriptExecutor.class);
	
	private Connection conn;
	
	public SqlScriptExecutor() throws SQLException {
		this.conn = MySQLFactory.getDataSource().getConnection();
	}
	
    public void executeSql(String sqlFile) throws Exception {
        Statement stmt = null;
        List<String> sqlList = loadSql(sqlFile);
        stmt = conn.createStatement();
        for (String sql : sqlList) {
        	logger.info("add sql [" + sql + "] to batch execute");
            stmt.addBatch(sql);
        }
        stmt.executeBatch();
    }

    private List<String> loadSql(String sqlFile) throws Exception {
        List<String> sqlList = new ArrayList<String>();
        InputStream sqlFileIn = this.getClass().getClassLoader().getResourceAsStream(sqlFile);
        try {
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
            return sqlList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
        	sqlFileIn.close();
        }
    } 
    
    public static void main(String[] args) throws Exception {
    	SqlScriptExecutor executor = new SqlScriptExecutor();
        executor.executeSql("vipshop_microscope.sql");
    }
}