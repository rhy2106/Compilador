package Model;
import java.util.List;

public class Parser{
	List<Token> tokens;
	Token token;

	public Parser(List<Token> tokens){
		this.tokens = tokens;
	}

	public Token getNextToken(){
		if(tokens.size() > 0)
			return tokens.remove(0);
		erro("EOF nao esperado");
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
		Arvore node = new Arvore("PROG");
		boolean fim = false;
		while(true){
			if( token == null
			|| token.tipo.equals("ACHA")
			|| token.tipo.equals("FCHA")
			|| token.tipo.equals("FIM_LINHA")) token = getNextToken();
			if(token.tipo.startsWith("TIPO")) fim = declarar_funcao(node);
			else if(token.tipo.equals("EOF")) break;
			else erro("esperado tipo");
			if(fim) break;
		}
		if(!token.tipo.equals("EOF")) erro("token apos o fim do programa");
		else token_print(token,node);

		return node;
	}

	void token_print( Token t, Arvore pai){
		pai.add(new Arvore(t));
	}

	Arvore token_print( String s){
		return new Arvore(s);
	}

	boolean tipo( Arvore pai){
		if(token.tipo.startsWith("TIPO")){
			Arvore node = token_print("tipo");
			pai.add(node);
			token_print(token,node);
			return true;
		}
		return false;
	}

	boolean declarar_funcao( Arvore pai){
		Arvore node = token_print("declarar_funcao");
		pai.add(node);
		boolean is_main = false;
		boolean has_return = false;
		token_print(token,node);
		token = getNextToken();
		if(token.tipo.equals("MAIN")){
			token_print(token,node);
			is_main = true;
		}
		else if(token.tipo.equals("VALOR_ID")) token_print(token,node);
		else erro("esperado nome da função");

		token = getNextToken();
		if(token.tipo.equals("AP")) token_print(token,node);
		else erro("Esperado 18");

		declarar_parametros(node);

		if(token.tipo.equals("FP")) token_print(token,node);
		else erro("Esperado 19");

		token = getNextToken();
		if(token.tipo.equals("ACHA")) token_print(token,node);
		else erro("Esperado 23");

		has_return = bloco(node);

		if(!has_return) erro("Esperado <=");

		if(token.tipo.equals("FCHA")) token_print(token,node);
		else erro("Esperado 24");

		if(is_main) token = getNextToken();
		return is_main;
	}

	void declarar_parametros( Arvore pai){
		Arvore node = token_print("declarar_parametros");
		pai.add(node);

		token = getNextToken();
		if(tipo(node)){
			token = getNextToken();
			if(token.tipo.equals("VALOR_ID")) token_print(token,node);
			else erro("esperado nome da variavel");
		} else return;

		while(true){
			token = getNextToken();
			if(token.tipo.equals("VIRGULA")){
				token_print(token,node);

				token = getNextToken();
				tipo(node);

				token = getNextToken();
				if(token.tipo.equals("VALOR_ID")) token_print(token,node);
				else erro("esperado nome da variavel");
			} else break;
		}
	}

	boolean bloco( Arvore pai){
		Arvore node = token_print("bloco");
		pai.add(node);
		boolean end_with_return = false;
		while(true){
			Arvore filho = token_print("cmd");
			node.add(filho);
			if(token.tipo.equals("ACHA")
			|| token.tipo.equals("FIM_LINHA"))
				token = getNextToken();
			if(token.tipo.equals("PRINT")) cmd_print(filho);
			else if(token.tipo.equals("INPUT")) cmd_input(filho);
			else if(token.tipo.equals("IF")) cmd_if(filho);
			else if(token.tipo.equals("FOR")) cmd_for(filho);
			else if(token.tipo.equals("WHILE")) cmd_while(filho);
			else if(token.tipo.startsWith("TIPO")) cmd_declarar(filho);
			else if(token.tipo.equals("VALOR_ID")) cmd_expressao(filho);
			else if(token.tipo.equals("BREAK")) cmd_break(filho);
			else if(token.tipo.equals("CONTINUE")) cmd_continue(filho);
			else if(token.tipo.equals("RETURN")){
				cmd_return(filho);
				end_with_return = true;
				continue;
			}
			else break;
			end_with_return = false;
		}
		return end_with_return;
	}

	void cmd_return( Arvore pai){
		Arvore node = token_print("return");
		pai.add(node);
		token_print(token,node);

		token = getNextToken();
		if(token.tipo.startsWith("VALOR")){
			token_print(token,node);
			token = getNextToken();
			if(token.tipo.startsWith("OP_ARI")){
				token_print(token,node);
				token = getNextToken();
				calculo(node);
			}
		}

		if(token.tipo.equals("FIM_LINHA")) token_print( token,node);
		else erro("fim da linha");
	}

	void cmd_print( Arvore pai){
		Arvore node = token_print("print");
		pai.add(node);
		token_print( token,node);

		token = getNextToken();
		valor(node);
		
		if(token.tipo.equals("FIM_LINHA")) token_print( token,node);
		else erro("fim da linha");
	}

	void cmd_input( Arvore pai){
		Arvore node = token_print("input");
		pai.add(node);
		token_print( token,node);

		token = getNextToken();
		if(token.tipo.equals("VALOR_ID")) token_print(token,node);
		else erro("esperado nome da variavel");
		
		token = getNextToken();
		if(token.tipo.equals("FIM_LINHA")) token_print( token,node);
		else erro("fim da linha");
	}

	void cmd_if( Arvore pai){
		Arvore node = token_print("if");
		pai.add(node);
		token_print( token,node);

		token = getNextToken();
		if(token.tipo.equals("AP")) token_print(token,node);
		else erro("Esperado 18");

		token = getNextToken();
		condicao(node);

		if(token.tipo.equals("FP")) token_print(token,node);
		else erro("Esperado 19");

		token = getNextToken();
		if(token.tipo.equals("ACHA")) token_print(token,node);
		else erro("Esperado 23");

		bloco(node);

		if(token.tipo.equals("FCHA")) token_print(token,node);
		else erro("Esperado 24");

		cmd_elseif(node);
	}

	void cmd_elseif( Arvore pai){
		while(true){
			token = getNextToken();
			if(token.tipo.equals("ELSEIF")){
				Arvore node = token_print("elseif");
				pai.add(node);
				token_print( token,node);

				token = getNextToken();
				if(token.tipo.equals("AP")) token_print(token,node);
				else erro("Esperado 18");

				token = getNextToken();
				condicao(node);

				if(token.tipo.equals("FP")) token_print(token,node);
				else erro("Esperado 19");

				token = getNextToken();
				if(token.tipo.equals("ACHA")) token_print(token,node);
				else erro("Esperado 23");

				bloco(node);

				if(token.tipo.equals("FCHA")) token_print(token,node);
				else erro("Esperado 24");
			} else if(token.tipo.equals("ELSE")){
				Arvore node = token_print("else");
				pai.add(node);
				token_print( token,node);

				token = getNextToken();
				if(token.tipo.equals("ACHA")) token_print(token,node);
				else erro("Esperado 23");

				bloco(node);

				if(token.tipo.equals("FCHA")) token_print(token,node);
				else erro("Esperado 24");
			} else break;
		}

	}

	void cmd_for( Arvore pai){
		Arvore node = token_print("for");
		pai.add(node);
		token_print(token,node);

		token = getNextToken();
		if(token.tipo.equals("AP")) token_print(token,node);
		else erro("Esperado 18");

		expressao(node);

		if(token.tipo.equals("FIM_LINHA")) token_print( token,node);
		else erro("fim da linha");

		token = getNextToken();
		condicao(node);

		if(token.tipo.equals("FIM_LINHA")) token_print( token,node);
		else erro("fim da linha");

		expressao(node);

		if(token.tipo.equals("FP")) token_print(token,node);
		else erro("Esperado 19");

		token = getNextToken();
		if(token.tipo.equals("ACHA")) token_print(token,node);
		else erro("Esperado 23");

		bloco(node);

		if(token.tipo.equals("FCHA")) token_print(token,node);
		else erro("Esperado 24");
		token = getNextToken();
	}

	void cmd_while( Arvore pai){
		Arvore node = token_print("while");
		pai.add(node);
		token_print(token,node);

		token = getNextToken();
		if(token.tipo.equals("AP")) token_print(token,node);
		else erro("Esperado 18");

		token = getNextToken();
		condicao(node);

		if(token.tipo.equals("FP")) token_print(token,node);
		else erro("Esperado 19");

		token = getNextToken();
		if(token.tipo.equals("ACHA")) token_print(token,node);
		else erro("Esperado 23");

		bloco(node);

		if(token.tipo.equals("FCHA")) token_print(token,node);
		else erro("Esperado 24");
		token = getNextToken();
	}

	void cmd_expressao( Arvore pai){
		Arvore node = token_print("cmd_expressao");
		pai.add(node);
		token_print(token,node);

		token = getNextToken();
		if(token.tipo.equals("AP")){
			token_print(token,node);

			parametros(node);

			if(token.tipo.equals("FP")) token_print(token,node);
			else erro("Esperado 19");
			token = getNextToken();
		} else if(token.tipo.equals("OP_ATR")){
			token_print(token,node);

			token = getNextToken();
			calculo(node);

		} else erro("token nao reconhecido");

		if(token.tipo.equals("FIM_LINHA")) token_print(token,node);
		else erro("fim linha");
	}

	void cmd_break( Arvore pai){
		Arvore node = token_print("break");
		pai.add(node);
		token_print(token,node);

		token = getNextToken();
		if(token.tipo.equals("FIM_LINHA")) token_print(token,node);
		else erro("Esperado 28");
	}

	void cmd_continue( Arvore pai){
		Arvore node = token_print("continue");
		pai.add(node);
		token_print(token,node);

		token = getNextToken();
		if(token.tipo.equals("FIM_LINHA")) token_print(token,node);
		else erro("Esperado 28");
	}

	void valor( Arvore pai){
		Arvore node = token_print("valor");
		pai.add(node);
		if(!token.tipo.equals("VALOR_ID")
		&& !token.tipo.equals("VALOR_INT")
		&& !token.tipo.equals("VALOR_DOUBLE")
		&& !token.tipo.equals("OP_ARI_SUBTRACAO")
		&& !token.tipo.equals("VALOR_STRING")
		&& !token.tipo.equals("VALOR_CHAR")
		&& !token.tipo.equals("VALOR_BOOL")) erro("exsperado valor");
		if(token.tipo.equals("VALOR_ID")){
			token_print(token,node);
			variavel(node);
		} else if( token.tipo.equals("OP_ARI_SUBTRACAO")
				|| token.tipo.equals("VALOR_INT")
				|| token.tipo.equals("VALOR_DOUBLE")){
			numero(node);
		} else{
			token_print(token,node);
			token = getNextToken();
		}
	}

	void numero( Arvore pai ){
		Arvore node = token_print("numero");
		pai.add(node);

		if( token.tipo.equals("OP_ARI_SUBTRACAO")){
			token_print(token,node);
			token = getNextToken();
		}
		token_print(token,node);
		token = getNextToken();
	}

	void variavel( Arvore pai){
		Arvore node = token_print("variavel");
		pai.add(node);

		token = getNextToken();
		if(token.tipo.equals("AP")){
			Arvore filho = token_print("chamar_funcao");
			node.add(filho);
			token_print(token,filho);

			parametros(node);

			if(token.tipo.equals("FP")) token_print(token,filho);
			else erro("Esperado 19");
			token = getNextToken();
		} 

	}

	void cmd_declarar( Arvore pai){
		Arvore node = token_print( "cmd_declarar");
		pai.add(node);
		token_print(token,node);

		token = getNextToken();
		if(!token.tipo.equals("VALOR_ID")) erro("esperado id");
		token_print( token,node);

		token = getNextToken();
		if(token.tipo.equals("OP_ATR")){
			token_print(token,node);
			token = getNextToken();
			calculo(node);
		}

		if(!token.tipo.equals("FIM_LINHA")) erro("fim linha");
		token_print( token,node);
	}

	void condicao( Arvore pai){
		Arvore node = token_print("condicao");
		pai.add(node);
		if(token.tipo.equals("AP")){
			token_print(token,node);
			token = getNextToken();
			condicao(node);
			if(token.tipo.equals("FP")) token_print(token,node);
			else erro("esperado 19");
			token = getNextToken();
			if(token.tipo.startsWith("OP_LOG")){
				token_print(token,node);
				token = getNextToken();
				condicao(node);
			}
		} else{
			calculo(node);
			op_relacional(node);
			token = getNextToken();
			calculo(node);
			if(token.tipo.startsWith("OP_LOG")){
				token_print(token,node);
				token = getNextToken();
				condicao(node);
			}
		}
	}

	void expressao( Arvore pai){
		Arvore node = token_print("expressao");
		pai.add(node);

		token = getNextToken();
		if(token.tipo.startsWith("TIPO")){
			token_print( token,node);
			token = getNextToken();
		}

		if(token.tipo.equals("VALOR_ID")) token_print( token,node);
		else erro("esperado id");

		token = getNextToken();
		if(token.tipo.equals("OP_ATR")) token_print( token,node);
		else erro("op atribuicao");

		token = getNextToken();
		calculo(node);
	}

	void calculo( Arvore pai){
		Arvore node = token_print("calculo");
		pai.add(node);
		if(token.tipo.equals("AP")){
			token_print(token,node);
			token = getNextToken();
			calculo(node);
			if(token.tipo.equals("FP")) token_print(token,node);
			else erro("esperado 19");
			token = getNextToken();
			if(token.tipo.startsWith("OP_ARI")){
				token_print(token,node);
				token = getNextToken();
				calculo(node);
			}
		} else{
			valor(node);
			if(token.tipo.startsWith("OP_ARI")){
				token_print(token,node);
				token = getNextToken();
				calculo(node);
			}
		}
	}

	void op_relacional( Arvore pai){
		Arvore node = token_print("op_relacional");
		pai.add(node);
		if(!token.tipo.equals("OP_REL_MAIOR")
		&& !token.tipo.equals("OP_REL_MENOR")
		&& !token.tipo.equals("OP_REL_DIFF")
		&& !token.tipo.equals("OP_REL_IGUAL")
		&& !token.tipo.equals("OP_REL_MAIOR_IGUAL")
		&& !token.tipo.equals("OP_REL_MENOR_IGUAL"))
			erro("op relacional faltnado");
		token_print(token,node);
	}

	void parametros( Arvore pai){
		Arvore node = token_print("parametros");
		pai.add(node);

		token = getNextToken();
		if(token.tipo.startsWith("VALOR")){
			if(token.tipo.equals("VALOR_ID")) variavel(node);
			else{
				token_print(token,node);
				token = getNextToken();
			}
		} else return;

		while(true){
			if(token.tipo.equals("VIRGULA")){
				token_print(token,node);

				token = getNextToken();
				if(token.tipo.startsWith("VALOR")){
					if(token.tipo.equals("VALOR_ID")) variavel(node);
					else{
						token_print(token,node);
						token = getNextToken();
					}
				} else erro("esperado parametro");
			} else break;
		}
	}
}
