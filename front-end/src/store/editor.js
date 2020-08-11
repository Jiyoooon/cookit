import axios from 'axios'
import SERVER from '@/api/url.js'
import router from '../router'

export default {
  namespaced: true,
  state: {
    recipe: {
      title: null,
      description: null,
      category_id: 0,
      servings: 0,
      cooking_time: 0,
      level: 0,
      main_image_file: null,
      tag: []
    },
    cookingStep: [
      {
        steps: 1,
        description: null,
        tip: null,
        step_image_file: null,
        step_image_url: null
      },
      {
        steps: 2,
        description: null,
        tip: null,
        step_image_file: null,
        step_image_url: null
      },
    ],
    mainIngr: [
      { name: null, quantity: null, is_essential: 1 },
      { name: null, quantity: null, is_essential: 1 },
    ],
    subIngr: [
      { name: null, quantity: null, is_essential: 0 },
      { name: null, quantity: null, is_essential: 0 },
    ],
    ingrQuery: [],
  },
  getters: {

  },
  mutations: {
    SET_RECIPE(state, data) {
      state.recipe = data
    },
    SET_COOKINGSTEP(state, data) {
      state.cookingStep = data
    },
    SET_MAININGR(state, data) {
      state.mainIngr = data
    },
    SET_SUBINGR(state, data) {
      state.subIngr = data
    },
    SET_INGRQUERY(state, data) {
      state.ingrQuery = data
    },
    addIngredient(state, essential) {
      const ref = essential ? state.mainIngr : state.subIngr;
      ref.push({
        name: null,
        quantity: null,
        is_essential: essential
      })
    },
    deleteIngredient(state, data) {
      const ref = data.essential ? state.mainIngr : state.subIngr;
      ref.splice(data.index, 1);
    },
    addCookingStep(state) {
      state.cookingStep.push({
        steps: state.cookingStep.length + 1,
        description: null,
        tip: null,
        step_image_file: null,
        step_image_url: null
      },)
    },
    deleteCookingStep(state, id) {
      state.cookingStep.splice(id, 1);
      for (var i = id; i < state.cookingStep.length; i++) {
				state.cookingStep[i].steps -= 1;
			}
    },
  }, 
  actions: {
    getRecipe({commit}, data) {
      commit('SET_RECIPE', data);
    },
    getMainIngr({commit}, data) {
      commit('SET_MAININGR', data);
    },
    getSubIngr({commit}, data) {
      commit('SET_SUBINGR', data);
    },
    getCookingSteps({commit}, data) {
      commit('SET_COOKINGSTEPS', data);
    },
    loadIngredients({commit}) {
      axios.get('/recipe/ingredients/small')
      .then((res) => {
        commit('SET_INGRQUERY', res.data);
      })
      .catch((err) => {
        console.log(err);
      })
    },
    onSubmitButton({state, rootState}) {
      // [1] 제목이랑 소개말은 필수
      if(!state.recipe.title) {
        alert("레시피 제목을 입력하세요.")
        return;
      }

      if(!state.recipe.description) {
        alert("레시피 소개말을 입력하세요.")
        return;
      }

      // [2] 재료 하나 이상 입력 필수
      // 재료 이름만 있는건 가능하지만 양만 있으면 안됨
      var ingrDone = false;
      const ingredients = [...state.mainIngr, ...state.subIngr];
      for (let i = 0; i < ingredients.length; i++) {
        if(ingredients[i].name && ingredients[i].quantity) {
          ingrDone = true;
        }
        if (!ingredients[i].name && ingredients[i].quantity) {
          alert("재료의 이름을 입력하세요.")
          return;
        }
      }
      if (!ingrDone) {
        alert("재료를 입력하세요.")
        return;
      }

      // [3] 조리 과정 하나 이상 필수
      var stepDone = false;
      for (let i = 0; i < state.cookingStep.length; i++) {
        if (state.cookingStep[i].description) {
          stepDone = true;
          break;
        }
      }
      if (!stepDone) {
        alert("조리 과정을 한 개 이상 입력하세요.")
        return;
      }

      // [4] 레시피 formData 생성
      // ingredient[2].key : value 이런 식으로 값이 들어감
      const recipeData = new FormData();
      for (let [key, value] of Object.entries(state.recipe)) {
        if (key == "main_image_file" && value == null) continue;
        // console.log(`recipe.${key}: ${value}`)
        recipeData.append(key, value);
      }
      
      for (let i = 0; i < ingredients.length; i++) {
        if (ingredients[i].name == null && ingredients[i].quantity == null) continue;
        for (let [key, value] of Object.entries(ingredients[i])) {
          // console.log(`ingredients[${i}].${key}: ${value}`)
          recipeData.append(`ingredients[${i}].${key}`, value)
        }
      }

      for (let i = 0; i < state.cookingStep.length; i++) {
        if (state.cookingStep[i].description == null && state.cookingStep[i].tip == null
          && state.cookingStep[i].step_image_file == null) continue;
        for (let [key, value] of Object.entries(state.cookingStep[i])) {
          if (key == "step_image_file" && value == null) continue;
          if (key == "step_image_url") continue;
          // console.log(`cookingStep[${i}].${key}: ${value}`)
          recipeData.append(`cookingStep[${i}].${key}`, value)
        }
      }

      // [5] 헤더 설정
      const headerConfig = { headers: {
        'Authorization': `token ${rootState['accounts/authToken']}`,
        'Content-Type': 'multipart/form-data'
      }}

      // [6] POST
      axios.post(SERVER.ROUTES.editor.saveRecipe, recipeData, headerConfig)
      .then((res) => {
        console.log(res)
        router.push({ name: 'MyBlogListView'})
        // 레시피 화면으로 redirect 필요
      })
      .catch((err) => {
        console.log(err)
      })
    },
    onSubmitButtonforUpdate({state, rootState}) {
      console.log('gkgkgkgkg')
      console.log(state.recipe)
      const recipeData = new FormData();
      for (let [key, value] of Object.entries(state.recipe)) {
        if (key == "main_image_file" && value == null) continue;
        console.log(`recipe.${key}: ${value}`)
        recipeData.append(key, value);
      }
      
      const ingredients = [...state.mainIngr, ...state.subIngr];
      for (let i = 0; i < ingredients.length; i++) {
        if (ingredients[i].name == null && ingredients[i].quantity == null) continue;
        for (let [key, value] of Object.entries(ingredients[i])) {
          console.log(`ingredients[${i}].${key}: ${value}`)
          recipeData.append(`ingredients[${i}].${key}`, value)
        }
      }

      for (let i = 0; i < state.cookingStep.length; i++) {
        if (state.cookingStep[i].description == null && state.cookingStep[i].tip == null
          && state.cookingStep[i].step_image_file == null) continue;
        for (let [key, value] of Object.entries(state.cookingStep[i])) {
          if (key == "step_image_file" && value == null) continue;
          console.log(`cookingStep[${i}].${key}: ${value}`)
          recipeData.append(`cookingStep[${i}].${key}`, value)
        }
      }

      const headerConfig = { headers: {
        'Authorization': `token ${rootState['accounts/authToken']}`,
        'Content-Type': 'multipart/form-data'
      }}
      axios.put(SERVER.ROUTES.editor.updateRecipe, recipeData, headerConfig)
      .then((res) => {
        console.log(res)
        // 레시피 화면으로 redirect 필요
      })
      .catch((err) => {
        console.log(err)
      })
    },
  }
}