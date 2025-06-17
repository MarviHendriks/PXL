<template>
    <div class="container">
        <div class="deworming-container">
            <p>{{ title }}</p>
            <div class="right-side">
                <p>{{ formattedDate }}</p>
              <svg v-if="isDone" class="done" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" enable-background="new 0 0 24 24">
                <path d="M12,0C5.373,0,0,5.373,0,12c0,6.627,5.373,12,12,12s12-5.373,12-12C24,5.373,18.627,0,12,0z M11,17.414l-4.707-4.707 l1.414-1.414L11,14.586l7.293-7.293l1.414,1.414L11,17.414z"/>
              </svg>
              <img class="not-done" v-else-if="isAdmin && !isDone" @click="updateDeworming()" width="48" height="48" src="https://img.icons8.com/material-sharp/48/FA5252/unchecked-radio-button.png" alt="unchecked-radio-button"/>
              <svg class="cross" v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" enable-background="new 0 0 24 24">
                <path d="M12,2C6.5,2,2,6.5,2,12c0,5.5,4.5,10,10,10s10-4.5,10-10C22,6.5,17.5,2,12,2z M16.9,15.5l-1.4,1.4L12,13.4l-3.5,3.5 l-1.4-1.4l3.5-3.5L7.1,8.5l1.4-1.4l3.5,3.5l3.5-3.5l1.4,1.4L13.4,12L16.9,15.5z"/>
              </svg>
            </div>
        </div>
    </div>
</template>

<script>
import { useGlobalStore } from '@/stores'

export default {
  name: 'DewormingComponent',
  data: () => {
    return {
      global: null,
      role: localStorage.getItem('userRole') || null,
      formattedDate: ''
    }
  },
  props: ['dewormingId', 'title', 'date', 'isDone'],
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  methods: {
    async updateDeworming () {
      try {
        const result = await this.global.confirmationPopUp(this.title, 'Bent u zeker dat u de medicatie heeft toegediend?', 'Bevestig')
        if (result.isConfirmed) {
          await this.global.updateDeworming(this.dewormingId, true)
          this.global.showToast(`${this.title} succesvol uitgevoerd`)
          this.$emit('dewormingUpdated', true)
        }
      } catch (error) {
        console.error('Error deleting mare', error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.formattedDate = this.global.formatDate(this.date)
  }
}
</script>

<style scoped>
.container {
    display: block;
    margin-bottom: 10px;
    border: 0;
}

.deworming-container {
    display: flex;
}

.deworming-container p {
    display: inline-block;
    margin: 10px;
    font-size: 0.90rem;
    font-weight: bold;
}

.right-side {
    flex: 1;
    margin: 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.done {
    width: 25px;
    height: 25px;
    margin-right: 10px;
    fill: #00bd7e;
}

.cross{
  width: 35px;
  height: 35px;
  margin-right: 8px;
  fill: #f44336;
}

.not-done {
  width: 30px;
  height: 30px;
  margin-right: 10px;
  fill: #f44336;
}

button:disabled {
    opacity: 1.0;
    background-color: black;
    color: white;
    font-weight: bold;
}
</style>
