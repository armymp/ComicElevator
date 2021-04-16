<template>
  <div>
    <v-container>
        <v-row  class="semi mb-5 rounded-xl" align="center">
                <v-col  cols="7">
                <v-text-field dark  label="title" v-model="title"

                ></v-text-field>
                </v-col>
                  <v-col  cols="4">
                    <v-text-field  dark label="issue number" v-model="issue"

                ></v-text-field>
                
                    </v-col>
                      <v-col  cols="1"  justify="center">
                            <v-btn @click="search"  color= secondary>Search</v-btn>
                     </v-col>
                    </v-row>
      <v-flex class="d-flex flex-wrap flex-row align-stretch" v-if="searchResults.length > 0 && !isLoading" :key="somekey">
          <v-col cols=3  v-for="comic in searchResults.slice(((12 * this.page) - 12),(this.page * 12))"
            v-bind:key="comic.id">
            <v-sheet class="rounded-xl mx-auto pa-2 ma-3  d-flex flex-column justify-space-between"
           
            max-width="320"
            height="665"
            text-center
            color="rgba(100,0,0,.3)"
            dark
            >
   
   

                <div>        
                <v-img height="450" class="rounded-xl"  v-bind:src='comic.thumbnail.path.concat("." + comic.thumbnail.extension)' lazy-src="..\images\comicblur.png" srcset="" />
                 <v-divider></v-divider>
                  <v-card-title text-center dark class="break">{{comic.title}}</v-card-title >
                </div>
                <div>
                   
                <v-btn rounded width="100%" color=primary disabled v-if="comicInCollection(comic.id)" :class="disabled">already in collection</v-btn>
            <v-btn rounded width="100%"
                    color= secondary
                    dark @click="addComicToCollection(comic)" v-else>add to collection</v-btn>
                </div>
            
            <!-- {{comic.issueNumber}} -->
            <!-- {{comic.id}} -->
            <!-- {{comic.characters}} -->
            <!-- {{comic.description}} -->
            
        
            
          
        
      </v-sheet>
      </v-col>
      </v-flex>
        <div class="loading" v-if="isLoading">
           <v-progress-circular
                indeterminate
            
                color="prime"
                class="my-auto"
                >
                <div><h4>...Searching for results</h4></div>
                </v-progress-circular>
          </div>
      <h2 v-if="searchResults.length === 0 && !isLoading">No search results found</h2>
    </v-container>
    <div class="text-center">
        <v-pagination
        dark
        color= "secondary"
        v-model="page"
        :length="searchResults.length / 12"
        v-if="searchResults.length > 0 && !isLoading"
        ></v-pagination>
        </div>
        <v-snackbar
      v-model="snackbar"
      color= error
    >
      Your Collection is too powerful, upgrade to Comic Elevator Super to increase your collection size
      </v-snackbar>
  </div>
</template>

<script>
import ComicServices from '../services/ComicServices'
import MarvelService from '../services/MarvelService'

export default {
    data() {
        return {
            searchResults: [],
            collection: {

            },
            page: 1,
            somekey:0,
            // collectionid: '',
            comicid: '',
            inCollection: false,
            isLoading: true,
            title:"",
            issue:"",
            snackbar:false,
        }
    },
    created() {
        this.loadSearchData();
         
    },
    methods: {
        loadSearchData(){
        let config = {
        params: {
            title : null,
            issue : null
        }
        }
        if(this.$route.query.title!=""){
            config.params.title = this.$route.query.title;
            this.title = this.$route.query.title;
        }

        if(this.$route.query.issue!=""){
            config.params.issue = this.$route.query.issue;
            this.issue = this.$route.query.issue;
        }
        ComicServices.getAllComicsByCollectionId(this.$route.query.collectionID).then(response => {
             this.collection = response.data;
             this.somekey++;
         });
        MarvelService.getComicList(config).then(response => {
             this.searchResults = response.data.data.results;
             this.searchResults.forEach(filterImg);
             function filterImg(item) {
                 
                if (item.thumbnail.path.includes("image_not")) {
                item.thumbnail.path = "http://i.annihil.us/u/prod/marvel/i/mg/8/f0/4bc5c33cc4139";
            }
             }
             this.isLoading = false;
         });
        },
        search(){
            this.$router.push({ name: "ComicSearch", query: {collectionID: this.$route.query.collectionID, title: this.title, issue: this.issue}});
            this.page=1;
            
            this.isLoading=true;
            this.loadSearchData();
            

        },
        addComicToCollection(comic) {
            if(this.$store.state.user.authorities[0].name!='ROLE_PREMIUM'&&this.collection.comics.length >= 100){
                this.snackbar=true;
                return;
            }
            let imagePath = comic.thumbnail.path;
            if (imagePath.includes("image_not")) {
                imagePath = "http://i.annihil.us/u/prod/marvel/i/mg/8/f0/4bc5c33cc4139"
            }
            let comicDTO = {
                name: comic.title,
                issueNumber: comic.issueNumber,
                publisher: "Marvel",
                creators: [
                    
                ],
                characters: [
                    
                ],
                thumbnailLink: imagePath.concat("." + comic.thumbnail.extension),
                series: comic.series.name,
                marvelID: comic.id
            }
            
            comic.characters.items.forEach(character => {
                comicDTO.characters.push(character.name);
            });
            comic.creators.items.forEach(creator => {
                comicDTO.creators.push(creator.name);
            });
            ComicServices.addComic(this.$route.query.collectionID, comicDTO);
            this.collection.comics.push(comicDTO);
        },
        comicInCollection(comicTitle) {
            let comics = (this.collection.comics);
            // comics = true;
            // let comics = comicTitle;
            // if (comicTitle == 'Spider-Man (2019) #5') {
            //     return true;
            // }
            
            if (comics.find(e => e.marvelID === comicTitle)) {
               return true;
             } else {
                 return false;
             }
        },
    },
    computed: {
        filterItems () {
        return this.searchResults.slice(this.startIndex, this.stopIndex);  // or any condition u want 
    }
    }
}
</script>

<style scoped>

h2 {
    text-align: center;
}
img:hover {
    height: 50px;
    
}
.loading {
    text-align: center;
}
.loading h4 {
    margin-top: 150px;
}
.break {
    font-size: 1rem;
    text-align: center;
    word-break: normal;
}
.semi{
    background: rgba(100,0,0,.3);
}

</style>