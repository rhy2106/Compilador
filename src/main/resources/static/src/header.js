const header = document.getElementById("header");
const botoes = ['boost', 'highlight']

function setup(){
	for(let i = 0; i < botoes.length; i++){
		const botao = document.createElement("button");
		botao.innerText = botoes[i];
		botao.setAttribute("onclick",botoes[i] + "()");
		header.appendChild(botao);
	}
}

setup();
