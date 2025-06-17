<template>
  <div class="animal-profile">
    <svg fill="#000000" height="40px" width="40px" id="back-button" xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 219.151 219.151" xml:space="preserve" @click='goBack'>
      <g>
        <path
          d="M109.576,219.151c60.419,0,109.573-49.156,109.573-109.576C219.149,49.156,169.995,0,109.576,0S0.002,49.156,0.002,109.575
  C0.002,169.995,49.157,219.151,109.576,219.151z M109.576,15c52.148,0,94.573,42.426,94.574,94.575
  c0,52.149-42.425,94.575-94.574,94.576c-52.148-0.001-94.573-42.427-94.573-94.577C15.003,57.427,57.428,15,109.576,15z" />
        <path d="M94.861,156.507c2.929,2.928,7.678,2.927,10.606,0c2.93-2.93,2.93-7.678-0.001-10.608l-28.82-28.819l83.457-0.008
  c4.142-0.001,7.499-3.358,7.499-7.502c-0.001-4.142-3.358-7.498-7.5-7.498l-83.46,0.008l28.827-28.825
  c2.929-2.929,2.929-7.679,0-10.607c-1.465-1.464-3.384-2.197-5.304-2.197c-1.919,0-3.838,0.733-5.303,2.196l-41.629,41.628
  c-1.407,1.406-2.197,3.313-2.197,5.303c0.001,1.99,0.791,3.896,2.198,5.305L94.861,156.507z" />
      </g>
    </svg>
    <div class="main-container">
      <img loading="lazy"
        src="https://cdn.builder.io/api/v1/image/assets/TEMP/89797b3dbf8bad6ae46f7d8277195fddc28a3b3e7e65d17066522f8151264a00?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
        alt="Primary animal image" class="primary-image" />
      <div class="header">
        <h2>{{ name }}</h2>
        <div class="foal-edit-options-container">
          <img @click="changeFoalPage()" id="changeButton" v-if="isAdmin" loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/5d0d36354281e84eb77a644ee731d39dc8749e6a3772f132d18095ed54e20440?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
            alt="Quaternary animal image" class="quaternary-image" />
          <button @click="deleteFoal()" id="deleteButton" v-if="isAdmin"><img width="50" height="50"
              src="https://img.icons8.com/sf-black-filled/64/trash.png" alt="trash" /></button>
        </div>
      </div>
      <div class="foal-data-container">
        <img v-if="gender == 'mare'" loading="lazy" src="https://i.imgur.com/8s1rflF.png" alt="Tertiary animal image"
          class="tertiary-image" />
        <img v-else-if="gender == 'stallion'" loading="lazy" src="https://i.imgur.com/wLER7bE.png"
          alt="Tertiary animal image" class="tertiary-image" />
        <div class="foalData" v-if="height != 0">
          <p><span class="text-content">Moeder:</span> <span class="important-info">{{ mother }}</span></p>
          <p><span class="text-content">Vader:</span> <span class="important-info">{{ father }}</span></p>
          <p><span class="text-content">Stokmaat:</span> <span class="important-info">{{ height }}m</span></p>
          <p><span class="text-content">Geboortedatum:</span> <span class="important-info">{{ birthDate }}</span></p>
        </div>
      </div>
      <div class="deworming-container">
        <DewormingComponent @dewormingUpdated="dewormingHandler" v-for="deworming in dewormings" :key="deworming.id" :dewormingId="deworming.id"
          :title="deworming.title" :date="deworming.date" :isDone="deworming.done"></DewormingComponent>
      </div>
    </div>
    <div class='client-add-button'>
      <button v-if="isAdmin && !hasClients" @click='navigateToAddClientPage()'>Klant toevoegen</button>
      <button v-else-if="hasClients" @click='navigateToClientDetailPage()'>Klant</button>
    </div>
  </div>
  <MenuComponent />
</template>
<script>

import { useGlobalStore } from '@/stores/index.js'
import DewormingComponent from './DewormingComponent.vue'
import router from '@/router'
import MenuComponent from '@/components/HomeScreen/MenuComponent.vue';

export default {
  name: 'FoalDetailsPage',
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  data () {
    return {
      global: null,
      foalDetails: null,
      dewormings: [],
      token: localStorage.getItem('horseToken') || null,
      role: localStorage.getItem('userRole') || null,
      mareId: localStorage.getItem('mareId') || null,
      mother: localStorage.getItem('mareName') || null,
      gender: '',
      height: 0,
      birthDate: '',
      father: '',
      name: '',
      medicated: false,
      hasClients: false
    }
  },
  components: {
    MenuComponent,
    DewormingComponent
  },
  methods: {
    goBack () {
      router.push('/mareDetails')
    },
    changeFoalPage () {
      router.push('/changeFoalData/')
    },
    navigateToAddClientPage () {
      router.push('/addClient/')
    },
    navigateToClientDetailPage () {
      router.push('/clientDetails/')
    },
    async getFoalByMareId (mareId) {
      try {
        await this.global.getFoalByMareId(mareId)
        this.foalDetails = this.global.foal
        this.gender = this.foalDetails.gender.toLowerCase()
        this.height = this.foalDetails.height
        this.birthDate = this.global.formatDate(this.foalDetails.dateOfBirth)
        this.father = this.foalDetails.stallion
        this.name = this.foalDetails.name
        this.dewormings = this.foalDetails.dewormings
        localStorage.setItem('foalId', this.foalDetails.id)

        this.isMedicated()
        this.hasClient()
      } catch (error) {
        console.log(error)
      }
    },
    async deleteFoal () {
      try {
        const result = await this.global.confirmationPopUp(`Bent u zeker dat u ${this.name} wilt verwijderen?`, 'U kan deze actie niet terugdraaien', 'Verwijder')
        if (result.isConfirmed) {
          await this.global.deleteFoal(this.foalDetails.id, this.mareId)
          this.global.showToast(`${this.foalDetails.name} succesvol verwijderd`)
          router.push('/mareDetails')
        }
      } catch (error) {
        console.error('Error deleting mare', error)
      }
    },
    dewormingHandler (data) {
      if (data) {
        this.getFoalByMareId(this.mareId)
      }
    },
    isMedicated () {
      this.medicated = true
      for (let i = 0; i < this.dewormings.length; i++) {
        if (!this.dewormings[i].done) {
          this.medicated = false
        }
      }
    },
    async hasClient () {
      await this.global.foalHasClient(this.foalDetails.id)
      this.hasClients = this.global.hasClient
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.getFoalByMareId(this.mareId)
  }
}
</script>

<style scoped>
.animal-profile {
  z-index: 10;
  width: 100vw;
  height: 93vh;
  background-color: #F3DAB1;
}

.main-container {
  display: flex;
  flex-direction: column;
  max-width: 500px;
  padding: 0 31px 0 31px;
  background-color: #F3DAB1;
  margin: 0 auto;
}

#back-button {
  aspect-ratio: 1;
  object-fit: cover;
  align-self: flex-start;
  margin-top: 10vh;
  margin-left: 10vw;
  position: fixed;
}

.primary-image {
  width: 201px;
  aspect-ratio: 1;
  object-fit: cover;
  margin-left: 10px;
  max-width: 100%;
  align-self: center;
}

#deleteButton {
  background-color: transparent;
  padding: 0;
  border: none;
}

#deleteButton:disabled {
  opacity: 0.7;
}

.foal-edit-options-container {
  display: inline-block;
  margin: 0;
  /*als je delete buttun hebt verander minwidth naar 150px*/
  min-width: 100px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid #bea276;
  padding: 5px;
}

.header h2 {
  display: inline-block;
  margin: 0;
  vertical-align: middle;
  max-width: 500px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  font-weight: bold;
}

.foal-data-container {
  margin-top: 10px;
  border-bottom: 2px solid #bea276;
  padding: 5px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.tertiary-image {
  object-fit: cover;
  width: 100px;
  align-self: center;
}

.important-info {
  color: black;
  font-weight: bold;
  font-size: 1rem;
}

.text-content {
  font-weight: 500;
  color: black;
  font-size: 1rem;
}

.mare-delete-button{
  color: white;
  background-color: black;
  border-radius: 10px;
  border: none;
  font-weight: bold;
  padding: 8px;
  width: 50vw;
  min-width: 200px;
  align-self: center;
}

.deworming-container{
  background-color: #f5f5f5;
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
}

p {
  margin: 0;
  padding: 0;
}
.client-add-button{
  text-align: center;
}
.client-add-button button{
  justify-content: center;
  align-items: center;
  border-radius: 9px;
  background-color: #273b4a;
  align-self: center;
  width: 217px;
  margin-top: 10%;
  margin-bottom: 20px;
  max-width: 100%;
  color: #fff;
  white-space: nowrap;
  text-align: center;
  letter-spacing: -0.3px;
  padding: 16px 50px;
  font: 600 15px/100% Inter, sans-serif;
  border: 2px solid #273b4a;
}
@media screen and (min-width: 800px) {
  .mare-edit-options-container {
    margin-left: 10vw;
  }

}

@media screen and (max-width: 410px) {
  .text-content {
    font-size: 3.5vw;
  }

  .important-info {
    font-size: 3.5vw;
  }

  .tertiary-image {
    min-width: 50px;
    width: 25vw;
  }
}
</style>
