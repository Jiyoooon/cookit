<template>
  <div>
    <b-container fluid="lg">
      <b-row v-for="(step, index) in cookingStep" :key="index">
				<b-col sm="1" class="mt-3"><h5>Step {{index + 1}}.</h5></b-col>
        <b-col sm="7">
          <b-container fluid="lg" id="step-container">
            <b-row align-v="center">
              <b-col>
								<b-form-textarea type="textarea" id="description" rows="3" max-rows="3" v-model="step.description" required no-resize></b-form-textarea>
							</b-col>
            </b-row>
						<b-row align-v="center">
							<b-col sm="1">💡 Tip</b-col>
							<b-col>
								<b-form-textarea type="textarea" id="tip" rows="1" max-rows="2" v-model="step.tip" placeholder="팁이 있다면 알려주세요!" required no-resize></b-form-textarea>
							</b-col>
						</b-row>
          </b-container>
        </b-col>
        <b-col sm="3">
					<b-container fluid="lg">
            <b-row>
              <b-col>
                <b-form-file enctype="multipart/form-data" v-model="step.step_image_file" accept="image/*" placeholder="사진 추가"
                  :id="index+''" @change="setThumbnail"></b-form-file>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <b-img v-if="imageUrl[index]!=null" :src="imageUrl[index]" height="180px" style="max-width:100%"/>
                <span v-else></span>
              </b-col>
            </b-row>
          </b-container>
        </b-col>
				<b-col sm="1">
					<b-container fluid="lg">
						<b-row>
							<b-col><b-icon class="mt-2" icon="backspace-fill" variant="warning" style="cursor: pointer" @click="deleteCookingStep(index)"></b-icon></b-col>
						</b-row>
					</b-container>
				</b-col>
      </b-row>
			<b-row>
				<b-col sm="4"></b-col>
				<b-col sm="4">
          <div @click="addCookingStep" style="width: 6em; display: block; margin: 0px auto; align-text: center; cursor: pointer">
            <b-icon class="mr-1" icon="plus-square-fill" variant="warning"></b-icon>추가</div>
				</b-col>
				<b-col sm="4"></b-col>
			</b-row>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'

export default {
	name: 'CookingStep',
	data() {
		return {
			imageUrl: {},
		}
	},
	computed: {
        ...mapState('editor', ['cookingStep']),
        ...mapState('recipes', ['selectedRecipe']),
	},
	methods: {
		...mapMutations('editor', ['addCookingStep', 'deleteCookingStep', 'SET_COOKINGSTEP', 'SET_UPDATETF']),
		setThumbnail(e) {
      console.log(e)
      const file = e.target.files[0];
      // console.log(file)
      const index = Number(e.path[0].id);
      console.log(index)
      if (!file) {
        this.imageUrl[index] = this.selectedRecipe.cookingStep[index].step_image
        return
      }
      this.imageUrl[index] = URL.createObjectURL(file);
      // console.log(this.imageUrl)
		},
    },
    created() {
        const cookstep = []
        for (var i=0; i < this.selectedRecipe.cookingStep.length; i++) {
            const step = {
                steps: this.selectedRecipe.cookingStep[i].steps,
                description: this.selectedRecipe.cookingStep[i].description,
                tip: this.selectedRecipe.cookingStep[i].tip,
                step_image_file: null,
            }
            this.imageUrl[`${i}`] = this.selectedRecipe.cookingStep[i].step_image
            cookstep.push(step)
        }
        console.log(this.imageUrl)
        this.SET_COOKINGSTEP(cookstep)
    },
    watch: {
      imageUrl: {
        deep: true,
        handler() {

        }
      },
      cookingStep: {
        deep: true,
        handler() {
          for (let i=0; i < this.cookingStep.length; i++) {
            if (!(this.cookingStep[i].description == this.selectedRecipe.cookingStep[i].description) ||
            !(this.cookingStep[i].tips == this.selectedRecipe.cookingStep[i].tips) ||
            !(this.cookingStep[i].step_image_file == this.selectedRecipe.cookingStep[i].step_image_file)
            ) {
              console.log(this.cookingStep)
              console.log(this.selectedRecipe.cookingStep)
              this.SET_UPDATETF(true)
          }
          }
        }
      }
    }
}
</script>

<style>
img { display: block; margin: 0px auto; }
</style>