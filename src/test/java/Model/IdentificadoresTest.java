package ModelTest;
import Model.Token;
import Model.Identificadores;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class IdentificadoresTest{
	private CharacterIterator code;
	private Identificadores afd;

	Token doToken(String s){
		this.code = new StringCharacterIterator(s);
		return afd.evaluate(code,0,0);
	}

	@BeforeEach
	void setup(){
		afd = new Identificadores();
	}

	@Test
	void identificador(){
		Token t = doToken("!$@$!@");
		assertEquals("VALOR_ID",t.tipo);
		assertEquals("!$@$!@",t.lexema);
	}
}
