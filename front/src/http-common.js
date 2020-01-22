import axios from "axios";

export default axios.create({
  // baseURL: "http://localhost:8197/api",
  baseURL: "https://localhost:8080/",
  headers: {
    "Content-type": "application/json"
  }
});