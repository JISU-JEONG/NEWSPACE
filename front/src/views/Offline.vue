<template>
<v-container>
  <!-- @detected-condition fires when the connectivity status of the device changes -->
  <offline @detected-condition="handleConnectivityChange">
      <p v-if="test" slot="online">It looks like you're online! Here's all the things you can do...</p>
      <p v-else slot="offline">You appear to be offline, that's okay, we can still do things...</p>
  </offline>
  <div v-if="!test">
    
  </div>
  {{test}}
  <v-container>
</template>

<script>
import offline from 'v-offline';

export default {
  data() {
    return {
      test : true
    }
  },
  components: {
    offline,
  },
  

  methods: {
    handleConnectivityChange(status) {
      console.log(status);
      if(status){
        this.test = true;
      }
      else{
        this.test = false;
      }
    }
  }
}
</script>