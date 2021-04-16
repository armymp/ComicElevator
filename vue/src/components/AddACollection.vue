//Add a collection to a user list of collections

<template>

 <v-dialog @input="reset" v-model="dialog" width="500" color="primary">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                class="mx-auto"
                  color= secondary
                  dark
                  v-bind="attrs"
                  v-on="on"
                  @click.stop="error=false"
                >
                  Add Collection
                </v-btn>
              </template>

              <v-card dark color= primary>
                <v-card-title class="headline prime">
                  Add Collection
                </v-card-title>

                <v-card-text >
                    <v-alert
            color="red"
            type="error"
            v-if="error"
          >
            The Collection name may not be blank!
          </v-alert>
                  <v-text-field v-model="collections.name" label="name"></v-text-field>   
                  <v-checkbox
              v-model="collections.private"
              label="private"
              color="secondary"
              
              hide-details
            ></v-checkbox>                       
                </v-card-text>
                <v-divider></v-divider>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  
                  <v-btn  color="green darken-1"
                  dark @click="addCollection()">Save</v-btn>     
                </v-card-actions>
              </v-card>
            </v-dialog>
  
</template>

<script>

import ComicServices from '../services/ComicServices';

export default {
   
    data() {
        return {
            dialog:false,
            error:false,
            collections: {
                userID: this.$store.state.user.id,
                name: "",
                private: false
            }
        };

    },
    methods: {
        reset(){
            this.collections.name = "";
            this.collections.private = false;
            this.error=false;
        },
        addCollection(){
            this.collections.name = this.collections.name.trim();
            if(this.collections.name != "") {
            ComicServices
            .addCollection(this.collections).then(response => {
                if(response.status == 200) {
                    ComicServices.getAllCollections(this.$route.params.username).then(response => {
             this.$store.commit("SET_COLLECTIONS", response.data)
            });
                }
            });
            this.dialog=false;
            this.collections.name = "";
            this.collections.private = false;
            }else{
                this.error=true;
            }


            // .then(response =>{
            //    if(response.status === 200){
            //        this.$router.push("/");
            //    }
            // })
        }
    }

}
</script>

<style>

</style>