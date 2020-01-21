import axios from "axios";

export default axios.create({
  // baseURL: "http://localhost:8197/api",
  baseURL: "https://accounts.google.com/signin/oauth/oauthchooseaccount?client_id=911315308359-sgleomjhn7cv7uoulr4ncmbuloj23cjq.apps.googleusercontent.com&as=QEt_VRM7GMlV8zmbg4Mh1g&destination=http%3A%2F%2Flocalhost%3A8080&approval_state=!ChRKRmJKQjJvRVQ3Vk9pMG9FeHhhQxIfSTFzWnRxN0V6dVlaNEJxcmlYbTVkb25DMm9GcF9CWQ%E2%88%99AJDr988AAAAAXife11THlbeWbdk0-uRNGXxAHsJ_xmS6&oauthgdpr=1&xsrfsig=ChkAeAh8T_r3iv3fwlTzXfr7txcRE6z0pzHdEg5hcHByb3ZhbF9zdGF0ZRILZGVzdGluYXRpb24SBXNvYWN1Eg9vYXV0aHJpc2t5c2NvcGU&flowName=GeneralOAuthFlow",
  headers: {
    "Content-type": "application/json"
  }
});