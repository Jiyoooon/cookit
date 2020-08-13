<template>
  <div>
    <b-container fluid>
      <b-row v-for="(step, index) in cookingStep" :key="index">
				<b-col lg="1" class="mt-3"><h5>Step {{index + 1}}.</h5></b-col>
        <b-col lg="7">
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
        <b-col lg="4">
					<b-container fluid>
            <b-row>
              <b-col cols="11">
                <b-form-file enctype="multipart/form-data" v-model="step.step_image_file" accept="image/*" placeholder="사진 추가"
                  :id="index+''" @change="setThumbnail"></b-form-file>
              </b-col>
							<b-col cols="1">
								<div style="display:block; text-align:right;">
									<b-icon class="mt-2" icon="backspace-fill" variant="warning" style="cursor: pointer;" @click="deleteCookingStep(index)"></b-icon>
								</div>
							</b-col>
            </b-row>
            <b-row>
              <b-col>
                <img v-if="step.step_image_file!=null" :src="step.step_image_url" height="180px" width="240px">
                <span v-else></span>
              </b-col>
            </b-row>
          </b-container>
        </b-col>
				<!-- <b-col lg="1">
					<b-container fluid="lg">
						<b-row>
							<b-col>
								<div style="display:block; text-align:right;">
									<b-icon class="mt-2" icon="backspace-fill" variant="warning" style="cursor: pointer;" @click="deleteCookingStep(index)"></b-icon>
								</div>
							</b-col>
						</b-row>
					</b-container>
				</b-col> -->
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
	computed: {
		...mapState('editor', ['cookingStep'])
	},
	methods: {
		...mapMutations('editor', ['addCookingStep', 'deleteCookingStep']),
		setThumbnail(e) {
			const file = e.target.files[0];
			if(!file) return;
			console.log(e)
			const index = Number(e.path[0].id);
			console.log(e.path[0])
			this.cookingStep[index].step_image_url = URL.createObjectURL(file);
		},
	},
}
</script>

<style>
img { display: block; margin: 0px auto; }
</style>