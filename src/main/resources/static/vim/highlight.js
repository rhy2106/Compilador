import {vim} from './vim.js';

export function carregarHighlight(){
	const text = vim.editor.value;
	vim.highlight.replaceChildren();

	const idChars = '[ !@#$%&*()\\-+/?:;.,<>|{}\\[\\]^~\'\"=_\\\\]';

	const rules = {
		vazio: /0+/,
        comentario: /25[\s\S]*?26/,
        palavra: /16[\s\S]*?16/,
        caracter: /17[\s\S]*?17/,
        reservado: new RegExp(`(?<!${idChars})(:\\)|<=|\\[\\]|#|@|\\$|\\?|\\? |\\.\\.\\.|:\\()(?!${idChars})`),
        tipo: new RegExp(`(?<!${idChars})(:\\-:|;\\-;|\\(\\/'\\-'\\)\\/|{\\/"}\\/|\\^\\-\\^|'\\-'|\\-_\\-|\\*_\\*)(?!${idChars})`),
        operador: /[1-9]|1[1-5]|18|19|28|23|24/,
        numero: /[a-z]+[A-Z]*/,
        id: /[!@#$%&\*\(\)\-\+/\?:;\.,><|\{\}\[\]\^~'"=_\\]+/
    };

	const regex = new RegExp(
		Object.entries(rules)
			.map(([name,regex]) => `(?<${name}>${regex.source})`)
			.join('|'),
		'g'
	);

	let lastIndex = 0;
	let match;
	
	while((match = regex.exec(text)) !== null){
		if(match.index > lastIndex){
			const plain = document.createTextNode(text.substring(lastIndex,match.index));
			vim.highlight.appendChild(plain);
		}

        const groupName = Object.keys(match.groups).find(key => match.groups[key] !== undefined);
        const span = document.createElement("span");

        span.classList.add(`${groupName}`);
        span.textContent = match[0];

        vim.highlight.appendChild(span);
        lastIndex = regex.lastIndex;
    }

    if (lastIndex < text.length) {
        vim.highlight.appendChild(document.createTextNode(text.substring(lastIndex)));
    }
}
