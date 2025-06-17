<template>
  <div id="dairy">
  <h2>Dekdatums</h2>
  <div id="coverDateComponent" class="diary">
    <CoverDateEntryComponent @isDeleted="entryHandler" @isEdited="entryHandler" v-for="entry in coveringEntries" :entryItem="entry.coveringDate" :isPregnant="isPregnant" :entryId="entry.id" :key="entry.id"></CoverDateEntryComponent>
  </div>
  <div class="add" v-if="isAdmin && !isPregnant">
    <img @click="addEntry()" src="https://cdn.builder.io/api/v1/image/assets/TEMP/3175ede57b77f4901a738d17a81ef4dbece920d9fe3d1a8d94a5667e8b757bf9?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
      alt="add" class="addButton" />
  </div>
  <AddCoverDatePopUp  @closePopUp="closePopUp" v-if="isAdding && isAdmin"></AddCoverDatePopUp>
  </div>
</template>

<script>
import { useGlobalStore } from '@/stores'
import CoverDateEntryComponent from '@/components/CoverDatesComponents/CoverDateEntryComponent.vue'
import AddCoverDatePopUp from '@/components/CoverDatesComponents/AddCoverDatePopUp.vue'

export default {
  name: 'DiaryComponent',
  data: () => {
    return {
      global: null,
      coveringEntries: [],
      token: localStorage.getItem('horseToken') || null,
      role: localStorage.getItem('userRole') || null,
      mareId: localStorage.getItem('mareId') || null,
      isAdding: false,
      isDisabled: true,
      currentSelectedYear: new Date().getFullYear()
    }
  },
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  props: ['isPregnant'],
  components: {
    CoverDateEntryComponent,
    AddCoverDatePopUp
  },
  methods: {
    async getCoverDates () {
      await this.global.getMareCoverdates(this.mareId)
      this.coveringEntries = this.global.coverDateEntries
    },
    entryHandler (data) {
      if (data) {
        this.getCoverDates()
        this.coveringEntries = this.global.coverDateEntries
      }
    },
    addEntry () {
      this.isAdding = true
    },
    closePopUp (data) {
      this.isAdding = !data
      this.getCoverDates()
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.getCoverDates()
  }
}
</script>

<style scoped>
.diary {
  max-height: 100px;
  min-height: 0px;
  overflow-y: auto;
  width: 100%;
  margin: 0px auto auto;
  border-radius: 10px;
  margin-bottom: 10px;
  background-color: #bea276;
}

.addButton{
  width: 30px;
}

.add{
  width: 100%;
  background-color: #bea276;
  text-align: center;
  border-radius: 10px;
  margin-bottom: 20px;
}

h2{
  align-self: center;
  font-weight: bold;
  margin-top: 20px;
}

::-webkit-scrollbar {
  width: 0;
}
</style>
