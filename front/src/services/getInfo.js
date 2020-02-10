import http from "./http-common"
import store from "../store";
function getinfo() {
  var token = localStorage.getItem("login-token");
  if (token !== null) {
    http
      .post(
        "/info",
        {},
        {
          headers: {
            "login-token": localStorage.getItem("login-token")
          }
        }
      )
      .then(res => {
        // this.username = res.data.data.name;
        console.log("토큰 정보조회 성공");
        localStorage.setItem("member_id", res.data.member_id);
        localStorage.setItem("member_name", res.data.data.name);
        localStorage.setItem("member_email", res.data.data.email);
        localStorage.setItem("member_keyword", res.data.data.keyword);
        localStorage.setItem("member_type", res.data.data.type);
        localStorage.setItem("auth", res.data.data.auth);
        localStorage.setItem("certifiedkey", res.data.data.certifiedkey);

        const payload = {
          token: localStorage.getItem("login-token"),
          member_id: localStorage.getItem("member_id"),
          member_name: localStorage.getItem("member_name"),
          auth: localStorage.getItem("auth"),
          member_keyword: localStorage.getItem("member_keyword"),
          member_news: JSON.parse(localStorage.getItem('member_news'))
        };
        store.dispatch("login", payload);

        // console.log(storage.getItem("member_id"));
        // console.log(storage.getItem("member_email"));
        // console.log(storage.getItem("member_name"));
        // console.log(storage.getItem("member_type"));
      })
      .catch(() => {
        alert("세션이 만료돠었습니다.");
        localStorage.removeItem("login-token");
        localStorage.removeItem("member_id");
        localStorage.removeItem("member_name");
        localStorage.removeItem("member_email");
        localStorage.removeItem("member_keyword")
        localStorage.removeItem("member_news")
        localStorage.removeItem("member_type");
        localStorage.removeItem("loginStatus");
        localStorage.removeItem("auth");
        store.dispatch("logout");
      });
  } else {
    // console.log("토큰이 없을때는 아무것도 안해요~");
  }
}

export default getinfo;