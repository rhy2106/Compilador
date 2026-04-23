import {vim} from './vim.js';

function substituir_visual(){
	vim.cmd = "";
	excluir_sel();
	vim.chmod("insert");
}

function excluir_visual(){
	vim.cmd = "";
	excluir_sel();
	vim.chmod("general");
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

function chpos_horizontal_visual(p){
	vim.cmd = "";
	console.log("horizontal: " + p);
	const text = vim.editor.value;
	const left = vim.editor.selectionStart;
	const right = vim.editor.selectionEnd;

	let newCursor = vim.visual_cursor + p;
	if(newCursor < 0) newCursor = 0;
	if(newCursor > text.length) newCursor = text.length;

	vim.visual_cursor = newCursor;
	vim.set_sel(
		Math.min(vim.visual_anchor,newCursor),
		Math.max(vim.visual_anchor,newCursor)
	);
}

function chpos_vertical_visual(p){
	vim.cmd = "";
	const linhas = vim.editor.value.split('\n');
	const cursor_all = vim.get_all(vim.visual_cursor);
	const targetLine = cursor_all.curLine + p;
	let newCursor = 0;

	for(let i = 0; i < targetLine; i++)
		newCursor += linhas[i].length + 1;
	newCursor += Math.min(cursor_all.curColumn, linhas[targetLine].length);

	vim.visual_cursor = newCursor;
	vim.set_sel(
		Math.min(vim.visual_anchor,newCursor),
		Math.max(vim.visual_anchor,newCursor)
	);
}

function yank(){
	const text = vim.editor.value;
	const left = vim.editor.selectionStart;
	const right = vim.editor.selectionEnd;
	const middleText = text.substring(left,right);

	vim.yank = middleText;
	vim.chmod("general");
}

function fim_linha_visual(){
	vim.cmd = "";
	const linhas = vim.editor.value.split('\n');
	const cursor_all = vim.get_all(vim.visual_cursor);
	const newCursor = cursor_all.prefColumnChars + linhas[cursor_all.curLine].length;

	vim.visual_cursor = newCursor;
	vim.set_sel(
		Math.min(vim.visual_anchor,newCursor),
		Math.max(vim.visual_anchor,newCursor)
	);
}

function cmc_linha_visual(){
	vim.cmd = "";
	const linhas = vim.editor.value.split('\n');
	const cursor_all = vim.get_all(vim.visual_cursor);
	const newCursor = cursor_all.prefColumnChars;

	vim.visual_cursor = newCursor;
	vim.set_sel(
		Math.min(vim.visual_anchor,newCursor),
		Math.max(vim.visual_anchor,newCursor)
	);
}

// Visual mode

export function visual(tecla){
	if(tecla != 'Shift'
	&& tecla != 'Meta'
	&& tecla != 'Enter') vim.cmd += tecla;
	if(tecla == 'Escape') vim.chmod("general");
	if(vim.cmd == 'V') vim.chmod("visual-linha");
	else if(vim.cmd == 'h') chpos_horizontal_visual(-1);
	else if(vim.cmd == 'j') chpos_vertical_visual(1);
	else if(vim.cmd == 'k') chpos_vertical_visual(-1);
	else if(vim.cmd == 'l') chpos_horizontal_visual(1);
	else if(vim.cmd == 'G') vim.set_sel(vim.visual_anchor,vim.editor.value.length);
	else if(vim.cmd == 'gg') vim.set_sel(0,vim.visual_anchor);
	else if(vim.cmd == 's') substituir_visual();
	else if(vim.cmd == 'x') excluir_visual();
	else if(vim.cmd == 'A') fim_linha_visual();
	else if(vim.cmd == 'D') excluir_visual();
	else if(vim.cmd == 'd') excluir_visual();
	else if(vim.cmd == '0') cmc_linha_visual();
	else if(vim.cmd == 'y') yank();
	else if(vim.cmd.length > 2) vim.cmd = "";
}

