class Vim{
	constructor(){
		this.mode = "general";
		this.cmd = "";
		this.editor = document.getElementById("code");
		this.barra = document.getElementById("barra");
		this.highlight = document.getElementById("highlight");
		this.editor.value = "25\n               --- Vim Comands---\n\n--- General Mode ---    | --- Visual Mode ---\nmove-around: h, j, k, l | move-around: h, j, k, l\nappend-end-line: A      | start-line: 0\nstart-line: 0           | end-line: A\nsubstitute: s           | substitute-selection: s\ndelete-char: x          | delete-selection: x, dd, D\ndelete-line: dd         | yank: y\ndelete-end-line: D      | general-mode: Esc\npaste: p                | visual-line-mode: V\ninsert-mode: i          |\nvisual-mode: v          | --- Visual Line Mode ---\nvisual-line-mode: V     | move-around: j, k\n                        | substitute-selection: s\n--- insert Mode ---     | delete-selection: x, dd, D\ngeneral-mode: Esc       | yank: y\n                        | general-mode: Esc\n                        | visual-mode: v\n26"
		this.visual_anchor = 0;
		this.visual_anchor2 = 0;
		this.visual_cursor = 0;
		this.yank = "";
	}

	pos(){
		return this.editor.selectionStart;
	}

	set_pos(p){
		this.cmd = "";
		this.editor.focus();
		this.editor.setSelectionRange(p,p);
	}

	set_sel(l,r){
		this.cmd = "";
		this.editor.focus();
		this.editor.setSelectionRange(l,r);
	}

	get_all(p){
		const text = this.editor.value;
		const linhas = text.split('\n');
		let prefColumnChars = 0;
		let curColumn = 0;
		let curLine = 0;
		let totalChars = text.length;
		for(let i = 0; i < linhas.length; i++){
			if(prefColumnChars + linhas[i].length >= p){
				curLine = i;
				curColumn = p - prefColumnChars;
				break;
			}
			prefColumnChars += linhas[i].length + 1;;
		}
		return {prefColumnChars, curLine, curColumn,totalChars};
	}

	chmod(m){
		this.cmd = "";
		this.mode = m;
		const linhas = this.editor.value.split('\n');
		const {prefColumnChars, curLine, curColumn,totalChars} = this.get_all(this.pos());
		if(this.mode == "visual"){
			this.visual_anchor = this.pos();
			this.visual_cursor = this.pos();
		}
		if(this.mode == "visual-linha"){
			this.visual_anchor = prefColumnChars;
			this.visual_anchor2 = prefColumnChars + linhas[curLine].length;
			this.visual_cursor = this.pos();
			this.set_sel(this.visual_anchor,this.visual_anchor2);
		}
		if(this.mode == "general") this.set_pos(this.pos());
		console.log(this.mode);
	}
}

export const vim = new Vim();
