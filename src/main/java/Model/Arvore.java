package Model;
import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Arvore{
	Token t;
	List<Arvore> filhos;
	PrintStream original = System.out;
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PrintStream saida = new PrintStream(baos);

	public Arvore(String nome){
		this.t = new Token("",nome);
		this.filhos = new ArrayList<>();
	}
	public Arvore(Token t){
		this.t = t;
		this.filhos = new ArrayList<>();
	}
	public void add(Arvore filho){
		filhos.add(filho);
	}

	public void print(){
		System.setOut(saida);
		System.out.println(t.lexema);
		print("", true, true);
		System.setOut(original);
	}

	private void print(String prefixo, boolean ultimo, boolean inicio){
		if(!inicio) System.out.println(prefixo + (ultimo ? "└── " : "├── ") + t.lexema);

		for(int i = 0; i < filhos.size(); i++){
			filhos.get(i).print(
				(!inicio ? prefixo + (ultimo ? "    " : "│   ") : ""),
				i == filhos.size() - 1,
				false
			);
		}
	}

	public String arvore(){
		return baos.toString();
	}
}
