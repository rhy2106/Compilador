package ModelTest;
import Model.Token;
import Model.Palavras;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PalavrasTest{
	private CharacterIterator code;
	private Palavras afd;

	Token doToken(String s){
		this.code = new StringCharacterIterator(s);
		return afd.evaluate(code,0,0);
	}

	@BeforeEach
	void setup(){
		afd = new Palavras();
	}

	@Test
	void identificarPalavrasMinusculas(){
		Token t = doToken("16abcdefg16");
		assertEquals("VALOR_STRING",t.tipo);
		assertEquals("16abcdefg16",t.lexema);
	}

	@Test
	void identificarPalavrasMaiusculas(){
		Token t = doToken("16ABCDEFG16");
		assertEquals("VALOR_STRING",t.tipo);
		assertEquals("16ABCDEFG16",t.lexema);
	}

	@Test
	void identificarNumeros(){
		Token t = doToken("16123456789016");
		assertEquals("VALOR_STRING",t.tipo);
		assertEquals("16123456789016",t.lexema);
	}

	@Test
	void identificarNumeros2(){
		Token t = doToken("1611616");
		assertEquals("VALOR_STRING",t.tipo);
		assertEquals("16116",t.lexema);
	}

	@Test
	void identificarSpeciais(){
		Token t = doToken("16 !#@16");
		assertEquals("VALOR_STRING",t.tipo);
		assertEquals("16 !#@16",t.lexema);
	}

	@Test
	void identificarVazio(){
		Token t = doToken("1616");
		assertEquals("VALOR_STRING",t.tipo);
		assertEquals("1616",t.lexema);
	}

	@Test
	void identificarQuebra(){
		Token t = doToken("16\n1\n2\n3\n4\n16");
		assertEquals("VALOR_STRING",t.tipo);
		assertEquals("16\n1\n2\n3\n4\n16",t.lexema);
	}

	@Test
	void identificarIncompleto(){
		Token t = doToken("162ads");
		assertEquals("STRING_ERROR",t.tipo);
		assertEquals("162ads",t.lexema);
	}
}
