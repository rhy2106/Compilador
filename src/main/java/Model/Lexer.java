package Model;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.ArrayList;

public class Lexer {

	private List<Token> tokens;
	private List<AFD> afds;
	private CharacterIterator code;
	private int linha;
	private int coluna;

	public Lexer(String code) {
		linha = 1;
		coluna = 0;
		tokens = new ArrayList<>();
		afds = new ArrayList<>();
		this.code = new StringCharacterIterator(code);
		afds.add(new Comentarios());
		afds.add(new Reservadas());
		afds.add(new Numeros());
		afds.add(new Palavras());
		afds.add(new Caracteres());
		afds.add(new Identificadores());
		afds.add(new Operadores());
	}

	public void skipWhiteSpace() {
		while(true){
			if(code.current() == '0'){
				code.next();
				coluna++;
			} else if (code.current() == '\n') {
				code.next();
				linha++;
				coluna = 0;
			} else break;
		}
	}

	public List<Token> getTokens() {
		Token t;
		do {
			skipWhiteSpace();
			t = searchNextToken();
			if(t == null) error();
			if(t.tipo.equals("COMENTARIO_ERROR")) comentarioError(t);
			if(t.tipo.equals("STRING_ERROR")) stringError(t);
			if(t.tipo.equals("CHAR_ERROR")) charError(t);
			if(!t.tipo.equals("COMENTARIO")) tokens.add(t);
		}while (!t.tipo.equals("EOF"));
		return tokens;
	}

	private Token searchNextToken() {
		int pos = code.getIndex();
		for(AFD afd : afds){
			Token t = afd.evaluate(code,linha,coluna);
			if(t != null){
				linha = t.linha;
				coluna = t.coluna;
				return t;
			}
			code.setIndex(pos);
		}
		return null;
	}

	private void error() {
		throw new RuntimeException(
				"\nError: token not recognized: " +
				"\nlinha: " + linha +
				"\ncoluna: " + coluna
				);
	}
	private void stringError(Token t){
		throw new RuntimeException(
				"\nError: \'16\' Missing at: " +
				"\n" + t.lexema +
				"\nlinha: " + linha +
				"\ncoluna: " + coluna
				);
	}
	private void charError(Token t){
		throw new RuntimeException(
				"\nError: \'17\' Missing at: " +
				"\n" + t.lexema +
				"\nlinha: " + linha +
				"\ncoluna: " + coluna
				);
	}
	private void comentarioError(Token t){
		throw new RuntimeException(
				"\nError: \'26\' Missing at: " + 
				"\n" + t.lexema +
				"\nlinha: " + linha +
				"\ncoluna: " + coluna
				);
	}
}
