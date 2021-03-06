$(function() {
  'use strict';

  var client;

  function showMessage(msg)
  {
	$('#messages').append('<tr>' +
  	  '<td>' + msg.time + '</td>' +
  	  '<td>' + msg.username + '</td>' +
  	  '<td>' + msg.message + '</td>' +
  	  '</tr>');
  }

  function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $('#from').prop('disabled', connected);
    $('#text').prop('disabled', !connected);
    if (connected) {
      $("#conversation").show();
      $('#text').focus();
    }
    else $("#conversation").hide();
  }

  $("form").on('submit', function (e) {
	   e.preventDefault();
  });

  $('#from').on('blur change keyup', function(ev) {
	   $('#connect').prop('disabled', $(this).val().length == 0 );
  });
  $('#connect,#disconnect,#text').prop('disabled', true);

  $('#connect').click(function() {
  	client = Stomp.over(new SockJS('/chat-websocket'));
  	client.connect({}, function (frame) {
      setConnected(true);
      client.subscribe('/api/showmessage', function (message) {
  	    showMessage(JSON.parse(message.body));
      });
  	});
  });

  $('#disconnect').click(function() {
	  if (client != null) {
	    client.disconnect();
	    setConnected(false);
	  }
    client = null;
  });

  $('#send').click(function() {
	  client.send("/postMessage", {}, JSON.stringify({
	    from: $("#from").val(),
	    text: $('#text').val(),
	  }));
    $('#text').val("");
  });
});
