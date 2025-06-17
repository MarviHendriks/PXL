<template>
    <div class="item-Container">
        <div class="header">
            <p class="date">{{ formattedDate }}</p>
            <div v-if="isAdmin && !isPregnant" class="buttons">
                <img @click="edit()" id="changeButton" src="https://img.icons8.com/sf-black-filled/64/edit.png"
                    alt="edit" />
                <button v-if="!isPregnant" @click="deleteEntry()" id="deleteButton"><img width="50" height="50"
                        src="https://img.icons8.com/sf-black-filled/64/trash.png" alt="trash" /></button>
            </div>
        </div>
    </div>
    <EditCoverDatePopUp :entry="entryItem" :entryId="entryId" @closePopUp="closePopUp" v-if="isEdditing && isAdmin"></EditCoverDatePopUp>
</template>

<script>
import { useGlobalStore } from '@/stores'
import EditCoverDatePopUp from '@/components/CoverDatesComponents/EditCoverDatePopUp.vue'

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
  props: ['entryItem', 'entryId', 'isPregnant'],
  components: {
    EditCoverDatePopUp
  },
  methods: {
    closePopUp (data) {
      this.isEdditing = !data
      this.$emit('isEdited', true)
      this.formattedDate = this.global.formatDate(this.entryItem)
    },
    async edit () {
      this.isEdditing = true
    },
    async deleteEntry () {
      try {
        const result = await this.global.confirmationPopUp('Wilt u deze dekdatum verwijderen?', 'U kan deze actie niet terugdraaien', 'Verwijder')
        if (result.isConfirmed) {
          await this.global.deleteCoverdateEntry(this.entryId)
          this.global.showToast('Dekdatum succesvol verwijderd')
          this.$emit('isDeleted', true)
        }
      } catch (error) {
        console.error('Error deleting diary entry', error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.formattedDate = this.global.formatDate(this.entryItem)
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
