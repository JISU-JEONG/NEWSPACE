import axios from "axios";

export default axios.create({
  // baseURL: "http://localhost:8197/api",
  baseURL: "http://52.79.249.4:8080/api",
  headers: {
    "Content-type": "application/json"
  }
});
