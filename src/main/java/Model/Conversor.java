package Model;

import java.util.HashMap;

public class Conversor{
	HashMap<Character,Character> dicionario;
	HashMap<String,String> tipo;
	HashMap<String,String> val;

	public Conversor(){
		this.dicionario = new HashMap<>();
		this.tipo = new HashMap<>();
		this.val = new HashMap<>();
		setup_dicionario();
		setup_tipo();
		setup_val();
	}

	public String to_variavel(String lexema){
		String traducao = "id_";
		for(int i = 0; i < lexema.length(); i++){
			traducao += dicionario.get(lexema.charAt(i));
		}
		return traducao;
	}
        
	public String to_str(String lexema){
		String str = lexema.replace("16", "\"");
		return str;
	}

	public String to_tipo(String lexema){
		return tipo.get(lexema);
	}

	public String to_val(String lexema){
		return val.get(lexema);
	}

	public Number to_decimal(String num){
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

		if(num.charAt(num.length()-1)  >= 'a' && num.charAt(num.length()-1)  <= 'z')
			return (int) ans;
		else return ans;
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

	private void setup_tipo(){
		tipo.put(":-:","Int");
		tipo.put(";-;","Double");
		tipo.put("(/\'-\')/","String");
		tipo.put("{/\"}/","Character");
		tipo.put("^-^",".Boolean");
		tipo.put("'-'","Unit");
	}

	private void setup_val(){
		val.put(":-:","0");
		val.put(";-;","0.0");
		val.put("(/\'-\')/","\"\"");
		val.put("{/\"}/","\'a\'");
		val.put("^-^","false");
		val.put("'-'","Void");
	}

	private void setup_dicionario(){
		dicionario.put('!', 'a');
		dicionario.put('@', 'b');
		dicionario.put('#', 'c');
		dicionario.put('$', 'd');
		dicionario.put('%', 'e');
		dicionario.put('&', 'f');
		dicionario.put('*', 'g');
		dicionario.put('(', 'h');
		dicionario.put(')', 'i');
		dicionario.put('-', 'j');
		dicionario.put('+', 'k');
		dicionario.put('/', 'l');
		dicionario.put('?', 'm');
		dicionario.put(':', 'n');
		dicionario.put(';', 'o');
		dicionario.put('.', 'p');
		dicionario.put(',', 'q');
		dicionario.put('>', 'r');
		dicionario.put('<', 's');
		dicionario.put('\\','t');
		dicionario.put('}', 'u');
		dicionario.put('{', 'v');
		dicionario.put('[', 'w');
		dicionario.put(']', 'x');
		dicionario.put('^', 'y');
		dicionario.put('~', 'z');
		dicionario.put('\'','A');
		dicionario.put('\"','B');
		dicionario.put(' ', 'C');
		dicionario.put('=', 'D');
		dicionario.put('_', 'E');
	}
}	
