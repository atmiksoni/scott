package com.flyme.thirdsdk.sms;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.flyme.common.util.ObjectUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtil {
	 public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	    public static String encode(String key, String data) {
	        try {
	            String str = encode(key, data.getBytes());
	            return str;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "";
	    }

	    public static String encode1(String key, String data, String iv) throws Exception {
	        return encode1(key, data.getBytes(), iv);
	    }

	    public static String encode(String key, byte[] data) throws Exception {
	        try {
	            DESKeySpec dks = new DESKeySpec(key.getBytes());

	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            Key secretKey = keyFactory.generateSecret(dks);
	            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
	            IvParameterSpec iv = new IvParameterSpec(JiaMiUtils.DESIV.getBytes());
	            AlgorithmParameterSpec paramSpec = iv;
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

	            byte[] bytes = cipher.doFinal(data);
	            return new BASE64Encoder().encode(bytes);
	        } catch (Exception e) {
	            throw new Exception(e);
	        }
	    }

	    public static String encode1(String key, byte[] data, String sic) throws Exception {
	        try {
	            DESKeySpec dks = new DESKeySpec(key.getBytes());

	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            Key secretKey = keyFactory.generateSecret(dks);
	            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
	            IvParameterSpec iv = new IvParameterSpec(sic.getBytes());
	            AlgorithmParameterSpec paramSpec = iv;
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

	            byte[] bytes = cipher.doFinal(data);
	            return new BASE64Encoder().encode(bytes);
	        } catch (Exception e) {
	            throw new Exception(e);
	        }
	    }

	    @SuppressWarnings("finally")
		public static byte[] decode(String key, byte[] data) throws Exception {
	    	byte[] result=null;
	        try {
	            DESKeySpec dks = new DESKeySpec(key.getBytes());
	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            Key secretKey = keyFactory.generateSecret(dks);
	            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
	            IvParameterSpec iv = new IvParameterSpec(JiaMiUtils.DESIV.getBytes());
	            AlgorithmParameterSpec paramSpec = iv;
	            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
	            result= cipher.doFinal(data);
	        } catch (Exception e) {
	            throw new Exception(e);
	        }finally{
	        	return result;
	        }
	    }
	    
	    public static String decodeValue(String key, String data) throws Exception {
	        byte[] datas;
	        String value = null;
	        datas = decode(key, new BASE64Decoder().decodeBuffer(data));
	        if(ObjectUtils.isEmpty(datas)){
	        	return "";
	        }
	        value = new String(datas);
	        if (value.equals("")) {
	            throw new Exception();
	        }
	        return value;
	    }
	    
	    
	    public static void main(String[] args) throws Exception {
	    	String tempKey = JiaMiUtils.getkey("1516441343166");//加密后的key
	    	JiaMiUtils.DESIV  = JiaMiUtils.getiv("1516441343166");//加密后的IV
	    	String mobile=encode(tempKey, "18530818979");
	    	
	        System.out.println(tempKey);
	        System.out.println(JiaMiUtils.DESIV);
	        System.out.println(mobile);
	        
	    	System.out.println(DESUtil.decodeValue(tempKey,"FdPHGQac+UZaMgORn/H9DA=="));
		}
}
