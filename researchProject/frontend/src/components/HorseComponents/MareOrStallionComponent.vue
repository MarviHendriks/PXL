<template>
  <div class="radioButtons">
    <input @click="navigateToCorrectPage(true)" type="radio" name="horse" id="mareRadioButton" checked/>
    <label for="mareRadioButton" >Merrie</label>
    <input @click="navigateToCorrectPage(false)" type="radio" name="horse" id="stallionRadioButton"/>
    <label for="stallionRadioButton">Hengst</label>
  </div>
</template>

<script>
export default {
  name: 'MareOrStallionComponent',
  data: () => {
    return {
    }
  },
  methods: {
    navigateToCorrectPage (isMare) {
      this.$emit('isMare', isMare)
    }
  }
}
</script>

<style scoped>
.radioButtons{
  display: block;
  text-align: center;
}

.radioButtons label{
  padding: 10px;
  font-weight: bold;
  font-size: larger;
}

.radioButtons label{
  margin-right: 30px;
}
</style>
