<template>
  <div>
    <div>
        <b-input-group
            v-for="size in ['sm']"
            :key="size"
            :size="size"
            class="mb-3"
            prepend="Label"
        >
            <b-form-input></b-form-input>
            <b-input-group-append>
            <b-button size="sm" text="Button" variant="success">Button</b-button>
            </b-input-group-append>
        </b-input-group>
    </div>
  </div>
</template>

<script>
export default {
    name: 'SearchBar',

}
</script>

<style>

</style>