<template>
  <div>
    <b-container fluid>
      <b-row>
        <b-col lg="8">
          <b-container fluid="xs" id="basicInfo-container">
            <b-row align-v="center">
              <b-col md="2" sm="3">제목<span style="color:red;">*</span></b-col>
              <b-col md="10" sm="9">
                <b-form-input type="text" v-model="recipe.title" required></b-form-input>
              </b-col>
            </b-row>
            <b-row>
              <b-col md="2" sm="3">소개말</b-col>
              <b-col md="10" sm="9">
                <b-form-textarea type="textarea" v-model="recipe.description"></b-form-textarea>
              </b-col>
            </b-row>
            <b-row align-v="center">
              <b-col md="2" sm="3">카테고리<span style="color:red;">*</span></b-col>
              <b-col md="3" sm="9">
                <b-form-select v-model="recipe.category_id" :options="categoryOpt" required></b-form-select>
              </b-col>
            </b-row>
            <b-row align-v="center">
              <b-col md="2" sm="3">정보</b-col>
              <b-col md="3" sm="3">
                <b-form-select v-model="recipe.servings" :options="servingOpt" required></b-form-select>
              </b-col>
              <b-col md="3" sm="3">
                <b-form-select v-model="recipe.cooking_time" :options="timeOpt" required></b-form-select>
              </b-col>
              <b-col md="3" sm="3">
                <b-form-select v-model="recipe.level" :options="levelOpt" required></b-form-select>
              </b-col>
              <b-col md="1" sm="0"></b-col>
            </b-row>
						<b-row align-v="center">
							<b-col md="2" sm="3">태그</b-col>
							<b-col>
								<b-form-tags 
								input-id="tags-remove-on-delete"
								:input-attrs="{ 'aria-describedby': 'tags-remove-on-delete-help' }"
								v-model="recipe.tag"
								separator=" "
								tag-variant="warning"
								remove-on-delete no-add-on-enter tag-pills
								class="mb-2"></b-form-tags>
								<b-form-text id="tags-remove-on-delete-help">
									<b-icon class="mr-1" icon="exclamation-circle-fill" variant="secondary"></b-icon>
									<kbd>Space</kbd>로 태그를 분리하고 <kbd>Backspace</kbd>로 지울 수 있어요.
								</b-form-text>
							</b-col>
						</b-row>
          </b-container>
        </b-col>
        <b-col lg="4">
          <b-container fluid="lg">
            <b-row>
              <b-col>
                <b-form-file enctype="multipart/form-data" v-model="recipe.main_image_file" accept="image/*" placeholder="음식 대표사진"
                  @change="setThumbnail"></b-form-file>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <b-img v-if="recipe.main_image_file!=null" :src="imageUrl" height="200px" style="max-width:100%"/>
                <span v-else></span>
              </b-col>
            </b-row>
						<b-row v-if="recipe.main_image_file!=null">
              <div class="text-btn" style="margin: 0 auto;" @click="deleteMain_Image_File">사진 삭제</div>
            </b-row>
          </b-container>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
export default {
		name: 'RecipeInfo',
		computed: {
			...mapState('editor', ['recipe'])
		},
    data() {
      return {
        imageUrl: null,
        categoryOpt: [
					{ value: 0, text: '-- 선택 --'},
					{ value: 1, text: '밥' },
					{ value: 2, text: '면' },
					{ value: 3, text: '탕/찌개' },
					{ value: 4, text: '메인' },
					{ value: 5, text: '반찬' },
					{ value: 6, text: '샐러드' },
					{ value: 7, text: '디저트' },
					{ value: 8, text: '기타' }
				],
				servingOpt: [
					{ value: 0, text: '-- 인원 --'},
					{ value: 1, text: '혼자' },
					{ value: 2, text: '둘이서' },
					{ value: 3, text: '3~4인' },
					{ value: 4, text: '5인 이상' }
				],
        timeOpt: [
					{ value: 0, text: '-- 시간 --' },
					{ value: 1, text: '15분 이하' },
					{ value: 2, text: '15~30분' }, 
					{ value: 3, text: '30분~1시간' },
					{ value: 4, text: '1~2시간' },
					{ value: 5, text: '2시간 이상' },
				],
        levelOpt: [
					{ value: 0, text: '-- 난이도 --' },
					{ value: 1, text: '★☆☆☆☆' },
					{ value: 2, text: '★★☆☆☆' }, 
					{ value: 3, text: '★★★☆☆' },
					{ value: 4, text: '★★★★☆' },
					{ value: 5, text: '★★★★★' },
				],
      }
    },
    methods: {
      ...mapMutations('editor', ['SET_UPDATETF','deleteMain_Image_File']),
        setThumbnail(e) {
					const file = e.target.files;
					if (file.length == 0) return;
          this.imageUrl = URL.createObjectURL(file[0]);
        }
    },
    watch: {
      recipe: {
        deep: true,
        handler() {
          this.SET_UPDATETF(true)
        }
      }
    }
}
</script>

<style>
img { display: block; margin: 0px auto; }
</style>