<template>
    <div>
        <b-container >
                    <h1 align="center"> 검색창입니다</h1>
            <v-row align="center" justify="center" align-self="center">
                <v-col md ="2" lg="3">
                </v-col>
                <v-col xs="8" sm="8" md="8" lg="6" align-self="center" align="center" >
        
                    <v-text-field
                    single-line
                    ></v-text-field>
                </v-col>
                <v-col xs="2" sm="2" md="2" lg="2">
                    <v-btn style="background-color:#CEF279" text large><b-icon icon="Search" font-scale="1.5" style="color: #7952b3;"></b-icon></v-btn>
                </v-col>
                <v-col >
            </v-col>
            </v-row>
        <v-row  align="center" justify="center" align-self="center">
            <v-col sm="1" md="2">
            </v-col>
            
            <v-col xs="2" sm="2" md="2" lg="2" align-self="center" align="center">
                <label>넣고싶은재료</label>
            </v-col>
            <v-col  xs="2" sm="3" md="3" lg="2" align-self="center" align="center" >
                <v-autocomplete
                :items="items1"
                v-model="selected1"
                chips
                full-width
                auto
                hide-no-data
                hide-selected
                single-line
                ></v-autocomplete>
            </v-col>

            <v-col  xs="2" sm="2" md="2" lg="2" align-self="center" align="center" >
                    빼고싶은재료
            </v-col>
            <v-col  xs="2" sm="3" md="3" lg="2" align-self="center" align="center">
                <v-autocomplete
                :items="items2"
                v-model="selected2"
                chips
                full-width
                auto
                hide-no-data
                single-line
                multiple
                deletable-chips
                ></v-autocomplete>
            </v-col>
             <v-col >
            </v-col>
        </v-row>
        <v-divider></v-divider>
            </b-container>
    </div>
    
</template>

<script>
  export default {
    name: 'RecipeSearchBar',
    data () {
      return {
        selected1: [],
        selected2: [],
        items1: ['마늘','돼지고기','닭고기','양파','마수리'],
        items2: ['마늘','돼지고기','닭고기','양파','마수리'],
      }
    },
    watch:{
        selected(newval,oldval){
            console.log(newval + 'VS' + oldval)
        }
    }
  }
</script>
    
<style>

</style>