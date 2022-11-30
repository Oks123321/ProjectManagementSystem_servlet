<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<script>
    function makePUTrequest(urlParametr) {
        $.ajax({

            url: urlParametr,
            type: 'PUT',
            success: function () {
                location.reload();
            }
        });
    }

    function makeDELETErequest(urlParametr) {
        if (confirm('Are you sure you want to delete this?')) {
            $.ajax({
                url: urlParametr,
                type: 'DELETE',
                success: function () {
                    location.reload();
                }
            });
        }

    }
    var putMethod = ( event ) => {
        event.preventDefault();
        var button = document.querySelectorAll("[name=update_data]");
        var url = button[0].getAttribute("resource")
        var messages = document.querySelectorAll("[class=form-control]");
        var body ={};
        messages.forEach(element=>{
            var name = element.getAttribute("name");
            if ( element.tagName != "TEXTAREA" ) {
                var value = element.value;
            } else {
                // if element is textarea, value attribute may return null or undefined
                var value = element.innerHTML;
            }
            body[name] = value;
        })
        {
            var e = document.querySelectorAll("[class=form-select]")[0];
            if (typeof e !== 'undefined'){
                var name = e.getAttribute("name");
                body[name] = e.value;
            }
        }
        var xhr = new XMLHttpRequest();
        xhr.open( "PUT", url );
        xhr.setRequestHeader( "Content-Type", "application/json; charset=utf-8" );
        xhr.onload = () => {
            if ( xhr.status === 200 ) {
                var button = document.querySelectorAll("[name=update_data]");
                button[0].innerHTML = "Updated!"
            } else {
                console.log( xhr.status, xhr.responseText );
            }
        }
        xhr.send( JSON.stringify(body) );
    };
    document.querySelectorAll( "[name=update_data]" ).forEach( element => {
        element.addEventListener( "click", putMethod);
    })
</script>
