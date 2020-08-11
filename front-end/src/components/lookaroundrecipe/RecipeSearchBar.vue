<template>
    <div>
        <b-container >
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
                <label style="font-size:2rem"><strong>넣고싶은재료</strong></label>
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
                        outlined
                        append-icon=''
                        @keydown.enter="selectSource(selecteditems[0])"
                    >
                    </v-autocomplete>
                    <v-list  ref = "Slist" v-if="searchtextS !='' && this.selecteditems.length && searchtextS != null" style="position:absolute; z-index:10; width: 93.7%;overflow-y:scroll; height:auto; max-height:300px">
                        <v-list-item-group
                            color="indigo"
                        >
                            <v-list-item
                                v-for="selecteditem in selecteditems"
                                :key="String(selecteditem.name)+ String(selecteditem.id)"
                                @keydown.down="focusMoveS(selecteditem.name)"
                                input-value="감자"
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
                    <label style="font-size:2rem"><strong>빼고싶은재료</strong></label>
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
                        outlined
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

                                <v-list-item-content >
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
            <v-col xs="1" sm="1" md="1" lg="1"></v-col>
            <v-col>
                <SelectedSource v-for="select in selected" :key="select.ingredientdata.name" :Sourceinfo="select" style="display:inline" @delete-source="deleteSource"></SelectedSource>
            </v-col>
        
        </v-row>
        <v-row>
            <b-form-group>
                <b-form-radio-group
                    id="btn-radios-2"
                    v-model="selectedcategory"
                    :options="categories"
                    buttons
                    button-variant="outline-primary"
                    size="lg"
                    name="radio-btn-outline"
                ></b-form-radio-group>
            </b-form-group>
        </v-row>
        <v-divider></v-divider>
            </b-container>
    </div>
    
</template>

<script>
import SelectedSource from "@/components/lookaroundrecipe/SelectedSource.vue"
import { mapActions, mapState, mapMutations } from 'vuex'
  export default {
    name: 'RecipeSearchBar',
    data () {
      return {
        categories:[
            { text: '전체', value: '0' },
            { text: '밥', value: '1' },
            { text: '면', value: '2' },
            { text: '탕/찌개', value: '3' },
            { text: '메인', value: '4' },
            { text: '반찬', value: '5' },
            { text: '샐러드', value: '6' },
            { text: '디저트', value: '7' },
            { text: '기타', value: '8'},
        ],
        loading: false,
        select: null,
        selecteditems: [],
        excludeditems: [],

        selected: [],//{ingredientdata:{name,kind,id },state }
        searchtext1:'',
        searchtextS:'',
        searchtextE:'',
        selectedcategory:0, 
      }
    },
    components:{
        SelectedSource,
    },
    methods: {
        searchRecipe(){//버튼을 눌렀을때,
            this.setRecipequery({querydata:this.searchtext1,selectedarray:this.selected,category: this.selectedcategory})
            this.searchtext1=''
        },
        selectSource(payload){
            if(!this.isOverlap(payload.name))
                this.selected.push({ingredientdata:payload, state:true})
            else
                alert("중복된값입니다!!")
            this.searchtextS=''
        },
        focusMoveS(payload){
            alert("테스트!")
            alert(payload)
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
        selectCategory(index){
            this.selectedcategory = index+1
        },
        ...mapActions('lookaround',['setRecipequery',]),
        ...mapMutations('lookaround',['initPage','setRecipequeryCategory','initRecipes'])
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
        selectedcategory(){
            this.setRecipequeryCategory(this.selectedcategory)
        }
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

.theme--light.v-divider {
    border-color: sandybrown !important;
}

#category {
    cursor: pointer;
}
</style>