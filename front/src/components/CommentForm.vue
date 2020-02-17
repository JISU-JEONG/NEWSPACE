<template>
    <div class="mt-5">
        <div v-if=this.$store.state.token>
            <form @submit.prevent="CommentCreate" class="inputcomment">
                <v-row class="inputbox1">
                    <textarea
                    v-model="text"
                    type="text" id="text"
                    class="textbox"
                    rows = "3"
                    >
                    </textarea>
                </v-row>
                <v-row class="inputbox2">
                    <v-btn
                    color="rgb(81, 126, 223)"
                    dark
                    class = "inputbtn"
                    depressed 
                    type="submit"
                    >
                        등록
                    </v-btn>
                </v-row>
            </form>
        </div>
        <div v-else class="defualtloign">
            <h1>로그인을 해주세요</h1>
        </div>
    </div>
</template>

<script>
import info from "../services/getInfo"

export default {
    name: 'commentForm',
    data(){
        return{
            text: ''
        }
    },
    methods: {
        CommentCreate() {
            this.$emit('commentCreate-event', this.text)
            this.text = ''
        }
    },
    beforeMount(){
        info();
    }
}
</script>

<style scoped>
.inputcomment{
    padding: 10px 50px 30px;
}
.defualtloign{
    height: 200px;
    text-align: center;
    padding-top: 50px;
    color : gray;
}
.inputbox1{
    border: solid 1px rgb(209, 209, 209);
    background-color: white;
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;
}
.inputbox2{
    border: solid 1px rgb(209, 209, 209);
    background-color: white;
    border-bottom-right-radius: 10px;
    border-bottom-left-radius: 10px;
}
.textbox{
    padding: 10px 10px;
    margin: 10px 10px;
    resize: none;
    width: 100%;
}
.inputbtn{
    margin: 5px;
}
</style>