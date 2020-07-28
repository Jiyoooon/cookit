<template>
  <div>
    <b-container fluid="lg">
      <b-row>
        <b-col sm="8">
          <b-container fluid="lg" id="basicInfo-container">
            <b-row align-v="center">
              <b-col sm="2">제목</b-col>
              <b-col sm="10">
                <b-form-input type="text" id="title" v-model="basicInfo.title" required></b-form-input>
              </b-col>
            </b-row>
            <b-row>
              <b-col sm="2">소개말</b-col>
              <b-col sm="10">
                <b-form-textarea type="textarea" id="intro" v-model="basicInfo.intro" required></b-form-textarea>
              </b-col>
            </b-row>
            <b-row align-v="center">
              <b-col sm="2">카테고리</b-col>
              <b-col sm="3">
                <b-form-select v-model="basicInfo.categoryID" :options="categoryOpt" required></b-form-select>
              </b-col>
            </b-row>
            <b-row align-v="center">
              <b-col sm="2">정보</b-col>
              <b-col sm="3">
                <b-form-select v-model="basicInfo.serving" :options="servingOpt" required></b-form-select>
              </b-col>
              <b-col sm="3">
                <b-form-select v-model="basicInfo.time" :options="timeOpt" required></b-form-select>
              </b-col>
              <b-col sm="3">
                <b-form-select v-model="basicInfo.level" :options="levelOpt" required></b-form-select>
              </b-col>
              <b-col sm="1"></b-col>
            </b-row>
          </b-container>
        </b-col>
        <b-col sm="4">
          <b-container fluid="lg">
            <b-row>
              <b-col>
                <b-form-file v-model="basicInfo.imageFile" accept="image/*" placeholder="음식 대표사진"
                  @change="setThumbnail"></b-form-file>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <img v-if="basicInfo.imageFile" :src="imageUrl" height="180px">
                <span v-else></span>
              </b-col>
            </b-row>
          </b-container>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
    name: 'RecipeInfo',
    data() {
        return {
            basicInfo: {
                title: null,
                intro: null,
                categoryID: null,
                serving: null,
                time: null,
                level: null,
                imageFile: null,
            },
            imageUrl: null,
            categoryOpt: [ { value: null, text: '선택' }, { value: 1, text: '밥' }, { value: 2, text: '면' },
            { value: 3, text: '탕/찌개' }, { value: 4, text: '메인' }, { value: 5, text: '반찬' },
            { value: 6, text: '샐러드' }, { value: 7, text: '디저트' }, { value: 8, text: '기타' } ],
            servingOpt: [ { value: null, text: '인원' }, { value: 1, text: '혼자' },
            { value: 2, text: '둘이서' }, { value: 3, text: '3~4인' }, { value: 4, text: '5인 이상' } ],
            timeOpt: [ { value: null, text: '시간' }, { value: 1, text: '15분 이하' }, { value: 2, text: '15~30분' }, 
            { value: 3, text: '30분~1시간' }, { value: 4, text: '1~2시간' }, { value: 5, text: '2시간 이상' }, ],
            levelOpt: [ { value: null, text: '난이도' }, { value: 1, text: '★☆☆☆☆' }, { value: 2, text: '★★☆☆☆' }, 
            { value: 3, text: '★★★☆☆' }, { value: 4, text: '★★★★☆' }, { value: 5, text: '★★★★★' }, ],
        }
    },
    methods: {
        setThumbnail(e) {
            console.log(e.target.files)
            const file = e.target.files[0];
            this.imageUrl = URL.createObjectURL(file);
        }
    }
}
</script>

<style>
img { display: block; margin: 0px auto; }
</style>