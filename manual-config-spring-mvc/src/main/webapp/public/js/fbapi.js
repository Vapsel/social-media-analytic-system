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
}

function login(){
    FB.login(function (loginResponse) {
        if (loginResponse.authResponse) {
            console.log('Welcome!  Fetching your information.... ');
            FB.api('/me?fields=location,hometown,name,likes{name,location},photos{place},work,posts', function (jsonResponse) {
                sendJsonViaAjax(jsonResponse)
            });
        } else {
            console.log('User cancelled login or did not fully authorize.');
        }
    }, {scope: 'user_likes, user_photos, user_about_me, user_posts, public_profile'});
}