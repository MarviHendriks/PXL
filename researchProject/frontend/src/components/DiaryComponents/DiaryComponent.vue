<template>
  <div id="dairy">
    <h2>Geboortedagboek</h2>
    <div class="filter" v-if="availableYears.length > 0">
      <select id="year-select" v-model="currentSelectedYear" @change="filterYear">
        <option v-for="year in availableYears" :key="year" :value="year">{{ year }}</option>
      </select>
    </div>
    <div id="diaryComponent" class="diary">
      <DiaryEntryComponent @isDeleted="entryHandler" @isEdited="entryHandler" v-for="entry in diaryEntries"
        :key="entry.id" :entryItem="entry.entry" :date="entry.date" :entryId="entry.id"></DiaryEntryComponent>
    </div>
    <div class="add" v-if="isAdmin">
      <img @click="addEntry()"
        src="https://cdn.builder.io/api/v1/image/assets/TEMP/3175ede57b77f4901a738d17a81ef4dbece920d9fe3d1a8d94a5667e8b757bf9?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
        alt="add" class="addButton" />
    </div>
    <AddEntryPopUp @closePopUp="closePopUp" v-if="isAdding && isAdmin"></AddEntryPopUp>
  </div>
</template>

<script>
import { useGlobalStore } from '@/stores'
import DiaryEntryComponent from '../DiaryComponents/DiaryEntryComponent.vue'
import AddEntryPopUp from '../DiaryComponents/AddEntryPopUp.vue'

export default {
  name: 'DiaryComponent',
  data: () => {
    return {
      global: null,
      diaryEntries: [],
      diaryEntriesBackup: [],
      token: localStorage.getItem('horseToken') || null,
      role: localStorage.getItem('userRole') || null,
      mareId: localStorage.getItem('mareId') || null,
      currentSelectedYear: new Date().getFullYear(),
      isAdding: false,
      isDisabled: true
    }
  },
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    },
    availableYears () {
      const years = new Set()
      this.diaryEntriesBackup.forEach(entry => {
        const year = new Date(entry.date).getFullYear()
        years.add(year)
      })
      return Array.from(years).sort((a, b) => b - a)
    }
  },
  components: {
    DiaryEntryComponent,
    AddEntryPopUp
  },
  methods: {
    async getDiary () {
      await this.global.getMareDiary(this.mareId)
      this.diaryEntries = this.global.diaryEntries.reverse()
      this.diaryEntriesBackup = this.diaryEntries
      await this.filterYear()
      this.changeDiary()
    },
    async filterYear () {
      this.diaryEntries = this.diaryEntriesBackup
      if (this.currentSelectedYear) {
        this.diaryEntries = this.diaryEntriesBackup.filter(entry => {
          const year = new Date(entry.date).getFullYear()
          return year === this.currentSelectedYear
        })
      } else {
        this.diaryEntries = this.diaryEntriesBackup
      }
    },
    entryHandler (data) {
      if (data) {
        this.getDiary()
      }
    },
    addEntry () {
      this.isAdding = true
    },
    closePopUp (data) {
      this.isAdding = !data
      this.getDiary()
      this.currentSelectedYear = this.availableYears[0]
    },
    changeDiary () {
      if (!this.isAdmin) {
        const styling = document.getElementById('diaryComponent')
        styling.style.borderRadius = '0px 10px 10px 10px'
      }
      if (this.diaryEntries.length <= 0) {
        this.isDisabled = true
      } else {
        this.isDisabled = false
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.getDiary()
  }

}
</script>

<style scoped>
.diary {
  max-height: 250px;
  min-height: 250px;
  background-color: #b0874d;
  overflow-y: auto;
  width: 100%;
  margin: 0px auto auto;
  border-radius: 0px 10px 0px 0px;

}

#year-select {
  outline: none;
  border: none;
  padding: 5px;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  margin: 0;
}

.addButton {
  width: 30px;
}

.add {
  width: 100%;
  background-color: #bea276;
  text-align: center;
  border-radius: 0px 0px 10px 10px;
}

h2 {
  align-self: center;
  font-weight: bold;
  margin-top: 20px;
}

::-webkit-scrollbar {
  width: 0;
}
</style>
