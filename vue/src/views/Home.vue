<template>
  <v-container flat  mx-0  fill-height  >
    <v-card flat class="transparent mx-auto" px-0 width="60vw"> 
    <v-carousel v-model="model" 
   
    cycle
    class="container">
    <v-carousel-item>
      <v-sheet
        class="transparent"
        height="100%"
        tile
      >
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
          <div class="display-3">
            <v-img 
    src="@/images/marvel_comics_cast.png" class="homepage"></v-img>
          </div>
        </v-row>
      </v-sheet>
    </v-carousel-item>
      <v-carousel-item>
      <v-sheet
        class="transparent"
        height="100%"
        tile
      >
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
          <div class="display-3">
            <v-img 
    src="@/images/captain_america_running.png" class="captain"></v-img>
          </div>
        </v-row>
      </v-sheet>
    </v-carousel-item>
     <v-carousel-item>
      <v-sheet
        class="transparent"
        height="100%"
        tile
      >
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
          <div class="display-3">
            <v-img 
    src="@/images/ironman_flying.png" class="ironman"></v-img>
          </div>
        </v-row>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</v-card>
    <div class="text">
    <h2>Comic Elevator</h2>
    <h4>
      Make your own comic book collections and add your favorite marvel comics to
      them. Manage your comic book collections and see how your stats compare to other users.
    </h4>
    </div>

  </v-container>

</template>

<script>
export default {
  data() {
    return {
      model: 0,
    };
  },
  created() {},
};
</script>
<style>

h2 {
  text-align: center;
  color: whitesmoke;
  font-family: Verdana, Geneva, Tahoma, sans-serif;
}

h4 {
  text-align: center;
  color: whitesmoke;
  font-family: Verdana, Geneva, Tahoma, sans-serif;
  overflow: visible;
}


.ironman {
   max-width:100%;
    max-height:100%;
    vertical-align: middle;
    width: 450px;
    height: 450px;
    line-height: 115px;
    text-align: center;
}

.hide-scrollbar::-webkit-scrollbar {
    display: none;
}

</style>
