<template>
   <v-flex class="d-flex flex-wrap flex-row">
         <v-btn class="mx-auto" :to="{ name: 'userCollections', params: {username: this.username} }" color= secondary dark>Go to {{username}}'s Collections</v-btn>
          <v-btn class="mx-auto"  :to="{ name: 'userHome', params: {username:  this.username} }" color= secondary dark>Go to {{username}}'s Page</v-btn>
    </v-flex >
</template>

<script>
import ComicServices from '../services/ComicServices'
export default {
    props: ["userID"],
    data(){
        return{
            username:""
        }
    },
    created(){
        ComicServices.getUserById(this.userID).then(response=>{
            this.username = response.data.username;
        });
    }
}
</script>

<style>

</style>