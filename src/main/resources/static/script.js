async function to_decimal(){
	var num = document.getElementById("hexavigesimal").value;

	const res = await fetch('/todecimal',{
		method: 'POST',
		headers: {
			'Content-Type': 'text/plain'
		},
		body: num
	});

	const data = await res.text();

	var ans = document.getElementById("ans_decimal");
	ans.innerText = data;
}

async function to_hexavigesimal(){
	var num = document.getElementById("decimal").value;

	const res = await fetch('/tohexavigesimal',{
		method: 'POST',
		headers: {
			'Content-Type': 'text/plain'
		},
		body: num
	});

	const data = await res.text();

	var ans = document.getElementById("ans_hexavigesimal");
	ans.innerText = data;
}

async function compilar(){
	const codigo_fonte = document.getElementById("code").value;

	console.log("codigo: " + codigo_fonte);

	const res = await fetch('/compile',{
		method: 'POST',
		headers: {
			'Content-Type': 'text/plain'
		},
		body: codigo_fonte
	});

	const data = await res.json();
	console.log("data: \n" + data);

	const resposta = document.getElementById("resposta");
	resposta.classList.remove("naovisivel");

	var erro = document.getElementById("erro"); 
	var erro_msg = document.getElementById("erro_msg");
	var traducao = document.getElementById("traducao"); 
	var arvore = document.getElementById("arvore"); 
	var codigo = document.getElementById("codigo"); 

	if(data.success){
		erro.classList.add("naovisivel");
		traducao.classList.remove("naovisivel");
		traducao.classList.add("bloco");
		arvore.innerText = data.arvore;
		codigo.innerText = data.codigo;
	} else{
		traducao.classList.add("naovisivel");
		traducao.classList.remove("bloco");
		erro.classList.remove("naovisivel");
		erro_msg.innerText = data.erro;
	}
}

async function tabela(){
	const res = await fetch('/tabela',{
		method: 'GET',
		headers: {
			'Content-Type': 'text/plain'
		},
	});

	const data = await res.text();
	console.log("data: \n" + data);

	const tabela = document.getElementById("tabela");
	const linhas = data.split("\n");
	for(let i = 0; i < linhas.length-1; i++){
		const colunas = linhas[i].split(";;");
		const linha = document.createElement("tr");
		for(let j = 0; j < colunas.length; j++){
			const c = document.createElement("td");
			const texto = document.createTextNode(colunas[j]);
			c.appendChild(texto);
			linha.appendChild(c);
		}
		tabela.appendChild(linha);
	}
}

tabela();
