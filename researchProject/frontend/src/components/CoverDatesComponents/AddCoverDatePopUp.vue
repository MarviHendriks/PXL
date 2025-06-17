<template>
  <div class="background">
  </div>
  <div class="pop-up">
    <h1>Dekdatum toevoegen</h1>
    <form @submit.prevent="addEntry">
      <input class="dateInput" type="date" v-model="entry" required autofocus>
      <div class="buttons">
        <button class="addButton">Toevoegen</button>
        <button @click="closePopUp()" class="cancelButton">Annuleren</button>
      </div>
    </form>
  </div>
</template>

<script>
import { useGlobalStore } from '@/stores'

export default {
  data: () => {
    return {
      global: null,
      mareId: localStorage.getItem('mareId') || null,
      entry: ''
    }
  },
  methods: {
    async addEntry () {
      try {
        await this.global.addEntryToCoverdates(this.mareId, this.entry)
        this.closePopUp()
      } catch (error) {
        console.error('Fout bij het toevoegen van de dekdatum:', error)
      }
    },
    closePopUp () {
      this.$emit('closePopUp', true)
    }
  },
  mounted () {
    this.global = useGlobalStore()
  }
}
</script>

<style scoped>
.background {
  z-index: 1;
  width: 100vw;
  height: 100vh;
  background-color: black;
  position: absolute;
  top: 0;
  left: 0;
  opacity: 0.6;
}

h1 {
  font-size: x-large;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
}
.dateInput {
  width: 90%;
  height: 40px;
  border-radius: 10px;
  border: 1px solid #666;
  padding: 5px;
  margin-bottom: 20px;
  font: 400 16px PT Mono, sans-serif;
}

.pop-up {
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2;
  width: 80vw;
  height: 35vh;
  min-height: 150px;
  min-width: 300px;
  max-width: 300px;
  max-height: 180px;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
}

.buttons {
  text-align: center;
}

.addButton {
  background-color: #293B4A;
  color: white;
  font-weight: bold;
  border-radius: 10px;
  outline: none;
  border: none;
  padding: 5px;
  width: 130px;
}

.cancelButton {
  background-color: transparent;
  color: #293B4A;
  ;
  font-weight: bold;
  outline: none;
  border: none;
  padding: 5px;
  width: 130px;
}

</style>
