<template>
  <div>
    <b-container>
      <b-row><b-col></b-col></b-row>
      <b-row>
        <b-col cols="3"></b-col>
        <b-col cols="6" class="text-center icon-container">
          <!-- 카카오톡으로 공유하기 -->
          <div @click="shareByKakao" id="kakao-btn" class="icon-item">
            <img src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png" />
          </div>

          <!-- 페이스북으로 공유하기 -->
          <div @click="gofacebook" class="icon-item" style="margin-left: 10px">
            <font-awesome-icon :icon="['fab', 'facebook-square']" style="color:#395794; font-size:39px; margin-top:-3px; margin-bottom:3px;"> </font-awesome-icon>
          </div>

          <!-- PDF 파일로 만들기 -->
          <div @click="makePDF" class="icon-item" style="margin-left: 7px; margin-right:7px;">
            <font-awesome-icon :icon="['fas', 'file-pdf']" style="color: #CD0303; font-size: 34px;"></font-awesome-icon>
          </div>

          <!-- 복사하기 -->
          <div @click="doPrint" class="icon-item">
            <font-awesome-icon :icon="['fas', 'print']" style="color: gray; font-size: 33px;"></font-awesome-icon>
          </div>
        </b-col>

        <b-col cols="3"></b-col>
      </b-row>
      <b-row>
        <b-form-text id="tags-remove-on-delete-help" style="margin: 0 auto;">
        <b-icon class="mr-1" icon="exclamation-circle-fill" variant="secondary"></b-icon> 레시피가 마음에 드셨나요?
        마음에 드는 레시피를 공유해보세요!
        </b-form-text>
      </b-row>
      <b-row><b-col></b-col></b-row>
      <b-row><b-col></b-col></b-row>
    </b-container>
  </div>
</template>

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
import html2canvas from 'html2canvas'
import jsPDF from 'jspdf' 
import $ from 'jquery'

export default {
  name: 'Share',
  data() {
    return {
      facebookUrl: null,
      currUrl: null
    }
  },
  props: {
    selectedRecipe: Object,
  },
  methods: {
    gofacebook(){
      window.open(this.facebookUrl);
    },
    shareByKakao(){
      console.log(this.selectedRecipe)
      console.log(this.currUrl)
      Kakao.Link.sendDefault({
        // container: '#kakao-link-btn',
        objectType: 'feed',
        content: {
          title: this.selectedRecipe.title, 
          description: this.selectedRecipe.description ? this.selectedRecipe.description : '',
          imageUrl: this.selectedRecipe.main_image,
          link: {
          webUrl: this.currUrl,
          mobileWebUrl: this.currUrl,
          androidExecParams: 'test',
          },
        },
        social: {
          viewCount: this.selectedRecipe.hits-0,
          commentCount: this.selectedRecipe.comments-0,
          likeCount: this.selectedRecipe.likeNum-0,
        },
        buttons: [
          {
            title: '보러가기',
            link: {
              webUrl: this.currUrl,
              mobileWebUrl: this.currUrl,
            },
          }
        ]
      });
    },
    makePDF () {
      window.html2canvas = html2canvas //Vue.js 특성상 window 객체에 직접 할당해야한다.
      // let that = this
      let pdf = new jsPDF('p', 'mm', 'a4')
      window.scrollTo(0, 0);
      let canvas = pdf.canvas
      const pageWidth = 210 //캔버스 너비 mm
      const pageHeight = 295 //캔버스 높이 mm
      canvas.width = pageWidth

      let ele = document.querySelector('#detail-view')
      console.log(document);
      let width = ele.offsetWidth // 셀렉트한 요소의 px 너비
      let height = ele.offsetHeight // 셀렉트한 요소의 px 높이
      let imgHeight = pageWidth * height/width // 이미지 높이값 px to mm 변환

      if(!ele){
        console.warn('recipe is not exist.')
        return false
      }
      html2canvas(ele).then(function(canvas) {
        let position = 0
        const imgData = canvas.toDataURL('image/png')

        return new Promise(function() {
          window.setTimeout(function() {
            pdf.addImage(imgData, 'png', 0, position, pageWidth, imgHeight, undefined, 'slow')
            let heightLeft = imgHeight //페이징 처리를 위해 남은 페이지 높이 세팅.
            heightLeft -= pageHeight
            while (heightLeft >= pageHeight) {
              position = heightLeft - imgHeight
              pdf.addPage();
              pdf.addImage(imgData, 'png', 0, position, pageWidth, imgHeight)
              heightLeft -= pageHeight
            }
            console.log(pdf)
            pdf.save('cookit-recipe'.toLowerCase() +'.pdf')
          })
        })
                //Paging 처리
      });	
		},      
    doPrint(){
      window.print();
    },
  },
  created() {
    this.facebookUrl = "https://www.facebook.com/sharer/sharer.php?u="+encodeURIComponent('http://i3a201.p.ssafy.io/recipe/'+this.selectedRecipe.recipe_id);
    this.currUrl = "https://i3a201.p.ssafy.io/recipe/"+this.selectedRecipe.recipe_id;
  }
}
</script>

<style>

.icon-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.icon-item {
  display: inline-block;
  cursor: pointer;
  width: 35px;
  height: 35px;
  text-align: center;
  margin-right: 5px;
  margin-left: 5px;
}
</style>