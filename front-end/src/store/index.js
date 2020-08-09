import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import createPersistedState from 'vuex-persistedstate'

import moduleAccounts from '@/store/accounts.js'
import moduleEditor from '@/store/editor.js'
import moduleLookAround from '@/store/lookaround.js'
import moduleRecipes from '@/store/recipes.js'
import moduleMyBlog from '@/store/myblog.js'

Vue.use(Vuex)
axios.defaults.baseURL = 'http://i3a201.p.ssafy.io:8080/cooking-0.0.1-SNAPSHOT';

// 0809 : index.js가 너무 길어져서 불편하니까 모듈별로 .js 파일을 분리했습니당
// 전역으로 사용할 것들은 아래에 작성해주세요 (ex. 스크롤 제일 위로 올리기 등)
// baseURL 때문에 웬만하면 axios의 경로는 api/uri.js에 정의해서 사용해주세욤

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    accounts: moduleAccounts,
    recipes: moduleRecipes,
    myblog: moduleMyBlog,
    editor: moduleEditor,
    lookaround: moduleLookAround,
  },
  plugins: [
    createPersistedState({
      paths: ['lookaround'],
    })
  ]
})
