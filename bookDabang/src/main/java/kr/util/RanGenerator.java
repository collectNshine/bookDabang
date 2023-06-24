package kr.util;
import java.util.Random;

public class RanGenerator {
	public static String NumGenerator() {
		Random random = new Random();
		String num = Integer.toString(random.nextInt(99999999)+11111111);
		return num;
	}
	
	public static String PwGenerator(int leng) {
			int index = 0;
			char[] charSet = new char[] {
					'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
					'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
					'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'!', '@', '#', '$', '%', '^', '&'
			};	

			StringBuffer password = new StringBuffer();
			Random random = new Random();

			for (int i = 0; i < leng ; i++) {
				double rd = random.nextDouble();
				index = (int) (charSet.length * rd);
				
				password.append(charSet[index]);
			}
			
			return password.toString(); 
	}
}
