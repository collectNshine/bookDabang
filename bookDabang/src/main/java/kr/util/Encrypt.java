package kr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Encrypt {
	//salt 값을 얻을 수 있는 메소드 
	public static String getSalt() {
		SecureRandom r = new SecureRandom();
		byte[] salt  = new byte[20];
		
		r.nextBytes(salt);
		
		StringBuffer sb = new StringBuffer();
		for(byte b : salt) {
			sb.append(String.format("%02x", b));
			//0f식의 값이 나온다.
		}
		return sb.toString();//스트링 버퍼의 값을 스트링으로 보내준다. 
	}
	
	//암호화 메서드
	public static String getEncrypt(String password,String salt) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512"); // SHA-512도 64비트의 해시값을 리턴한다.
			
			md.update((password + salt).getBytes());
			byte[] pwdsalt = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for(byte b:pwdsalt) {
				sb.append(String.format("%02x", b));
			}
			result = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return result;
	}
}
