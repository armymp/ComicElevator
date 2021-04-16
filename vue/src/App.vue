<template>

  <v-app  class="bg">
    
    <v-app-bar app color = primary height="100" >
      <v-container class="py-0 fill-height" text-center >
        
          <v-avatar  size="85" >
            
          <img 
        src="../src/images/ComicElevatorLogo.png"
        alt="Comic Elevator"
       
      >
          </v-avatar> 
          <h1 dark class="ml-2 bada"  >Comic Elevator</h1>
           
        <v-text-field
          placeholder="Search For Collections"
          class=" mr-5 ml-5 mt-5 "
          filled
          dense
          dark
          v-model="search"
        />
        
        <v-btn dark color=secondary @click="searchForCollection()">Search</v-btn>  
     
      </v-container>
      
      <v-tabs
        fixed-tabs
        class="mr-9"
        color="white white"
        dark
      >
        <v-tab :to="{ name: 'home'}">Home</v-tab>
       
          <v-tab  :to="{ name: 'userCollections', params: {username: this.$store.state.user.username} }" v-if="$store.state.token!=''" > 
            Collection
          </v-tab >
     <v-tab  darken :to="{ name: 'userHome',params: {username: this.$store.state.user.username}}"  v-if="$store.state.token!=''">Profile </v-tab>
      </v-tabs>
      
      <v-btn offset-y v-if="$store.state.token == ''" :to="{ name: 'login'}" color= secondary >LogIn</v-btn>
      <v-menu offset-y v-if="$store.state.token != ''">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color= secondary

            v-bind="attrs"
            v-on="on"
          >
            {{$store.state.user.username}}
          </v-btn>
        </template>
        <v-list color=secondary dark>
         
          
          <v-list-item :to="{ name: 'logout' }">
            <v-list-item-title>Logout</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
     
    </v-app-bar>
   
    <v-main >
       
      <v-container >
         
        <v-sheet
           min-height="85vh"
   class="bg" 
          >
       
          <router-view :key="force"/>
     
        </v-sheet>
           
      </v-container>
    
    </v-main>
    <div class="flist">
    <friends-list :key="this.$store.state.user.id"/>
    </div>"
    <v-footer app :padless="true" >
      <v-card
        flat
        tile
        width="100%"
        color = primary
        class="text-center"
        
      >
      <v-card-text class="white--text">
          {{ new Date().getFullYear() }} — <strong>&copy; Comic Elevator</strong>
          <br>
          <strong >All &copy;Marvel images are registered trademarks of &copy;Disney</strong>
          
        </v-card-text>
        </v-card>
    </v-footer>
  
  </v-app>
  
</template>
<script>
import FriendsList from './views/FriendsList.vue';


export default {
  components: { FriendsList },
  data(){
    return{
      force:0,
      search:""
    }
  },
  unmounted() {
    this.$store.commit("LOGOUT");
    
  },
  methods:{
     searchForCollection() {
            
            //will need a conditional to make sure string isn't empty and will need a .trim()
            this.$router.push({ name: "CollectionSearch", query: {name: this.search}});
            this.search="";
            this.force++;
        },
  }
}
</script>

<style>
  @font-face {
  font-family: "BADA";
  src: url(../src/fonts/BADABB__.TTF) format("truetype");
  }
  
  .bada{
    font-family: "BADA", 'Courier New', Courier, monospace;
    color: #ABB3D8;
  }
  .bg {
  background: url('../src/images/triangles.png') no-repeat center center fixed !important;
  background-size: cover;
  }
  .flist {
    position: sticky;
  }
  

</style>

