<template>
    <div class="background">
    </div>
    <div class="pop-up">
        <h1>Edit invulling</h1>
      <form @submit.prevent="editEntry">
        <textarea v-model="edit" required autofocus></textarea>
        <div class="buttons">
            <button class="addButton">Opslaan</button>
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
      edit: ''
    }
  },
  props: ['entry', 'entryId'],
  methods: {
    async editEntry () {
      try {
        await this.global.updateDiaryEntry(this.entryId, this.edit)
        this.closePopUp()
      } catch (error) {
        console.error('Fout bij het toevoegen van dagboek invulling:', error)
      }
    },
    closePopUp () {
      this.$emit('closePopUp', true)
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.edit = this.entry
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
    opacity: 0.3;
}

h1 {
    font-size: x-large;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
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
    min-height: 300px;
    min-width: 300px;
    max-width: 500px;
    max-height: 500px;
    border-radius: 10px;
    padding: 20px;
}

textarea {
    width: 100%;
    height: 65%;
    border-radius: 10px;
    border: 1.5px solid lightgray;
    outline: none;
    resize: none;
    padding: 5px;
    margin-bottom: 10px;
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

form {
  height: 280px;
}
</style>
