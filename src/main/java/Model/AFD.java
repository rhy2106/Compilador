package Model;
import java.text.CharacterIterator;

public abstract class AFD{
	public abstract Token evaluate(CharacterIterator code, int linha, int coluna);
	public boolean isSeparator(CharacterIterator code){
		if( code.current() == '0' ||
			code.current() == '\n') return true;
		return false;
	}
}
