(function(e){function t(t){for(var a,s,i=t[0],l=t[1],c=t[2],m=0,d=[];m<i.length;m++)s=i[m],Object.prototype.hasOwnProperty.call(o,s)&&o[s]&&d.push(o[s][0]),o[s]=0;for(a in l)Object.prototype.hasOwnProperty.call(l,a)&&(e[a]=l[a]);u&&u(t);while(d.length)d.shift()();return r.push.apply(r,c||[]),n()}function n(){for(var e,t=0;t<r.length;t++){for(var n=r[t],a=!0,i=1;i<n.length;i++){var l=n[i];0!==o[l]&&(a=!1)}a&&(r.splice(t--,1),e=s(s.s=n[0]))}return e}var a={},o={app:0},r=[];function s(t){if(a[t])return a[t].exports;var n=a[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,s),n.l=!0,n.exports}s.m=e,s.c=a,s.d=function(e,t,n){s.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},s.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},s.t=function(e,t){if(1&t&&(e=s(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(s.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var a in e)s.d(n,a,function(t){return e[t]}.bind(null,a));return n},s.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return s.d(t,"a",t),t},s.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},s.p="/";var i=window["webpackJsonp"]=window["webpackJsonp"]||[],l=i.push.bind(i);i.push=t,i=i.slice();for(var c=0;c<i.length;c++)t(i[c]);var u=l;r.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"034f":function(e,t,n){"use strict";var a=n("8a23"),o=n.n(a);o.a},"0b07":function(e,t,n){},"0e90":function(e,t,n){},1:function(e,t){},"13b5":function(e,t,n){"use strict";var a=n("0b07"),o=n.n(a);o.a},1892:function(e,t,n){},2451:function(e,t,n){},"267e":function(e,t,n){},"3cbe":function(e,t,n){"use strict";var a=n("0e90"),o=n.n(a);o.a},"4bba":function(e,t,n){},"553c":function(e,t,n){"use strict";var a=n("1892"),o=n.n(a);o.a},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var a=n("2b0e"),o=n("f309"),r=(n("bf40"),n("623c")),s=(n("b116"),n("1f54"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-app",[n("Navigation"),n("v-content",{staticClass:"background"},[n("router-view")],1),n("AppFooter")],1)}),i=[],l=(n("5363"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return"Admin"!==e.$route.name?n("nav",[n("v-app-bar",{attrs:{color:"blue lighten-2",dark:"",app:"","clipped-right":e.$vuetify.breakpoint.smAndUp}},[e.$vuetify.breakpoint.smAndUp?n("v-btn",{attrs:{to:"/",text:""}},[n("span",[e._v("New space")])]):n("v-btn",{attrs:{to:"/",icon:""}},[n("v-icon",[e._v("mdi-home")])],1),this.$store.state.auth?n("v-btn",{attrs:{to:"/Admin",text:""}},[n("span",[e._v("Admin")])]):e._e(),n("v-spacer"),n("v-form",{staticStyle:{width:"350px"},on:{submit:function(t){return t.preventDefault(),e.onSubmit(e.searchValue)}}},[n("v-text-field",{attrs:{label:"검색","hide-details":"",clearable:"","append-outer-icon":"mdi-magnify"},on:{"click:append-outer":function(t){return t.stopPropagation(),e.onSubmit(e.searchValue)}},model:{value:e.searchValue,callback:function(t){e.searchValue=t},expression:"searchValue"}})],1),n("v-spacer"),n("v-btn",{staticClass:"ml-3",attrs:{text:""},on:{click:function(t){return t.stopPropagation(),e.changeDrawer()}}},[n("v-icon",[e._v("mdi-menu")])],1)],1),n("v-navigation-drawer",{attrs:{app:"",right:"","disable-resize-watcher":!0,clipped:e.$vuetify.breakpoint.smAndUp},model:{value:e.drawer,callback:function(t){e.drawer=t},expression:"drawer"}},[this.$store.state.token?n("div",[n("v-list-item",[n("v-list-item-content",[n("v-list-item-title",[n("v-btn",{attrs:{text:"",to:"/Profile"}},[e._v(e._s(e.usernmae))])],1)],1),n("v-btn",{attrs:{text:""},on:{click:e.logout}},[e._v("로그아웃")])],1)],1):n("div",[n("v-list-item",[n("v-btn",{attrs:{text:"",to:"/login"}},[e._v("로그인")]),n("v-spacer"),n("v-btn",{attrs:{text:"",to:"/signup"}},[e._v("회원가입")])],1)],1),n("v-divider"),n("v-list",{attrs:{dense:""}},[e._l(e.items,(function(t){return[t.children?n("v-list-group",{key:t.text,attrs:{"prepend-icon":t.model?t.icon:t["icon-alt"],"append-icon":""},scopedSlots:e._u([{key:"activator",fn:function(){return[n("v-list-item-content",[n("v-list-item-title",[e._v(e._s(t.text))])],1),n("v-badge",{attrs:{inline:"",color:"red",content:t.children.length}})]},proxy:!0}],null,!0),model:{value:t.model,callback:function(n){e.$set(t,"model",n)},expression:"item.model"}},e._l(t.children,(function(t,a){return n("v-list-item",{key:a,attrs:{link:""}},[t.icon?n("v-list-item-action",[n("v-icon",[e._v(e._s(t.icon))])],1):e._e(),n("v-list-item-content",[n("v-list-item-title",[e._v(e._s(t.text))])],1)],1)})),1):n("v-list-item",{key:t.text,attrs:{link:""}},[n("v-list-item-action",[n("v-icon",[e._v(e._s(t.icon))])],1),n("v-list-item-content",[n("v-list-item-title",[e._v(e._s(t.text))])],1)],1)]}))],2)],1)],1):e._e()}),c=[],u=n("2f62");a["a"].use(u["a"]);var m=new u["a"].Store({state:{token:null,member_id:null,member_name:null,auth:null,member_keyword:null,error:!1},mutations:{setToken:function(e,t){e.token=t.token,e.auth=t.auth,e.member_id=t.member_id,e.member_name=t.member_name,e.member_keyword=t.member_keyword},setError:function(e){e.error=!0}},actions:{login:function(e,t){e.commit("setToken",t)},logout:function(e){e.commit("setToken",{token:null,member_id:null,member_name:null,auth:null,member_keyword:null})},error:function(e){e.commit("setError")}},modules:{}}),d=n("bc3a"),p=n.n(d),v=p.a.create({baseURL:"http://52.79.249.4:8080/api",headers:{"Content-type":"application/json"}});n("b0c0");function h(){var e=localStorage.getItem("login-token");null!==e?v.post("/info",{},{headers:{"login-token":localStorage.getItem("login-token")}}).then((function(e){console.log("토큰 정보조회 성공"),localStorage.setItem("member_id",e.data.member_id),localStorage.setItem("member_name",e.data.data.name),localStorage.setItem("member_email",e.data.data.email),localStorage.setItem("member_keyword",e.data.data.keyword),localStorage.setItem("member_type",e.data.data.type),localStorage.setItem("auth",e.data.data.auth);var t={token:localStorage.getItem("login-token"),member_id:localStorage.getItem("member_id"),member_name:localStorage.getItem("member_name"),auth:localStorage.getItem("auth"),member_keyword:localStorage.getItem("member_keyword")};m.dispatch("login",t)}))["catch"]((function(){alert("세션이 만료돠었습니다."),localStorage.removeItem("login-token"),localStorage.removeItem("member_id"),localStorage.removeItem("member_name"),localStorage.removeItem("member_email"),localStorage.removeItem("member_keyword"),localStorage.removeItem("member_type"),localStorage.removeItem("loginStatus"),localStorage.removeItem("auth"),m.dispatch("logout")})):console.log("토큰이 없을때는 아무것도 안해요~")}var f=h,g=(n("45fc"),n("8c4f")),b=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("NewsList")],1)},w=[],_=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("transition",{attrs:{name:"list"}},[n("v-container",{staticClass:"main_web"},[n("v-tabs",{attrs:{"background-color":"transparent",grow:""},model:{value:e.tab,callback:function(t){e.tab=t},expression:"tab"}},[n("v-tab",[e._v(" all ")]),n("v-tab",[e._v(" Samsung ")]),n("v-tab",[e._v(" LG ")]),n("v-tab",[e._v(" SK ")])],1),n("v-tabs-items",{model:{value:e.tab,callback:function(t){e.tab=t},expression:"tab"}},[n("v-tab-item",e._l(e.AllList.length,(function(t){return n("div",{key:t,staticClass:"box"},[n("News",{attrs:{news:e.AllList[t-1]}})],1)})),0),n("v-tab-item",e._l(e.Samsunglist.length,(function(t){return n("div",{key:t,staticClass:"box"},[n("News",{attrs:{news:e.Samsunglist[t-1]}})],1)})),0),n("v-tab-item",e._l(e.LGlist.length,(function(t){return n("div",{key:t,staticClass:"box"},[n("News",{attrs:{news:e.LGlist[t-1]}})],1)})),0),n("v-tab-item",e._l(e.SKlist.length,(function(t){return n("div",{key:t,staticClass:"box"},[n("News",{attrs:{news:e.SKlist[t-1]}})],1)})),0)],1)],1)],1)},y=[],k=(n("a9e3"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-container",[n("div",{staticClass:"cardbox",class:e.news.brand},[n("div",{on:{click:function(t){return e.goDetail(e.news.news_id)}}},[n("div",{staticClass:"brand-title"},["LG"===e.news.brand?n("span",[e._v(e._s(e.news.brand)+" Electronics")]):"SK"===e.news.brand?n("span",[e._v(e._s(e.news.brand)+" Hynix")]):n("span",[e._v(e._s(e.news.brand))])]),n("div",{staticClass:"head_font"},[e._v(e._s(e.news.title))]),n("div",{staticClass:"box"},[e._v(" "+e._s(e.news.date)+" ")])]),n("div",{staticClass:"tagfont"},e._l(e.news.keyword.split(" "),(function(t){return n("span",{key:t,staticStyle:{cursor:"pointer"},on:{click:function(n){return e.onClickKeyword(t)}}},[e._v(" #"+e._s(t)+" ")])})),0)])])}),x=[],S={name:"News",data:function(){return{}},props:{news:{type:Object},company:{type:String}},methods:{goDetail:function(e){lt.push({name:"detail",params:{id:e}})},onClickKeyword:function(e){this.$router.push({name:"search",params:{searchValue:e}})["catch"]((function(e){}))}}},I=S,C=(n("13b5"),n("2877")),V=n("6544"),K=n.n(V),$=n("a523"),L=Object(C["a"])(I,k,x,!1,null,null,null),T=L.exports;K()(L,{VContainer:$["a"]});var O={name:"NewsList",props:{limits:{type:Number,default:8},loadMore:{type:Boolean,default:!1}},components:{News:T},data:function(){return{Samsunglist:[],LGlist:[],SKlist:[],AllList:[],tab:null,popup:!0,dialog:!1}},methods:{SamsungRecentNewsList:function(){var e=this;v.get("/getSamsungRecent").then((function(t){return e.Samsunglist=t.data}))["catch"]((function(){e.errored=!0}))},LgRecentNewsList:function(){var e=this;v.get("/getLgRecent").then((function(t){return e.LGlist=t.data}))["catch"]((function(){e.errored=!0}))},SkRecentNewsList:function(){var e=this;v.get("/getSkRecent").then((function(t){return e.SKlist=t.data}))["catch"]((function(){e.errored=!0}))},getAllNewsRecent:function(){var e=this;v.get("/getAllNewsRecent").then((function(t){return e.AllList=t.data}))["catch"]((function(){e.errored=!0}))}},mounted:function(){this.SamsungRecentNewsList(),this.LgRecentNewsList(),this.SkRecentNewsList(),this.getAllNewsRecent()}},j=O,N=(n("3cbe"),n("71a3")),P=n("c671"),A=n("fe57"),R=n("aac8"),E=Object(C["a"])(j,_,y,!1,null,null,null),M=E.exports;K()(E,{VContainer:$["a"],VTab:N["a"],VTabItem:P["a"],VTabs:A["a"],VTabsItems:R["a"]});var F={name:"HomePage",components:{NewsList:M},methods:{},beforeMount:function(){f()}},B=F,G=Object(C["a"])(B,b,w,!1,null,"481e853e",null),D=G.exports,U=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-container",[e.loading?n("v-container",[n("h1",[e._v("로딩중이다!!!!!!!!기다려!!!!!!!!!!!")])]):e._e(),n("transition",{attrs:{name:"list"}},[e.show?n("v-container",{staticClass:"main_web"},[n("h1",[e._v(e._s(e.searchValue)+" 검색결과")]),n("v-tabs",{attrs:{"background-color":"transparent",grow:""},model:{value:e.tab,callback:function(t){e.tab=t},expression:"tab"}},e._l(Object.keys(e.newsObject),(function(t){return n("v-tab",{key:t},[e._v(e._s(t.slice(0,-4)+" ("+e.newsObject[t].length+")"))])})),1),n("v-tabs-items",{model:{value:e.tab,callback:function(t){e.tab=t},expression:"tab"}},e._l(Object.keys(e.newsObject),(function(t){return n("v-tab-item",{key:t},[e.newsObject[t].length>0?n("span",e._l(e.newsObject[t].length>20?20:e.newsObject[t].length,(function(a){return n("div",{key:a,staticClass:"box"},[n("News",{attrs:{news:e.newsObject[t][a-1],company:t}})],1)})),0):n("span",{staticStyle:{"font-size":"40px",position:"absolute",top:"50px",left:"30px"}},[e._v(e._s(t.slice(0,-4)+"는 "+e.searchValue+" 검색 결과가 없네요"))])])})),1)],1):e._e()],1),e.empty?n("v-container",{staticClass:"main_web"},[n("h1",[e._v(e._s(e.searchValue)+" 검색 결과가 없네요. 좀 제대로 검색해보세요")])]):e._e()],1)},z=[],H={components:{News:T},data:function(){return{loading:!1,show:null,empty:null,newsObject:{AllList:[],Samsunglist:[],LGlist:[],SKlist:[]},tab:null,searchValue:this.$route.params.searchValue}},mounted:function(){this.searchNews()},watch:{$route:"searchNews"},methods:{searchNews:function(){var e=this;this.show=null,this.loading=!0,this.empty=null,this.searchValue=this.$route.params.searchValue,v.get("/findNews/".concat(this.$route.params.searchValue)).then((function(t){t.data.some((function(e){return e.length>0}))?(e.loading=!1,e.newsObject.AllList=t.data[0],e.newsObject.Samsunglist=t.data[1],e.newsObject.LGlist=t.data[2],e.newsObject.SKlist=t.data[3],e.show=!0):(e.loading=!1,e.empty=!0)}))["catch"]((function(e){console.log(e)}))}}},W=H,J=(n("c2e3"),Object(C["a"])(W,U,z,!1,null,null,null)),Z=J.exports;K()(J,{VContainer:$["a"],VTab:N["a"],VTabItem:P["a"],VTabs:A["a"],VTabsItems:R["a"]});var X=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("v-container",{staticClass:"newsbody"},[n("div",[n("h1",[e._v(e._s(e.news.title))])]),n("div",{staticClass:"tagfont"},e._l(e.keywords,(function(t){return n("span",{key:t,staticStyle:{cursor:"pointer"},on:{click:function(n){return e.onClickKeyword(t)}}},[e._v("#"+e._s(t))])})),0),n("hr"),n("br"),n("div",{staticClass:"page",domProps:{innerHTML:e._s(e.news.body)}}),n("hr"),n("CommentForm",{on:{"commentCreate-event":e.CommentCreate}}),n("CommentList",{attrs:{comments:e.comments}}),n("v-snackbar",{attrs:{bottom:"",right:"",color:"blue lighten-2 text--white",timeout:e.timeout},model:{value:e.snackbar,callback:function(t){e.snackbar=t},expression:"snackbar"}},[e._v(" "+e._s(e.snackbarInnerText)+" "),n("v-btn",{attrs:{text:""},on:{click:function(t){e.snackbar=!1}}},[e._v(" 닫기 ")])],1)],1)],1)},Y=[],q=(n("ac1f"),n("1276"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",e._l(e.comments,(function(t){return n("div",{key:t.username},[n("v-card",{attrs:{flat:""}},[n("v-card-title",{staticClass:"cardtop"},[e._v(e._s(t.member_name))]),n("v-card-text",{staticClass:"commentdate"},[e._v(" "+e._s(t.comment_date)+" "),t.member_id==e.$store.state.member_id?n("button",{on:{click:function(n){return e.deleteComment(t)}}},[e._v("X")]):e._e()]),n("v-card-text",{staticClass:"commentbody black--text"},[n("strong",[e._v(e._s(t.comment_text))])])],1),n("hr")],1)})),0)}),Q=[],ee=(n("c975"),n("a434"),{name:"comment",props:{comments:{type:Array}},data:function(){return{member_id:0}},methods:{deleteComment:function(e){var t=this,n=confirm("댓글을 삭제하시겠습니까?");if(n){var a=localStorage,o={headers:{"login-token":a.getItem("login-token")}};console.log(a.getItem("login-token")),p.a["delete"]("http://52.79.249.4:8080/api/comment/".concat(e.comment_id),o).then((function(n){var a=t.comments.indexOf(e);a>-1&&t.comments.splice(a,1)}))["catch"]((function(e){console.log(e)}))}}},mounted:function(){var e={token:localStorage.getItem("login-token"),member_id:localStorage.getItem("member_id")};this.$store.dispatch("login",e)}}),te=ee,ne=(n("d38b"),n("b0af")),ae=n("99d9"),oe=Object(C["a"])(te,q,Q,!1,null,null,null),re=oe.exports;K()(oe,{VCard:ne["a"],VCardText:ae["a"],VCardTitle:ae["b"]});var se=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"my-5"},[n("h3",{staticClass:"px-5"},[e._v("토론 게시판")]),this.$store.state.token?n("div",[n("form",{staticClass:"inputcomment",on:{submit:function(t){return t.preventDefault(),e.CommentCreate(t)}}},[n("v-row",{staticClass:"inputbox1"},[n("textarea",{directives:[{name:"model",rawName:"v-model",value:e.text,expression:"text"}],staticClass:"textbox",attrs:{type:"text",id:"text",rows:"5"},domProps:{value:e.text},on:{input:function(t){t.target.composing||(e.text=t.target.value)}}},[e._v(" 댓글을 달아 주세요 ")])]),n("v-row",{staticClass:"inputbox2"},[n("v-btn",{staticClass:"inputbtn",attrs:{depressed:"",type:"submit",text:"",color:"white"}},[n("strong",[e._v("등록")])])],1)],1)]):n("div",{staticClass:"defualtloign"},[n("h1",[e._v("로그인을 해주세요")])]),n("hr")])},ie=[],le={name:"commentForm",data:function(){return{text:""}},methods:{CommentCreate:function(){this.$emit("commentCreate-event",this.text),this.text=""}},beforeMount:function(){f()}},ce=le,ue=(n("a9e2"),n("8336")),me=n("0fd9"),de=Object(C["a"])(ce,se,ie,!1,null,null,null),pe=de.exports;K()(de,{VBtn:ue["a"],VRow:me["a"]});var ve={name:"detail",data:function(){return{news:[],keywords:[],comments:[],snackbar:!1,timeout:2e3,snackbarInnerText:""}},components:{CommentList:re,CommentForm:pe},methods:{CommentCreate:function(e){var t=this;if(""!==e){var n=localStorage,a={news_id:this.$route.params.id,comment_text:e};p.a.post("http://52.79.249.4:8080/api/comment",a,{headers:{"login-token":n.getItem("login-token")}}).then((function(e){t.snackbar=!0,t.snackbarInnerText="댓글이 등록되었습니다.",t.CommentGet()}))["catch"]((function(e){console.log(e)}))}},getNews:function(){var e=this;p.a.get("http://52.79.249.4:8080/api/getNews/".concat(this.$route.params.id)).then((function(t){e.news=t.data,e.keywords=e.news.keyword.split(" ")}))["catch"]((function(e){console.log(e)}))},CommentGet:function(){var e=this;p.a.get("http://52.79.249.4:8080/api/comment/".concat(this.$route.params.id)).then((function(t){e.comments=t.data,console.log(e.comments)}))["catch"]((function(e){console.log(e)}))},onClickKeyword:function(e){this.$router.push({name:"search",params:{searchValue:e}})["catch"]((function(e){}))}},beforeMount:function(){f()},mounted:function(){this.getNews(),this.CommentGet(),console.log(localStorage.getItem("member_id"))}},he=ve,fe=(n("9699"),n("2db4")),ge=Object(C["a"])(he,X,Y,!1,null,"68ad852f",null),be=ge.exports;K()(ge,{VBtn:ue["a"],VContainer:$["a"],VSnackbar:fe["a"]});var we=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-container",[n("v-row",[n("v-col",{attrs:{cols:"6"}},[n("v-card",[n("v-container",{staticStyle:{"min-height":"430px"}},[n("v-card-title",[e._v("회원가입")]),n("v-divider"),n("v-form",{ref:"form",on:{submit:function(t){return t.preventDefault(),e.Signup(t)}},model:{value:e.valid,callback:function(t){e.valid=t},expression:"valid"}},[n("v-text-field",{attrs:{rules:e.nameRules,label:"이름",type:"text"},model:{value:e.name,callback:function(t){e.name=t},expression:"name"}}),n("v-text-field",{attrs:{rules:e.emailRules,label:"이메일",type:"text"},model:{value:e.email,callback:function(t){e.email=t},expression:"email"}}),n("v-text-field",{attrs:{rules:e.passwordRules,label:"비밀번호",type:"password"},model:{value:e.password,callback:function(t){e.password=t},expression:"password"}}),n("v-text-field",{attrs:{rules:e.passwordCheckRules,label:"비밀번호 확인",type:"password"},model:{value:e.passwordCheck,callback:function(t){e.passwordCheck=t},expression:"passwordCheck"}}),n("v-btn",{staticClass:"mt-3 white--text",attrs:{id:"signupBtn",disabled:!e.valid,color:"blue lighten-2",type:"submit"}},[e._v("가입하기")])],1)],1)],1)],1),n("v-col",{attrs:{cols:"6"}},[n("v-card",[n("v-container",{staticStyle:{"min-height":"430px"}},[n("v-card-title",[e._v("관심 키워드 등록")]),n("v-divider"),n("v-card",{staticStyle:{"min-height":"120px"}},[n("span",[e._v("선택된 아해들")]),n("v-divider"),n("v-container",[n("ul",{on:{click:function(t){return t.stopPropagation(),e.unselectKeyword(t)}}},[n("transition-group",{attrs:{name:"list"}},e._l(e.selectedKeywords,(function(t){return n("li",{key:t,staticClass:"text--darken-3"},[e._v(e._s(t))])})),0)],1)])],1),n("hr"),n("v-card",{staticClass:"mt-3",staticStyle:{"min-height":"120px"}},[n("span",[e._v("선택되지 못한 아해들")]),e._v(" "),n("br"),n("v-divider"),n("v-container",[n("ul",{on:{click:function(t){return t.stopPropagation(),e.selectKeyword(t)}}},[n("transition-group",{attrs:{name:"list",id:"selectedSpan"}},e._l(e.unselectedKeywords,(function(t){return n("li",{key:t},[e._v(e._s(t))])})),0)],1)])],1),n("v-text-field",{staticStyle:{"margin-top":"16px"},attrs:{label:"추가하고싶은 키워드","append-icon":"mdi-plus",error:e.error,"error-messages":e.errorMessages},on:{"click:append":e.userInputKeywordToList,keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:(t.stopPropagation(),e.userInputKeywordToList(t))},input:e.inputKeyword},model:{value:e.userInputKeyword,callback:function(t){e.userInputKeyword=t},expression:"userInputKeyword"}})],1)],1)],1)],1)],1)},_e=[],ye=(n("99af"),n("c740"),n("4160"),n("498a"),n("159b"),{name:"SignupPage",data:function(){var e=this;return{valid:!1,name:"",email:"",password:"",passwordCheck:"",nameRules:[function(e){return!!e||"이름을 입력하세요."}],emailRules:[function(e){return!!e||"이메일을 입력하세요."},function(e){return/.+@+./.test(e)||"이메일 형식이 아닙니다."}],passwordRules:[function(e){return!!e||"비밀번호를 입력하세요."}],passwordCheckRules:[function(e){return!!e||"비밀번호 확인을 입력하세요."},function(t){return t===e.password||"비밀번호가 일치하지 않습니다."}],userInputKeyword:"",selectedKeywords:[],unselectedKeywords:[],textColor:"red--text",textColorList:["red--text","purple--text","blue--text","teal--text","lime--text","yellow--text","brown--text","grey--text"],error:!1,errorMessages:"",trySignup:!1}},mounted:function(){this.keywordSetting()},methods:{Signup:function(){var e=this;this.$refs.form.validate()&&(this.selectedKeywords.length>0||this.trySignup?p.a.post("http://52.79.249.4:8080/member/signup",{email:this.email,password:this.password,name:this.name,inputkeyword:this.selectedKeywords,type:"nomal"}).then((function(t){0==t.data?alert("아이디가 중복됩니다."):(alert("가입 성공!!"),e.$router.push("/login"))}))["catch"]((function(e){console.log(e)})):(alert("관심 키워드를 등록하시면 더 많은 정보를 받아보실 수 있습니다."),this.trySignup=!0))},keywordSetting:function(){var e=this;v.get("/getUserKeyword").then((function(t){e.unselectedKeywords=t.data}))},selectKeyword:function(e){if("LI"===e.target.tagName){var t=e.target.innerText,n=this.unselectedKeywords.findIndex((function(e){return e===t}));this.selectedKeywords.findIndex((function(e){return e===t}))<0&&this.selectedKeywords.push(t),this.unselectedKeywords.splice(n,1)}},unselectKeyword:function(e){if("LI"===e.target.tagName){var t=e.target.innerText,n=this.selectedKeywords.findIndex((function(e){return e===t}));this.unselectedKeywords.findIndex((function(e){return e===t}))<0&&this.unselectedKeywords.push(t),this.selectedKeywords.splice(n,1)}},inputKeyword:function(e){e.length&&(this.error=!1,this.errorMessages="")},userInputKeywordToList:function(){var e=this,t="";this.userInputKeyword.length<2?(this.error=!0,this.errorMessages="두글자 이상을 입력해주세요"):(this.userInputKeyword.split(" ").forEach((function(n){e.selectedKeywords.findIndex((function(e){return e===n}))<0&&n.length>1?e.selectedKeywords.push(n):(t=t.concat(" ".concat(n)),console.log(t))})),t=t.trim(),t&&(this.error=!0,this.errorMessages="".concat(t,"은(는) 이미 추가되었거나 너무 짧은 단어입니다."))),this.userInputKeyword=""}}}),ke=ye,xe=(n("f3e9"),n("62ad")),Se=n("ce7e"),Ie=n("4bd4"),Ce=n("8654"),Ve=Object(C["a"])(ke,we,_e,!1,null,"7f240460",null),Ke=Ve.exports;K()(Ve,{VBtn:ue["a"],VCard:ne["a"],VCardTitle:ae["b"],VCol:xe["a"],VContainer:$["a"],VDivider:Se["a"],VForm:Ie["a"],VRow:me["a"],VTextField:Ce["a"]});var $e=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-container",[n("v-row",{staticClass:"justify-center"},[n("v-col",{attrs:{cols:"7"}},[n("v-card",{attrs:{raised:""}},[n("v-alert",{directives:[{name:"show",rawName:"v-show",value:this.$store.state.error,expression:"this.$store.state.error"}],attrs:{color:"error",icon:"warning"}},[e._v("로그인 정보를 확인해주세요")]),n("v-card-title",[e._v("로그인")]),n("v-container",[n("v-form",{ref:"loginForm",on:{submit:function(t){return t.preventDefault(),e.login(t)}},model:{value:e.loginValid,callback:function(t){e.loginValid=t},expression:"loginValid"}},[n("v-container",[n("v-text-field",{attrs:{label:"이메일",type:"email",rules:e.emailRules},model:{value:e.email,callback:function(t){e.email=t},expression:"email"}}),n("v-text-field",{attrs:{label:"비밀번호",type:"password",rules:e.passwordRules},model:{value:e.password,callback:function(t){e.password=t},expression:"password"}}),n("v-btn",{staticClass:"white--text mt-3",attrs:{disabled:!e.loginValid,color:"blue lighten-2",type:"submit"},on:{click:e.login}},[e._v("로그인")])],1)],1),n("v-divider")],1),n("v-subheader",[e._v("소셜 로그인")]),n("v-container",{attrs:{"d-flex":"","justify-space-around":""}},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.socialemail,expression:"socialemail"}],attrs:{type:"hidden"},domProps:{value:e.socialemail},on:{input:function(t){t.target.composing||(e.socialemail=t.target.value)}}}),n("v-btn",{attrs:{dark:"",color:"indigo darken-2"},on:{click:e.FacebookLogin}},[e._v("Facebook 로그인")]),n("v-btn",{attrs:{dark:"",color:"red darken-2"},on:{click:e.GoogleLogin}},[e._v("Google 로그인")])],1)],1)],1)],1)],1)},Le=[],Te=(n("d3b7"),n("59ca")),Oe=n.n(Te),je=(n("ea7b"),{apiKey:"AIzaSyCmNi_cE0oKL_pZHbSD4ot8f-sjIJp8ZMc",authDomain:"ssafydb-780f1.firebaseapp.com",databaseURL:"https://ssafydb-780f1.firebaseio.com",projectId:"ssafydb-780f1",storageBucket:"ssafydb-780f1.appspot.com",messagingSenderId:"905531341249",appId:"1:905531341249:web:9a2349b97422991f44d740",measurementId:"G-CPB8S2P52F"}),Ne=(Oe.a.initializeApp(je),{name:"login",computed:{error:function(){return this.$store.state.error}},data:function(){return{type:"",email:"",password:"",username:"",socialemail:"",duplicationflag:0,loginValid:!1,infoValid:!1,emailRules:[function(e){return!!e||"이메일을 입력하세요"},function(e){return!!/.+@+./.test(e)||"이메일 형식이 아닙니다."}],passwordRules:[function(e){return!!e||"비밀번호를 입력하세요"}],emptyRules:[function(e){return!!e||"값을 입력해주세요"}]}},methods:{login:function(){var e=this;this.$refs.loginForm.validate()&&p.a.post("http://52.79.249.4:8080/member/signin",{email:this.email,password:this.password,type:"nomal",tokenname:"login-token"}).then((function(t){if(t.data.status){localStorage.setItem("login-token",t.headers["login-token"]),localStorage.setItem("loginStatus",t.data.name);var n={token:localStorage.getItem("login-token"),member_id:"",member_name:t.data.member_name,auth:localStorage.removeItem("auth"),member_keyword:t.data.member_keyword};console.log(n),e.$store.dispatch("login",n),e.$router.push("/",(function(){}))}else e.$store.dispatch("error")}))["catch"]((function(t){e.$store.dispatch("error"),console.log(t)}))},FacebookLogin:function(){var e=this,t=new Oe.a.auth.FacebookAuthProvider,n=this,a=function(){return new Promise((function(e,a){Oe.a.auth().signInWithPopup(t).then((function(t){n.username=t.user.displayName,n.socialemail=t.user.uid,n.type="facebook",e("ㄲ")}))["catch"]((function(e){console.log(e),a(e)}))}))};a().then((function(){e.duplicationCheck()}))},GoogleLogin:function(){var e=this,t=new Oe.a.auth.GoogleAuthProvider,n=this,a=function(){return new Promise((function(e,a){Oe.a.auth().signInWithPopup(t).then((function(t){console.log(t.user),n.username=t.user.displayName,n.socialemail=t.user.uid,n.type="google",e("ㄲ")}))["catch"]((function(e){console.log(e),a(e)}))}))};a().then((function(){e.duplicationCheck()}))},duplicationCheck:function(){var e=this,t=this,n=function(){return new Promise((function(e){p.a.post("http://52.79.249.4:8080/member/signupcheck",{email:t.socialemail}).then((function(n){console.log(n.data),"Notexist"==n.data?t.duplicationflag=1:t.duplicationflag=0,e("ㄲ")}))}))};n().then((function(){var n=function(){return new Promise((function(e){p.a.post("http://52.79.249.4:8080/member/socialtoken",{email:t.socialemail,name:t.username,type:t.type}).then((function(n){localStorage.setItem("login-token",n.headers["login-token"]),localStorage.setItem("loginStatus",t.username),e("ㄲ")}))}))};n().then((function(){if(1==t.duplicationflag)e.$router.push("/SocialSignup",(function(){}));else{var n={token:localStorage.getItem("login-token"),member_id:"",member_name:localStorage.getItem("loginStatus"),auth:localStorage.removeItem("auth")};e.$store.dispatch("login",n),e.$router.push("/",(function(){}))}}))}))}}}),Pe=Ne,Ae=n("0798"),Re=n("e0c7"),Ee=Object(C["a"])(Pe,$e,Le,!1,null,null,null),Me=Ee.exports;K()(Ee,{VAlert:Ae["a"],VBtn:ue["a"],VCard:ne["a"],VCardTitle:ae["b"],VCol:xe["a"],VContainer:$["a"],VDivider:Se["a"],VForm:Ie["a"],VRow:me["a"],VSubheader:Re["a"],VTextField:Ce["a"]});var Fe=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-container",[n("v-row",{attrs:{align:"center",justify:"center"}},[n("v-col",{attrs:{cols:"12",sm:"12",md:"4"}},[n("v-card",[n("v-container",{staticStyle:{"min-height":"430px"}},[n("v-card-title",[e._v("관심 키워드 등록")]),n("v-divider"),n("v-card",{staticStyle:{"min-height":"120px"}},[n("span",[e._v("선택된 아해들")]),n("v-divider"),n("v-container",[n("ul",{on:{click:function(t){return t.stopPropagation(),e.unselectKeyword(t)}}},[n("transition-group",{attrs:{name:"list"}},e._l(e.selectedKeywords,(function(t){return n("li",{key:t,staticClass:"text--darken-3"},[e._v(e._s(t))])})),0)],1)])],1),n("hr"),n("v-card",{staticClass:"mt-3",staticStyle:{"min-height":"120px"}},[n("span",[e._v("선택되지 못한 아해들")]),n("br"),n("v-divider"),n("v-container",[n("ul",{on:{click:function(t){return t.stopPropagation(),e.selectKeyword(t)}}},[n("transition-group",{attrs:{name:"list",id:"selectedSpan"}},e._l(e.unselectedKeywords,(function(t){return n("li",{key:t},[e._v(e._s(t))])})),0)],1)])],1),n("v-text-field",{staticStyle:{"margin-top":"16px"},attrs:{label:"추가하고싶은 키워드","append-icon":"mdi-plus",error:e.error,"₩":"","error-messages":e.errorMessages},on:{"click:append":e.userInputKeywordToList,keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:(t.stopPropagation(),e.userInputKeywordToList(t))},input:e.inputKeyword},model:{value:e.userInputKeyword,callback:function(t){e.userInputKeyword=t},expression:"userInputKeyword"}}),n("v-btn",{staticClass:"mt-3 white--text",attrs:{id:"signupBtn",color:"blue lighten-2"},on:{click:e.Signup}},[e._v("가입하기")])],1)],1)],1)],1)],1)},Be=[],Ge={name:"SocialSignpPage",data:function(){return{userInputKeyword:"",selectedKeywords:[],unselectedKeywords:[],error:!1,errorMessages:"",trySignup:!1}},methods:{Signup:function(){var e=this;this.selectedKeywords.length>0||this.trySignup?axios.post("http://52.79.249.4:8080/member/signup",{email:localStorage.getItem("member_email"),name:localStorage.getItem("member_name"),inputkeyword:this.selectedKeywords,type:localStorage.getItem("member_type")}).then((function(){alert("가입성공!!"),e.logintoken()}))["catch"]((function(e){console.log(e)})):(alert("관심 키워드를 등록하시면 더 많은 정보를 받아보실 수 있습니다."),this.trySignup=!0)},logintoken:function(){var e=this,t=this,n=function(){return new Promise((function(e){axios.post("http://52.79.249.4:8080/member/socialtoken",{email:localStorage.getItem("member_email"),name:localStorage.getItem("member_email"),type:localStorage.getItem("member_type")}).then((function(t){localStorage.setItem("login-token",t.headers["login-token"]),e("ㄲ")}))}))};n().then((function(){localStorage.setItem("loginStatus",t.name);var n={token:localStorage.getItem("login-token"),member_id:"",member_name:localStorage.getItem("member_name")};e.$store.dispatch("login",n),e.$router.push("/",(function(){}))}))},keywordSetting:function(){var e=this;v.get("/getUserKeyword").then((function(t){e.unselectedKeywords=t.data}))},selectKeyword:function(e){if("LI"===e.target.tagName){var t=e.target.innerText,n=this.unselectedKeywords.findIndex((function(e){return e===t}));this.selectedKeywords.findIndex((function(e){return e===t}))<0&&this.selectedKeywords.push(t),this.unselectedKeywords.splice(n,1)}},unselectKeyword:function(e){if("LI"===e.target.tagName){var t=e.target.innerText,n=this.selectedKeywords.findIndex((function(e){return e===t}));this.unselectedKeywords.findIndex((function(e){return e===t}))<0&&this.unselectedKeywords.push(t),this.selectedKeywords.splice(n,1)}},inputKeyword:function(e){e.length&&(this.error=!1,this.errorMessages="")},userInputKeywordToList:function(){var e=this,t="";this.userInputKeyword.length<2?(this.error=!0,this.errorMessages="두글자 이상을 입력해주세요"):(this.userInputKeyword.split(" ").forEach((function(n){e.selectedKeywords.findIndex((function(e){return e===n}))<0&&n.length>1?e.selectedKeywords.push(n):t=t.concat(" ".concat(n))})),t=t.trim(),t&&(this.error=!0,this.errorMessages="".concat(t,"은(는) 이미 추가되었거나 너무 짧은 단어입니다."))),this.userInputKeyword=""}},mounted:function(){this.keywordSetting()},beforeMount:function(){f()}},De=Ge,Ue=(n("553c"),Object(C["a"])(De,Fe,Be,!1,null,null,null)),ze=Ue.exports;K()(Ue,{VBtn:ue["a"],VCard:ne["a"],VCardTitle:ae["b"],VCol:xe["a"],VContainer:$["a"],VDivider:Se["a"],VRow:me["a"],VTextField:Ce["a"]});var He=function(){var e=this,t=e.$createElement,n=e._self._c||t;return null!==e.user?n("div",[n("v-container",[n("div",{staticClass:"header_box"},[n("div",{staticClass:"header_name"},[e._v(' "'+e._s(e.user.member.name)+'"님 환영합니다. ')]),n("div",{staticClass:"header_email"},[e._v(" "+e._s(e.user.member.email)+" ")])]),n("div",{staticClass:"comment_font"},[n("div",[n("h1",[e._v(" "+e._s(e.user.count)+" ")])]),n("div",[n("p",[e._v("comments")])])]),n("v-card",[n("v-card-title",[e._v(' "'+e._s(e.user.member.name)+'"님이 선택한 키워드 ')]),n("v-card-text",e._l(e.user.member.keyword.split(" "),(function(t){return n("span",{key:t},[e._v(" #"+e._s(t)+" ")])})),0)],1)],1),n("v-container",[n("div",{staticClass:"body_box"},[n("div",{staticClass:"newsbody"},[e._v(" "+e._s(e.user.member.name)+"'s NEWS ROOM ")])])])],1):n("div")},We=[],Je={name:"Profile",data:function(){return{user:null}},methods:{get_user:function(){var e=this,t={headers:{"login-token":localStorage.getItem("login-token")}};v.post("/profile/",{member_id:this.$store.state.member_id},t).then((function(t){e.user=t.data,console.log(e.user)}))["catch"]((function(e){console.log(e)}))}},mounted:function(){this.get_user()}},Ze=Je,Xe=(n("ab5e"),Object(C["a"])(Ze,He,We,!1,null,"96ab7eec",null)),Ye=Xe.exports;K()(Xe,{VCard:ne["a"],VCardText:ae["a"],VCardTitle:ae["b"],VContainer:$["a"]});var qe=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div")},Qe=[],et={},tt=et,nt=Object(C["a"])(tt,qe,Qe,!1,null,null,null),at=nt.exports;a["a"].use(g["a"]);var ot=[{path:"/",name:"home",component:D},{path:"/search/:searchValue",name:"search",component:Z},{path:"/detail/:id",name:"detail",component:be,meta:{scrollToTop:!0}},{path:"/signup",name:"signup",component:Ke,meta:{scrollToTop:!0,needBlockAuthUser:!0}},{path:"/login",name:"login",component:Me,meta:{scrollToTop:!0,needBlockAuthUser:!0}},{path:"/SocialSignup",name:"SocialSignup",component:ze,meta:{scrollToTop:!0,needBlockAuthUser:!0}},{path:"/Profile",name:"Profile",component:Ye},{path:"/Admin",name:"Admin",component:at}],rt=function(e,t,n){return n?(console.log("savedPosition",n),n):null===n?(console.log("null일때!!"),{x:0,y:0}):void 0},st=new g["a"]({mode:"history",routes:ot,scrollBehavior:rt,base:"/"});function it(e,t,n){null!==localStorage.getItem("loginStatus")&&null!==localStorage.getItem("login-token")?n("/"):n()}st.beforeEach((function(e,t,n){e.matched.some((function(e){return e.meta.needBlockAuthUser}))?it(e,t,n):n()}));var lt=st,ct={name:"Navigation",props:{},computed:{usernmae:function(){return this.$store.state.member_name}},data:function(){return{searchValue:"",drawer:!1,dialog:!1,items:[{icon:"mdi-history",text:"혹시 게시판을 만들게 된다면"},{icon:"mdi-chevron-up","icon-alt":"mdi-chevron-down",text:"2020.01.10",model:!1,children:[{icon:"mdi-plus",text:"Create label"}]},{icon:"mdi-chevron-up","icon-alt":"mdi-chevron-down",text:"2020.01.23",model:!1,children:[{text:"Import"},{text:"Export"},{text:"Print"},{text:"Undo changes"},{text:"Other contacts"}]}]}},methods:{changeDrawer:function(){this.drawer=!this.drawer},onSubmit:function(e){this.$router.push({name:"search",params:{searchValue:e}})["catch"]((function(e){})),this.searchValue=""},logout:function(){localStorage.removeItem("login-token"),localStorage.removeItem("member_id"),localStorage.removeItem("member_name"),localStorage.removeItem("member_keyword"),localStorage.removeItem("loginStatus"),localStorage.removeItem("auth"),this.auth=0,this.$store.dispatch("logout")},init:function(){if(null!=localStorage.getItem("loginStatus")&&null!=localStorage.getItem("login-token")){var e={token:localStorage.getItem("login-token"),member_id:localStorage.getItem("member_id"),member_name:localStorage.getItem("loginStatus"),auth:localStorage.getItem("auth")};this.$store.dispatch("login",e)}}},beforeMount:function(){f()}},ut=ct,mt=n("40dc"),dt=n("4ca6"),pt=n("132d"),vt=n("8860"),ht=n("56b0"),ft=n("da13"),gt=n("1800"),bt=n("5d23"),wt=n("f774"),_t=n("2fa4"),yt=Object(C["a"])(ut,l,c,!1,null,null,null),kt=yt.exports;K()(yt,{VAppBar:mt["a"],VBadge:dt["a"],VBtn:ue["a"],VDivider:Se["a"],VForm:Ie["a"],VIcon:pt["a"],VList:vt["a"],VListGroup:ht["a"],VListItem:ft["a"],VListItemAction:gt["a"],VListItemContent:bt["a"],VListItemTitle:bt["b"],VNavigationDrawer:wt["a"],VSpacer:_t["a"],VTextField:Ce["a"]});var xt=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-footer",{staticClass:"px-5 py-5",attrs:{dark:"",padless:"",app:"",absolute:""}},[n("span",[e._v("ABOUT US")]),n("v-spacer"),n("span",[n("v-icon",{attrs:{size:"20px"}},[e._v("mdi-alpha-c-circle-outline")]),e._v(" "+e._s((new Date).getFullYear())+" "),n("strong",[e._v("AMT")]),e._v(", Made with "),n("v-icon",{attrs:{size:"24px"}},[e._v("mdi-heart")]),e._v(" for a better Web ")],1)],1)},St=[],It={name:"appfooter"},Ct=It,Vt=n("553a"),Kt=Object(C["a"])(Ct,xt,St,!1,null,null,null),$t=Kt.exports;K()(Kt,{VFooter:Vt["a"],VIcon:pt["a"],VSpacer:_t["a"]});var Lt={name:"App",data:function(){return{}},icons:{iconfont:"mdi"},components:{Navigation:kt,AppFooter:$t}},Tt=Lt,Ot=(n("034f"),n("7496")),jt=n("a75b"),Nt=Object(C["a"])(Tt,s,i,!1,null,null,null),Pt=Nt.exports;K()(Nt,{VApp:Ot["a"],VContent:jt["a"]});var At=n("1881"),Rt=n.n(At);a["a"].config.productionTip=!1,a["a"].use(r["a"]),a["a"].use(o["a"]),a["a"].use(Rt.a,{dynamic:!0}),new a["a"]({router:lt,store:m,vuetify:new o["a"]({theme:{primary:"#3f51b5",secondary:"#b0bec5",accent:"#8c9eff",error:"#b71c1c"}}),render:function(e){return e(Pt)}}).$mount("#app")},"8a23":function(e,t,n){},9699:function(e,t,n){"use strict";var a=n("4bba"),o=n.n(a);o.a},a9e2:function(e,t,n){"use strict";var a=n("ad4e"),o=n.n(a);o.a},ab5e:function(e,t,n){"use strict";var a=n("e82c"),o=n.n(a);o.a},ad4e:function(e,t,n){},c2e3:function(e,t,n){"use strict";var a=n("267e"),o=n.n(a);o.a},d38b:function(e,t,n){"use strict";var a=n("2451"),o=n.n(a);o.a},e82c:function(e,t,n){},ee09:function(e,t,n){},f3e9:function(e,t,n){"use strict";var a=n("ee09"),o=n.n(a);o.a}});
//# sourceMappingURL=app.11b0de94.js.map