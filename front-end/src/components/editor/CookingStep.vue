<template>
  <div>
    <b-container fluid="lg">
      <b-row v-for="(step, index) in steps" :key="index">
				<b-col sm="1" class="mt-3"><h5>Step {{index + 1}}.</h5></b-col>
        <b-col sm="7">
          <b-container fluid="lg" id="step-container">
            <b-row align-v="center">
              <b-col>
								<b-form-textarea type="textarea" id="description" rows="3" maxrows="3" v-model="step.description" required no-resize></b-form-textarea>
							</b-col>
            </b-row>
						<b-row align-v="center">
							<b-col sm="1">💡 Tip</b-col>
							<b-col>
								<b-form-textarea type="textarea" id="tip" rows="1" maxrows="2" v-model="step.tip" placeholder="팁이 있다면 알려주세요!" required no-resize></b-form-textarea>
							</b-col>
						</b-row>
          </b-container>
        </b-col>
        <b-col sm="3">
					<b-container fluid="lg">
            <b-row>
              <b-col>
                <b-form-file v-model="step.imageFile" accept="image/*" placeholder="사진 추가"
                  @change="setThumbnail"></b-form-file>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <img v-if="step.imageFile" :src="imageUrl" height="180px">
                <span v-else></span>
              </b-col>
            </b-row>
          </b-container>
        </b-col>
				<b-col sm="1">
					<b-container fluid="lg">
						<b-row>
							<b-col><b-button id="deleteBtn" @click="deleteStep(index)">X</b-button></b-col>
						</b-row>
					</b-container>
				</b-col>
      </b-row>
			<b-row>
				<b-col sm="3"></b-col>
				<b-col sm="4"><b-button variant="primary" id="addBtn" @click="addStep" block>추가</b-button></b-col>
				<b-col sm="5"></b-col>
			</b-row>
    </b-container>
  </div>
</template>

<script>
export default {
	name: 'CookingStep',
	data() {
		return {
			steps: [
				{ description: "",
				tip: "",
				imageFile: null },
			],
			imageUrl: null
		}
	},
	methods: {
		addStep() {
			this.steps.push({ name: "", quantity: "" });
		},
		deleteStep(index) {
      this.steps.splice(index, 1)
		},
		setThumbnail(e) {
			const file = e.target.files[0];
			this.imageUrl = URL.createObjectURL(file);
		},
	}
}
</script>

<style>
img { display: block; margin: 0px auto; }
</style>