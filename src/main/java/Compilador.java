package Compilador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import Model.*;

public class Compilador{
	public static void main(String args[]){
		String body = "";
		try {
            File myFile = new File(args[0]);
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                body += reader.nextLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
		try{
			Lexer lexer = new Lexer(body);
			List<Token> tokens = lexer.getTokens();
			Parser parser = new Parser(tokens);
			parser.parse();

			Arvore arvore = parser.getTree();
			arvore.print();

			String result = arvore.arvore();
			String result2 = parser.codigo();

			System.out.println(result);

			System.out.println(result2);
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
	}
}
