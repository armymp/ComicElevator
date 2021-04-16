<template>

  <v-container >


    <v-card   dark color="transparent" justify-center class="semi mx-auto d-flex flex-column align-center" height="100" flat >
    <h1 class="mx-5" >{{$route.params.username}}
      <v-icon
        color="yellow"
        large
        v-if="this.user!=null&&this.user.authorities[0].name=='ROLE_PREMIUM'"
      >
        mdi-star
      </v-icon> 
     
      </h1>

        <v-card v-if="!isCurrentUser" color="rgba(0,0,0,0)" :elevation=0 dark text-center class="d-flex flex-row justify-center align-center">
          
          <v-btn class="ml-auto mr-5" color= secondary dark :to="{ name: 'userCollections', params: {username: this.$route.params.username} }" >Go to User's Collections</v-btn>
           <v-btn class="mr-auto" color= secondary dark @click="addUser" v-if="!isFollowing">Follow</v-btn>
           <v-btn class="mr-auto" color= secondary dark @click="unfollow" v-if="isFollowing">Unfollow</v-btn>
         </v-card>
    </v-card >



    <v-card color="rgba(0,0,0,.3)" class="rounded-xl pt-4 pl-5" height="75" flat single-line dark 
      ><h2 >
        <span class="mdi mdi-chart-box"></span> Check Out How Your Stats Compare to the Current
        Stats of the Site
      </h2></v-card
    >

  
     
    <v-card color="rgba(0,0,0,.3)" class="mt-5 semi d-flex flex-row align-center justify-space-around rounded-xl"  dark align-center text-center flat>
 
      <v-card color="transparent" class="display-3 d-flex flex-column align-center ma-5  rounded-xl"  text-center width="30vw" v-if="characterData.length>1" flat>
        <h2 class ="ma-auto text-center">{{characterOptions.chart.title}}</h2>
        <GChart type="ColumnChart" :data="characterData" :options="characterOptions" v-if="chartLoaded" style="color:red"/>
      </v-card>
       <v-card  color="rgba(0,0,0,0)"  width="400" height ="600" class="display-3 align-center ma-5 rounded-xl"  v-else>
                 <h2 >Fill a collection with comics</h2>
                 <h2 >to see collection stats!</h2>
                </v-card>
 
      <v-card color="transparent" class="display-3 d-flex flex-column align-center ma-5  rounded-xl"  text-center width="30vw"  v-if="creatorData.length>1" flat>
        <h2  class ="ma-auto text-center">{{creatorOptions.chart.title}}</h2>
        <GChart type="ColumnChart" :data="creatorData" :options="creatorOptions" v-if="chartLoaded2" />
      </v-card>
       <v-card color="rgba(0,0,0,.0)" width="400" height ="600" class="display-3 align-center ma-5  rounded-xl"  v-else>
                 <h2 >Fill a collection with comics</h2>
                 <h2 >to see collection stats!</h2>
                </v-card>
      
    </v-card>

<v-card
      color="rgba(0,0,0,.3)"
      class="mt-5 semi d-flex flex-row align-center justify-space-around rounded-xl"
      dark
      align-center
      text-center
      flat
    >
      <v-card
       color="transparent"
        class="display-3 d-flex flex-column align-center ma-5  rounded-xl"
        dark
        align-center
        text-center
        flat
        width="30vw"
      >
        
          <h2 class ="ma-auto text-center">{{ allCharacterOptions.chart.title }}</h2>
          <GChart
            type="ColumnChart"
            :data="allCharacterData"
            :options="allCharacterOptions"
            v-if="chartLoaded3"
          />
        
      </v-card>

      <v-card
        color="transparent"
        class="display-3 d-flex flex-column align-center ma-5  rounded-xl"
        dark
        align-center
        text-center
        flat
        width="30vw"
      >
        
          <h2 class ="ma-auto text-center">{{ allCreatorOptions.chart.title }}</h2>
          <GChart
            type="ColumnChart"
            :data="allCreatorData"
            :options="allCreatorOptions"
            v-if="chartLoaded4"
          />
        
      </v-card>
    </v-card>



  </v-container>
</template>

<script>
import { GChart } from 'vue-google-charts'
import ComicServices from '../services/ComicServices';


  export default {
    components: {
      GChart
    },
    data() {
      return{
        user: {},
        model: 0,
        chartLoaded:false,
        chartLoaded2:false,
        chartLoaded3: false,
        chartLoaded4: false,
        characterData: [
          ['Comic Name', 'Comics']
        ],
        
        characterOptions: {
            chart: {
              title: "",
            },
            bars: 'vertical', // Required for Material Bar Charts.
            hAxis: { format: 'decimal' ,
             textStyle:{color: '#FFF'}
            },
            vAxis: {  
             textStyle:{color: '#FFF'}
            },
            legend: {
        textStyle: { color: 'white' }
    },
            height: 400,
            width:600,
            colors: ['#1b9e77'],
             backgroundColor: {
        fill: '#AA0000',
        fillOpacity: 0.0
      },
            
          },
        creatorData: [
          ['Creator Name', 'comics']
        ],
        creatorOptions: {
            chart: {
              title: "",
            },
            bars: 'vertical', // Required for Material Bar Charts.
            hAxis: { format: 'decimal' ,
             textStyle:{color: '#FFF'}
            },
            vAxis: {  
             textStyle:{color: '#FFF'}
            },
            legend: {
        textStyle: { color: 'white' }
    },
            height: 400,
            width:600,
            colors: ['#1c9e77'],
            backgroundColor: {
        fill: '#AA0000',
        fillOpacity: 0.0
      },
       
          },
      allCharacterData: [["Comic Name", "Comics"]],
      allCharacterOptions: {
        chart: {
          title: "",
        },
        bars: "vertical", // Required for Material Bar Charts.
        hAxis: { format: 'decimal' ,
             textStyle:{color: '#FFF'}
            },
            vAxis: {  
             textStyle:{color: '#FFF'}
            },
            legend: {
        textStyle: { color: 'white' }
    },
            height: 400,
            width:600,
            colors: ['#1c9e77'],
            backgroundColor: {
        fill: '#AA0000',
        fillOpacity: 0.0
      },
      },
      allCreatorData: [["Creator Name", "comics"]],
      allCreatorOptions: {
        chart: {
          title: "",
        },
        bars: "vertical", // Required for Material Bar Charts.
       hAxis: { format: 'decimal' ,
             textStyle:{color: '#FFF'}
            },
            vAxis: {  
             textStyle:{color: '#FFF'}
            },
            legend: {
        textStyle: { color: 'white' }
    },
            height: 400,
            width:600,
            colors: ['#1c9e77'],
            backgroundColor: {
        fill: '#AA0000',
        fillOpacity: 0.0
      },
      },
          
      }
    
    }, 
    created(){
      ComicServices.getUserByUsername(this.$route.params.username).then(response =>{
          this.user = response.data;
      }),
      ComicServices.getTopCharacterByUser(this.$route.params.username).then(response =>{
        console.log();
        response.data.forEach(element => {
          let arr = [];
          arr.push(element.name);
          arr.push(element.number);
          this.characterData.push(arr);
        });
        this.characterOptions.chart.title = `Top ${this.characterData.length-1} Characters in User's Collections`;
        this.chartLoaded=true;
      });
      ComicServices.getTopCreatorByUser(this.$route.params.username).then(response =>{
        console.log(response.data);
        response.data.forEach(element => {
          let arr = [];
          arr.push(element.name);
          arr.push(element.number);
          this.creatorData.push(arr);
        });
        this.creatorOptions.chart.title = `Top ${this.creatorData.length-1} Creators in User's Collections`;
        this.chartLoaded2=true;
      });
       ComicServices.getAllTopCharacterByUser().then((response) => {
      console.log(response.data);
      response.data.forEach((element) => {
        let arr = [];
        arr.push(element.name);
        arr.push(element.number);
        this.allCharacterData.push(arr);
      });
      this.allCharacterOptions.chart.title = `Top ${
        this.allCharacterData.length - 1
      } Characters Sitewide`;
      this.chartLoaded3 = true;
    });
    ComicServices.getAllTopCreatorByUser().then((response) => {
      console.log(response.data);
      response.data.forEach((element) => {
        let arr = [];
        arr.push(element.name);
        arr.push(element.number);
        this.allCreatorData.push(arr);
      });
      this.allCreatorOptions.chart.title = `Top ${
        this.allCreatorData.length - 1
      } Creators Sitewide`;
      this.chartLoaded4 = true;
    });
    },
    computed: {
        isCurrentUser() {
            return this.$route.params.username === this.$store.state.user.username;
        },
        isFollowing() {
          return this.$store.state.friends.find(e => e.id == this.user.id);
        }
    },
    methods: {
      addUser() {
        let friendDTO = {
          friendID: this.user.id,
          userID: this.$store.state.user.id,
          statusID: 1,
          statusIDDescription: 'follow'
        }
        ComicServices.addFriendByUserId(friendDTO).then(response => {
          if (response.status == 200) {
            ComicServices.getFriendsByUserId(this.$store.state.user.id).then(response => {
            this.$store.commit("SET_FRIENDS", response.data);
          });
          }
        });
        },
        unfollow() {
          ComicServices.unfollowUser(this.user.id).then(response => {
          if (response.status == 200) {
            ComicServices.getFriendsByUserId(this.$store.state.user.id).then(response => {
            this.$store.commit("SET_FRIENDS", response.data);
          });
          }
        })
        }
      }
    

}
</script>

<style>

.semi{
    background: rgba(0,0,100,.3);
}
</style>