rejectOptions = {
    reject: {
    	all: false,
    	msie5: true, msie6: true, msie7: true, msie8: true,
    	opera7: true, opera8: true, opera9: true, opera10: true, 
        safari2: true, safari3: true, safari4: true, 
        mozillaX: false, 
        unknown: true // Everything else  
    },
    browserInfo: { // Settings for which browsers to display  
        firefox: {  
            text: 'Firefox 16', // Text below the icon  
            url: 'http://www.mozilla.com/firefox/' // URL For icon/text link  
        },  
        safari: {  
            text: 'Safari 5',  
            url: 'http://www.apple.com/safari/download/'  
        },  
        opera: {  
            text: 'Opera 12',  
            url: 'http://www.opera.com/download/'  
        },  
        chrome: {  
            text: 'Chrome 22',  
            url: 'http://www.google.com/chrome/'  
        },  
        msie: {  
            text: 'Internet Explorer 9',  
            url: 'http://www.microsoft.com/windows/Internet-explorer/'  
        }
    },
    display: ['firefox','chrome','msie','opera','safari'],
    header: '¿Sabías que tu navegador de Internet está desactualizado?',  
    // Paragraph 1  
    paragraph1: 'Tu navegador está desactualizado, y es muy seguro que no sea compatible '+  
                'con SIMCA 2.0. Aquí hay un listado de navegadores populares y actualizados '+  
                'que puedes usar.',  
    // Paragraph 2  
    paragraph2: 'Haz clic en uno de los iconos para dirigir a los sitios de descarga',  
    close: true, // Allow closing of window  
    // Message displayed below closing link  
    closeMessage: 'Al cerrar esta ventana aceptas que tu experiencia de usuario en SIMCA 2.0 '+  
                    'no va a ser óptima usando un navegador de Internet antiguo.',  
    closeLink: 'Cerrar esta ventana emergente.', // Text for closing link  
    closeURL: '#', // Close URL  
    closeESC: false, // Allow closing of window with esc key  
    
    imagePath: 'resources/images/browsers/' // Path where images are located  
};