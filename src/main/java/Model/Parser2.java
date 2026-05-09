package Model;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;

public class Parser2{
	List<Token> tokens;
	HashMap<String,String> mapa = new HashMap<>();
	Stack<String> forIncrease = new Stack<>();
	Token token;
	Arvore raiz;
	Conversor c = new Conversor();
	PrintStream original = System.out;
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PrintStream saida = new PrintStream(baos);

	public Parser2(List<Token> tokens){
		this.tokens = tokens;
		raiz = newTree("PROG");
		token = getNextToken();
	}

	public Token getNextToken(){
		if(tokens.size() > 0)
			return tokens.remove(0);
		return null;
	}

	private void erro(String regra){
		throw new RuntimeException(
			"\n=====================================================" +
			"\nRegra: " + regra  +
			"\nToken invalido: " + (token == null ? "null" : token.lexema) +
			"\nlinha: " + token.linha + " coluna: " + token.coluna +
			"\n====================================================="
			);
	}
        
	public Arvore arvore(){

		boolean fim = false;
		while(true){
			if( token == null
			|| token.tipo.equals("ACHA")
			|| token.tipo.equals("FCHA")
			|| token.tipo.equals("FIM_LINHA")) token = getNextToken();
			if(token.tipo.startsWith("TIPO")) fim = decFuncao(raiz);
			else if(token.tipo.equals("EOF")) break;
			else erro("esperado tipo");
			if(fim) break;
		}
		if(!token.tipo.equals("EOF")) erro("token apos o fim do programa");
		else token_print(token,raiz);


		return raiz;
	}
        
	public String codigo(){
		return baos.toString();
	}
	void token_print( Token t, Arvore pai){
		pai.add(new Arvore(t));
	}

	Arvore token_print( String s){
		return new Arvore(s);
	}

	private void tradutor(String codigo){
		System.out.print(codigo);
	}
        
	private void tradutor(Number codigo){
		System.out.print(codigo);
	}

	void addToken( Token t, Arvore pai){
		pai.add(new Arvore(t));
		token = getNextToken();
	}
        
	void addToken( Token t, Arvore pai, String newcode){
		tradutor(newcode);
		pai.add(new Arvore(t));
		token = getNextToken();
	}
        
	void addToken( Token t, Arvore pai, Number newcode){
		tradutor(newcode);
		pai.add(new Arvore(t));
		token = getNextToken();
	}

	Arvore newTree(String s){
		return new Arvore(s);
	}

	public Arvore getTree(){
		return raiz;
	}

	public boolean parse(){
		System.setOut(saida);
		if(!decFuncao(raiz)) return false;
		if(!token.tipo.equals("EOF")){
			erro("Token após o fim do programa");
			return false;
		} else addToken(token,raiz);
		System.setOut(original);
		return true;
	}

	private boolean decFuncao(Arvore pai){
		Arvore node = newTree("declarar funcao");
		pai.add(node);
		ByteArrayOutputStream exp = new ByteArrayOutputStream();
		PrintStream local_saida = new PrintStream(exp);
		System.setOut(local_saida);
		if(!tipo(node)) return false;
		System.setOut(saida);
		if(!sufixDecFuncao(node, exp.toString())) return false;
		return true;
	}

	private boolean tipo(Arvore pai){
		Arvore node = newTree("tipo");
		pai.add(node);
		if(token.tipo.startsWith("TIPO")){
			addToken(token,node,c.to_tipo(token.lexema));
			return true;
		} else{
			erro("(tipo) Esperado Tipo");
			return false;
		}
	}

	private boolean sufixDecFuncao(Arvore pai, String exp){
		Arvore node = newTree("sufix declarar funcao");
		pai.add(node);
		boolean is_main = false;
		if(token.tipo.equals("VALOR_ID")){ 
                    addToken(token,node, "fun " + c.to_variavel(token.lexema));
                    }else if(token.tipo.equals("MAIN")){
			addToken(token,node, "fun main");
			is_main = true;
		} else{
			erro("(sufix declarar funcao) Esperado Nome da Funcao");
			return false;
		}

		if(token.tipo.equals("AP")) addToken(token,node, "(");
		else{
			erro("(sufix declarar funcao) Esperado abrir parenteses (18)");
			return false;
		}

		if(!token.tipo.equals("FP")){
			if(!decParam(node)) return false;
		}

		if(token.tipo.equals("FP")) addToken(token,node, "): " + exp);
		else{
			erro("(sufix declarar funcao) Esperado fechar parenteses (19)");
			return false;
		}

		if(token.tipo.equals("ACHA")) addToken(token,node, "{\n");
		else{
			erro("(sufix declarar funcao) Esperado abrir Chaves");
			return false;
		}

		if(!bloco(node)) return false;

		if(token.tipo.equals("FCHA")) addToken(token,node, "}\n");
		else{
			erro("(sufix declarar funcao) Esperado fechar Chaves");
			return false;
		}
		
		if(!is_main){
			if(!decFuncao(node)) return false;
		} else if(!token.tipo.equals("EOF")){
			erro("(sufix declarar funcao) Codigo apos o fim do programa");
			return false;
		}
		return true;
	}

	private boolean decParam(Arvore pai){
		Arvore node = newTree("declarar parametros");
		pai.add(node);
		
		ByteArrayOutputStream exp = new ByteArrayOutputStream();
		PrintStream local_saida = new PrintStream(exp);
		System.setOut(local_saida);
		if(!tipo(node)) return false;
		System.setOut(saida);
		if(token.tipo.equals("VALOR_ID")) addToken(token,node, c.to_variavel(token.lexema));
		else{
			erro("(declarar param) Esperado nome do Parametro");
			return false;
		}
		tradutor(": " + exp.toString());

		if(!sufixDecParam(node)) return false;
		return true;
	}
	private boolean sufixDecParam(Arvore pai){
		Arvore node = newTree("sufix declarar parametros");
		pai.add(node);
		if(token.tipo.equals("VIRGULA")){
			addToken(token,node, ", ");
			if(!decParam(node)) return false;
		}
		return true;
	}

	private boolean bloco(Arvore pai){
		Arvore node = newTree("bloco");
		pai.add(node);
		while(token != null && !token.tipo.equals("FCHA")){
			if(token.tipo.equals("PRINT")){
				if(!cmdPrint(node)) return false;
			} else if(token.tipo.equals("INPUT")){
				if(!cmdInput(node)) return false;
			} else if(token.tipo.equals("IF")){
				if(!cmdIf(node)) return false;
			} else if(token.tipo.equals("WHILE")){
				if(!cmdWhile(node)) return false;
			} else if(token.tipo.equals("FOR")){
				if(!cmdFor(node)) return false;
			} else if(token.tipo.equals("VALOR_ID")){
				if(!cmdExpressao(node)) return false;
			} else if(token.tipo.startsWith("TIPO")){
				if(!cmdDeclaracao(node)) return false;
			} else if(token.tipo.equals("BREAK")){
				if(!cmdBreak(node)) return false;
			} else if(token.tipo.equals("CONTINUE")){
				if(!cmdContinue(node)) return false;
			} else if(token.tipo.equals("RETURN")){
				if(!cmdReturn(node)) return false;
			} else if(token.tipo.equals("EOF")) {
				erro("(bloco) Unexpected EOF");
				return false;
			} else{
				erro("(bloco) Unexpected Token");
			}
		}
		return true;
	}

	private boolean cmdPrint(Arvore pai){
		Arvore node = newTree("cmd print");
		pai.add(node);
		addToken(token,node, "print(");
		if(!valor(node)) return false;
		if(token.tipo.equals("FIM_LINHA")) addToken(token,node, ");\n");
		else{
			erro("(cmd print) Esperado fim de linha");
			return false;
		}
		return true;
	}

	private boolean cmdInput(Arvore pai){
		Arvore node = newTree("cmd input");
		pai.add(node);
		addToken(token,node);
		String mapa_id = token.lexema;
		if(token.tipo.equals("VALOR_ID")){
			String sufixo = "";
			if(mapa.get(mapa_id) == "Void") erro("(cmd input) Tentando dar valor para uma variavel void");
			else if(mapa.get(mapa_id) == "Character") sufixo = ".first";
			else sufixo = ".to" + mapa.get(mapa_id);
			addToken(token,node, c.to_variavel(token.lexema) + " = readln()" + sufixo + "()");
		}
		else{
			erro("(cmd input) Esperado variavel");
			return false;
		}
		if(token.tipo.equals("FIM_LINHA")) addToken(token,node, ";\n");
		else{
			erro("(cmd input) Esperado fim de linha");
			return false;
		}
		return true;
	}

	private boolean cmdReturn(Arvore pai){
		Arvore node = newTree("cmd return");
		pai.add(node);
		addToken(token,node, "return ");
                
		if(!token.tipo.equals("FIM_LINHA")){
			if(!valor(node)) return false;
		}
		if(token.tipo.equals("FIM_LINHA")) addToken(token,node, ";\n");
		else{
			erro("(cmd return) Esperado fim de linha");
			return false;
		}
		return true;
	}

	private boolean cmdBreak(Arvore pai){
		Arvore node = newTree("cmd break");
		pai.add(node);
		addToken(token,node,"break");
		if(token.tipo.equals("FIM_LINHA")) addToken(token,node,";\n");
		else{
			erro("(cmd break) Esperado fim de linha");
			return false;
		}
		return true;
	}

	private boolean cmdContinue(Arvore pai){
		Arvore node = newTree("cmd continue");
		pai.add(node);
		tradutor(forIncrease.peek());
		addToken(token,node,"continue");
		if(token.tipo.equals("FIM_LINHA")) addToken(token,node,";\n");
		else{
			erro("(cmd continue) Esperado fim de linha");
			return false;
		}
		return true;
	}

	private boolean cmdExpressao(Arvore pai){
		Arvore node = newTree("cmd expressao");
		pai.add(node);
		if(!expressao(node)) return false;
		if(token.tipo.equals("FIM_LINHA")) addToken(token,node, ";\n");
		else{
			erro("(cmd expressao) Esperado fim de linha");
			return false;
		}
		return true;
	}

	private boolean expressao(Arvore pai){
		Arvore node = newTree("expressao");
		pai.add(node);
		addToken(token,node, c.to_variavel(token.lexema));
		if(!sufixExpressao(node)) return false;
		return true;
	}

	private boolean sufixExpressao(Arvore pai){
		Arvore node = newTree("sufix expressao");
		pai.add(node);
		if(token.tipo.equals("OP_ATR")){
			addToken(token,node, " = ");
			if(!calculo(node)) return false;
		} else if(token.tipo.equals("AP")){
			addToken(token,node, "(");
			if(!token.tipo.equals("FP")){
				if(!param(node)) return false;
			}
			if(token.tipo.equals("FP")) addToken(token,node, ")");
			else erro("(sufix expressao) Esperado fechar Parenteses");
		} else{
			erro("(sufix expessao) Expressao Invalida");
			return false;
		}
		return true;
	}

	private boolean cmdDeclaracao(Arvore pai){
		Arvore node = newTree("cmd declaracao");
		pai.add(node);
		if(!declaracao(node)) return false;
		if(token.tipo.equals("FIM_LINHA")) addToken(token,node, ";\n");
		else{
			erro("(cmd declaracao) Esperado fim de linha");
			return false;
		}
		return true;
	}
	
	private boolean declaracao(Arvore pai){
		Arvore node = newTree("declaracao");
		pai.add(node);
		String mapa_tipo = token.lexema;

		ByteArrayOutputStream exp = new ByteArrayOutputStream();
		PrintStream local_saida = new PrintStream(exp);
		System.setOut(local_saida);
		if(!tipo(node)) return false;
		System.setOut(saida);

		String mapa_id = token.lexema;
		if(token.tipo.equals("VALOR_ID")) addToken(token,node, "var " + c.to_variavel(token.lexema) + ": " + exp.toString());
		else erro("(declaracao) Esperado nome da variavel");
		mapa.put(mapa_id, c.to_tipo(mapa_tipo));

		if(token.tipo.equals("OP_ATR")){
			addToken(token,node, " = ");
			if(!calculo(node)) return false;
		} else{
			tradutor(" = " + c.to_val(mapa_tipo));
		}
		return true;
	}

	private boolean cmdIf(Arvore pai){
		Arvore node = newTree("cmd if");
		pai.add(node);
		addToken(token,node, "if");

		if(token.tipo.equals("AP")) addToken(token,node, "(");
		else{
			erro("(cmd if) Esperado abrir parenteses (18)");
			return false;
		}

		if(!condicao(node)) return false;

		if(token.tipo.equals("FP")) addToken(token,node, ")");
		else{
			erro("(cmd if) Esperado fechar parenteses (19)");
			return false;
		}

		if(token.tipo.equals("ACHA")) addToken(token,node, "{\n");
		else{
			erro("(cmd if) Esperado abrir Chaves");
			return false;
		}

		if(!bloco(node)) return false;

		if(token.tipo.equals("FCHA")) addToken(token,node, "}\n");
		else{
			erro("(cmd if) Esperado fechar Chaves");
			return false;
		}
		
		if(!sufixIf(node)) return false;
		return true;
	}

	private boolean sufixIf(Arvore pai){
		Arvore node = newTree("sufix if");
		pai.add(node);
		if(token.tipo.equals("ELSEIF")){
			if(!cmdElseIf(node)) return false;
		} else if(token.tipo.equals("ELSE")){
			if(!cmdElse(node)) return false;
		} return true;
	}

	private boolean cmdElseIf(Arvore pai){
		Arvore node = newTree("elseif");
		pai.add(node);
		addToken(token,node, "else if");

		if(token.tipo.equals("AP")) addToken(token,node, "(");
		else{
			erro("(cmd else if) Esperado abrir parenteses (18)");
			return false;
		}

		if(!condicao(node)) return false;

		if(token.tipo.equals("FP")) addToken(token,node, ")");
		else{
			erro("(cmd else if) Esperado fechar parenteses (19)");
			return false;
		}

		if(token.tipo.equals("ACHA")) addToken(token,node, "{\n");
		else{
			erro("(cmd else if) Esperado abrir Chaves");
			return false;
		}

		if(!bloco(node)) return false;

		if(token.tipo.equals("FCHA")) addToken(token,node, "}\n");
		else{
			erro("(cmd else if) Esperado fechar Chaves");
			return false;
		}
		
		if(!sufixIf(node)) return false;
		return true;
	}

	private boolean cmdElse(Arvore pai){
		Arvore node = newTree("else");
		pai.add(node);
		addToken(token,node, "else");

		if(token.tipo.equals("ACHA")) addToken(token,node, "{\n");
		else{
			erro("(cmd else) Esperado abrir Chaves");
			return false;
		}

		if(!bloco(node)) return false;

		if(token.tipo.equals("FCHA")) addToken(token,node, "}\n");
		else{
			erro("(cmd else) Esperado fechar Chaves");
			return false;
		}
		return true;
	}

	private boolean cmdWhile(Arvore pai){
		Arvore node = newTree("while");
		pai.add(node);
		addToken(token,node, "while");

		forIncrease.push("");

		if(token.tipo.equals("AP")) addToken(token,node, "(");
		else{
			erro("(cmd while) Esperado abrir parenteses (18)");
			return false;
		}

		if(!condicao(node)) return false;

		if(token.tipo.equals("FP")) addToken(token,node, ")");
		else{
			erro("(cmd while) Esperado fechar parenteses (19)");
			return false;
		}

		if(token.tipo.equals("ACHA")) addToken(token,node, "{\n");
		else{
			erro("(cmd while) Esperado abrir Chaves");
			return false;
		}

		if(!bloco(node)) return false;

		if(token.tipo.equals("FCHA")) addToken(token,node, "}\n");
		else{
			erro("(cmd while) Esperado fechar Chaves");
			return false;
		}

		forIncrease.pop();

		return true;
	}

	private boolean cmdFor(Arvore pai){
		Arvore node = newTree("for");
		pai.add(node);
		// addToken(token,node, "for");
		addToken(token,node,"run {\n");

		if(token.tipo.equals("AP")) addToken(token,node, "");
		else{
			erro("(cmd for) Esperado abrir parenteses (18)");
			return false;
		}

		if(!declaracao(node)) return false;

		if(token.tipo.equals("FIM_LINHA")) addToken(token,node, ";\n");
		else{
			erro("(cmd for) Esperado fim linha");
			return false;
		}

		tradutor("while(");
		if(!condicao(node)) return false;

		if(token.tipo.equals("FIM_LINHA")) addToken(token,node, ")");
		else{
			erro("(cmd for) Esperado fim linha");
			return false;
		}

		ByteArrayOutputStream exp = new ByteArrayOutputStream();
		PrintStream local_saida = new PrintStream(exp);
		System.setOut(local_saida);
		// exp
		if(!expressao(node)) return false;
		
		if(token.tipo.equals("FP")) addToken(token,node, ";\n");
		else{
			erro("(cmd for) Esperado fechar parenteses (19)");
			return false;
		}
		//exp

		System.setOut(saida);

		forIncrease.push(exp.toString());

		if(token.tipo.equals("ACHA")) addToken(token,node, "{\n");
		else{
			erro("(cmd for) Esperado abrir Chaves");
			return false;
		}

		if(!bloco(node)) return false;

		tradutor(exp.toString()); // exp

		if(token.tipo.equals("FCHA")) addToken(token,node, "}\n");
		else{
			erro("(cmd for) Esperado fechar Chaves");
			return false;
		}

		tradutor("}\n");

		forIncrease.pop();

		return true;
	}

	private boolean param(Arvore pai){
		Arvore node = newTree("parametros");
		pai.add(node);

		if(!valor(node)) return false;

		if(!sufixParam(node)) return false;
		return true;
	}
	private boolean sufixParam(Arvore pai){
		Arvore node = newTree("sufix parametros");
		pai.add(node);
		if(token.tipo.equals("VIRGULA")){
			addToken(token,node, ", ");
			if(!param(node)) return false;
		}
		return true;
	}
	
	private boolean condicao(Arvore pai){
		Arvore node = newTree("condicao");
		pai.add(node);

		if(token.tipo.equals("AP")){
			addToken(token,node, "(");
			if(!condicao(node)) return false;
			if(token.tipo.equals("FP")) addToken(token,node, ")");
			else erro("(condicao) Esperado fechar Parenteses");
		} else{
			if(!calculo(node)) return false;

			if(token.tipo.startsWith("OP_REL")){
				if(token.tipo.endsWith("MAIOR")) addToken(token,node, " > ");
				else if(token.tipo.endsWith("MENOR")) addToken(token,node, " < ");
				else if(token.tipo.endsWith("DIFF")) addToken(token,node, " != ");
				else if(token.tipo.endsWith("MAIOR_IGUAL")) addToken(token,node, " >= ");
				else if(token.tipo.endsWith("MENOR_IGUAL")) addToken(token,node, " <= ");
				else if(token.tipo.endsWith("IGUAL")) addToken(token,node, " == ");
			} else{
				erro("(condicao) Esperado Operador relacional");
				return false;
			}

			if(!calculo(node)) return false;
		}
		if(!sufixCondicao(node)) return false;
		return true;
	}

	private boolean sufixCondicao(Arvore pai){
		Arvore node = newTree("sufix condicao");
		pai.add(node);
		if(token.tipo.startsWith("OP_LOG")){
			if(token.tipo.endsWith("AND")) addToken(token,node, " && ");
			else if(token.tipo.endsWith("OR")) addToken(token,node, " || ");
			if(!condicao(node)) return false;
		}
		return true;
	}

	private boolean calculo(Arvore pai){
		Arvore node = newTree("calculo");
		pai.add(node);

		if(token.tipo.equals("AP")){
			addToken(token,node, "(");
			if(!calculo(node)) return false;
			if(token.tipo.equals("FP")) addToken(token,node,")");
			else erro("(calculo) Esperado fechar Parenteses");
		} else{
			if(!valor(node)) return false;
		}
		if(!sufixCalculo(node)) return false;
		return true;
	}

	private boolean sufixCalculo(Arvore pai){
		Arvore node = newTree("sufix calculo");
		pai.add(node);
		if(token.tipo.startsWith("OP_ARI")){
			if(token.tipo.endsWith("SOMA")) addToken(token,node, " + ");
			else if(token.tipo.endsWith("SUBTRACAO")) addToken(token,node, " - ");
			else if(token.tipo.endsWith("MULTIPLICACAO")) addToken(token,node, " * ");
			else if(token.tipo.endsWith("DIVISAO")) addToken(token,node, " / ");
			else if(token.tipo.endsWith("MODULO")) addToken(token,node, " % ");
                        
			if(!calculo(node)) return false;
		}
		return true;
	}

	private boolean valor(Arvore pai){
		Arvore node = newTree("valor");
		pai.add(node);
		if(token.tipo.equals("OP_ARI_SUBTRACAO")){
			if(!nums(node)) return false;
		} else if(token.tipo.equals("VALOR_INT")){
			if(!nums(node)) return false;
		} else if(token.tipo.equals("VALOR_DOUBLE")){
			if(!nums(node)) return false;
		} else if(token.tipo.equals("VALOR_ID")){
			if(!variavel(node)) return false;
		} else if(token.tipo.equals("VALOR_STRING")) addToken(token,node, c.to_str(token.lexema));
		else if(token.tipo.equals("VALOR_CHAR")) addToken(token,node);
		else if(token.tipo.equals("VALOR_BOOL")) addToken(token,node);
		else if(token.tipo.equals("VALOR_STRING")) addToken(token,node, c.to_str(token.lexema));
		else erro("(valor) Esperado valor");
		return true;
	}

	private boolean variavel(Arvore pai){
		Arvore node = newTree("variavel");
		pai.add(node);
		addToken(token,node, c.to_variavel(token.lexema));
		if(!sufixVariavel(node)) return false;
		return true;
	}

	private boolean sufixVariavel(Arvore pai){
		Arvore node = newTree("sufix variavel");
		pai.add(node);
		if(token.tipo.equals("AP")){
			addToken(token,node, "(");
			if(!token.tipo.equals("FP")){
				if(!param(node)) return false;
			}
			if(token.tipo.equals("FP")) addToken(token,node, ")");
			else{
				erro("(sufix variavel) Esperado fechar parenteses");
				return false;
			}
		}
		return true;
	}

	private boolean nums(Arvore pai){
		Arvore node = newTree("numeros");
		pai.add(node);
		if(token.tipo.equals("OP_ARI_SUBTRACAO")){
			addToken(token,node, "-");
			if(!sufixNums(node)) return false;
		} else addToken(token,node , c.to_decimal(token.lexema));
		return true;
	}
	private boolean sufixNums(Arvore pai){
		Arvore node = newTree("sufix numeros");
		pai.add(node);
		if(token.tipo.equals("VALOR_INT")
		|| token.tipo.equals("VALOR_DOUBLE"))
			addToken(token,node, c.to_decimal(token.lexema));
		else erro("(sufix num) Esperado valor numerico");
		return true;
	}
        
}
