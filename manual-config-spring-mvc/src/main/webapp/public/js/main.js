$(function () {
    $(".smas-fb-access-token").on('click', function () {
        $.ajax({
            type: "POST",
            url: "/start/processuserdata?text="+FB.getAccessToken(),
//             data: {"text":FB.getAccessToken()},
            // contentType: "application/json; charset=utf-8",
//             dataType: "json",
            success: function(data){
                console.log(+data);
            },
            fail: function(errMsg) {
                console.log(+errMsg);
            }
        });
    });
});


