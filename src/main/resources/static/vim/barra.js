import {vim} from  './vim.js';

export function carregarBarra(){
	const linhas = vim.editor.value.split('\n');
	const {prefColumnChars,curLine,curColumn,totalChars} = vim.get_all(vim.pos());
	let nums = "";

	for(let i = 0; i < curLine; i++){
		nums += (curLine - i) + '\n';
		for(let j = 1; j < linhas[i].length / 75; j++) nums += '\n';
	}
	nums += (curLine+1) + '\n'
	for(let j = 1; j < linhas[curLine].length / 75; j++) nums += '\n';
	for(let i = curLine+1; i < linhas.length; i++){
		nums += (i - curLine) + '\n';
		for(let j = 1; j < linhas[i].length / 75; j++) nums += '\n';
	}

	vim.barra.innerText = nums;
}
