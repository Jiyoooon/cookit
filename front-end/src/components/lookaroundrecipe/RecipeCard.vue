<template>
  <v-card
    max-width="344"
    class="mx-auto"
  >
    
    <v-list-item-title class="headline"><h5>레시피 제목이 들어가는 자리입니다 테스트테스트</h5></v-list-item-title>

    <v-img
        src="https://cdn.vuetifyjs.com/images/cards/mountain.jpg"
        height="194"
        @click.native="readRecipe"
        style="cursor:pointer"
    ></v-img>

    <v-card-text class="maintext">
        <span>레시피의 본문 내용이 간단하게 들어가는 자리입니다.레시피의 본문 내용이 간단하게 들어가는 자리입니다.레시피의 본문 내용이 간단하게 들어가는 자리입니다.레시피의 본문 내용이 간단하게 들어가는 자리입니다.레시피의 본문 내용이 간단하게 들어가는 자리입니다.</span>
    </v-card-text>
    <v-list-item>
      <v-list-item-avatar> <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTYXlYOj_-LSUnhNxvbn5qSyvYcXWXSqVnefQ&usqp=CAU"></v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-subtitle>by 유저 아이디</v-list-item-subtitle>
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
    methods:{
        readRecipe(){
            alert("레시피읽는화면으로 들어갑니다.")
        }
    }
}
</script>

<style>
    .headline > h5{
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .maintext > span{
        height: 5%;
        overflow: hidden;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical; 
    }
</style>