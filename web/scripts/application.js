$(function () {
    "use strict";

    //    var header = $('#header');
    var content = $('#content');
    //    var input = $('#input');
    //    var status = $('#status');
    //    var myName = false;
    //    var author = null;
    //    var logged = false;
    var socket = $.atmosphere;
    var subSocket;
    var transport = 'websocket';

    // We are now ready to cut the request
    var request = {
        url: "http://localhost:2020/server-test/push?id=dubic",
        contentType : "application/json",
        logLevel : 'debug',
        transport : transport ,
        enableProtocol : true,
        fallbackTransport: 'long-polling'
    };


    request.onOpen = function(response) {
        content.html($('<p>', {
            text: 'Atmosphere connected using ' + response.transport
        }));
        //        input.removeAttr('disabled').focus();
        //        status.text('Choose name:');
        transport = response.transport;
        console.log("response transport is >> "+response.transport)
        subSocket.push("one guy online");
        if (response.transport == "local") {
            subSocket.pushLocal("Name?");
        }
    };

    //    <!-- For demonstration of how you can customize the fallbackTransport using the onTransportFailure function -->
    request.onTransportFailure = function(errorMsg, request) {
        jQuery.atmosphere.info(errorMsg);
        if (window.EventSource) {
            request.fallbackTransport = "sse";
            transport = "see";
        }
        content.html($('<h3>', {
            text: 'Atmosphere Chat. Default transport is WebSocket, fallback is ' + request.fallbackTransport
        }));
    };

    request.onMessage = function (response) {
        console.log("msg pushed is >>> "+response.responseBody);
        content.append('<p>'+response.responseBody+'</p>');
    // We need to be logged first.
    //        if (!myName) return;

    //        var message = response.responseBody;
    //        try {
    //            var json = jQuery.parseJSON(message);
    //        } catch (e) {
    //            console.log('This doesn\'t look like a valid JSON: ', message.data);
    //            return;
    //        }
    //
    ////        input.removeAttr('disabled').focus();
    ////        if (!logged) {
    ////            logged = true;
    ////            status.text(myName + ': ').css('color', 'blue');
    ////        } else {
    //            var me = "author";
    //            var date = typeof(json.time) == 'string' ? parseInt(json.time) : json.time;
    //            addMessage(json.author, json.text, me ? 'blue' : 'black', new Date(date));
    //        }
    };

    request.onClose = function(response) {
        subSocket.push(jQuery.stringifyJSON({
            author: 'author', 
            message: 'disconnecting'
        }));
    //        logged = false;
    };

    request.onError = function(response) {
        content.html($('<p>', {
            text: 'Sorry, but there\'s some problem with your '
        + 'socket or the server is down'
        }));
    };

    subSocket = socket.subscribe(request);

    //    input.keydown(function(e) {
    //        if (e.keyCode === 13) {
    //            var msg = $(this).val();
    //
    //            // First message is always the author's name
    //            if (author == null) {
    //                author = msg;
    //            }
    //
    //            subSocket.push(jQuery.stringifyJSON({ author: author, message: msg }));
    //            $(this).val('');
    //
    //            input.attr('disabled', 'disabled');
    //            if (myName === false) {
    //                myName = msg;
    //            }
    //        }
    //    });

    function addMessage(author, message, color, datetime) {
        content.append('<p><span style="color:' + color + '">' + author + '</span> @ ' +
            + (datetime.getHours() < 10 ? '0' + datetime.getHours() : datetime.getHours()) + ':'
            + (datetime.getMinutes() < 10 ? '0' + datetime.getMinutes() : datetime.getMinutes())
            + ': ' + message + '</p>');
    }
});
