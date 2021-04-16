<template>
<v-expansion-panels accordion flat dark :key="$route.query.name">
    <v-expansion-panel class="mb-5 container transparent"
      v-for="collection in collections"
            v-bind:key="collection.collection_ID"
    >
      <v-expansion-panel-header  color="primary">
        {{collection.name}}
      </v-expansion-panel-header>
      <v-expansion-panel-content class="bg">
        <collection-info class="mt-5" v-bind:userID="collection.userID" :key="collection.userID"/>
        <comics-list v-bind:collectionID="collection.collectionID" v-bind:userID="collection.userID" :key="collection.collectionID"/>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
    import ComicServices from '../services/ComicServices';
    import ComicsList from '../components/ComicsList.vue';
    import CollectionInfo from '../components/CollectionInfo';
    
    export default {

      components: { ComicsList, CollectionInfo },
      data() {
      
       return {
         collections :  [],
         }
      },
      created(){
        ComicServices.searchCollections(this.$route.query.name).then(responce => {
          this.collections = responce.data;
        })
      }
    }
</script>
<style >
.container{
  position: relative;
  z-index: 0;
}
</style>