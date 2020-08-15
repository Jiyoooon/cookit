<template>
    <div>
        <b-container >
           <b-row align-v="center">
            <b-col md="9" sm="8" cols="8">
                <b-form-input
                    type="text"
                    v-model="searchtext"
                    @keydown.enter="searchRecipe"
                    placeholder="레시피 제목 검색"
                    single-line
                />
            </b-col>
            <b-col md="3" sm="4" cols="4">
                <div class="block-btn btn-style1" @click="searchRecipe">검색</div>
            </b-col>
            </b-row>
        </b-container>
    </div>
    
</template>

<script>
import { mapActions, mapState } from 'vuex'
  export default {
    name: 'BlogSearchBar',
    data () {
      return {
        searchtext:'',
      }
    },
    methods: {
        searchRecipe(){//버튼을 눌렀을때,
            console.log(this.searchtext)
            this.setRecipequery2({query:this.searchtext, current:this.currentshow})
            this.searchtext=''
            setTimeout(() => {
                // this.fortest()
            },1000);
        },
        fortest(){
            this.$router.go(0)
        },
        ...mapActions('lookaround',['setRecipequery2']),
    },
    computed:{
        ...mapState('lookaround',['ingredients']),
        ...mapState('myblog', ['currentshow'])
    },
  }
</script>
    
<style>
.contain{
    display: inline;
    margin: 0.5%;
    padding: 0.5%;
    text-align: center;
    align-items: center;
}

.except{
    display: inline;
    background-color: #F51A1A;
    border-radius: 10%;
    margin: 0.5%;
    padding: 0.5%;
}
</style>