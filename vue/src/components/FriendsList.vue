<template >
  <v-container class="friends-container" color = primary v-if="$store.state.token != ''">
      <div @click="expand = !expand">
                <v-card-title class="top-friends-display">Following</v-card-title>
                 </div>
      <v-expand-transition>
      <v-flex>
          <!-- remove this an uncomment code below-->
           <v-sheet dark v-for="friend in this.$store.state.friends"
            v-bind:key="friend.id"
            >
            <router-link :to="{ name: 'userHome',params: {username: friend.username}}">
            <div v-show="expand" class="friends-display">
                
                    <v-card-title class="headline"
                        v-text="friend.username"
                        >
                    </v-card-title>
                
            </div> 
            </router-link>
          </v-sheet> 
          <!-- <v-sheet> -->
              <!-- <v-btn
        class="ma-2 bg"
        color="primary"
        @click="expand = !expand"
      >
        View Friends
      </v-btn> -->
             
              <!-- <div v-show="expand" class="friends-display">
                  <v-card-title class="headline">
                      placeholder1
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder2
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder3
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder4
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder5
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder6
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder7
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder8
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder9
                  </v-card-title>
                   <v-card-title class="headline">
                      placeholder10
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder12
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder13
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder14
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder15
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder16
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder17
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder18
                  </v-card-title>
                  <v-card-title class="headline">
                      placeholder19
                  </v-card-title>
              </div> 
          </v-sheet> -->
      </v-flex>
      </v-expand-transition>
  </v-container>
</template>

<script>
import ComicServices from '../services/ComicServices'
export default {
    data() {
        return {
            expand: false,
            friends: {

            }
        }
    },
    created() {
        ComicServices.getFriendsByUserId(this.$store.state.user.id).then(response => {
           this.friends = response.data;
           this.$store.commit("SET_FRIENDS", response.data);
        });
    },
    computed: {

    },
    
}
</script>

<style scoped>
.friends-display:hover{
     background-color: #00003E;
}
.friends-container {
    margin-bottom: 64px;
    margin-top: 150px;
    width:250px;
    max-height: 320px;
    overflow: auto;
    position: fixed;
    bottom: 0;
    right: .5%;
    z-index: 100 !important;
    font-size: 1rem;
   
    text-align: center;
    opacity: 1;
    border-top-right-radius: 25px;
    border-top-left-radius: 25px;
}
.vflex {
    background-color: #00002E;
}
.top-friends-display {
    color: white;
    background-color: #00002E;
    text-align: center;
    z-index: 100 !important;
    margin-top: -25px;
    margin-bottom: -10px;
    justify-content: center;
}
.friends-display {
    color: rgb(0, 146, 19);
    background-color: #00002E;
    text-align: center;
    z-index: 100 !important;
}
.sticky {
    position: sticky;
    z-index: 100 !important;
}
::-webkit-scrollbar {
    display: none;
}
.headline {
    border: 1px solid black;
    height: 60px;
    justify-content: center;  
    z-index: 100 !important; 
     
}

</style>