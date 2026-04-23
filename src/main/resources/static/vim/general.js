import {vim} from './vim.js';

function chpos_horizontal(p){
	vim.cmd = "";
	const text = vim.editor.value;
	console.log("horizontal: " + p);
	vim.set_pos(Math.max(0, Math.min(text.length, vim.pos() + p)));
}

function chpos_vertical(p){
	vim.cmd = "";
	const linhas = vim.editor.value.split('\n');
	const {prefColumnChars,curLine,curColumn,totalChars} = vim.get_all(vim.pos());
	const targetLine = curLine + p;
	if(targetLine < 0) return vim.set_pos(0);
	if(targetLine >= linhas.length) return vim.set_pos(totalChars);

	let newPos = 0;

	for(let i = 0; i < targetLine; i++)
		newPos += linhas[i].length + 1;
	newPos += Math.min(curColumn, Math.max(0,linhas[targetLine].length));

	return vim.set_pos(newPos);
}

function cmc_linha(){
	vim.cmd = "";
	const {prefColumnChars,curLine,curColumn,totalChars} = vim.get_all(vim.pos());
	vim.set_pos(prefColumnChars);
}

function fim_linha(){
	vim.cmd = "";
	const text = vim.editor.value;
	const linhas = text.split('\n');
	const {prefColumnChars,curLine,curColumn,totalChars} = vim.get_all(vim.pos());

	vim.set_pos(prefColumnChars + linhas[curLine].length);
	vim.chmod("insert");
}

function nova_linha(p){
	vim.cmd = "";
	const text = vim.editor.value;
	const linhas = text.split('\n');
	const {prefColumnChars,curLine,curColumn,totalChars} = vim.get_all(vim.pos());
	const newLine = curLine + p;

	let newPos = 0;
	if(newLine > curLine)
		newPos = prefColumnChars + linhas[curLine].length + 1;
	else
		newPos = prefColumnChars;

	const newText = text.substring(0,newPos) + "\n" + text.substring(newPos,totalChars);
	vim.editor.value = newText;
	vim.set_pos(newPos);
	vim.chmod("insert");
}

function del_linha(){
	vim.cmd = "";
	const text = vim.editor.value;
	const linhas = text.split('\n');
	const {prefColumnChars,curLine,curColumn,totalChars} = vim.get_all(vim.pos());
	const targetLine = curLine;
	const left = prefColumnChars;
	const right = left + linhas[curLine].length + 1;
	const newText = text.substring(0,left) + text.substring(right,totalChars);
	const middleText = text.substring(left,right);

	vim.yank = middleText;
	vim.editor.value = newText;
	vim.set_pos(left);
}

function del_fim_linha(){
	vim.cmd = "";
	const text = vim.editor.value;
	const linhas = text.split('\n');
	const {prefColumnChars,curLine,curColumn,totalChars} = vim.get_all(vim.pos());
	const left = prefColumnChars + curColumn;
	const right = prefColumnChars + linhas[curLine].length;
	const newText = text.substring(0,left) + text.substring(right,totalChars);
	const middleText = text.substring(left,right);

	vim.yank = middleText;
	vim.editor.value = newText;
	vim.set_pos(left);
}

function substituir(){
	vim.cmd = "";
	const text = vim.editor.value;
	vim.set_sel(vim.pos(), Math.min(vim.pos() + 1, text.length-1));
	excluir_sel();
	vim.chmod("insert");
}

function excluir(){
	vim.cmd = "";
	const text = vim.editor.value;
	vim.set_sel(vim.pos(), Math.min(vim.pos() + 1, Math.max(0,text.length-1)));
	excluir_sel();
}

function excluir_sel(){
	const text = vim.editor.value;
	const left = vim.editor.selectionStart;
	const right = vim.editor.selectionEnd;
	const newText = text.substring(0,left) + text.substring(right,text.length);
	const middleText = text.substring(left,right);

	vim.yank = middleText;
	vim.editor.value = newText;
	vim.set_pos(left);
}

function paste(p){
	const text = vim.editor.value;
	const linhas = text.split('\n');
	const {prefColumnChars,curLine,curColumn,totalChars} = vim.get_all(vim.pos());
	const targetLine = curLine + p;
	let left = 0;

	if(targetLine > curLine)
		left = prefColumnChars + linhas[curLine].length + 1;
	else
		left = prefColumnChars;

	const newText = text.substring(0,left) + vim.yank + text.substring(left,totalChars);
	vim.editor.value = newText;
	vim.set_pos(left);
}

export function general(tecla){
	if(tecla != 'Shift'
	&& tecla != 'Meta'
	&& tecla != 'Enter') vim.cmd += tecla;
	if(tecla == 'Escape') vim.chmod("general");
	if(vim.cmd == 'i') vim.chmod("insert");
	else if(vim.cmd == 'v') vim.chmod("visual");
	else if(vim.cmd == 'V') vim.chmod("visual-linha");
	else if(vim.cmd == 'h') chpos_horizontal(-1);
	else if(vim.cmd == 'j') chpos_vertical(1);
	else if(vim.cmd == 'k') chpos_vertical(-1);
	else if(vim.cmd == 'l') chpos_horizontal(1);
	else if(vim.cmd == 'A') fim_linha();
	else if(vim.cmd == 'o') nova_linha(1);
	else if(vim.cmd == 'O') nova_linha(-1);
	else if(vim.cmd == 'G') vim.set_pos(vim.editor.value.length);
	else if(vim.cmd == 'gg') vim.set_pos(0);
	else if(vim.cmd == 'D') del_fim_linha();
	else if(vim.cmd == 'dd') del_linha();
	else if(vim.cmd == '0') cmc_linha();
	else if(vim.cmd == 's') substituir();
	else if(vim.cmd == 'x') excluir();
	else if(vim.cmd == 'p') paste(1);
	else if(vim.cmd == 'P') paste(-1);
	else if(vim.cmd.length > 2) vim.cmd = "";
}
