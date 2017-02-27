function combinarCeldas(nombreClase)
{
	$a = $("td."+nombreClase);
	mergeFrom = 0;
	mergeTo = 0;
	$a.each(function(index, value)
	{
		if ($a.eq(index).text() == $a.eq(index+1).text())
		{
			mergeTo++;
		}
		else
		{
			var rowspan = mergeTo - mergeFrom + 1;
			$a.eq(mergeFrom).attr("rowspan", rowspan);
			for (var j = mergeFrom; j < mergeTo; j++)
			{
				$a.eq(j+1).remove();
			}
			mergeFrom = mergeTo+1;
			mergeTo++;
		}
		
	});
}
function combinarCeldasColumnaAux(nombreClase, nombreClaseAux)
{
	$a = $("td."+nombreClase);
	$aux = $("td."+nombreClaseAux);
	mergeFrom = 0;
	mergeTo = 0;
	$a.each(function(index, value)
	{
		if ($a.eq(index).text() == $a.eq(index+1).text() & $aux.eq(index).text() == $aux.eq(index+1).text() )
		{
			mergeTo++;
		}
		else
		{
			var rowspan = mergeTo - mergeFrom + 1;
			if(!$a.eq(mergeFrom).attr("rowspan")>0){
				$a.eq(mergeFrom).attr("rowspan", rowspan);
			}
			for (var j = mergeFrom; j < mergeTo; j++)
			{
				$a.eq(j+1).remove();
			}
			mergeTo++;
			mergeFrom = mergeTo;
		}
		
	});
}

/**
 * Combina las celdas del horario de clases para representar visualmente la duración de las clases
 */
function combinarCeldasHorario(){
	$("span[class|='horas']").each(function(){
		var horas=parseInt($(this).attr("class").split("-")[1]);
		var clase = $(this).parent().attr("class");
		var i=0;
		var fila = $(this).parents("tr"); 
		for(i=2;i<=horas;i++){
			fila = fila.next(); 
			fila.find("td."+clase).remove();
		}
		$(this).parent().attr({rowspan:horas});
	});
	
	var materia=1;
	$(".horarioGrafico span[class|='horas']").each(function(){
		$(this).parent().attr("class","");
		var titulo=$(this).attr("title");
		var claseMateria=false;
		$(".horarioGrafico span[title='"+titulo+"']").each(function(){
			if($(this).parent().attr("class").match(/materia/)){
				claseMateria = $(".horarioGrafico span[title='"+titulo+"']").parent().attr("class");
				return;
			}
		});
		
		var m=claseMateria;
		if(!claseMateria){
			m="materia-"+(materia++);
			$(this).parent().addClass(m);
		}
		else $(this).parent().addClass(claseMateria);
		
		$(".gruposMaterias span[title='"+titulo+"']").parents("tr").attr("class",m);
	});
	
	$(".gruposMaterias .cruzada").parents("tr").attr("class","cruce");
	$(".horarioGrafico .cruzada").parents("td").attr("class","cruce");
	
}

/**
 * Habilita la funcionalidad de selección simple/múltiple sobre las celdas de la  
 * matrícula académica, resaltando el contenido completo de la fila
 */
function seleccionMaterias(){
    $(".mat-materia .ui-icon-check").parents("tr").addClass("ui-state-highlight");
    seleccionGrupos();
}

/**
 * Cambia el estilo visual de la fila del grupo de clase seleccionado según el color que está 
 * dentro del elemento SPAN cuyo atrituto class es "bgcolor"  
 */
function seleccionGrupos(){
	$(".bkcolor2").each(function(){
		var c=$(this).html();
		$(this).parents("div").prev("h3").prepend('<span class="bkcolor" style="background:'+c+'"></span>');
		$(this).next(".ui-datatable").find(".ui-icon-check").parents("tr").css("background",c);
	}).remove();
}

function stickyHorario(){
	var s = $("#sticky-horario");
	$("#canvasHorario,#horarioGrafico").css("width",s.outerWidth()+"px");
	var pos = s.position();	
	var stickermax = $(document).outerHeight() - $("#footer").outerHeight() - s.outerHeight() - 40; //40 value is the total of the top and bottom margin
	var windowpos = $(window).scrollTop();
	if (windowpos >= pos.top && windowpos < stickermax) {
		s.attr("style", ""); //kill absolute positioning
		s.addClass("stick"); //stick it
	} else if (windowpos >= stickermax) {
		s.removeClass(); //un-stick
		s.css({position: "absolute", top: stickermax + "px"}); //set sticker right above the footer
		
	} else {
		s.removeClass(); //top of page
	}
	$(window).scroll(function() {
		var windowpos = $(window).scrollTop();
		if (windowpos >= pos.top && windowpos < stickermax) {
			s.attr("style", ""); //kill absolute positioning
			s.addClass("stick"); //stick it
		} else if (windowpos >= stickermax) {
			s.removeClass(); //un-stick
			s.css({position: "absolute", top: stickermax + "px"}); //set sticker right above the footer
			
		} else {
			s.removeClass(); //top of page
		}
	});
}

function botonEliminarMateria(){
	$("button.btnElimMateria").each(function(){
		var dis=$(this).hasClass("ui-state-disabled");
		
	    var h3=$(this).parents("div").prev("h3");
	    $(".ui-icon",h3).css("display","none");
	    if(dis){
	    	// h3.append('<span class="btnElimMateriaDisabled" title="No se puede quitar esta materia porque es un correquisito de otra materia"></span>');
	    }
	    else{
		   	h3.append('<span class="btnElimMateria" title="Quitar seleccion de esta materia"></span>');
		   	$(".btnElimMateria",h3).attr("onclick",$(this).attr("onclick"));
	    }
	});
};

function ajusteMatricula(){seleccionMaterias();seleccionGrupos();stickyHorario();botonEliminarMateria();}


/**
 * Pone colores en las notas definitivas, parseando el valor a lo correcto
 */
function colorearNotas(nombreClase)
{
	$('.'+nombreClase).each(function(){
		
		if ($(this).hasClass("cancelacion")) $(this).addClass("nota-color").addClass("nota-color-habilita");
		else if ($(this).text()+0 >= 3)
		{
			//$(this).parent().css("background-color", "rgb(185, 223, 187)");
			$(this).addClass("nota-color").addClass("nota-color-gana");
		}
		else if($(this).text()+0 < 3 || $(this).text() == 'N')
		{
			//$(this).parent().css("background-color", "rgb(248, 192, 203)");
			//Esta parte es para poner el trailing zero en las notas definitivas.
			if ($(this).text()+0 < 1) $(this).text("0"+$.trim($(this).text()));
			$(this).addClass("nota-color").addClass("nota-color-pierde");
		}
		else if ($(this).text() == 'A')
		{
			//$(this).parent().css("background-color", "rgb(185, 223, 187)");
			$(this).addClass("nota-color").addClass("nota-color-gana");
		}
		else if ($(this).text() == 'NC' || $(this).text() == 'CA' )
		{
			//$(this).parent().css("background-color", "rgb(185, 223, 187)");
			$(this).addClass("nota-color").addClass("nota-color-habilita");
		}
	});
}

/**
 * Función para poner el cero al principio de cada nota, en caso de no tenerlo.
 */
function trailingZero(nombreClase)
{
	$('.'+nombreClase).each(function()
	{
		if ($(this).text().trim().substring(0,1)=='.') $(this).text("0"+$.trim($(this).text()));
	});
}