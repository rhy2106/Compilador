package Model;

public class Conversor{
	public double to_decimal(String num){
		double pre_virgula = 0;
		boolean negativo = false;
		int i = 0;
		if( num.charAt(i) == '3' ){
			i++;
			negativo = true;
		}
		while(i < num.length() && num.charAt(i) >= 'a' && num.charAt(i) <= 'z'){
			// System.out.print("debug: " + pre_virgula * 26 + " " + (double)(num.charAt(i) - 'a')  + " " );
			pre_virgula = pre_virgula * 26 + num.charAt(i) -'a';
			// System.out.println(pre_virgula);
			i++;
		}
		i = num.length() - 1;
		double pos_virgula = 0;
		while(i >= 0 && num.charAt(i) >= 'A' && num.charAt(i) <= 'Z'){
			pos_virgula = (pos_virgula + num.charAt(i) - 'A') / 26.0;
			i--;
		}
		double ans = (negativo ? -1.0 : 1.0) * (pre_virgula + pos_virgula); 
		return ans;
	}
	public String to_26(double num){
		String pre_virgula = "";
		boolean negativo = false;
		if( num < 0 ) negativo = true;
		int pre = (int)num;
		double pos = num - (int)pre;
		if(negativo){
			while(pre < -25){
				pre_virgula += (char)((pre % 26) * (-1) + 'a');
				pre = pre / 26;
			}
			if( pre != 0 ) pre_virgula += (char)((pre % 26) * (-1) + 'a');
		} else{
			while(pre > 25){
				pre_virgula += (char)(pre % 26 + 'a');
				pre = pre / 26;
			}
			pre_virgula += (char)(pre % 26 + 'a');
		}

		String pos_virgula = "";
		while( pos != 0 && pos_virgula.length() <= 11 ){
			pos = pos * 26.0;
			pos_virgula += (char)( (int)pos + 'A' );
			pos -= (int)pos;
		}
		
		String ans = (negativo ? "3" : "") + new StringBuilder(pre_virgula).reverse().toString() + pos_virgula;
		return ans;

	}
}	
