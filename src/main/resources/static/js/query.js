var name = "xx"
$('.ui.dropdown')
    .dropdown()
;

function checkValidation(){
    if (document.getElementById("search-by").value === "coordinate") {
        $('.ui.form')
            .form({
                fields: {
                    x: {
                        identifier: 'x',
                        rules: [
                            {
                                type: 'empty',
                                prompt: 'Please enter x coordinate',
                            },
                            {
                                type: 'number',
                                prompt: 'Please enter a valid coordinate',
                            }
                        ]
                    },
                    y: {
                        identifier: 'y',
                        rules: [
                            {
                                type: 'empty',
                                prompt: 'Please enter y coordinate',
                            }
                        ]
                    },
                    z: {
                        identifier: 'z',
                        rules: [
                            {
                                type: 'empty',
                                prompt: 'Please enter z coordinate',
                            }
                        ]
                    },
                }
            })
    }

    if (document.getElementById("search-by").value === "starname") {
        $('.ui.form')
            .form({
                fields: {
                    name: {
                        identifier: 'starname',
                        rules: [
                            {
                                type: 'empty',
                                prompt: 'Please enter star name'
                            }
                        ]
                    }
                }
            });
    }

    if (document.getElementById("query-type").value === "neighbors") {
        $('.ui.form')
            .form('add rule', 'k', {
                        identifier: 'k',
                        rules: [
                            {
                                type: 'empty',
                                prompt: 'Please enter number of stars'
                            }
                        ]
            });
    }
    else if(document.getElementById("query-type").value === "radius") {
        $('.ui.form')
            .form('add rule', 'r', {
                        identifier: 'radius',
                        rules: [
                            {
                                type: 'empty',
                                prompt: 'Please enter radius'
                            }
                        ]
            });
    }
}

function toggleSearchMode(){
    if (document.getElementById("search-by").value === "coordinate") {
        document.querySelector('#input-starname').classList.add("inv");
        document.querySelector('#input-x').classList.remove("inv");
        document.querySelector('#input-y').classList.remove("inv");
        document.querySelector('#input-z').classList.remove("inv");
    }
    else if (document.getElementById("search-by").value === "starname") {
        document.querySelector('#input-x').classList.add("inv");
        document.querySelector('#input-y').classList.add("inv");
        document.querySelector('#input-z').classList.add("inv");
        document.querySelector('#input-starname').classList.remove("inv");
    }
}

function toggleQueryType() {
    if (document.getElementById("query-type").value === "neighbors") {
        document.querySelector('#input-r').classList.add("inv");
        document.querySelector('#input-k').classList.remove("inv");
    }
    else if (document.getElementById("query-type").value === "radius"){
        document.querySelector('#input-k').classList.add("inv");
        document.querySelector('#input-r').classList.remove("inv");
    }
}

function loadFile(){
    $.ajax({
        url : '/upload',
        type : "get",
        data :  $( '#uploadForm').serialize(),
        success : function(data) {
            if (data.substring(0, 5) === "ERROR") {
                errMsg = data.substring(6, data.length);
                document.querySelector('#error-message').innerText = errMsg;
                $('.ui.error.basic.modal')
                    .modal('show')
                ;
            } else {
                document.querySelector('#success-message').innerText = data;
                $('.ui.success.basic.modal')
                    .modal('show')
                ;
            }

        },
    });
    return false; //return false to prevent form auto submission. Used ajax instead.
}

function clearData() {
    $.ajax({
        url : '/clear',
        type : "get",
        success : function(data) {
            document.querySelector('#filepath').value = "";

            $('.ui.clear.basic.modal')
                .modal('show')
            ;

        },
    });
    return false; //return false to prevent form auto submission. Used ajax instead.
}


