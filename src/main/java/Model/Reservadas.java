package Model;
import java.text.CharacterIterator;

public class Reservadas extends AFD{
	@Override
	public Token evaluate(CharacterIterator code,int linha, int coluna){
		Token t = null;
		String s = "";
		char[] caracteres = {'!','@','#','$','%', '&','(',')','*','-', '+','/','?',';',':', '<','>','\\','|','/', '{','}','\'','\"',' ', '=','_','[',']','.','^' };
		boolean yes = true;
		while(yes){
			yes = false;
			for(int i = 0; i < caracteres.length; i++){
				if(code.current() == caracteres[i]) yes = true;
			}
			if(!yes) break;
			s += code.current();
			code.next();
			coluna++;
		}
		if(s.equals(":)")) 				t = new Token("MAIN"		,":)"			,linha,coluna);
		else if(s.equals(" ")) 			t = new Token("IF"			," "			,linha,coluna);
		else if(s.equals("? ")) 		t = new Token("ELSEIF"		,"? "			,linha,coluna);
		else if(s.equals("?")) 			t = new Token("ELSE"		,"?"			,linha,coluna);
		else if(s.equals("@")) 			t = new Token("WHILE"		,"@"			,linha,coluna);
		else if(s.equals("$")) 			t = new Token("FOR"			,"$"			,linha,coluna);
		else if(s.equals("<=")) 		t = new Token("RETURN"		,"<="			,linha,coluna);
		else if(s.equals(":(")) 		t = new Token("BREAK"		,":("			,linha,coluna);
		else if(s.equals("...")) 		t = new Token("CONTINUE"	,"..."			,linha,coluna);
		else if(s.equals("[]")) 		t = new Token("PRINT"		,"[]"			,linha,coluna);
		else if(s.equals("#")) 			t = new Token("INPUT"		,"#"			,linha,coluna);
		else if(s.equals("\'-\'")) 		t = new Token("TIPO_VOID"	,"\'-\'"		,linha,coluna);
		else if(s.equals(":-:")) 		t = new Token("TIPO_INT"	,":-:"			,linha,coluna);
		else if(s.equals(";-;")) 		t = new Token("TIPO_DOUBLE"	,";-;"			,linha,coluna);
		else if(s.equals("(/\'-\')/")) 	t = new Token("TIPO_STRING"	,"(/\'-\')/"	,linha,coluna);
		else if(s.equals("{/\"}/")) 	t = new Token("TIPO_CHAR"	,"{/\"}/"		,linha,coluna);
		else if(s.equals("^-^")) 		t = new Token("TIPO_BOOL"	,"^-^"			,linha,coluna);
		else if(s.equals("-_-")) 		t = new Token("BOOL_TRUE"	,"-_-"			,linha,coluna);
		else if(s.equals("*_*")) 		t = new Token("BOOL_FALSE"	,"*_*"			,linha,coluna);
		else if(code.current() == CharacterIterator.DONE) t = new Token("EOF", "29",linha,coluna);
		return t;
	}
}
