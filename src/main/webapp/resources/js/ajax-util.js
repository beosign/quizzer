function onevent(data) {
    var status = data.status; // Can be "begin", "complete" or "success".
    var source = data.source; // The parent HTML DOM element.

    console.log("data: " + data);
    console.log("source: " + status);
    console.log("status: " + source);
    
    switch (status) {
        case "begin": // Before the ajax request is sent.
            // ...
            break;

        case "complete": // After the ajax response is arrived.
            // ...
            break;

        case "success": // After update of HTML DOM based on ajax response.
            // ...
            break;
    }
}

function onerror(data) {
	var status = data.status; // Can be "begin", "complete" or "success".
	var source = data.source; // The parent HTML DOM element.
	
	console.log("data: " + data);
	console.log("source: " + status);
	console.log("status: " + source);
	console.log("errorname: " + data.errorname);
	console.log("errormessage: " + data.errormessage);
	console.log("description: " + data.description);
	
	
	switch (status) {
	case "begin": // Before the ajax request is sent.
		// ...
		break;
		
	case "complete": // After the ajax response is arrived.
		// ...
		break;
		
	case "success": // After update of HTML DOM based on ajax response.
		// ...
		break;
	}
}