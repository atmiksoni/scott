package com.flyme.core.shiro.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.flyme.base.rbac.account.pojo.Account;


/**
 * 密码工具类
 */
public class PasswordHelper {
	private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private static String algorithmName = "md5";
	private static int hashIterations = 2;

	public static String encryptPassword(Account account, String password) {
		String salt = randomNumberGenerator.nextBytes().toHex();
		account.setSalt(salt);
		String newPassword = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(account.getAccountName() + salt), hashIterations).toHex();
		account.setPassword(newPassword);
		return newPassword;
	}
	/* 根据用户密码获取加密的密码 与 getPwd作用一样*/
	public static String getOldPassword(Account account, String password) {
		return new SimpleHash(algorithmName, password, ByteSource.Util.bytes(account.getAccountName() + account.getSalt()), hashIterations).toHex();
	}

	
	public static boolean checkMd5Password(String username, String password, String salt, String md5cipherText) {
		// 组合username,两次迭代，对密码进行加密
		String password_cipherText = new Md5Hash(password, username + salt, 2).toHex();
		return md5cipherText.equals(password_cipherText);
	}
	/* 根据用户密码获取加密的密码 与  getOldPassword作用一样*/
	public static String getPwd(String accountName, String password, String salt) {
		return new Md5Hash(password, accountName + salt, 2).toHex();
	}

	// 不能全是相同的数字或者字母（如：000000、111111、aaaaaa） 全部相同返回true
	public static boolean equalStr(String numOrStr) {
		boolean flag = true;
		char str = numOrStr.charAt(0);
		for (int i = 0; i < numOrStr.length(); i++) {
			if (str != numOrStr.charAt(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// 不能是连续的数字--递增（如：123456、12345678）连续数字返回true
	public static boolean isOrderNumeric(String numOrStr) {
		boolean flag = true;// 如果全是连续数字返回true
		boolean isNumeric = true;// 如果全是数字返回true
		for (int i = 0; i < numOrStr.length(); i++) {
			if (!Character.isDigit(numOrStr.charAt(i))) {
				isNumeric = false;
				break;
			}
		}
		if (isNumeric) {// 如果全是数字则执行是否连续数字判断
			for (int i = 0; i < numOrStr.length(); i++) {
				if (i > 0) {// 判断如123456
					int num = Integer.parseInt(numOrStr.charAt(i) + "");
					int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") + 1;
					if (num != num_) {
						flag = false;
						break;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	// 不能是连续的数字--递减（如：987654、876543）连续数字返回true
	public static boolean isOrderNumeric_(String numOrStr) {
		boolean flag = true;// 如果全是连续数字返回true
		boolean isNumeric = true;// 如果全是数字返回true
		for (int i = 0; i < numOrStr.length(); i++) {
			if (!Character.isDigit(numOrStr.charAt(i))) {
				isNumeric = false;
				break;
			}
		}
		if (isNumeric) {// 如果全是数字则执行是否连续数字判断
			for (int i = 0; i < numOrStr.length(); i++) {
				if (i > 0) {// 判断如654321
					int num = Integer.parseInt(numOrStr.charAt(i) + "");
					int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") - 1;
					if (num != num_) {
						flag = false;
						break;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	public static void main(String[] args) {
		Account account = new Account("1");
		account.setAccountName("admin");
		account.setMobile("15005771717");
		account.setSalt("a88576832f483aa881472e2f3440c04f");
		String pass = getOldPassword(account, "123456");
		System.out.println(pass);
		System.out.println(getPwd("admin", "123456", "a88576832f483aa881472e2f3440c04f"));
	}

}
