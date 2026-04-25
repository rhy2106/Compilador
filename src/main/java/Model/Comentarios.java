package Model;
import java.text.CharacterIterator;

public class Comentarios extends AFD{
	@Override
	public Token evaluate(CharacterIterator code,int linha, int coluna){
		Token t = null;
		String s = "";
		if(code.current() == '2'){
			s += code.current();
			code.next();
			coluna++;
			if(code.current() == '5'){
				s += code.current();
				code.next();
				coluna++;
				boolean erro = false;
				while(true){
					if(code.current() == CharacterIterator.DONE){
						erro = true;
						break;
					}
					if(code.current() == '\n'){
						linha++;
						coluna = 0;
					} else coluna++;
					if(code.current() == '6'
					  && s.charAt(s.length()-1) == '2'){
						s += code.current();
						code.next();
						break;
					}
					s += code.current();
					code.next();
				}
				if(erro) t = new Token("COMENTARIO_ERROR",s,linha,coluna);
				else t = new Token("COMENTARIO",s,linha,coluna);
			}
		} else if(code.current() == CharacterIterator.DONE) t = new Token("EOF", "29",linha,coluna);
		return t;
	}
}
