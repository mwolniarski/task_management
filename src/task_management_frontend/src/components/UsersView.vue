<template>
  <div>
    <div v-for="user in userList" v-bind:key="user.id" class="card" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">{{user.firstName}}</h5>
            <h6 class="card-subtitle mb-2 text-muted">{{user.lastName}}</h6>
            <h6 class="card-subtitle mb-2 text-muted">{{user.email}}</h6>
            <a v-bind:href="'http://localhost:8080/userEdit/' + user.id" class="btn btn-primary">Edit</a>
        </div>
    </div>
  </div>
</template>

<script>

export default {
    props: ['userList'],
    data(){
        return {
        }
    }
}
</script>

<style>

</style>