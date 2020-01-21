<template>
<div>
  <br><br><br>
  <v-btn
      color="primary"
      dark
      @click.stop="dialog = true"
    >
      Open Dialog
    </v-btn>

    <v-dialog
      v-model="dialog"
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">됩니까?</v-card-title>

        <v-card-text>
          Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running.
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            Disagree
          </v-btn>

          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            Agree
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <button v-on:click="login()">Add 1</button>
</div>
</template>

<script>
export default {
  name: 'Login',
  data: () => ({
    dialog: false,
  }),
  methods: {
    login(){
      window.open('', '_self', '');
      window.close();
    }
  }
}
</script>

<style>

</style>