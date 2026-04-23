import {vim} from './vim.js';
import {general} from './general.js';
import {visual} from './visual.js';
import {visual_linha} from './visual_linha.js';
import {carregarBarra} from './barra.js';
import {carregarHighlight} from './highlight.js';

function setup(){
	document.getElementById("status").innerText = vim.mode;
	document.getElementById("cmd").innerText = vim.cmd;
	carregarBarra();
	carregarHighlight();
}

vim.editor.addEventListener("keydown",function(event){
    const tecla = event.key;
	if(vim.mode != "insert") event.preventDefault();
	if(vim.mode == "general") general(tecla);
	else if(vim.mode == "visual") visual(tecla);
	else if(vim.mode == "visual-linha") visual_linha(tecla);
	else if(vim.mode == "insert"){
		if(tecla == 'Escape'){
			vim.chmod("general");
		}
	}

	document.getElementById("status").innerText = vim.mode;
	document.getElementById("cmd").innerText = vim.cmd;
	carregarBarra();
});

vim.editor.addEventListener("input",function(){
	carregarHighlight();
});
vim.editor.addEventListener("keyup",function(){
	carregarHighlight();
});

vim.editor.addEventListener("scroll",function(){
	vim.barra.scrollTop = vim.editor.scrollTop;
});

vim.editor.addEventListener("scroll",function(){
	vim.highlight.scrollTop = vim.editor.scrollTop;
});

setup();
