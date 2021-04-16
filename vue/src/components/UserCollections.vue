//Display all collections a user has
//if they select on the icon it will take
//them to ComicsList
<template>
<v-container>
    <v-row  justify="center" v-if="isCurrentUser">
  <v-col class="d-flex justify-center mb-5">
      
         <add-a-collection />
         
         
      
    </v-col>
   </v-row>
   <v-row  dark justify="center"  v-if="!isCurrentUser">
  <v-col class="d-flex justify-center mb-5">
      
         <v-card color="rgba(0,0,0,0)" :elevation=0 dark text-center class="d-flex flex-column">
           <h2>{{this.$route.params.username}}'s Collections</h2>
          <v-btn class="mx-auto" color= secondary dark :to="{ name: 'userHome', params: {username: this.$route.params.username} }" >Go to User Profile</v-btn>
         </v-card>
         
      
    </v-col>
   </v-row>
  <v-expansion-panels accordion flat  dark>
            
            <!--added div below and moved v-for and v-bind from v-expansion panel to v-expansion panels
            in order to add a div that would only display elements if the user is signed in or the 
            collection is not private -->
    
    <v-expansion-panel  
           
            
      v-for="collection in filtered"
            v-bind:key="collection.collection_ID"
            class="mb-5"
    >
      
      <v-expansion-panel-header  dark
           
            color="primary" >
            <div class="d-flex justify-left align-center">
              <div v-if="isCurrentUser">
            <v-icon left class="mr-4" v-if="!collection.private">mdi-eye</v-icon>
            <v-icon left class="mr-4" v-if="collection.private">mdi-eye-off</v-icon>
            </div>
        {{collection.name}}
        </div>
      </v-expansion-panel-header>
      <v-expansion-panel-content class="bg">
        <v-flex v-if="isCurrentUser" class="d-flex flex-row align-center">
          <v-expansion-panels class="ma-1">
            <v-expansion-panel>
              <v-expansion-panel-header color="primary">
                <template color="primary" v-slot:default="{ open }">
                  <v-row  no-gutters >
                    <v-col cols="4"  class="addCollection">
                      Add comic
                    </v-col>
                    <v-col
                      cols="8"
                      class="text--secondary"
                    >
                      <v-fade-transition leave-absolute>
                        <span 
                          v-if="open"
                          key="0"
                        >
                          Search the Marvel database for a comic
                        </span>
                       
                      </v-fade-transition>
                    </v-col>
                  </v-row>
                </template>
              </v-expansion-panel-header>
              <v-expansion-panel-content color="primary">
                <v-row>
                <v-col  cols="8">
                <v-text-field label="title" v-model="searchForT"
                ></v-text-field>
                </v-col>
                  <v-col  cols="4">
                    <v-text-field label="issue number" v-model="searchForI"
                ></v-text-field>
                    </v-col>
                    </v-row>
                    <v-btn text @click="searchForComic(collection.collectionID)">Search</v-btn>  
              </v-expansion-panel-content>
            </v-expansion-panel>
             </v-expansion-panels>
          <div class="text-center ma-1">
            <v-dialog  @input="reset"  v-model="dialog" width="500" color="primary">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  color= accent
                  dark
                  v-bind="attrs"
                  v-on="on"
                >
                  Edit
                </v-btn>
              </template>

              <v-card dark color= primary>
                <v-card-title class="headline prime">
                  Edit Collection
                </v-card-title>

                <v-card-text >
                  <v-text-field v-model="rename" label="name"></v-text-field>    
                  <v-checkbox
              v-model="isPrivate"
              label="private"
              color="secondary"
              
              hide-details
            ></v-checkbox>                           
                </v-card-text>
                <v-divider></v-divider>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn  color="green lighten-1"
                  dark @click="updateCollection(collection)">Save</v-btn>     
                </v-card-actions>
              </v-card>
            </v-dialog>
          </div>
            <v-btn color="red lighten-1" dark class="ma-1" @click="deleteCollection(collection.collectionID)">Delete</v-btn>
        </v-flex>
        <comics-list v-bind:collectionID="collection.collectionID" v-bind:userID="collection.userID" :key="collection.collectionID" />
      </v-expansion-panel-content>
    </v-expansion-panel>
    
  </v-expansion-panels>
</v-container>
</template>
<script>
import ComicServices from '../services/ComicServices';
import AddACollection from './AddACollection.vue';
import ComicsList from './ComicsList.vue';
export default {
  components: { AddACollection, ComicsList },
   data() {
       return {
     expand:false,
     error:false,
     createCollection: false,
     collectionid: '',
     searchForI:"",
     searchForT:"",
     rename:"",
     isPrivate:false,
     backdoor:0,
     dialog : false,
       }
   },
    created() {
         ComicServices.getAllCollections(this.$route.params.username).then(response => {
             this.$store.commit("SET_COLLECTIONS", response.data)
         });
         //window.alert(this.collections.length);
    },
    computed: {
    
        collections() {
            return this.$store.state.userCollections;
        },
        isCurrentUser() {
            return this.$route.params.username === this.$store.state.user.username;
        },
        filtered(){
          this.backdoor;
        
          return this.$store.state.userCollections.filter(item=>{
            if(this.isCurrentUser||item.private==false){
                return true;
            }
            return false;
          })
        }
    },
    methods: {
        deleteCollection(collectionId) {
            ComicServices.removeCollection(collectionId).then(response => {
                if(response.status == 200) {
                    ComicServices.getAllCollections(this.$route.params.username).then(response => {
                    this.$store.commit("SET_COLLECTIONS", response.data)
                   
                });
                }
            });
        },
        searchComic(collectionId) {
             this.collectionid = collectionId;
        },
        searchForComic(collectionId) {
            this.collectionid = collectionId;
            //will need a conditional to make sure string isn't empty and will need a .trim()
            this.$router.push({ name: "ComicSearch", query: {collectionID: this.collectionid, title: this.searchForT, issue: this.searchForI}});
        },
        reset(){
this.rename = "";
            this.isPrivate = false;
            this.error=false;
        },
        updateCollection(collection) {
            collection.name = this.rename;
            collection.private = this.isPrivate;
            collection.name = collection.name.trim();
            if(collection.name != "") {
            ComicServices.updateCollection(collection).then(response => {
                if(response.status == 200) {
                    this.changeId = '';
                    this.changeName = false;
                    ComicServices.getAllCollections(this.$route.params.username).then(response => {
             this.$store.commit("SET_COLLECTIONS", response.data)
         });
          this.reset();
                }
            });
            }else{
              this.error=true;
            }
        },
    }
}
</script>
<style scoped>
.container {
  position: relative;
  z-index: 0;
  border:none;
}
.transparent {
  opacity: 0.1;
}
.addCollection {
  width: 100px;
  white-space: nowrap;
}
.collectionDropdown {
  margin-bottom: 10px;
}

</style>