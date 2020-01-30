import http from "../http-common";
const storage = localStorage;

function getinfo(){
    var token = storage.getItem("login-token");
      if (token != null) {
        http
          .post(
            "/info",
            {},
            {
              headers: {
                "login-token": storage.getItem("login-token")
              }
            }
          )
          .then(res => {
            // this.username = res.data.data.name;
            storage.setItem("member_id", res.data.member_id);
            storage.setItem("member_name", res.data.data.name);
          })
      } else {
        storage.removeItem("login-token");
        storage.setItem("member_id", "");
        storage.setItem("member_name", "");
        console.log("토큰정보가 없습니다.");
        this.loginStatus = false;
      }
}

export default getinfo;
