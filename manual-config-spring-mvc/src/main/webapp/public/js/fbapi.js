window.fbAsyncInit = function () {
    FB.init({
        appId: '744869465672302',
        cookie: true,  // enable cookies to allow the server to access
                       // the session
        xfbml: true,  // parser social plugins on this page
        version: 'v2.8', // use graph api version 2.8
    });

    // Now that we've initialized the JavaScript SDK, we call
    // FB.getLoginStatus().  This function gets the state of the
    // person visiting this page and can return one of three states to
    // the callback you provide.  They can be:
    //
    // 1. Logged into your app ('connected')
    // 2. Logged into Facebook, but not your app ('not_authorized')
    // 3. Not logged into Facebook and can't tell if they are logged into
    //    your app or not.
    //
    // These three cases are handled in the callback function.

//        FB.getLoginStatus(function (response) {
//            statusChangeCallback(response);
//        });

};

// Load the SDK asynchronously
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function getAjaxRequest() {
    try {
        var request = new XMLHttpRequest();
    } catch (e1) {
        try {
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e2) {
            try {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e3) {
                request = false;
            }
        }
    }
    return request;
}

function sendJsonViaAjax(response) {
    var request = getAjaxRequest();
    if (!request) {
        console.log("Ajax request error.");
        return;
    }

    var fbJson = JSON.stringify(response);
    request.open("POST", '/userJson', true);
    request.send(fbJson);
    request.onreadystatechange = fillPreferences;
}

var jsonResponseGlobal;

function login(){
    FB.login(function (loginResponse) {
        if (loginResponse.authResponse) {
            console.log('Welcome!  Fetching your information.... ');
            FB.api('/me?fields=location,hometown,name,likes{name,location,about},photos{place},work', function (jsonResponse) {
            //FB.api('me?fields=likes.limit(5)',
                    jsonResponseGlobal = jsonResponse;
                    receiveLikes(jsonResponse.likes);
                }
            );
        } else {
            console.log('User cancelled login or did not fully authorize.');
        }
    }, {scope: 'user_hometown, user_location, user_likes, user_about_me, user_posts, email, public_profile'});
}

var likes = [];

function receiveLikes(responseLikes) {
    if(responseLikes.data.length > 0){
        likes = likes.concat(responseLikes.data);
        //console.log(responseLikes.paging.next);
    }

    if(responseLikes.paging && responseLikes.paging.next) {
        FB.api(responseLikes.paging.next, // get the next page
            'GET',
            {}, // LEAVE THIS EMPTY
            receiveLikes
        );
    } else {
        jsonResponseGlobal.likes.data = likes;
        sendJsonViaAjax(jsonResponseGlobal);
    }
}

function fillPreferences() {
    if (this.readyState == 3 && this.status == 200) {
        var $body = $('body');
        var responseDTO = JSON.parse(this.response);
        $body.append($('<div/>').append(responseDTO.satAnswer));
        responseDTO.sortedPreferences.forEach(function (value) {
            $body.append($('<div/>').append(value));
        })
    }
}
