import axios from "axios";

export default axios.create({
  // baseURL: "http://localhost:8197/api",
  baseURL: "https://1023d4e8.ngrok.io/",
  headers: {
    "Content-type": "application/json"
  }
});