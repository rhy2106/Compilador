package Model;
import java.text.CharacterIterator;

public class Numeros extends AFD{
	@Override
	public Token evaluate(CharacterIterator code,int linha, int coluna){
		Token t = null;
		String tipo = "VALOR_INT";
		String s = "";
		while(code.current() >= 'a' && code.current() <= 'z'){
			s += code.current();
			code.next();
			coluna++;
		}
		if(s.length() > 0){
			while(code.current() >= 'A' && code.current() <= 'Z'){
				s += code.current();
				code.next();
				coluna++;
				tipo = "VALOR_DOUBLE";
			}
			t = new Token(tipo, s, linha, coluna);
		} else if(code.current() == CharacterIterator.DONE)
			return t = new Token("EOF", "29",linha,coluna);
		return t;
	}
}
