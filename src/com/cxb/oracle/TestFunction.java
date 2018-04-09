package com.cxb.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.junit.Test;

import com.cxb.uitls.JDBCUtils;

import oracle.jdbc.OracleTypes;

/**
 * @author ChenXb
 *               程序中调用存储函数
 * 2018年4月9日
 */
public class TestFunction {

	@Test
	public void testFunction(){
		//第一个问号是返回值
		//{?= call <procedure-name>[(<arg1>,<arg2>,...)]}
		String sql="{?=call queryempincome(?)}";
		Connection conn=null;
		CallableStatement call=null;
		try {
			//连接数据库
			conn=JDBCUtils.getConnection();
			//基于连接创建的statement
			call=conn.prepareCall(sql);
			//对于输出参数，声明
			call.registerOutParameter(1, OracleTypes.NUMBER);
			//对于输出参数，赋值
			call.setInt(2, 7839);
			//执行调用
			call.execute();
			//取出年收入的结果
			double income = call.getDouble(1);
			System.out.println("年收入："+income);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(conn, call, null);
		}
	}
}
