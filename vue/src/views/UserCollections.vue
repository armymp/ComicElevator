<template>
  <div>
  <user-collections :key="$route.params.username"/>
  <!-- <add-a-collection /> -->
  </div>
</template>

<script>
import UserCollections from '@/components/UserCollections'
// import AddACollection from './AddACollection.vue'

export default {
    components: {
        UserCollections,
        // AddACollection
    }
}
</script>

<style>

</style>