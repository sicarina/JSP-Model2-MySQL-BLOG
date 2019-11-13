package com.sample.util;

import java.security.MessageDigest;
import java.util.Random;

// 256bit, 해시되었으므로 복호화 불가
public class SHA256 {
	public static String getEncrypt(String rawPassword, String salt) {
		String result = "";
		byte bPassword[] = rawPassword.getBytes();
		byte bSalt[] = salt.getBytes();
		
		// 패스워드와 salt를 카피해서 합성한 byte배열 생성 
		byte bSynthesizedPassword[] = new byte[bPassword.length + bSalt.length];
		System.arraycopy(bPassword, 0, bSynthesizedPassword, 0, bPassword.length);
		System.arraycopy(bSalt, 0, bSynthesizedPassword, bPassword.length, bSalt.length);
		
		try {
			// 해시 알고리즘 선택
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			// md에 패스워드 원문을 전달하여 SHA-256으로 암호화된 값 생성
			md.update(bSynthesizedPassword);
			
			// SHA-256으로 암호화된 값 리턴받음 : 10진수
			byte bResult[] = md.digest();
			
			// 10진수로 이루어진 바이트 배열을 16진수로 변경하여 StringBuffer에 저장
			StringBuffer sb = new StringBuffer();
			for (byte b : bResult) {
				// 바이트는 -128~127이므로 0~255로 표현하기 위해 변경 : 0xff(16진수)는 10진수로 하면 255
				// int를 byte로 변경할 경우 2진수로 표현된 숫자의 첫자리가 0이면 앞쪽은 모두 0으로, 첫자리가 1이면 앞쪽은 모두 1로 채워짐
				// ex. int 150 = 00000000 00000000 00000000 10010110
				//	-> byte 150 = 11111111 11111111 11111111 10010110 :: 숫자 깨짐
				// 깨진 byte 데이터를 0~255로 변경하여 원래 숫자 그대로 확인하기 위해
				// byte로 변경된 데이터를 0xff(16진수, =255, =00000000 00000000 00000000 11111111)과 & 연산 수행
				// &(and) 연산은 1&1만 1로 리턴되고 다른 연산은 0으로 리턴되므로 깨진 byte의 앞쪽 1을 모두 0으로 치환하여 원래 수 확인 가능
				int chgByte = b & 0xff;
				
				// 10진수의 int형을 16진수의 int형으로 변경한 후 String 타입으로 형변환
				sb.append(Integer.toString(chgByte, 16));
			}
			
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String getSalt() {
        // 랜덤한 바이트로 이루어진 salt 값 생성
        byte[] salt = new byte[8];
		Random random = new Random();
        random.nextBytes(salt);
         
        // 생성한 salt값을 문자열로 변경
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < salt.length; i++) {
            // byte값을 Hex 값으로 변경
            sb.append(String.format("%02x",salt[i]));
        }
         
        return sb.toString();
	}
}
