package com.cxb.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.junit.Test;

import com.cxb.uitls.JDBCUtils;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleCallableStatement;

/**
 * @author ChenXb
 *               程序访问包体里面的存储过程
 * 2018年4月9日
 */
public class TestCursor {
	
	@Test
	public void testCursor(){
		//{call <procedure-name>[(<arg1>,<arg2>,...)]}
		String sql="{call MYPACKAGE.queryEmpList(?,?)}";
		Connection conn=null;
		CallableStatement call=null;
		ResultSet rs=null;
		try {
			//连接数据库
			conn=JDBCUtils.getConnection();
			//基于连接创建的statement
			call=conn.prepareCall(sql);
			//对于输出参数，赋值
			call.setInt(1, 10);
			//对于输出参数，声明
			call.registerOutParameter(2, OracleTypes.CURSOR);
			//执行调用
			call.execute();
			//因为call是接口  要使用其子类
			rs = ((OracleCallableStatement)call).getCursor(2);
			while(rs.next()){
				//取出员工号，薪水，姓名，职位
				int empno = rs.getInt("empno");
				String name = rs.getString("ename");
				double sal = rs.getDouble("sal");
				String empjob = rs.getString("empjob");
				System.out.println(empno+name+sal+empjob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(conn, call, rs);
		}
	}
}
