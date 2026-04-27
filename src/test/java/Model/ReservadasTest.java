package ModelTest;
import Model.Token;
import Model.Reservadas;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ReservadasTest{
	private CharacterIterator code;
	private Reservadas afd;

	Token doToken(String s){
		this.code = new StringCharacterIterator(s);
		return afd.evaluate(code,0,0);
	}

	@BeforeEach
	void setup(){
		afd = new Reservadas();
	}

	@Test
	void reservada_main(){
		Token t = doToken(":)");
		assertEquals("MAIN",t.tipo);
		assertEquals(":)",t.lexema);
	}

	@Test
	void reservada_if(){
		Token t = doToken(" ");
		assertEquals("IF",t.tipo);
		assertEquals(" ",t.lexema);
	}

	@Test
	void reservada_elseif(){
		Token t = doToken("? ");
		assertEquals("ELSEIF",t.tipo);
		assertEquals("? ",t.lexema);
	}

	@Test
	void reservada_else(){
		Token t = doToken("?");
		assertEquals("ELSE",t.tipo);
		assertEquals("?",t.lexema);
	}  

	@Test
	void reservada_while(){
		Token t = doToken("@");
		assertEquals("WHILE",t.tipo);
		assertEquals("@",t.lexema);
	}  

	@Test
	void reservada_for(){
		Token t = doToken("$");
		assertEquals("FOR",t.tipo);
		assertEquals("$",t.lexema);
	}  

	@Test
	void reservada_return(){
		Token t = doToken("<=");
		assertEquals("RETURN",t.tipo);
		assertEquals("<=",t.lexema);
	}  

	@Test
	void reservada_break(){
		Token t = doToken(":(");
		assertEquals("BREAK",t.tipo);
		assertEquals(":(",t.lexema);
	}  

	@Test
	void reservada_continue(){
		Token t = doToken("...");
		assertEquals("CONTINUE",t.tipo);
		assertEquals("...",t.lexema);
	}  

	@Test
	void reservada_print(){
		Token t = doToken("[]");
		assertEquals("PRINT",t.tipo);
		assertEquals("[]",t.lexema);
	}  

	@Test
	void reservada_input(){
		Token t = doToken("#");
		assertEquals("INPUT",t.tipo);
		assertEquals("#",t.lexema);
	}  

	@Test
	void reservada_void(){
		Token t = doToken("\'-\'");
		assertEquals("TIPO_VOID",t.tipo);
		assertEquals("\'-\'",t.lexema);
	}  

	@Test
	void reservada_int(){
		Token t = doToken(":-:");
		assertEquals("TIPO_INT",t.tipo);
		assertEquals(":-:",t.lexema);
	}  

	@Test
	void reservada_double(){
		Token t = doToken(";-;");
		assertEquals("TIPO_DOUBLE",t.tipo);
		assertEquals(";-;",t.lexema);
	}  

	@Test
	void reservada_string(){
		Token t = doToken("(/\'-\')/");
		assertEquals("TIPO_STRING",t.tipo);
		assertEquals("(/\'-\')/",t.lexema);
	}  

	@Test
	void reservada_char(){
		Token t = doToken("{/\"}/");
		assertEquals("TIPO_CHAR",t.tipo);
		assertEquals("{/\"}/",t.lexema);
	}  

	@Test
	void reservada_bool(){
		Token t = doToken("^-^");
		assertEquals("TIPO_BOOL",t.tipo);
		assertEquals("^-^",t.lexema);
	}  

	@Test
	void reservada_true(){
		Token t = doToken("-_-");
		assertEquals("BOOL_TRUE",t.tipo);
		assertEquals("-_-",t.lexema);
	}  

	@Test
	void reservada_false(){
		Token t = doToken("*_*");
		assertEquals("BOOL_FALSE",t.tipo);
		assertEquals("*_*",t.lexema);
	}
}
