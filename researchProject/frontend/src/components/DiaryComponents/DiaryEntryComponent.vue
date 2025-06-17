<template>
    <div class="item-Container">
        <div class="header">
            <p class="date">{{ formattedDate }}</p>
            <div v-if="isAdmin" class="buttons">
                <img @click="edit()" id="changeButton" src="https://img.icons8.com/sf-black-filled/64/edit.png"
                    alt="edit" />
                <button @click="deleteEntry()" id="deleteButton"><img width="50" height="50"
                        src="https://img.icons8.com/sf-black-filled/64/trash.png" alt="trash" /></button>
            </div>
        </div>
        <p class="entry">{{ entryItem }}</p>
    </div>
    <EditEntryPopUp :entry="entryItem" :entryId="entryId" @closePopUp="closePopUp" v-if="isEdditing && isAdmin"></EditEntryPopUp>
</template>

<script>
import { useGlobalStore } from '@/stores'
import EditEntryPopUp from '../DiaryComponents/EditEntryPopUp.vue'

export default {
  name: 'diaryEntryComponent',
  data: () => {
    return {
      global: null,
      role: localStorage.getItem('userRole') || null,
      formattedDate: '',
      isEdditing: false
    }
  },
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  props: ['entryItem', 'date', 'entryId'],
  components: {
    EditEntryPopUp
  },
  methods: {
    formatDateAndHours (dateToFormat) {
      const date = new Date(dateToFormat)
      const day = date.getDate()
      const month = date.getMonth() + 1
      const year = date.getFullYear()
      const hour = date.getHours()
      const minutes = date.getMinutes()
      return `${hour.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')} • ${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`
    },
    closePopUp (data) {
      this.isEdditing = !data
      this.$emit('isEdited', true)
    },
    async edit () {
      this.isEdditing = true
    },
    async deleteEntry () {
      try {
        const result = await this.global.confirmationPopUp('Bent u zeker?', 'U kan deze actie niet terugdraaien', 'Verwijder')
        if (result.isConfirmed) {
          await this.global.deleteMareEntry(this.entryId)
          this.global.showToast('dagboek invulling succesvol verwijderd')
          this.$emit('isDeleted', true)
        }
      } catch (error) {
        console.error('Error deleting diary entry', error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.formattedDate = this.formatDateAndHours(this.date)
  }
}
</script>

<style scoped>
.item-container {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
    width: 40px;
}

.date {
    padding: 5px;
    font-weight: bold;
    color: white;
    margin: 0;
    text-align: center;
    display: inline-block;
}

.entry {
    width: 100%;
    outline: none;
    padding: 10px;
    border: none;
    margin: 0;
    height: auto;
    background-color: white;
}

#changeButton {
    width: 30px;
}

#deleteButton {
    background-color: transparent;
    padding: 0;
    border: none;
    display: inline-block;
}

#deleteButton img {
    height: 30px;
    width: 30px;
}

.buttons {
    display: inline-block;
}

.header {
    justify-content: space-between;
    display: flex;
    width: 100%;
    background-color: #bea276;
}
</style>
