package Model;
import java.text.CharacterIterator;

public class Operadores extends AFD{
	@Override
	public Token evaluate(CharacterIterator code,int linha, int coluna){
		Token t = null;

		int pos = code.getIndex();
		if(code.current() == '1'){
			code.next();
			coluna++;
			t = new Token("OP_ATR","1",linha,coluna);
			if(code.current() == '1') t = new Token("OP_REL_MAIOR","11",linha,coluna);
			else if(code.current() == '2') t = new Token("OP_REL_MENOR","12",linha,coluna);
			else if(code.current() == '3') t = new Token("OP_REL_DIFF","13",linha,coluna);
			else if(code.current() == '4') t = new Token("OP_LOG_AND","14",linha,coluna);
			else if(code.current() == '5') t = new Token("OP_LOG_OR","15",linha,coluna);
			else if(code.current() == '8') t = new Token("AP","18",linha,coluna);
			else if(code.current() == '9') t = new Token("FP","19",linha,coluna);
			else{
				code.setIndex(pos);
				coluna--;
			}
		} else if(code.current() == '2'){
			code.next();
			coluna++;
			t = new Token("OP_ARI_SOMA","2",linha,coluna);
			if(code.current() == '1') t = new Token("ACO","21",linha,coluna);
			else if(code.current() == '2') t = new Token("FCO","22",linha,coluna);
			else if(code.current() == '3') t = new Token("ACHA","23",linha,coluna);
			else if(code.current() == '4') t = new Token("FCHA","24",linha,coluna);
			else if(code.current() == '5') t = new Token("ACOM","25",linha,coluna);
			else if(code.current() == '6') t = new Token("FCOM","26",linha,coluna);
			else if(code.current() == '7') t = new Token("VIRGULA","27",linha,coluna);
			else if(code.current() == '8') t = new Token("FIM_LINHA","28",linha,coluna);
			else{
				code.setIndex(pos);
				coluna--;
			}
		}
		else if(code.current() == '3') t = new Token("OP_ARI_SUBTRACAO","3",linha,coluna);
		else if(code.current() == '4') t = new Token("OP_ARI_MULTIPLICACAO","4",linha,coluna);
		else if(code.current() == '5') t = new Token("OP_ARI_DIVISAO","5",linha,coluna);
		else if(code.current() == '6') t = new Token("OP_ARI_MODULO","6",linha,coluna);
		else if(code.current() == '7') t = new Token("OP_REL_IGUAL","7",linha,coluna);
		else if(code.current() == '8') t = new Token("OP_REL_MENOR_IGUAL","8",linha,coluna);
		else if(code.current() == '9') t = new Token("OP_REL_MAIOR_IGUAL","9",linha,coluna);
		else if(code.current() == CharacterIterator.DONE) t = new Token("EOF", "29",linha,coluna);
		code.next();
		coluna++;
		return t;
	}
}
