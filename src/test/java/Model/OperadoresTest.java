package ModelTest;
import Model.Token;
import Model.Operadores;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class OperadoresTest{
	private CharacterIterator code;
	private Operadores afd;

	Token doToken(String s){
		this.code = new StringCharacterIterator(s);
		return afd.evaluate(code,0,0);
	}

	@BeforeEach
	void setup(){
		afd = new Operadores();
	}

	@Test
	void op_atr(){
		Token t = doToken("1");
		assertEquals("OP_ATR",t.tipo);
		assertEquals("1",t.lexema);
	}
	@Test
	void op_ari_soma(){
		Token t = doToken("2");
		assertEquals("OP_ARI_SOMA",t.tipo);
		assertEquals("2",t.lexema);
	}
	@Test
	void op_ari_sub(){
		Token t = doToken("3");
		assertEquals("OP_ARI_SUBTRACAO",t.tipo);
		assertEquals("3",t.lexema);
	}
	@Test
	void op_ari_mul(){
		Token t = doToken("4");
		assertEquals("OP_ARI_MULTIPLICACAO",t.tipo);
		assertEquals("4",t.lexema);
	}
	@Test
	void op_ari_div(){
		Token t = doToken("5");
		assertEquals("OP_ARI_DIVISAO",t.tipo);
		assertEquals("5",t.lexema);
	}
	@Test
	void op_ari_mod(){
		Token t = doToken("6");
		assertEquals("OP_ARI_MODULO",t.tipo);
		assertEquals("6",t.lexema);
	}
	@Test
	void op_rel_igual(){
		Token t = doToken("7");
		assertEquals("OP_REL_IGUAL",t.tipo);
		assertEquals("7",t.lexema);
	}
	@Test
	void op_rel_menor_igual(){
		Token t = doToken("8");
		assertEquals("OP_REL_MENOR_IGUAL",t.tipo);
		assertEquals("8",t.lexema);
	}
	@Test
	void op_rel_maior_igual(){
		Token t = doToken("9");
		assertEquals("OP_REL_MAIOR_IGUAL",t.tipo);
		assertEquals("9",t.lexema);
	}
	@Test
	void op_rel_maior(){
		Token t = doToken("11");
		assertEquals("OP_REL_MAIOR",t.tipo);
		assertEquals("11",t.lexema);
	}
	@Test
	void op_rel_menor(){
		Token t = doToken("12");
		assertEquals("OP_REL_MENOR",t.tipo);
		assertEquals("12",t.lexema);
	}
	@Test
	void op_rel_diff(){
		Token t = doToken("13");
		assertEquals("OP_REL_DIFF",t.tipo);
		assertEquals("13",t.lexema);
	}
	@Test
	void op_log_and(){
		Token t = doToken("14");
		assertEquals("OP_LOG_AND",t.tipo);
		assertEquals("14",t.lexema);
	}
	@Test
	void op_log_or(){
		Token t = doToken("15");
		assertEquals("OP_LOG_OR",t.tipo);
		assertEquals("15",t.lexema);
	}
	@Test
	void ap(){
		Token t = doToken("18");
		assertEquals("AP",t.tipo);
		assertEquals("18",t.lexema);
	}
	@Test
	void fp(){
		Token t = doToken("19");
		assertEquals("FP",t.tipo);
		assertEquals("19",t.lexema);
	}
	@Test
	void aco(){
		Token t = doToken("21");
		assertEquals("ACO",t.tipo);
		assertEquals("21",t.lexema);
	}
	@Test
	void fco(){
		Token t = doToken("22");
		assertEquals("FCO",t.tipo);
		assertEquals("22",t.lexema);
	}
	@Test
	void acha(){
		Token t = doToken("23");
		assertEquals("ACHA",t.tipo);
		assertEquals("23",t.lexema);
	}
	@Test
	void fcha(){
		Token t = doToken("24");
		assertEquals("FCHA",t.tipo);
		assertEquals("24",t.lexema);
	}
	@Test
	void acom(){
		Token t = doToken("25");
		assertEquals("ACOM",t.tipo);
		assertEquals("25",t.lexema);
	}
	@Test
	void fcom(){
		Token t = doToken("26");
		assertEquals("FCOM",t.tipo);
		assertEquals("26",t.lexema);
	}
	@Test
	void virgula(){
		Token t = doToken("27");
		assertEquals("VIRGULA",t.tipo);
		assertEquals("27",t.lexema);
	}
	@Test
	void fim_linha(){
		Token t = doToken("28");
		assertEquals("FIM_LINHA",t.tipo);
		assertEquals("28",t.lexema);
	}
}
