package Model;
import java.text.CharacterIterator;

public class Identificadores extends AFD{
	@Override
	public Token evaluate(CharacterIterator code,int linha,int coluna){
		Token t = null;
		String s = "";
		char[] caracteres = {'!','@','#','$','%', '&','(',')','*','-', '+','/','?',';',':', '<','>','\\','|','/', '{','}','\'','\"',' ', '=','_','[',']','.','^'};
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
		if(!s.equals("")) t = new Token("VALOR_ID",s,linha,coluna);
		else if(code.current() == CharacterIterator.DONE) t = new Token("EOF", "29",linha,coluna);
		return t;
	}
}
