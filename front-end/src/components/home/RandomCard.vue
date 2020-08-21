<template>
  <div class="item">
      <b-img class="img-responsive" :src="recipe.main_image" alt="" height="240px"/>
      <div class="item-details">
			<h5>{{ recipe.title }}</h5>
			<p>{{ recipe.description }}</p>
		</div>
  </div>
</template>

<script>
export default {
	name: 'RandomCard',
	props: {
		recipe: Object,
	}
}
</script>

<style scoped>

.item-details {
	background-color: #333;
	color: #fff;
	padding: 20px 10px;
	text-align: left;
}
.item-details h5 {
  margin: 0 0 15px;
	font-size: 18px;
	line-height: 18px;
}
.item-details h5 span {
	color: red;
	float: right;
	padding-right: 20px;
}
.item-details p {
	font-size: 14px;
}
.item {
	text-align: center;
}
</style>