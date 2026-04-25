package Model;
public class Token{
	public String tipo;
	public String lexema;
	public int linha;
	public int coluna;
	Token(String tipo, String lexema){
		this.tipo = tipo;
		this.lexema = lexema;
	}
	Token(String tipo, String lexema, int linha, int coluna){
		this.tipo = tipo;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
	}
	@Override
	public String toString(){
		return "<" + tipo + "," + lexema + ">";
	}
}
