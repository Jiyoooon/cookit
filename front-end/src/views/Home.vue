<template>
  <div id="home_wrapper">
    <header class="header" data-menustate="closed">
      <div class="grid-item  grid-item--primary">
      <b-img :src="getImageUrl(0)" class="object-fit" />
      <a href="#" class="logo">
			<h1>COOKIT</h1>
		</a>
	</div>
	<div class="grid-item grid-item--secondary  grid-item--content">
		<article class="article  article--align-right">
			<h5 class="rotate rotate--right">SSAFY<br/>@Seoul</h5>
		</article>
		<article class="article">
			<h4><span>이제부터</span> <span>요리할 땐,</span></h4>
			<h1 style="font-weight:bold; font-style:italic;">COOKIT</h1>
			<p>편리하고 스마트한 요리를 즐겨보세요!</p>
		</article>
	</div>
	<div class="grid-item grid-item--secondary">
		<b-img :src="getImageUrl(1)" class="object-fit"/>
	</div>
	<div class="grid-item grid-item--tertiary">
		<b-img :src="getImageUrl(2)" class="object-fit"/>
	</div>
	<div class="grid-item grid-item--tertiary grid-item--content">
		<article v-if="!isLoggedIn" class="article">
			<p>아직 회원이 아니신가요? COOKIT을 누려보세요!</p>
      <div style="float:right;"><a href="/emailAuth">→ 회원가입</a></div>
		</article>
    <article v-else class="article">
      <p>본인만의 레시피가 있나요? COOKIT에 공유해보세요!</p>
      <div style="float:right;"><a href="/recipeCreate">→ 레시피 쓰러 가기</a></div>
    </article>
	</div>
	<div class="grid-item grid-item--tertiary">
		<b-img :src="getImageUrl(3)" class="object-fit"/>
	</div>
</header>

<div class="loading-overlay">
	<span class="loading-overlay__content h1">COOKIT</span>
</div>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'

export default {
    name: 'Home',
    data() {
      return {
         mainProps: { blank: false, blankColor: '#777', width: 75, height: 75, class: 'm1' },
         image: [],
      }
    },
    computed: {
        ...mapGetters('accounts', ['isLoggedIn']),
        ...mapState('accounts', ['authUser']),
    },
    methods: {
      getImageUrl(num) {
        return require('../assets/main_image/' + this.image[num] + '.jpg')
      }
    },
    created() {
      var max = 15;
      var arr = Array();
      for (var i = 0; i <= max; i++) {
        arr.push(i);
      }
      arr.sort(function() {
        return Math.random() - Math.random();
      })
      for(let i = 0; i < 4; i++) {
        this.image.push(arr[i]);
      }
    }
}
</script>

<style scoped>

#home_wrapper {
  width: 100%;
  display: block;
  margin: 0px auto;
  background-color: #fff;
}

html, body {
	height: 100%;
	min-height: 100%;
	background: #a56a44;
	color: white;
	font-family: 'Roboto', sans-serif;
	-webkit-font-smoothing: antialiased;
	font-size: calc(.2vw + 12px);
  line-height: 1.4;
}
body {
	overflow-x: hidden;
}
h1, h2, h3, h4, h5, h6, p {
	margin-top: 0;
}
h1 span, h2 span, h3 span, h4 span, h5 span, h6 span, p span {
	vertical-align: middle;
}
h1 span + span, h2 span + span, h3 span + span, h4 span + span, h5 span + span, h6 span + span, p span + span {
	display: inline-flex;
	align-items: center;
}
h1 span + span::before, h2 span + span::before, h3 span + span::before, h4 span + span::before, h5 span + span::before, h6 span + span::before, p span + span::before {
	display: inline-block;
	content: "";
	width: 3.5em;
	height: 1px;
	background: white;
	margin: 0 0.5em 0 0.3em;
}
h1, .h1 {
	color: white;
	font-family: 'Roboto', sans-serif;
	font-weight: 400;
	font-size: 2.4rem;
	margin-bottom: 0;
}
h2, h3 {
	font-family: 'PT Serif', serif;
	font-weight: 700;
}
h2 {
	font-size: 2rem;
	margin-bottom: 0;
	padding-bottom: 0.6rem;
}
h4 {
	margin-bottom: 0;
	padding-bottom: 1.6rem;
}
a {
	color: inherit;
}
svg {
	fill: currentColor;
}
img, svg {
	vertical-align: middle;
}
.logo {
	text-decoration: none;
	transition: 0.2s ease-in-out;
}
.header {
	position: relative;
	min-height: 100vh;
	display: grid;
	grid-template: repeat(3, [row-start] minmax(25vh, 1fr)) / repeat(12, [col-start] 1fr);
	will-change: transform, width;
	transition: 0.5s transform cubic-bezier(0.39, 0.575, 0.565, 1);
}
@media (max-width: 700px) {
	.header {
    grid-template: repeat(3, [row-start] 1fr) / repeat(6, [col-start] 1fr);
	}
}
.header[data-menustate="open"] {
	transform: translateX(50%);
	animation: expand 0.5s cubic-bezier(0.39, 0.575, 0.565, 1) 1;
}
.header[data-menustate="open"] .grid-item {
	animation: shuffle 0.5s cubic-bezier(0.39, 0.575, 0.565, 1) 1;
}
.header[data-menustate="open"] .logo {
	opacity: 0;
	pointer-events: none;
	visibility: hidden;
}
.article {
  font-size: 0.9em;
	padding: 1rem 2rem 1.8rem 1.4rem;
  color: white;
}
.article + .article {
  margin-top: auto;
	padding-top: 0;
}
.article--align-right {
	text-align: right;
}
.rotate {
	display: inline-block;
	margin: 0;
}
.rotate--right {
	transform: rotate(-90deg);
	transform-origin: center right;
}
.object-fit {
	width: 100%;
	height: 100%;
	object-fit: cover;
}
.grid-item {
	background: #a56a44;
	position: relative;
	will-change: border;
}
 .grid-item::after {
	content: "";
	position: absolute;
	z-index: 0;
	top: 0;
	right: 0;
	bottom: 0;
	width: 0%;
	background: #a56a44;
	animation: background-swipe-out 1s cubic-bezier(0.785, 0.135, 0.15, 0.86) 2.1s 1;
}
 .grid-item--primary {
	grid-area: span 3 / span 6;
	display: grid;
	align-items: center;
	justify-items: start;
}
 .grid-item--primary > img, .grid-item--primary > .logo {
	grid-area: 1;
}
 .grid-item--secondary {
	grid-area: span 2 / span 3;
	animation-duration: 1.4s;
}
 .grid-item--tertiary {
	background: #898979;
	grid-area: span 1 / span 2;
	animation-duration: 1.8s;
}
 .grid-item--content {
	display: flex;
	flex-direction: column;
	justify-content: center;
}
 .grid-item .logo {
	transform: rotate(90deg);
}
 .loading-overlay {
	position: fixed;
	z-index: 1;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	display: grid;
	place-items: center;
	margin: -200vh -200vw;
	will-change: transform, padding, opacity;
	animation: hide-overlay 2.4s cubic-bezier(0.39, 0.575, 0.565, 1) 1;
	opacity: 0;
	pointer-events: none;
	visibility: hidden;
}
.loading-overlay__content {
	position: relative;
	transform: rotate(90deg);
	padding: 50vw 50vh;
	will-change: transform, padding, opacity;
	animation: rotate-expand-out 2.4s cubic-bezier(0.39, 0.575, 0.565, 1) 1;
}
.loading-overlay__content::after {
	content: "";
	position: absolute;
	z-index: -1;
	top: 0;
	right: 0;
	bottom: 0;
	width: 100%;
	background: #a56a44;
	will-change: width;
	animation: background-swipe-in 0.3s cubic-bezier(0.39, 0.575, 0.565, 1) 1;
}
@keyframes background-swipe-in {
	from {
    width: 0%;
	}
}
@keyframes background-swipe-out {
	0%, 65% {
    width: 100%;
	}
  100% {
    width: 0%;
	}
}
@keyframes rotate-expand-out {
  0%, 15%, 30%, 50%, 65%, 95%, 100% {
    transform: rotate(-90deg);
    padding: 0.2em 2em;
    opacity: 1;
	}
	30%, 50% {
    transform: rotate(0deg);
    padding: 4em 2.4em;
	}
	65%, 95% {
    transform: rotate(90deg);
    padding: 50vw 50vh;
	}
	100% {
    transform: rotate(90deg);
    padding: 50vw 50vh;
	}
}
@keyframes hide-overlay {
	0%, 90% {
    opacity: 1;
    visibility: visible;
    background: white;
	}
	95% {
		background: transparent;
	}
	100% {
		opacity: 0;
		visibility: hidden;
	}
}
@keyframes expand {
	0%, 100% {
		width: 100%;
	}
	50% {
		width: 110vw;
	}
}
@keyframes shuffle {
	0%, 90% {
		border-left: 0 solid #a56a44;
	}
	50% {
		border-left: 2vw solid #a56a44;
	}
}
 
</style>