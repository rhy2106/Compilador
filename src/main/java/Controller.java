package Compilador;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import Model.*;

@RestController
@CrossOrigin(origins = "*")
public class Controller extends Exception{
	@PostMapping("/tohexavigesimal")
	public String to_hexavigesimal(@RequestBody String body){
		System.out.println("/tohexavigesimal");
		Conversor c = new Conversor();
		String ans = "" + c.to_26(Double.parseDouble(body));
		System.out.println(ans);
		return ans;
	}

	@PostMapping("/todecimal")
	public String to_decimal(@RequestBody String body){
		System.out.println("/todecimal");
		Conversor c= new Conversor();
		String ans = "" + c.to_decimal(body);
		System.out.println();
		return ans;
	}

	@PostMapping("/compile")
	public Map<String,Object> compile(@RequestBody String body){
		Map<String,Object> response = new HashMap<>();
		System.out.println("/compile");
		try{
			Lexer lexer = new Lexer(body);
			List<Token> tokens = lexer.getTokens();
			Parser2 parser = new Parser2(tokens);
			if(!parser.parse()){
				response.put("success",false);
				response.put("erro","Erro ao formar arvore");
				return response;
			}
			// Parser parser = new Parser(tokens);
			// Arvore arvore = parser.arvore();
			
			Arvore arvore = parser.getTree();
			arvore.print();
			String result = arvore.arvore();

			System.out.println(response);

			response.put("success",true);
			response.put("arvore",result);
			response.put("codigo","");
			return response;

		}catch(Exception e){
			response.put("success",false);
			response.put("erro",e.toString());
			return response;
		}
	}

	@GetMapping("/tabela")
	public String compile(){
		try{
			FileReader arq = new FileReader("src/main/resources/static/tabela.txt");
			BufferedReader arquivo = new BufferedReader(arq);

			String linha = arquivo.readLine();
			String s = "";
			while(linha != null){
				s += linha + '\n';
				linha = arquivo.readLine();
			}
			arq.close();
			return s;
		}catch(IOException e){
			return e.toString();
		}
	}
}
