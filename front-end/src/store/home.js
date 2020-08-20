import axios from 'axios'
import SERVER from '../api/url.js'

export default {
    namespaced:true,
    state: {
      randomRecipes: [
        {
          category_id: 0,
          comments: 0,
          cooking_time: 0,
          create_date: "",
          delete_date: "",
          description: "",
          hits: 0,
          level: 0,
          like: [
            0
          ],
          likeNum: 0,
          main_image: "",
          recipe_id: 0,
          recipe_user: 0,
          recipe_user_name: "",
          recipe_user_profileImage: "",
          servings: 0,
          tag: [
            ""
          ],
          tagString: "",
          title: "",
          update_date: ""
        }
      ]
    },
    mutations: {
      SET_RANDOM(state, data) {
        state.randomRecipes = data;
      }
    },
    actions: {
      getRandomRecipes({commit}) {
        axios.get(SERVER.ROUTES.home.randomRecipe)
        .then((res) => {
          console.log(res.data)
          commit('SET_RANDOM', res.data)
        })
        .catch((err) => {
          console.log(err.response)
        })
      }
    },
  }