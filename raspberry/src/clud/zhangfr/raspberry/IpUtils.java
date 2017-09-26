package clud.zhangfr.raspberry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class IpUtils {
	static String getInIp() throws UnknownHostException{
			return InetAddress.getLocalHost().getHostAddress();
	}
	static String getPublicIp() throws IOException{
		InputStream ins = null;
		try {
			URL url = new URL("http://1212.ip138.com/ic.asp");
//			URL url = new URL("http://www.ip138.com/");
			URLConnection con = url.openConnection();
			ins = con.getInputStream();
			InputStreamReader isReader = new InputStreamReader(ins, "GB2312");
			BufferedReader bReader = new BufferedReader(isReader);
			StringBuffer webContent = new StringBuffer();
			String str = null;
			while ((str = bReader.readLine()) != null) {
				webContent.append(str+"\n");
			}
			int start = webContent.indexOf("[") + 1;
			int end = webContent.indexOf("]");
			return webContent.substring(start, end);
		} finally {
			if (ins != null) {
				ins.close();
			}
		}
	}
}
