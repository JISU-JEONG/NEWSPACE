(function() {
    var t, e, n, i, r = {}.hasOwnProperty,
        o = [].slice;
    t = {
        LF: "\n",
        NULL: "\x00"
    };
    n = function() {
        var e;

        function n(t, e, n) {
            this.command = t;
            this.headers = e != null ? e : {};
            this.body = n != null ? n : ""
        }
        n.prototype.toString = function() {
            var e, i, o, s, u;
            e = [this.command];
            o = this.headers["content-length"] === false ? true : false;
            if (o) {
                delete this.headers["content-length"]
            }
            u = this.headers;
            for (i in u) {
                if (!r.call(u, i)) continue;
                s = u[i];
                e.push("" + i + ":" + s)
            }
            if (this.body && !o) {
                e.push("content-length:" + n.sizeOfUTF8(this.body))
            }
            e.push(t.LF + this.body);
            return e.join(t.LF)
        };
        n.sizeOfUTF8 = function(t) {
            if (t) {
                return encodeURI(t).match(/%..|./g).length
            } else {
                return 0
            }
        };
        e = function(e) {
            var i, r, o, s, u, a, c, f, h, l, p, d, g, b, m, v, y;
            s = e.search(RegExp("" + t.LF + t.LF));
            u = e.substring(0, s).split(t.LF);
            o = u.shift();
            a = {};
            d = function(t) {
                return t.replace(/^\s+|\s+$/g, "")
            };
            v = u.reverse();
            for (g = 0, m = v.length; g < m; g++) {
                l = v[g];
                f = l.indexOf(":");
                a[d(l.substring(0, f))] = d(l.substring(f + 1))
            }
            i = "";
            p = s + 2;
            if (a["content-length"]) {
                h = parseInt(a["content-length"]);
                i = ("" + e).substring(p, p + h)
            } else {
                r = null;
                for (c = b = p, y = e.length; p <= y ? b < y : b > y; c = p <= y ? ++b : --b) {
                    r = e.charAt(c);
                    if (r === t.NULL) {
                        break
                    }
                    i += r
                }
            }
            return new n(o, a, i)
        };
        n.unmarshall = function(n) {
            var i, r, o, s;
            r = n.split(RegExp("" + t.NULL + t.LF + "*"));
            s = {
                frames: [],
                partial: ""
            };
            s.frames = function() {
                var t, n, o, s;
                o = r.slice(0, -1);
                s = [];
                for (t = 0, n = o.length; t < n; t++) {
                    i = o[t];
                    s.push(e(i))
                }
                return s
            }();
            o = r.slice(-1)[0];
            if (o === t.LF || o.search(RegExp("" + t.NULL + t.LF + "*$")) !== -1) {
                s.frames.push(e(o))
            } else {
                s.partial = o
            }
            return s
        };
        n.marshall = function(e, i, r) {
            var o;
            o = new n(e, i, r);
            return o.toString() + t.NULL
        };
        return n
    }();
    e = function() {
        var e;

        function r(t) {
            this.ws = t;
            this.ws.binaryType = "arraybuffer";
            this.counter = 0;
            this.connected = false;
            this.heartbeat = {
                outgoing: 1e4,
                incoming: 1e4
            };
            this.maxWebSocketFrameSize = 16 * 1024;
            this.subscriptions = {};
            this.partialData = ""
        }
        r.prototype.debug = function(t) {
            var e;
            return typeof window !== "undefined" && window !== null ? (e = window.console) != null ? e.log(t) : void 0 : void 0
        };
        e = function() {
            if (Date.now) {
                return Date.now()
            } else {
                return (new Date).valueOf
            }
        };
        r.prototype._transmit = function(t, e, i) {
            var r;
            r = n.marshall(t, e, i);
            if (typeof this.debug === "function") {
                // this.debug(">>> " + r)
            }
            while (true) {
                if (r.length > this.maxWebSocketFrameSize) {
                    this.ws.send(r.substring(0, this.maxWebSocketFrameSize));
                    r = r.substring(this.maxWebSocketFrameSize);
                    if (typeof this.debug === "function") {
                        this.debug("remaining = " + r.length)
                    }
                } else {
                    return this.ws.send(r)
                }
            }
        };
        r.prototype._setupHeartbeat = function(n) {
            var r, o, s, u, a, c;
            if ((a = n.version) !== i.VERSIONS.V1_1 && a !== i.VERSIONS.V1_2) {
                return
            }
            c = function() {
                var t, e, i, r;
                i = n["heart-beat"].split(",");
                r = [];
                for (t = 0, e = i.length; t < e; t++) {
                    u = i[t];
                    r.push(parseInt(u))
                }
                return r
            }(), o = c[0], r = c[1];
            if (!(this.heartbeat.outgoing === 0 || r === 0)) {
                s = Math.max(this.heartbeat.outgoing, r);
                if (typeof this.debug === "function") {
                    this.debug("send PING every " + s + "ms")
                }
                this.pinger = i.setInterval(s, function(e) {
                    return function() {
                        e.ws.send(t.LF);
                        return typeof e.debug === "function" ? e.debug(">>> PING") : void 0
                    }
                }(this))
            }
            if (!(this.heartbeat.incoming === 0 || o === 0)) {
                s = Math.max(this.heartbeat.incoming, o);
                if (typeof this.debug === "function") {
                    this.debug("check PONG every " + s + "ms")
                }
                return this.ponger = i.setInterval(s, function(t) {
                    return function() {
                        var n;
                        n = e() - t.serverActivity;
                        if (n > s * 2) {
                            if (typeof t.debug === "function") {
                                t.debug("did not receive server activity for the last " + n + "ms")
                            }
                            return t.ws.close()
                        }
                    }
                }(this))
            }
        };
        r.prototype._parseConnect = function() {
            var t, e, n, i;
            t = 1 <= arguments.length ? o.call(arguments, 0) : [];
            i = {};
            switch (t.length) {
                case 2:
                    i = t[0], e = t[1];
                    break;
                case 3:
                    if (t[1] instanceof Function) {
                        i = t[0], e = t[1], n = t[2]
                    } else {
                        i.login = t[0], i.passcode = t[1], e = t[2]
                    }
                    break;
                case 4:
                    i.login = t[0], i.passcode = t[1], e = t[2], n = t[3];
                    break;
                default:
                    i.login = t[0], i.passcode = t[1], e = t[2], n = t[3], i.host = t[4]
            }
            return [i, e, n]
        };
        r.prototype.connect = function() {
            var r, s, u, a;
            r = 1 <= arguments.length ? o.call(arguments, 0) : [];
            a = this._parseConnect.apply(this, r);
            u = a[0], this.connectCallback = a[1], s = a[2];
            if (typeof this.debug === "function") {
                // this.debug("Opening Web Socket...")
            }
            this.ws.onmessage = function(i) {
                return function(r) {
                    var o, u, a, c, f, h, l, p, d, g, b, m, v;
                    c = typeof ArrayBuffer !== "undefined" && r.data instanceof ArrayBuffer ? (o = new Uint8Array(r.data), typeof i.debug === "function" ? i.debug("--- got data length: " + o.length) : void 0, function() {
                        var t, e, n;
                        n = [];
                        for (t = 0, e = o.length; t < e; t++) {
                            u = o[t];
                            n.push(String.fromCharCode(u))
                        }
                        return n
                    }().join("")) : r.data;
                    i.serverActivity = e();
                    if (c === t.LF) {
                        if (typeof i.debug === "function") {
                            i.debug("<<< PONG")
                        }
                        return
                    }
                    if (typeof i.debug === "function") {
                        // i.debug("<<< " + c)
                    }
                    d = n.unmarshall(i.partialData + c);
                    i.partialData = d.partial;
                    m = d.frames;
                    v = [];
                    for (g = 0, b = m.length; g < b; g++) {
                        f = m[g];
                        switch (f.command) {
                            case "CONNECTED":
                                if (typeof i.debug === "function") {
                                    // i.debug("connected to server " + f.headers.server)
                                }
                                i.connected = true;
                                i._setupHeartbeat(f.headers);
                                v.push(typeof i.connectCallback === "function" ? i.connectCallback(f) : void 0);
                                break;
                            case "MESSAGE":
                                p = f.headers.subscription;
                                l = i.subscriptions[p] || i.onreceive;
                                if (l) {
                                    a = i;
                                    h = f.headers["message-id"];
                                    f.ack = function(t) {
                                        if (t == null) {
                                            t = {}
                                        }
                                        return a.ack(h, p, t)
                                    };
                                    f.nack = function(t) {
                                        if (t == null) {
                                            t = {}
                                        }
                                        return a.nack(h, p, t)
                                    };
                                    v.push(l(f))
                                } else {
                                    v.push(typeof i.debug === "function" ? i.debug("Unhandled received MESSAGE: " + f) : void 0)
                                }
                                break;
                            case "RECEIPT":
                                v.push(typeof i.onreceipt === "function" ? i.onreceipt(f) : void 0);
                                break;
                            case "ERROR":
                                v.push(typeof s === "function" ? s(f) : void 0);
                                break;
                            default:
                                v.push(typeof i.debug === "function" ? i.debug("Unhandled frame: " + f) : void 0)
                        }
                    }
                    return v
                }
            }(this);
            this.ws.onclose = function(t) {
                return function() {
                    var e;
                    e = "Whoops! Lost connection to " + t.ws.url;
                    if (typeof t.debug === "function") {
                        t.debug(e)
                    }
                    t._cleanUp();
                    return typeof s === "function" ? s(e) : void 0
                }
            }(this);
            return this.ws.onopen = function(t) {
                return function() {
                    if (typeof t.debug === "function") {
                        // t.debug("Web Socket Opened...")
                    }
                    u["accept-version"] = i.VERSIONS.supportedVersions();
                    u["heart-beat"] = [t.heartbeat.outgoing, t.heartbeat.incoming].join(",");
                    return t._transmit("CONNECT", u)
                }
            }(this)
        };
        r.prototype.disconnect = function(t, e) {
            if (e == null) {
                e = {}
            }
            this._transmit("DISCONNECT", e);
            this.ws.onclose = null;
            this.ws.close();
            this._cleanUp();
            return typeof t === "function" ? t() : void 0
        };
        r.prototype._cleanUp = function() {
            this.connected = false;
            if (this.pinger) {
                i.clearInterval(this.pinger)
            }
            if (this.ponger) {
                return i.clearInterval(this.ponger)
            }
        };
        r.prototype.send = function(t, e, n) {
            if (e == null) {
                e = {}
            }
            if (n == null) {
                n = ""
            }
            e.destination = t;
            return this._transmit("SEND", e, n)
        };
        r.prototype.subscribe = function(t, e, n) {
            var i;
            if (n == null) {
                n = {}
            }
            if (!n.id) {
                n.id = "sub-" + this.counter++
            }
            n.destination = t;
            this.subscriptions[n.id] = e;
            this._transmit("SUBSCRIBE", n);
            i = this;
            return {
                id: n.id,
                unsubscribe: function() {
                    return i.unsubscribe(n.id)
                }
            }
        };
        r.prototype.unsubscribe = function(t) {
            delete this.subscriptions[t];
            return this._transmit("UNSUBSCRIBE", {
                id: t
            })
        };
        r.prototype.begin = function(t) {
            var e, n;
            n = t || "tx-" + this.counter++;
            this._transmit("BEGIN", {
                transaction: n
            });
            e = this;
            return {
                id: n,
                commit: function() {
                    return e.commit(n)
                },
                abort: function() {
                    return e.abort(n)
                }
            }
        };
        r.prototype.commit = function(t) {
            return this._transmit("COMMIT", {
                transaction: t
            })
        };
        r.prototype.abort = function(t) {
            return this._transmit("ABORT", {
                transaction: t
            })
        };
        r.prototype.ack = function(t, e, n) {
            if (n == null) {
                n = {}
            }
            n["message-id"] = t;
            n.subscription = e;
            return this._transmit("ACK", n)
        };
        r.prototype.nack = function(t, e, n) {
            if (n == null) {
                n = {}
            }
            n["message-id"] = t;
            n.subscription = e;
            return this._transmit("NACK", n)
        };
        return r
    }();
    i = {
        VERSIONS: {
            V1_0: "1.0",
            V1_1: "1.1",
            V1_2: "1.2",
            supportedVersions: function() {
                return "1.1,1.0"
            }
        },
        client: function(t, n) {
            var r, o;
            if (n == null) {
                n = ["v10.stomp", "v11.stomp"]
            }
            r = i.WebSocketClass || WebSocket;
            o = new r(t, n);
            return new e(o)
        },
        over: function(t) {
            return new e(t)
        },
        Frame: n
    };
    if (typeof exports !== "undefined" && exports !== null) {
        exports.Stomp = i
    }
    if (typeof window !== "undefined" && window !== null) {
        i.setInterval = function(t, e) {
            return window.setInterval(e, t)
        };
        i.clearInterval = function(t) {
            return window.clearInterval(t)
        };
        window.Stomp = i
    } else if (!exports) {
        self.Stomp = i
    }
}).call(this);