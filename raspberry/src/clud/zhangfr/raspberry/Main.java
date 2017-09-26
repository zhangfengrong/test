package clud.zhangfr.raspberry;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.mysql.jdbc.StringUtils;
/**
 * 
 * @author zfr11
 */

/**
 *
 * @anthor leo
 * @date 2016年6月27日下午12:40:14
 * @description
 *
 **/

public class Main {
	public static void main(String[] args){
		boolean flg = false;
		while (!flg) {
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
				printlnLog(e.getMessage());
			}
			try {
				String inIp = start();
				if(!StringUtils.isNullOrEmpty(inIp)&&
						!"127.0.1.1".equals(inIp)&&
							!"error".equals(inIp)){
					flg =true;
				}
			} catch (Exception e) {
				printlnLog(e.getMessage());
			}
		}
	}
	private static String intranet_ip;
	private static String public_ip;
	private static boolean sqlOk=true;
	private static String start(){
		String inIp = null;
		String publicIp =null;
		try {
			inIp = IpUtils.getInIp();
			publicIp = IpUtils.getPublicIp();
		} catch (UnknownHostException e) {
			printlnLog(e.getMessage());
		} catch (IOException e) {
			printlnLog(e.getMessage());
		}
		
		if(StringUtils.isNullOrEmpty(inIp)){
			inIp="error";
		}
		if(StringUtils.isNullOrEmpty(publicIp)){
			publicIp="error";
		}
		if(inIp.equals(intranet_ip)&&publicIp.equals(public_ip)&&sqlOk){
			System.out.println("ip信息无变化"+inIp+"/"+publicIp);
		}else{
			intranet_ip = inIp;
			public_ip =publicIp;
			String sql ="insert ip(intranet_ip,public_ip,created_ts)values('"+inIp+"','"+publicIp+"',NOW())";
			printlnLog(sql);
			//insertDb(sql); //插入数据库
		}
		return inIp;
	}
	private static void insertDb(String sql) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JDBCUtil.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			sqlOk = true;
		} catch (SQLException e) {
			sqlOk = false;
			printlnLog(e.getMessage());
		}finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				printlnLog("[close]"+e.getMessage());
			}
		}
	}
	public static void printlnLog(String msg){
		try {
			EmailTools.sendEmail("树莓派状态变更",msg,"1124008958@qq.com");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(new Date()+":\t"+msg);
	}
}
