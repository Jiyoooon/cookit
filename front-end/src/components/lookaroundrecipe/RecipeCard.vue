<template>
  <v-card
    max-width="344"
    min-height="344"
    class="mx-auto"
  >

    <v-list-item-title 
      class="headline"
      @click="readRecipe"
      style="cursor:pointer; padding: 3%"
    ><h5><strong>{{ recipe.title }}</strong></h5></v-list-item-title>
    <v-img
        :src='recipe.main_image'
        height="194"
        @click.native="readRecipe"
        style="cursor:pointer"
    ></v-img>
    

    <v-card-text class="maintext">
        <span>{{ recipe.description }}</span>
    </v-card-text>
    <v-list-item>
      <v-list-item-avatar> <img 
        :src='recipe.main_image'></v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-subtitle ><span style="cursor:pointer">by {{ recipe.recipe_user_name}}</span></v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
    <v-card-actions>
      <v-btn
        text
        color="deep-purple accent-4"
      >
        Read
      </v-btn>
      <v-btn
        text
        color="deep-purple accent-4"
      >
        Bookmark
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
    name:'RecipeCard',
    props:{
      recipe:Object,
    },
    methods:{
        readRecipe(){
            alert("레시피읽는화면으로 들어갑니다.( 레시피아이디 : " +this.recipe.recipe_id +" )" )
        }
    },
}
</script>

<style>
    .headline > h5{
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .maintext > span{
        height: 64px;
        overflow: hidden;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical; 
    }
</style>