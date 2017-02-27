function keydriver(frm, textbox, evt) {
	var keynum;
	var keychar;
	var numcheck;
	
	if (window.event){ // IE
		keynum = evt.keyCode;				
	}	 
	else if (evt.which) { // Netscape/Firefox/Opera
		keynum = evt.which;
	}

	var tabla = textbox.parentNode.parentNode.parentNode.parentNode;
	var i = textbox.parentNode.parentNode.parentNode.rowIndex;
	var maxrows = tabla.rows.length;

	if (keynum == 38) {//ARRIBA								
		if (i > 1) {
			var sig = tabla.rows[(i - 2)].cells[3].childNodes[0].childNodes[0];
		}
	} else if (keynum == 40) {//ABAJO				
		i = i - 1;
		if (maxrows > (i + 1)) {
			var sig = tabla.rows[(i + 1)].cells[3].childNodes[0].childNodes[0];			
		}
	}
	sig.select();
}

function keydriver2(frm, textbox, evt) {
	
	//Función para sustituir el enter y las flechas de arriba y abajo por el correspondiente elemento siguiente o anterior.
	var keynum = 0;
	
	if (window.event){ // IE
		keynum = evt.keyCode;				
	}	 
	else if (evt.which) { // Netscape/Firefox/Opera
		keynum = evt.which;
	}

	var pos = $('.inputNota').index(textbox);
	
	if (keynum == 38) {//ARRIBA								
		if (pos != 0) {
			var sig = $('.inputNota').eq(pos-1);
		}
	} else if (keynum == 40 || keynum == 13) {//ABAJO				
		if (pos != $('.inputNota').size()) {
			var sig = $('.inputNota').eq(pos+1);
		}
	}
	sig.focus();
}