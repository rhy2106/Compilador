package ModelTest;
import Model.Token;
import Model.Numeros;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class NumerosTest{
	private CharacterIterator code;
	private Numeros afd;

	Token doToken(String s){
		this.code = new StringCharacterIterator(s);
		return afd.evaluate(code,0,0);
	}

	@BeforeEach
	void setup(){
		afd = new Numeros();
	}

	@Test
	void inteiro(){
		Token t = doToken("afskd");
		assertEquals("VALOR_INT",t.tipo);
		assertEquals("afskd",t.lexema);
	}

	@Test
	void flutuante(){
		Token t = doToken("aASD");
		assertEquals("VALOR_DOUBLE",t.tipo);
		assertEquals("aASD",t.lexema);
	}

	@Test
	void flutuante_err(){
		Token t = doToken("ASD");
		assertEquals(null, t);
	}
}
