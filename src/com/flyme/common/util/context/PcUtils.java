package com.flyme.common.util.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.flyme.common.util.ObjectUtils;



/**
 * pc操作工具类
 */
public class PcUtils {
	private PcUtils() {
	}

	/**
	 * 按照"XX-XX-XX-XX-XX-XX"格式，获取本机MAC地址
	 * 
	 * @throws Exception
	 */
	public static String getMacAddress() throws Exception {
		Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
		while (ni.hasMoreElements()) {
			NetworkInterface netI = ni.nextElement();
			byte[] bytes = netI.getHardwareAddress();
			if (netI.isUp() && netI != null && bytes != null && bytes.length == 6) {
				StringBuffer sb = new StringBuffer();
				for (byte b : bytes) {
					// 与11110000作按位与运算以便读取当前字节高4位
					sb.append(Integer.toHexString((b & 240) >> 4));
					// 与00001111作按位与运算以便读取当前字节低4位
					sb.append(Integer.toHexString(b & 15));
					sb.append("-");
				}
				sb.deleteCharAt(sb.length() - 1);
				return sb.toString().toUpperCase();
			}
		}
		return null;
	}

	/**
	 * 获取客户端IP
	 */
	public static String getIpAddr() {
		HttpServletRequest request = ContextUtils.getRequest();
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ObjectUtils.isNotEmpty(ip)) {
			String[] ips = ip.split(",");
			ip = ips[0];
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "218.28.191.23";
		}
		return ip;
	}

	/**
	 * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 获取Unix网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			/**
			 * Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				/**
				 * 寻找标示字符串[hwaddr]
				 */
				index = line.toLowerCase().indexOf("hwaddr");
				/**
				 * 找到了
				 */
				if (index != -1) {
					/**
					 * 取出mac地址并去除2边空格
					 */
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取Linux网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getLinuxMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			/**
			 * linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("硬件地址");
				/**
				 * 找到了
				 */
				if (index != -1) {
					/**
					 * 取出mac地址并去除2边空格
					 */
					mac = line.substring(index + 4).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		// 取不到，试下Unix取发
		if (mac == null) {
			return getUnixMACAddress();
		}

		return mac;
	}
	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			/**
			 * windows下的命令，显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ipconfig /all");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				/**
				 * 寻找标示字符串[physical address]
				 */
				// index = line.toLowerCase().indexOf("physical address");
				// if (index != -1) {
				if (line.split("-").length == 6) {
					index = line.indexOf(":");
					if (index != -1) {
						/**
						 * 取出mac地址并去除2边空格
						 */
						mac = line.substring(index + 1).trim();
					}
					break;
				}
				index = line.toLowerCase().indexOf("物理地址");
				if (index != -1) {
					index = line.indexOf(":");
					if (index != -1) {
						/**
						 * 取出mac地址并去除2边空格
						 */
						mac = line.substring(index + 1).trim();
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	public static String getMac() {
		String os = getOSName();
		String mac;
		if (os.startsWith("windows")) {
			mac = getWindowsMACAddress();
		} else if (os.startsWith("linux")) {
			mac = getLinuxMACAddress();
		} else {
			mac = getUnixMACAddress();
		}
		return mac == null ? "" : mac;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("操作系统:" + getOSName());
		System.out.println("window系统下网卡地址:" + PcUtils.getMacAddress());
		// System.out.println("IP地址:" + PcUtils.getIpAddr());
		System.out.println("网卡地址:" + PcUtils.getMac());
	}

}