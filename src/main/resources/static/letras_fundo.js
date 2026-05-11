const textarea = document.getElementById("code");
const fundo = document.getElementById("fundo");

let palavras = [];

function criarLetras() {
	const idChars = '[ !@#$%&*()\\-+/?:;.,<>|{}\\[\\]^~\'\"=_\\\\]';

	const rules = {
		vazio: /0+/,
        comentario: /25[\s\S]*?26/,
        palavra: /16[\s\S]*?16/,
        caracter: /17[\s\S]*?17/,
        reservado: new RegExp(`(?<!${idChars})(:\\)|<=|\\[\\]|#|@|\\$|\\?|\\? |\\.\\.\\.|:\\()(?!${idChars})`),
        tipo: new RegExp(`(?<!${idChars})(:\\-:|;\\-;|\\(\\/'\\-'\\)\\/|{\\/"}\\/|\\^\\-\\^|'\\-'|\\-_\\-|\\*_\\*)(?!${idChars})`),
        operador: /18|19|28|23|24|1[1-5]|[1-9]/,
        numero: /[a-z]+[A-Z]*/,
        id: /[!@#$%&\*\(\)\-\+/\?:;\.,><|\{\}\[\]\^~'"=_\\]+/
    };

	const regex = new RegExp(
		Object.entries(rules)
			.map(([name,regex]) => `(?<${name}>${regex.source})`)
			.join('|'),
		'g'
	);

	const matches = [...textarea.value.matchAll(regex)];
	const ignore = ['vazio','comentario'];
	
	const texto = matches.filter( m => {
		const grupo = Object.keys(m.groups).find(key => m.groups[key] !== undefined)
		return !ignore.includes(grupo);
	}).map( m => m[0] );
	console.log(texto);

	if(texto.length > palavras.length){
		for(let i = palavras.length; i < texto.length; i++){
			const span = document.createElement("span");
			span.className = "letra";
			fundo.appendChild(span);
			palavras.push({
				el: span,
				x: Math.random() * fundo.clientWidth,
				y: Math.random() * fundo.clientHeight,
				vx: (Math.random() - 0.5) * 4,
				vy: (Math.random() - 0.5) * 4,
				rotate: (Math.random() - 0.5) * 720
			});
		}
	} else if(texto.length < palavras.length){
		for(let i = palavras.length - 1; i >= texto.length; i--) {
			fundo.removeChild(palavras[i].el);
			palavras.pop();
		}
	}
	
	palavras.forEach((letra, i) => {
	    letra.el.textContent = texto[i];
	});
}


textarea.addEventListener("input", criarLetras);

function animar() {
	palavras.forEach(palavra => {

		const w = palavra.el.offsetWidth;
		const h = palavra.el.offsetHeight;

		palavra.x = Math.max(0,Math.min(palavra.x + palavra.vx, fundo.clientWidth - w));
		palavra.y = Math.max(0,Math.min(palavra.y + palavra.vy, fundo.clientHeight - h));
		palavra.rotate = (palavra.rotate < 0 ? (palavra.rotate - 5) % 360 : (palavra.rotate + 5) % 360);

		if( palavra.x + palavra.vx <= 0 ||
			palavra.x + palavra.vx >= fundo.clientWidth - w) palavra.vx *= -1;
		if( palavra.y + palavra.vy <= 0 ||
			palavra.y + palavra.vy >= fundo.clientHeight - h) palavra.vy *= -1;

		palavra.el.style.transform = `translate(${palavra.x}px, ${palavra.y}px) rotate(${palavra.rotate}deg)`;
	});

	requestAnimationFrame(animar);
}

animar();
criarLetras();

const observer = new ResizeObserver(entries => {
	fundo.style.height = Math.max(window.innerHeight, document.body.offsetHeight) + "px";
});

function boost(){
	if(fundo.classList.contains("naovisivel")) fundo.classList.remove("naovisivel");
	else fundo.classList.add("naovisivel");
}

observer.observe(document.body);
