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
            
            <v-col xs="4" sm="5" md="5" lg="4" align-self="center" align="center">
                <label style="font-size:1rem">넣고싶은재료</label>
                <div
                    class="mx-auto"
                    max-width="auto"
                >
                    <v-autocomplete
                        :loading="loading"
                        :selecteditems= 'selecteditems'
                        :search-input.sync="searchtextS"
                        color="white"
                        item-text="small"
                        flat
                        append-icon=''
                        @keydown.enter="selectSource(selecteditems[0])"
                    >
                    </v-autocomplete>
                    <v-list v-if="searchtextS !='' && this.selecteditems.length && searchtextS != null" style="position:absolute; z-index:10; width: 93.7%;overflow-y:scroll; height:auto; max-height:300px">
                        <v-list-item-group
                            color="indigo"
                        >
                            <v-list-item
                                v-for="selecteditem in selecteditems"
                                :key="String(selecteditem.name)+ String(selecteditem.id)"
                            >

                                <v-list-item-content @click="selectSource(selecteditem)">
                                    <v-list-item-title  v-text="selecteditem.name" style="text-color:red"></v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-list-item-group>
                    </v-list>
                </div>
            </v-col>

            <v-col xs="4" sm="5" md="5" lg="4" align-self="center" align="center" >
                    <label style="font-size:1rem">빼고싶은재료</label>
            <div
                    class="mx-auto"
                    max-width="auto"
                >
                    <v-autocomplete
                        :loading="loading"
                        :selecteditems= 'excludeditems'
                        :search-input.sync="searchtextE"
                        cache-items
                        color="white"
                        item-text="small"
                        flat
                        append-icon=''
                        @keydown.enter="excludedSource(excludeditems[0])"
                    >
                    </v-autocomplete>
                    <v-list v-if="searchtextE !='' && this.excludeditems.length && searchtextE != null" style="position:absolute; z-index:10; width: 93.7%;overflow-y:scroll; height:auto; max-height:300px">
                        <v-list-item-group
                            color="indigo"
                        >
                            <v-list-item
                                v-for="excludeditem in excludeditems"
                                :key="String(excludeditem.name)+ String(excludeditem.id)"
                            >

                                <v-list-item-content>
                                    <v-list-item-title @click="excludedSource(excludeditem)" v-text="excludeditem.name" style="text-color:red"></v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-list-item-group>
                    </v-list>
                </div>
            </v-col>
             <v-col >
                
            </v-col>
        </v-row>
        <v-row>
            <SelectedSource v-for="select in selected" :key="select.ingredientdata.name" :Sourceinfo="select" style="display:inline" @delete-source="deleteSource"></SelectedSource>
        </v-row>
        <v-divider></v-divider>
            </b-container>
    </div>
    
</template>

<script>
import SelectedSource from "@/components/lookaroundrecipe/SelectedSource.vue"
import { mapActions, mapState } from 'vuex'
  export default {
    name: 'RecipeSearchBar',
    data () {
      return {
        loading: false,
        select: null,
        selecteditems: [],
        excludeditems: [],

        selected: [],//{ingredientdata:{name,kind,id },state }
        searchtext1:'',
        searchtextS:'',
        searchtextE:'', 
      }
    },
    components:{
        SelectedSource,
    },
    methods: {
        searchRecipe(){//버튼을 눌렀을때,
            this.setRecipequery({querydata:this.searchtext1,selectedarray:this.selected})
            this.searchtext1=''
        },
        selectSource(payload){
            if(!this.isOverlap(payload.name))
                this.selected.push({ingredientdata:payload, state:true})
            else
                alert("중복된값입니다!!")
            this.searchtextS=''
        },
        excludedSource(payload){
            if(!this.isOverlap(payload.name))
                this.selected.push({ingredientdata:payload, state:false})
            else
                alert("중복된값입니다!!")
            this.searchtextE=''
        },
        deleteSource (item) {
            this.selected.splice(this.selected.map(x => x.ingredientdata.name).indexOf(item), 1)
            this.selected = [...this.selected]
        },
        isOverlap(sourcename){
            if(this.selected.length)
                if(this.selected.map(x => x.ingredientdata.name).indexOf(sourcename) !== -1)
                    return true
                else
                    return false
            else
                return false
        },
        // 넣고싶은재료
        querySelectionsS (v) {
            if(v !==''){
                //alert(v)
                this.loading = true
                setTimeout(() => {
                    this.selecteditems = this.ingredients.filter(e => {
                        //e : 필터링되기전 모든 데이터
                        //alert((e || '').toLowerCase().indexOf((v || '').toLowerCase()) > -1)
                        //해당 문자열이 있으면 true 없으면 false반환
                        // || => 왼쪽의 값이없으면 ''로 대체된다.
                        return (e.name || '').indexOf((v || '')) > -1
                    })
                    this.loading = false
                }, 500)
            }
        },
        // 빼고싶은 재료
        querySelectionsE (v) {
            this.loading = true
            setTimeout(() => {
                this.excludeditems = this.ingredients.filter(e => {
                    return (e.name || '').indexOf((v || '')) > -1
                })
                this.loading = false
            }, 500)
        },
        ...mapActions('lookaround',['setRecipequery']),
    },
    computed:{
        ...mapState('lookaround',['ingredients'])
    },
    watch: {
        searchtextS(val){
            if(val ==''||val == null)
                this.selecteditems=[]
            else
                this.querySelectionsS(val)
            
        },
        searchtextE(val){
            if(val ==''||val == null)
                this.excludeditems=[]
            else
                this.querySelectionsE(val)
        },
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