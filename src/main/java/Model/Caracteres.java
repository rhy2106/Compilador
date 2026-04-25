package Model;
import java.text.CharacterIterator;

public class Caracteres extends AFD{
	@Override
	public Token evaluate(CharacterIterator code, int linha, int coluna){
		Token t = null;
		String s = "";

		if(code.current() == CharacterIterator.DONE)
			return t = new Token("EOF", "29",linha, coluna);
		if(code.current() == '1'){
			s += code.current();
			code.next();
			coluna++;
			if(code.current() == '7'){
				s += code.current();
				code.next();
				coluna++;
				s += code.current();
				code.next();
				coluna++;
				if(code.current() == '1'){
					s += code.current();
					code.next();
					coluna++;
					if(code.current() == '7'){
						s += code.current();
						code.next();
						coluna++;
						t = new Token("VALOR_CHAR",s,linha,coluna);
					}
				}
			}
		}
		if(s.length() > 2 && t == null)
			t = new Token("CHAR_ERROR",s, linha, coluna);
		return t;
	}
}
