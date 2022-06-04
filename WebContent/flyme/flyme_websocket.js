WebSockct = function(id) {
	var me = this;
	var websocket;
	var url = null;
	var urlPath = "/admin/websocket";
	var urlJs = "/admin/sockjs/websocket";
	var host = window.location.host;
	alert(host);
	me.onopen = function(evnt) {
	};
	me.onmessage = function(evnt) {
	};
	me.onerror = function(evnt) {
	};
	me.onclose = function(evnt) {
	};
	var transports = [];
	if (window.location.protocol == 'http:') {
		url = 'ws://' + host + urlPath;
	} else {
		url = 'wss://' + host + urlPath;
	}
	me.init = function() {
		if ('WebSocket' in window) {
			websocket = new WebSocket(url);
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket(url);
		} else {
			websocket = new SockJS(urlJs, undefined, {
				protocols_whitelist : [ 'websocket', 'xdr-streaming', 'xhr-streaming', 'iframe-eventsource', 'iframe-htmlfile', 'xdr-polling', 'xhr-polling', 'iframe-xhr-polling', 'jsonp-polling' ]
			});
		}
		websocket.onopen = function(evnt) {
			me.onopen(evnt);
		};
		websocket.onmessage = function(evnt) {
			me.onmessage(evnt);
		};
		websocket.onerror = function(evnt) {
			me.onerror(evnt);
		};
		websocket.onclose = function(evnt) {
			me.onclose(evnt);
		}
	}
	me.send = function(message) {
		var send = function() {
			socket.send("{'send':1,'take':2,'message':" + message + "}");
		};
		if (websocket.readyState !== 1) {
			websocket.close();
			me.init();
			setTimeout(function() {
				send();
			}, 250);

			websocket.send(message);
		} else {
			send();
		}
	}
	return me;
}
