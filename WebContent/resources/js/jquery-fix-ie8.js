jQuery.ajaxSetup({
    xhr: function() {
            //return new window.XMLHttpRequest();
            try{
                if(window.ActiveXObject)
                    return new window.ActiveXObject("Microsoft.XMLHTTP");
            } catch(e) { }

            return new window.XMLHttpRequest();
        }
});