package ModelTest;
import Model.Token;
import Model.Comentarios;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ComentariosTest{
	private CharacterIterator code;
	private Comentarios afd;

	Token doToken(String s){
		this.code = new StringCharacterIterator(s);
		return afd.evaluate(code,0,0);
	}

	@BeforeEach
	void setup(){
		afd = new Comentarios();
	}

	@Test
	void identificarPalavrasMinusculas(){
		Token t = doToken("25abcdefg26");
		assertEquals("COMENTARIO",t.tipo);
		assertEquals("25abcdefg26",t.lexema);
	}

	@Test
	void identificarPalavrasMaiusculas(){
		Token t = doToken("25ABCDEFG26");
		assertEquals("COMENTARIO",t.tipo);
		assertEquals("25ABCDEFG26",t.lexema);
	}

	@Test
	void identificarNumeros(){
		Token t = doToken("25123456789026");
		assertEquals("COMENTARIO",t.tipo);
		assertEquals("25123456789026",t.lexema);
	}

	@Test
	void identificarNumeros2(){
		Token t = doToken("2512626");
		assertEquals("COMENTARIO",t.tipo);
		assertEquals("25126",t.lexema);
	}

	@Test
	void identificarSpeciais(){
		Token t = doToken("25 !#@26");
		assertEquals("COMENTARIO",t.tipo);
		assertEquals("25 !#@26",t.lexema);
	}

	@Test
	void identificarVazio(){
		Token t = doToken("2526");
		assertEquals("COMENTARIO",t.tipo);
		assertEquals("2526",t.lexema);
	}

	@Test
	void identificarQuebra(){
		Token t = doToken("25\n1\n2\n3\n4\n26");
		assertEquals("COMENTARIO",t.tipo);
		assertEquals("25\n1\n2\n3\n4\n26",t.lexema);
	}

	@Test
	void identificarIncompleto(){
		Token t = doToken("252ads");
		assertEquals("COMENTARIO_ERROR",t.tipo);
		assertEquals("252ads",t.lexema);
	}
}
