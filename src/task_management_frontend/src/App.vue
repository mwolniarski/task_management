<template>
  <div id="app" class="container">
    <UsersView v-bind:userList="userList"/>
  </div>
</template>

<script>
import UsersView from './components/UsersView.vue'

export default {
  name: 'App',
  components: {
    UsersView
  },
  data(){
    return{
      usersList: []
    }
  },
  methods:{

  },
  async created(){
    console.log("Creation");
    var list = [];
    await fetch('http://localhost:8080/api/users/getUsers').then(users => {
      console.log(users.body);
    });
    console.log("Created userlist :");
    console.log(list);
  }
}
</script>

<style>

</style>
