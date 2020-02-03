import axios from "axios";

export default axios.create({
  // baseURL: "http://localhost:8197/api",
  baseURL: "http://192.168.31.85:8080/api",
  headers: {
    "Content-type": "application/json"
  }
});
