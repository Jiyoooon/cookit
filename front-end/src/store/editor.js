import axios from 'axios'
import SERVER from '@/api/url.js'
import router from '@/router'

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
      { name: null, quantity: null, is_essential: 1, valid: null },
      { name: null, quantity: null, is_essential: 1, valid: null },
    ],
    subIngr: [
      { name: null, quantity: null, is_essential: 0, valid: null },
      { name: null, quantity: null, is_essential: 0, valid: null },
    ],
    ingrQuery: [],
  },
  getters: {
    // 레시피 데이터 입력 여부, 글자수 등을 확인
    // 유효하지 않을 경우 false 리턴, 유효하면 true 리턴
    isValidRecipe(state) {
      // [*] 제목이랑 카테고리 필수 & 글자수 제한
      if(!state.recipe.title) {
        alert("레시피 제목을 입력하세요.")
        return false;
      }
      if (200 < state.recipe.title.length) {
        alert("레시피 제목은 200자까지 가능합니다.")
        return false;
      }
      if (state.recipe.description) {
        if(500 < state.recipe.description.length) {
          alert("레시피 소개말은 500자까지 가능합니다.")
          return false;
        }
      }
      if(!state.recipe.category_id) {
        alert("카테고리를 입력하세요.")
        return false;
      }

      // [*] 재료명 하나 이상 입력 필수
      // 재료 이름만 있는건 가능하지만 양만 있으면 안됨
      var ingrDone = false;
      const ingredients = [...state.mainIngr, ...state.subIngr];
      for (let i = 0; i < ingredients.length; i++) {
        if(ingredients[i].name) {
          ingrDone = true;
        }
        if (!ingredients[i].name && ingredients[i].quantity) {
          alert("재료의 이름을 입력하세요.")
          return false;
        }
        if (ingredients[i].quantity && 50 < ingredients[i].quantity.length) {
          alert("재료량은 50자까지 가능합니다.")
          return false;
        }
      }
      if (!ingrDone) {
        alert("재료를 입력하세요.")
        return false;
      }

      // [*] 조리 과정 하나 이상 필수
      // 조리 과정만 있는건 가능하지만 팁만 있거나 이미지만 있으면 안됨
      var stepDone = false;
      for (let i = 0; i < state.cookingStep.length; i++) {
        if (state.cookingStep[i].description) {
          stepDone = true;
        }
        if ((!state.cookingStep[i].description && state.cookingStep[i].tip)
        || (!state.cookingStep[i].description && state.cookingStep[i].step_image_url)) {
          alert("조리 과정을 입력하세요.")
          return false;
        }
        if (state.cookingStep[i].description && 500 < state.cookingStep[i].description.length) {
          alert("조리과정 설명은 500자까지 가능합니다.")
          return false;
        }
        if (state.cookingStep[i].tip && 500 < state.cookingStep[i].tip.length) {
          alert("팁은 500자까지 가능합니다.")
          return false;
        }
      }
      if (!stepDone) {
        alert("조리 과정을 한 개 이상 입력하세요.")
        return false;
      }
      return true;
    },
    // state에 저장된 레시피 데이터를 바탕으로 formData를 리턴
    getRecipeData(state) {
      const ingredients = [...state.mainIngr, ...state.subIngr];

      // [*] 레시피 formData 생성
      // ingredient[2].key : value 이런 식으로 값이 들어감
      const recipeData = new FormData();
      for (let [key, value] of Object.entries(state.recipe)) {
        if (!value) continue;
        // console.log(`recipe.${key}: ${value}`)
        recipeData.append(key, value);
      }
      
      for (let i = 0; i < ingredients.length; i++) {
        if (ingredients[i].name == null && ingredients[i].quantity == null) continue;
        for (let [key, value] of Object.entries(ingredients[i])) {
          if(key == "valid") continue;
          if (!value) continue;
          // console.log(`ingredients[${i}].${key}: ${value}`)
          recipeData.append(`ingredients[${i}].${key}`, value)
        }
      }

      for (let i = 0; i < state.cookingStep.length; i++) {
        if (state.cookingStep[i].description == null && state.cookingStep[i].tip == null
          && state.cookingStep[i].step_image_file == null) continue;
        for (let [key, value] of Object.entries(state.cookingStep[i])) {
          if (!value) continue;
          if (key == "step_image_url") continue;
          // console.log(`cookingStep[${i}].${key}: ${value}`)
          recipeData.append(`cookingStep[${i}].${key}`, value)
        }
      }
      return recipeData;
    },
    // rootState에 저장된 유저 token을 포함한 header를 리턴
    getHeader(state, getters, rootState) {
      return { headers: {
        'Authorization': `token ${rootState.accounts.authToken}`,
        'Content-Type': 'multipart/form-data'
      }};
    },
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
        is_essential: essential,
        valid: null
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
        commit('SET_INGRQUERY', 
        res.data.sort(function(a, b) {
          return a.length < b.length ? -1 : a.length > b.length ? 1 : 0;
        }));
      })
      .catch((err) => {
        console.log(err);
      })
    },
    onSubmitButton({ getters }) {
      if(!getters.isValidRecipe) return;
      const recipeData = getters.getRecipeData;
      const headerConfig = getters.getHeader;
      // [*] POST
      axios.post(SERVER.ROUTES.editor.saveRecipe, recipeData, headerConfig)
      .then((res) => {
        console.log(res)
        return new Promise(() => {
          router.push({ name: 'MyBlogListView'})
        })
        // 레시피 화면으로 redirect 필요
      })
      .catch((err) => {
        console.log(err)
      })
    },
    onSubmitButtonforUpdate({ getters }) {
      if(!getters.isValidRecipe) return;
      const recipeData = getters.getRecipeData;
      const recipeId = router.history.current.params.recipe_id;
      recipeData.append('recipe_id', `${recipeId}`)
      const headerConfig = getters.getHeader;
      axios.put(SERVER.ROUTES.editor.updateRecipe, recipeData, headerConfig)
      .then((res) => {
        console.log(res)
        return new Promise(() => {
          router.push({ name: 'SelectedRecipe', recipe_id: recipeId })
        })
      })
      .catch((err) => {
        console.log(err)
      })
    },
    deleteRecipe({ getters }, recipe_id) {
      this._vm.$root.$bvModal.msgBoxConfirm('한번 삭제된 데이터는 복구되지 않습니다.', {
        title: '정말로 삭제하시겠습니까?',
        size: 'md',
        buttonSize: 'sm',
        okVariant: 'danger',
        okTitle: 'YES',
        cancelTitle: 'NO',
        footerClass: 'p-2',
        hideHeaderClose: false,
        centered: true
      })
        .then((ans) => {
          if (ans) {
            const headerConfig = getters.getHeader;
            axios.delete(SERVER.ROUTES.editor.deleteRecipe + String(recipe_id), headerConfig)
            .then(res => {
              console.log(res.data)
              router.push({ name: 'MyBlogListView' })
            })
            .catch(err => {
              console.err(err.response)
            })
          }
        })
    }
  }
}