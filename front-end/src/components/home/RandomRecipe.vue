<template>
	<div class="container">
		<div class="row">
      <div class="col-sm-12">
				<carousel class="carousel" :loop="true" :center="true" :margin="30" :autoplay="true" :dots="false" :nav="false" :smartSpeed="450"
				:responsive="{0:{items:1},768:{items:2}, 1170:{items:3}}">
					<template slot="prev"><span class="prev"><i class="fa fa-angle-left"></i></span></template>
					<random-card :recipe="randomRecipes[0]"></random-card>
					<random-card :recipe="randomRecipes[1]"></random-card>
					<random-card :recipe="randomRecipes[2]"></random-card>
					<random-card :recipe="randomRecipes[3]"></random-card>
					<random-card :recipe="randomRecipes[4]"></random-card>
					<random-card :recipe="randomRecipes[5]"></random-card>
					<!-- <random-card v-for="(recipe, index) in randomRecipes" :key="index" :recipe="recipe"></random-card> -->
					<template slot="next"><span class="next"><i class="fa fa-angle-right"></i></span></template>
				</carousel>
      </div>
    </div>
	</div>
</template>

<script>
import randomCard from '@/components/home/RandomCard.vue'
import carousel from 'vue-owl-carousel'
import { mapState, mapActions } from 'vuex'

export default {
	name: 'RandomRecipe',
	components: {
		randomCard,
		carousel
	},
	data() {
		return {

		}
	},
	computed: {
		...mapState('home', ['randomRecipes']),
	},
	methods: {
		...mapActions('home', ['getRandomRecipes'])
	},
	created() {
		this.getRandomRecipes();
	},
}
</script>

<style scoped>
.testimonials {
	background-color: #f33f02;
	position: relative;
	padding-top: 80px;
}

.testimonials:after {
	content: '';
	position: absolute;
	bottom: 0;
	left: 0;
	right: 0;
	width: 100%;
	height: 30%;
	background-color: #ddd;
}
 .carousel {
	position: relative;
}
.carousel .next, .carousel .prev {
	display: block;
	width: 40px;
	height: 40px;
	line-height: 50px;
	position: absolute;
	font-size: 20px;
	color: #222;
	text-align: center;
}
 .carousel .prev {
	left: -70px;
}
 .carousel .next {
	right: -70px;
}
 
</style>
