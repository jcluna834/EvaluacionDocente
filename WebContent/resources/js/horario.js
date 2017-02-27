function imprimirHorario(){
    var dataUrl = document.getElementById("canvasHorario").toDataURL();
    w = window.open(); //return;
    self.focus();  
    w.document.open();  
    w.document.write('<html><head></head><body><img src="'+dataUrl+'" alt="" /></body></html>');  
    w.document.close();  
    w.print();  
    w.close(); 
}

  var horario={
    _W:1280,
    _H:720,
    desde:9,
    hasta:18,
    ctx:null,
    altoHeader:50,
    anchoHora:80,
    anchoDia:80,
    horas:14,
    altoFila:40,
    materias:[],
    nombreDiasFull:["Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"],
    nombreDias:["LUN","MAR","MIE","JUE","VIE","SAB","DOM"],
    full:false,
    init:function(full){
      this.full=full;
      document.getElementById("divHorario").innerHTML= '<canvas id="canvasHorario" width="'+this._W+'px" height="'+this._H+'px" style="width:'+(this.full?"640px":"100%")+'"></canvas>';
      this.ctx = document.getElementById("canvasHorario").getContext('2d');
      this.ctx.fillStyle="#fff";
      this.ctx.textBaseline = 'middle';
      this.ctx.fillRect(0,0,this._W,this._H);
      if(this.full) this.anchoHora=108;
      // Estos extremos de horas se ajustarán según los horarios disponibles
      this.desde=11;
      this.hasta=14;
      this.horas=this.hasta-this.desde+1;
      this.anchoDia = (this._W-this.anchoHora)/7;
      this.altoFila = ((this._H-this.altoHeader)/this.horas);
    },
    reset:function(){
      this.ctx.strokeStyle = '#000';
      this.ctx.fillStyle = '#fff';
      this.ctx.font = '18pt Calibri';
      this.ctx.textAlign = 'center';
    },
    dibujarHeader:function(){
      this.ctx.fillStyle = '#356b9c';
      this.ctx.font = '22pt Calibri';
      this.ctx.textAlign = 'center';
      this.ctx.fillText("Hora", this.anchoHora/2, this.altoHeader/2);

      var i;
      for(i=0;i<this.nombreDiasFull.length;i++){
        this.ctx.fillText(this.full?this.nombreDiasFull[i]:this.nombreDias[i], this.anchoHora + (i*this.anchoDia) + (this.anchoDia*1/2), this.altoHeader/2);
      }
    },
    dibujarLineas:function(){
      
      this.ctx.fillStyle = '#e8f3fd';
      this.ctx.strokeStyle = '#356b9c';
      this.ctx.lineWidth=2;
      var i,ancho=this.anchoHora + (7*this.anchoDia);
      this.ctx.fillRect(0,0,ancho,this.altoHeader);// Header bg
      this.ctx.strokeRect(0,0, ancho, this.altoHeader);
      this.ctx.strokeRect(0,0, this.anchoHora,this.altoHeader+(this.horas*this.altoFila));
      
      for(i=0;i<this.horas;i++){
        this.ctx.strokeRect(0,this.altoHeader+(i*this.altoFila), ancho, this.altoFila);
      }
      for(i=0;i<7;i++){
        this.ctx.strokeRect(this.anchoHora+(i*this.anchoDia),0, this.anchoDia, this.altoHeader+(this.horas*this.altoFila));
      }
      
    },
    dibujarHoras:function(){
      var i=1,hora,tam=18;
      this.ctx.fillStyle = '#356b9c';
      this.ctx.font = tam+'pt Calibri';
      this.ctx.textAlign = 'right';
      for(hora=this.desde;hora<=this.hasta;hora++){
    	var _h = (hora%12)==0?"12":hora%12,am=(hora<12)?"am":(hora==12)?" m ":"pm";
    	if(this.full) _h += ":00";
        this.ctx.fillText(_h+" "+am, this.anchoHora -8,this.altoHeader+(i*this.altoFila)-(this.altoFila/2));
        i++;
      }
    },
    dibujar:function(){
      this.horas=this.hasta-this.desde;
      this.anchoDia = (this._W-this.anchoHora)/7;
      this.altoFila = ((this._H-this.altoHeader)/this.horas);
      
      this.dibujarLineas();
      this.dibujarHeader();
      this.dibujarHoras();
      var i;
      for(i=0;i<this.materias.length;i++) this.dibujarMateria(this.materias[i]);
      
      var dataUrl = document.getElementById("canvasHorario").toDataURL();
      $("#imagenhorario input").val(dataUrl);
    },
    dibujarMateria:function(m){
      this.reset();
      this.ctx.textBaseline = 'bottom';
      this.ctx.font = '14pt Calibri';
      var dia = 0;
      for(dia=0;dia<this.nombreDiasFull.length;dia++){if(this.nombreDiasFull[dia]==m.dia)break;}
      
      /* Posición superior izquierda */
      var x=this.anchoHora+(dia*this.anchoDia),y=this.altoHeader + ((m.hora-this.desde)*this.altoFila),altoMateria=this.altoFila*m.duracion;
      
      /* Recuadro de la materia */
      this.ctx.fillStyle=m.color;
      if(m.haycruce)this.ctx.fillStyle="#ebccd1";
      
      this.ctx.fillRect(x+1,y+1,this.anchoDia-2,altoMateria-2);
      
      this.ctx.fillStyle="black";
      if(m.haycruce){
    	  this.ctx.fillStyle="red";
    	  this.ctx.font = 'bold 15pt Calibri';
    	  y+=25;
          this.ctx.fillText("HAY CRUCE", x + (this.anchoDia/2)+2, y);
          this.ctx.fillRect(x+5,y+1,this.anchoDia-10,2);
          
          y+=5;
          this.ctx.font = '14pt Calibri';
      }
      
      var a=this.wrapTituloMateria(m.nombre,x + (this.anchoDia/2)+2,y+25);
      
      this.ctx.font = 'bold 14pt Calibri';
      if(!m.haycruce) this.wrapTituloMateria(m.salon, x + (this.anchoDia/2)+2, a);
    },
    agregarMateria:function(materia){
      if(materia.hora<this.desde)this.desde=materia.hora;
      if((materia.hora+materia.duracion)>this.hasta)this.hasta=(materia.hora+materia.duracion);
      
      this.materias.push(materia);
    },
    wrapTituloMateria:function(titulo, x, y) {
      var words = titulo.split(' ');
      var line = '';

      for(var n = 0; n < words.length; n++) {
        var testLine = line + words[n] + ' ';
        var metrics = this.ctx.measureText(testLine);
        var testWidth = metrics.width;
        if (testWidth > this.anchoDia && n > 0) {
          this.ctx.fillText(line, x, y);
          line = words[n] + ' ';
          y += 20;
        }
        else {
          line = testLine;
        }
      }
      this.ctx.fillText(line, x, y);
      return y+25;
    }
  }; /* End Horario Object */

  var full=false;
  try{full=$(".horario-container").hasClass("horario-full");}catch(err){};
  if(full)$(".horario-print").css("display","block");
  try{
    horario.init(full);
    
    var materias=cargarMaterias();
    for(var i=0;i<materias.length;i++)horario.agregarMateria(materias[i]);
    
    horario.dibujar();

    
  }catch(err){
    console.log(err);
  }