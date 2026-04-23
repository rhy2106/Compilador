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

function chpos_vertical_visual(p){
	vim.cmd = "";
	const linhas = vim.editor.value.split('\n');
	const cursor_all = vim.get_all(vim.visual_cursor);
	const targetLine = Math.max(0,Math.min(cursor_all.curLine + p,vim.editor.value.length));
	let leftNewCursor = 0;
	let rightNewCursor = 0;

	for(let i = 0; i < targetLine; i++){
		leftNewCursor += linhas[i].length + 1;
		rightNewCursor += linhas[i].length + 1;
	}
	rightNewCursor += linhas[targetLine].length + 1;

	vim.visual_cursor = leftNewCursor;
	vim.set_sel(
		Math.min(vim.visual_anchor,leftNewCursor),
		Math.max(vim.visual_anchor2,rightNewCursor)
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

// Visual mode

export function visual_linha(tecla){
	if(tecla != 'Shift'
	&& tecla != 'Meta'
	&& tecla != 'Enter') vim.cmd += tecla;
	if(tecla == 'Escape') vim.chmod("general");
	if(vim.cmd == 'v') vim.chmod("visual");
	else if(vim.cmd == 'j') chpos_vertical_visual(1);
	else if(vim.cmd == 'k') chpos_vertical_visual(-1);
	else if(vim.cmd == 'G') vim.set_sel(vim.visual_anchor,vim.editor.value.length);
	else if(vim.cmd == 'gg') vim.set_sel(0,vim.visual_anchor2);
	else if(vim.cmd == 's') substituir_visual();
	else if(vim.cmd == 'x') excluir_visual();
	else if(vim.cmd == 'D') excluir_visual();
	else if(vim.cmd == 'd') excluir_visual();
	else if(vim.cmd == 'y') yank();
	else if(vim.cmd.length > 2) vim.cmd = "";
}

