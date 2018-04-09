package com.cxb.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.junit.Test;

import com.cxb.uitls.JDBCUtils;

import oracle.jdbc.OracleTypes;

/**
 * @author ChenXb
 *              程序里面调用存储过程
 * 2018年4月9日
 */
public class TestProcedure {

	/**
	 * 
	 * testProcedure  
	 * @author 81046
	 * @date 2018年4月9日下午2:00:39
	 */
/*  --out参数：查询某个员工姓名，月薪和职位
	create or replace procedure queryempinform(eno in number,
	                                           pename out VARCHAR2,
	                                           psal   out number,
	                                           pjob   out varchar2)                             
	as
	begin
	  --得到该员工的姓名，月薪和职位
	  select ename,sal,empjob into pename ,psal,pjob from emp where empno=eno;

	end;
	/     
*/
	@Test
	public void testProcedure(){
		//jdk api上面的写法
		//{call <procedure-name>[(<arg1>,<arg2>,...)]}
		String sql="{call queryempinform(?,?,?,?)}";
		Connection conn=null;
		CallableStatement call=null;
		try {
			//得到一个连接
			conn=JDBCUtils.getConnection();
			//通过连接创建statement
			call=conn.prepareCall(sql);
			//对应in参数   赋值
			call.setInt(1, 7839);
			
			//对于out参数，申明
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.NUMBER);
			call.registerOutParameter(4, OracleTypes.VARCHAR);
			//执行调用
			call.execute();
			
			//取出结果
			String name = call.getString(2);
			double sal = call.getDouble(3);
			String job = call.getString(4);
			System.out.println(name+"的工资是："+sal+"   工作是："+job);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(conn, call, null);
		}
	}
}
