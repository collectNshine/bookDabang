package kr.util;
import java.util.Random;

public class RanGenerator {
	public static String NumGenerator() {
		Random random = new Random();
		String num = Integer.toString(random.nextInt(99999999)+11111111);
		return num;
	}
	
	public static String PwGenerator(int leng) {//임시 패스워드는 10자로 보내자..
			int index = 0;
			char[] charSet = new char[] {
					'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
					'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
					'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'!', '@', '#', '$', '%', '^', '&'
			};	//배열안의 문자 숫자는 원하는대로

			StringBuffer password = new StringBuffer();
			Random random = new Random();

			for (int i = 0; i < leng ; i++) {
				double rd = random.nextDouble();
				index = (int) (charSet.length * rd);
				
				password.append(charSet[index]);
			}
			
			return password.toString(); 
		    //StringBuffer를 String으로 변환해서 return 하려면 toString()을 사용하면 된다.
	}
}
