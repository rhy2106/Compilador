package ModelTest;
import Model.Token;
import Model.Caracteres;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CaracteresTest{
	private CharacterIterator code;
	private Caracteres afd;

	Token doToken(String s){
		this.code = new StringCharacterIterator(s);
		return afd.evaluate(code,0,0);
	}

	@BeforeEach
	void setup(){
		afd = new Caracteres();
	}

	@Test
	void identificarLetrasMinusculas(){
		Token t = doToken("17a17");
		assertEquals("VALOR_CHAR",t.tipo);
		assertEquals("17a17",t.lexema);
	}

	@Test
	void identificarLetrasMaiusculas(){
		Token t = doToken("17B17");
		assertEquals("VALOR_CHAR",t.tipo);
		assertEquals("17B17",t.lexema);
	}

	@Test
	void identificarNumeros(){
		Token t = doToken("17117");
		assertEquals("VALOR_CHAR",t.tipo);
		assertEquals("17117",t.lexema);
	}

	@Test
	void identificarSpeciais(){
		Token t = doToken("17!17");
		assertEquals("VALOR_CHAR",t.tipo);
		assertEquals("17!17",t.lexema);
	}

	@Test
	void erroSemCaracter(){
		Token t = doToken("1717");
		assertEquals("CHAR_ERROR",t.tipo);
		assertEquals("171",t.lexema);
	}

	@Test
	void erroMaisDeUmCaracter(){
		Token t = doToken("17a117");
		assertEquals("CHAR_ERROR",t.tipo);
		assertEquals("17a1",t.lexema);
	}
}
