<template>
  <section class="container">
    <img loading="lazy"
         src="https://cdn.builder.io/api/v1/image/assets/TEMP/89797b3dbf8bad6ae46f7d8277195fddc28a3b3e7e65d17066522f8151264a00?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
         alt="" class="main-image"/>
    <div class="secondary-container">
      <i class="search-icon"><font-awesome-icon :icon="['fas', 'search']" /></i>
      <input type="text" id="search" v-model="searchInput" @input="saveFilters">
    </div>
    <div v-if="showFilter" class="filter-container">
      <form class="filter-dialog" @submit.prevent="saveFilters">
        <h2>Paarden Filter</h2>
        <label>Kies een optie:</label>
        <select v-model="selectedOption" required>
          <option value="mares">Merrie</option>
          <option value="stallions">Hengst</option>
        </select>

        <template v-if="selectedOption === 'mares'">
          <h4>Merrie Filters:</h4>
          <div id="drachtig">
            <label>
              <input type="radio" v-model="isPregnant" value="true"> Drachtig
            </label>
            <label>
              <input type="radio" v-model="isPregnant" value="false"> Niet drachtig
            </label>
          </div>
          <div>
            <label>Uitgerekend van:</label>
            <input type="date" v-model="dueDateFrom">
            <label>Tot:</label>
            <input type="date" v-model="dueDateTo">
          </div>
        </template>

        <template v-if="selectedOption === 'stallions'">
          <h4>Hengst Filters:</h4>
          <div>
            <label>Kleurcode:</label>
            <input type="text" v-model="colorCode">
          </div>
        </template>

        <div class="filter-actions">
          <button class="save" type="submit">Filters Opslaan</button>
          <button type="button" class="reset" @click="resetFilters">Reset</button>
          <button type="button" class="cancel" @click="cancelFilter">Annuleren</button>
        </div>
      </form>
    </div>
      <!-- SVG-afbeelding van een filterpictogram -->
    <div class="buttonContainer">
      <font-awesome-icon :icon="['fas', 'sliders']" @click="toggleFilter" class="filterButton" size="2x"/>
      <font-awesome-icon :icon="['fas', 'circle-plus']" v-if="isAdmin" @click="toAddHorsePage" class="addButton" size="2x"/>
    </div>
    <HorseList :mares="filteredMares" v-if="filteredMares.mares != null && filteredMares.stallions != null && (filteredMares.mares.length > 0 || filteredMares.stallions.length > 0)"/>
    <div v-if="filteredMares.mares != null && filteredMares.stallions != null && (filteredMares.mares.length <= 0 && filteredMares.stallions.length <= 0)" id="noResults">
      <b>Geen resultaten gevonden.</b>
    </div>
  </section>
  <MenuComponent />
</template>
<script>
import HorseList from '../HorseComponents/HorseList.vue'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import * as byPrefixAndName from '@fortawesome/free-solid-svg-icons'
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import axios from 'axios'
import { useGlobalStore } from '@/stores'
import MenuComponent from '@/components/HomeScreen/MenuComponent.vue'

library.add(fas)

export default {
  name: 'App',
  computed: {
    byPrefixAndName () {
      return byPrefixAndName
    },
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  components: {
    MenuComponent,
    FontAwesomeIcon,
    HorseList
  },
  data () {
    return {
      global: null,
      mares: [],
      filteredMares: [],
      token: localStorage.getItem('horseToken') || null,
      role: localStorage.getItem('userRole') || null,
      searchInput: '',
      selectedOption: '',
      isPregnant: '',
      dueDateFrom: '',
      dueDateTo: '',
      colorCode: '',
      timeout: null,
      showFilter: false
    }
  },
  methods: {
    toAddHorsePage () {
      this.$router.push('/addHorse')
    },
    async fetchHorses () {
      try {
        await this.global.getAllHorses()
        this.filteredMares = this.global.mareList
      } catch (error) {
        console.error('Error fetching horses:', error)
      }
    },
    async searchHorses () {
      clearTimeout(this.timeout) // Wis het vorige timeout als het nog actief is
      this.timeout = setTimeout(async () => {
        axios.defaults.headers.common.Authorization = `Bearer ${this.token}`
        try {
          const response = await axios.get(`http://localhost:8081/horses/search?name=${this.searchInput}`)
          this.filteredMares = response.data
        } catch (error) {
          console.error('Error searching horses:', error)
        }
      }, 200) // Wacht 300 milliseconden voordat de zoekopdracht wordt uitgevoerd
    },
    toggleFilter () {
      this.showFilter = !this.showFilter
    },
    async saveFilters () {
      // if (!this.$refs.filterForm.checkValidity()) {
      //   // Als het formulier ongeldig is, geef de standaard foutmelding van het formulier weer
      //   this.$refs.filterForm.reportValidity()
      //   return
      // }
      clearTimeout(this.timeout)
      this.timeout = setTimeout(async () => {
        let endpoint = ''
        if (this.selectedOption === 'mares') {
          endpoint = `http://localhost:8081/mares/filter?isPregnant=${this.isPregnant}&dueDateFrom=${this.dueDateFrom}&dueDateTo=${this.dueDateTo}`
        } else if (this.selectedOption === 'stallions') {
          endpoint = `http://localhost:8081/stallions/filter?colorCode=${this.colorCode}`
        } else {
          this.searchHorses()
          return
        }

        try {
          const filterResponse = await axios.get(endpoint)
          let searchResponse = await axios.get(`http://localhost:8081/horses/search?name=${this.searchInput}`)
          if (this.selectedOption === 'mares') {
            searchResponse = await axios.get(`http://localhost:8081/mares/search?name=${this.searchInput}`)
          } else if (this.selectedOption === 'stallions') {
            searchResponse = await axios.get(`http://localhost:8081/stallions/search?name=${this.searchInput}`)
          }
          const filteredMaresFromFilter = filterResponse.data
          const maresFromSearch = searchResponse.data
          let combinedMares = filteredMaresFromFilter
          if (this.selectedOption === 'mares' && maresFromSearch.length > 0) {
            combinedMares = filteredMaresFromFilter.mares.filter(mareFromFilter => maresFromSearch.some(mareFromSearch => mareFromSearch.id === mareFromFilter.id))
            this.filteredMares.mares = combinedMares
            this.filteredMares.stallions = []
          } else if (this.selectedOption === 'stallions' && maresFromSearch.length > 0) {
            combinedMares = filteredMaresFromFilter.stallions.filter(mareFromFilter => maresFromSearch.some(mareFromSearch => mareFromSearch.id === mareFromFilter.id))
            this.filteredMares.stallions = combinedMares
            this.filteredMares.mares = []
          } else {
            this.searchHorses()
          }
          this.showFilter = false
        } catch (error) {
          console.error('Error saving filters:', error)
        }
      }, 200)
    },
    resetFilters () {
      // Reset alle filters
      this.selectedOption = ''
      this.isPregnant = ''
      this.dueDateFrom = ''
      this.dueDateTo = ''
      this.colorCode = ''
      this.birthDate = ''
      this.fetchHorses()
      this.showFilter = false
    },
    cancelFilter () {
      // Annuleer en verberg het filtervenster
      this.showFilter = false
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.fetchHorses()
  },
  watch: {
    searchInput (newVal) {
      if (newVal.trim() !== '') {
        this.searchHorses()
      } else {
        this.filteredMares = this.mares
      }
    }
  }
}

</script>
<style scoped>
.container {
  z-index: 10;
  display: flex;
  flex-direction: column;
  width: 100vw;
  height:93vh;
  padding: 0 31px 0 31px;
  background-color: #F3DAB1;
  margin: 0;
  align-items: center;
  margin: auto;
}

.main-image {
  width: 210px;
  height: 150px;
  aspect-ratio: 1;
  object-fit: cover;
  max-width: 100%;
  align-self: center;
}

.secondary-container {
  display: flex;
  justify-content: center;
  align-items: stretch;
  padding: 6px;
  border: 1px solid #666;
  border-radius: 7.59px;
  background-color: #fff;
  overflow: hidden;
  max-width: 350px;
  box-shadow: 2px 1px 5px 1px rgba(0, 0, 0, 0.273);
  min-height: 6.3vh;
}
#search{
  border:none;
  width:350px;
  font-size: 15px;
}
#search:focus{
  outline: none;
}
.search-icon{
  margin: 10px;
  align-self: center;
  color:rgba(0, 0, 0, 0.564);
}

#drachtig {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100vw;
}
.buttonContainer {
  display: flex;
  margin-top: 1vh;
  margin-bottom: 1vh;
  width: 100vw;
  justify-content: flex-end;
  max-width: 350px;
}
.filter-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
}
.filter-dialog {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  max-width: 400px;
  width: 100%;
}
.filter-dialog label {
  margin-bottom: 10px;
  font-size: 16px;
  font-family: Poppins, sans-serif;
  display: inline-block;
  width: 10em;
  margin-right: .5em;
}
.filter-dialog input[type="date"],
.filter-dialog input[type="text"],
.filter-dialog select {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 16px;
}
.filter-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
.filter-actions button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}
.filter-actions button.save {
  background-color: #4CAF50;
  color: white;
}
.filter-actions button.reset {
  background-color: #f44336;
  color: white;
}
.filter-actions button.cancel {
  background-color: #ccc;
  color: #333;
}
.filterButton {
  align-self: center;
  margin-right: 4vw;
}
.addButton {
  align-self: center;
}
#noResults {
  position: relative;
  height: 550px;
  max-height:  550px;
  overflow-y: auto;
  width: 100%;
  margin: 0 auto;
  font-size: 16px;
  font-family: Poppins, sans-serif;
}
#noResults b {
  color: #666;
  font-weight: bold;
}

@media screen and (max-height: 600px) {
  .main-image {
    position: absolute;
    opacity: 0;
}
.secondary-container{
  margin-top: 20px;
}
}
</style>
