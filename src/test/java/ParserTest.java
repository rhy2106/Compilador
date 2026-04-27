package CompiladorTest;
import Model.Token;
import Model.Lexer;
import Model.Parser2;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest{
	private Lexer lexer;
	private Parser2 parser;

	private boolean analise(String body){
		boolean ans;
		try{
			Lexer lexer = new Lexer(body);
			List<Token> tokens = lexer.getTokens();
			Parser2 parser = new Parser2(tokens);
			parser.parse();
			ans = true;
		} catch(Exception e){
			ans = false;
		}
		return ans;
	}

	@Test
	public void test_hello_world(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000[]016Hello World1628\n";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_declarar_variavel(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000:-:0!!010a028\n";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}
	
	@Test
	public void test_input(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000:-:0!!28\n";
		body += "0000#0!!28";
		body += "[]0!!28";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_expressao(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000:-:0!!010a028\n";
		body += "0000!!10!!020b030c28";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_expressao_calculo(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000:-:0!!010a028\n";
		body += "0000!!1018!!2018b3c194k2p193c28";
		body += "25  x = (x+(1-2)*10+14)-2; 26";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_if(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000 180!!070a01923\n";
		body += "00000000[]016!! e igual a 01628";
		body += "000024\n";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_if_condicao(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "#0!28";
		body += "#0!!28";
		body += "#0!!!28";
		body += "0000 1818!2!!11!!!191418!!2!!!11!191418!2!!!11!!191418!4!2!!4!!7!!!4!!!15!4!2!!!4!!!7!!4!!15!!4!!2!!!4!!!7!4!191923\n";
		body += "25 (a+b > c) && (b+c > a) && (a+c > b) && (a*a+b*b == c*c || a*a+c*c == b*b || b*b+c*c == a*a) 26";
		body += "00000000[]16os lados !, !!, !!! formam um triangulo retangulo1628";
		body += "000024\n";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_else(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000 180!!070a01923\n";
		body += "00000000[]016!! e igual a 01628";
		body += "000024\n";
		body += "0000?23\n";
		body += "00000000[]016!! e diferente de 01628";
		body += "000024";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}
	
	@Test
	public void test_ifelse(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000 180!!070a01923\n";
		body += "00000000[]016!! e igual a 01628";
		body += "000024\n";
		body += "0000? 180!!0110a01923\n";
		body += "00000000[]016!! e maior que 01628";
		body += "000024";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_ifelse_else(){
		String body = "";
		body += ":-:0:)181923\n";
		body += "0000 180!!070a01923\n";
		body += "00000000[]016!! e igual a 01628";
		body += "000024\n";
		body += "0000? 180!!0110a01923\n";
		body += "00000000[]016!! e maior que 01628";
		body += "000024";
		body += "0000?23";
		body += "00000000[]016!! e menor que 01628";
		body += "000024";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_if_dentro_if(){
		String body = "";
		body += ":-:0:)181923";
		body += "0000 18!!11a1923";
		body += "00000000 18!!7a1923";
		body += "000000000000[]16!! e igual a 01628";
		body += "0000000024";
		body += "00000000?23";
		body += "000000000000[]16!! e maior que 01628";
		body += "0000000024";
		body += "000024";
		body += "0000?23";
		body += "00000000[]016!! e menor que 01628";
		body += "000024";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_while(){
		String body = "";
		body += ":-:0:)181923";
		body += "0000:-:0!!1a28";
		body += "0000@18!!8k1923";
		body += "00000000!!010!!2b28";
		body += "000024";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_while_dentro_while(){
		String body = "";
		body += ":-:0:)181923";
		body += "0000:-:0!!1a28";
		body += "0000@18!!8k1923";
		body += "00000000:-:0$$010$$2a28";
		body += "00000000@18$$12k1923";
		body += "000000000000[]0$$28";
		body += "0000000024";
		body += "00000000!!010!!2b28";
		body += "000024";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_for(){
		String body = "";
		body += ":-:0:)181923";
		body += "0000$18:-:0!!1a28!!12t28!!1!!2b1923";
		body += "00000000[]0!!28";
		body += "000024";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_for_dentro_for(){
		String body = "";
		body += ":-:0:)181923";
		body += "0000$18:-:0!!1a28!!12t28!!1!!2b1923";
		body += "00000000$18:-:0!1a28!12t28!1!2b1923";
		body += "00000000[]0!28";
		body += "0000000024";
		body += "000024";
		body += "0000<=a28\n";
		body += "24\n";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void test_declarar_funcao(){
		String body = "";
		body +="'-'0!@18:-:0!<27:-:0!>1923";
		body +="0000$18:-:0!!010!<28!!12!>28!!1!!2b1923";
		body +="00000000[]0!!28";
		body +="000024";
		body +="24";
		body +=":-:0:)181923";
		body +="0000:-:0!28";
		body +="0000!@18!27!!1928";
		body +="0000<=a28";
		body +="24";
		boolean ans = analise(body);
		assertEquals(true,ans);
	}

	@Test
	public void sem_main(){
		String body = "";
		body +="'-'0!@18:-:0!<27:-:0!>1923";
		body +="0000$18:-:0!!010!<28!!12!>28!!1!!2b1923";
		body +="00000000[]0!!28";
		body +="000024";
		body +="24";
		boolean ans = analise(body);
		assertEquals(false,ans);
	}
}
