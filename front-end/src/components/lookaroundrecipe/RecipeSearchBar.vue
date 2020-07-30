<template>
    <div>
        <b-container >
                    <h1 align="center" style="font-size:3rem"> 검색창입니다</h1>
            <v-row align="center" justify="center" align-self="center">
                <v-col md ="2" lg="3">
                </v-col>
                <v-col xs="8" sm="8" md="8" lg="6" align-self="center" align="center" >
        
                    <v-text-field v-model="searchtext1"
                    single-line
                    @keydown.enter="searchRecipe" searchtext1=''
                    ></v-text-field>
                </v-col>
                <v-col xs="2" sm="2" md="2" lg="2">
                    <v-btn style="background-color:#CEF279" text large @click="searchRecipe" searchtext1=''><b-icon icon="Search" font-scale="1.5" style="color: #7952b3;"></b-icon></v-btn>
                </v-col>
                <v-col >
            </v-col>
            </v-row>
        <v-row  align="center" justify="center" align-self="center">
            <v-col sm="1" md="2">
            </v-col>
            
            <v-col xs="2" sm="2" md="2" lg="2" align-self="center" align="center">
                <label style="font-size:1rem">넣고싶은재료</label>
            </v-col>
            <v-col  xs="2" sm="3" md="3" lg="2" align-self="center" align="center" >
                <v-text-field
                    @keydown.enter="selectSource"
                    v-model="searchtext2"
                >    
                </v-text-field>
            </v-col>

            <v-col  xs="2" sm="2" md="2" lg="2" align-self="center" align="center" >
                    <label style="font-size:1rem">빼고싶은재료</label>
            </v-col>
            <v-col  xs="2" sm="3" md="3" lg="2" align-self="center" align="center">
                <v-text-field
                    @keydown.enter="exceptSource"
                    v-model="searchtext3"
                >
                    
                </v-text-field>
            </v-col>
             <v-col >
            </v-col>
        </v-row>
        <v-row>
            <SelectedSource v-for="select in selected" :key="select.name" :Sourceinfo="select" style="display:inline" @delete-source="deleteSource"></SelectedSource>
        </v-row>
        <v-divider></v-divider>
            </b-container>
    </div>
    
</template>

<script>
import SelectedSource from "@/components/lookaroundrecipe/SelectedSource.vue"
import { mapActions } from 'vuex'
  export default {
    name: 'RecipeSearchBar',
    data () {
      return {
        selected: [],
        searchtext1:'',
        searchtext2:'',
        searchtext3:'',
      }
    },
    components:{
        SelectedSource,
    },
    methods: {
        searchRecipe(){
            alert(String(this.searchtext1) + "을 검색합니다.")
            this.setRecipequery(this.searchtext1)
            this.searchtext1=''
            //검색할 레시피 키워드를 넘겨줌
        },
        selectSource(event){
            this.searchtext2=''
            if(!this.isOverlap(event.target.value)){
                this.selected.push({name:event.target.value, state:true})
            }
            else
                alert("중복된값입니다!!")
        },
        exceptSource(event){
            this.searchtext3=''
            if(!this.isOverlap(event.target.value)){
                this.selected.push({name:event.target.value, state:false})
            }
            else
                alert("중복된값입니다!!")
        },
        deleteSource (item) {
            //컴포넌트를 지우면서 배열에서도 삭제
            alert(this.selected.map(x => x.name).indexOf(item))
            this.selected.splice(this.selected.map(x => x.name).indexOf(item), 1)
            this.selected = [...this.selected]
        },
        isOverlap(sourcename){
            //중복여부 테스트
            alert("출력값 : " + this.selected.map(x => x.name).indexOf(sourcename))
            if(this.selected.length)
                if(this.selected.map(x => x.name).indexOf(sourcename) !== -1)
                    return true
                else
                    return false
            else
                return false
        },
        ...mapActions('lookaround',['setRecipequery']),
    },
    computed:{
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