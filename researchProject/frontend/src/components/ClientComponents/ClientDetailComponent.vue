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

      <div class="client-data-container">
        <div class="header">
          <div class="client-edit-options-container">
            <img @click="changeClientPage()" id="changeButton" v-if="isAdmin" loading="lazy"
                 src="https://cdn.builder.io/api/v1/image/assets/TEMP/5d0d36354281e84eb77a644ee731d39dc8749e6a3772f132d18095ed54e20440?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
                 alt="Quaternary animal image" class="quaternary-image" />
            <button @click="deleteClient()" id="deleteButton" v-if="isAdmin"><img width="50" height="50" src="https://img.icons8.com/sf-black-filled/64/trash.png" alt="trash" />
            </button>
          </div>
        </div>
        <div class="clientData">
          <div class="section">
            <p><span class="important-info">Naam</span><br />
              <span class="text-content">{{ clientName }}</span></p>
          </div>
          <div class="section">
            <p><span class="important-info">Contact gegevens</span></p>
            <p><span class="section-content">Email: </span> <span class="text-content">{{ email }}</span></p>
            <p><span class="section-content">GSM-nummer: </span> <span class="text-content">{{ phoneNumber }}</span></p>
          </div>
          <div class="section">
            <p><span class="important-info">Adressen</span></p>
            <p><span class="section-content">Thuisadres:</span> <span class="text-content">{{ homeAddress }}</span></p>
            <p><span class="section-content">Leveradres:</span> <span class="text-content">{{ deliveryAddress }}</span></p>
          </div>
          <div class="section" id="remaining-section">
            <p><span class="important-info">Overige</span></p>
            <p v-if="departureDate == null"><span class="section-content">Vertrekdatum:</span> <span class="text-content">{{ departureMonth.charAt(0).toUpperCase() + departureMonth.slice(1) }}</span></p>
            <p v-else><span class="section-content">Vertrekdatum:</span> <span class="text-content">{{ departureDate }}</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>
  <MenuComponent />
</template>

<script>

import { useGlobalStore } from '@/stores/index.js'
import router from '@/router'
import MenuComponent from '@/components/HomeScreen/MenuComponent.vue'

export default {
  name: 'ClientDetailsPage',
  components: { MenuComponent },
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  data () {
    return {
      global: null,
      clientDetails: null,
      foalId: localStorage.getItem('foalId') || null,
      role: localStorage.getItem('userRole') || null,
      clientName: '',
      email: '',
      phoneNumber: '',
      homeAddress: '',
      deliveryAddress: '',
      departureMonth: '',
      departureDate: null
    }
  },
  methods: {
    goBack () {
      this.$router.push('/foalDetails')
    },
    changeClientPage () {
      router.push('/changeClientData/')
    },
    getDepartureDate () {
      return this.global.departureDate
    },
    async deleteClient () {
      try {
        const result = await this.global.confirmationPopUp('Bent u zeker dat u deze klant wilt verwijderen?', 'U kan deze actie niet terugdraaien', 'Verwijder')
        if (result.isConfirmed) {
          await this.global.deleteClient(this.clientDetails.id, this.foalId)
          this.global.showToast(`${this.clientDetails.name} succesvol verwijderd`)
          this.$router.push('/foalDetails')
        }
      } catch (error) {
        console.error('Error deleting client', error)
      }
    },
    async getClientByFoalId (foalId) {
      try {
        await this.global.getClientByFoalId(foalId)
        this.clientDetails = this.global.client
        this.clientName = this.clientDetails.name
        this.email = this.clientDetails.email
        this.phoneNumber = this.clientDetails.phoneNumber
        this.homeAddress = this.clientDetails.homeAddress
        this.deliveryAddress = this.clientDetails.deliveryAddress
        this.departureMonth = this.clientDetails.movingMonth
        this.departureDate = this.clientDetails.movingDate == null ? null : this.global.formatDate(this.clientDetails.movingDate)
      } catch (error) {
        console.log(error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.getClientByFoalId(this.foalId)
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
  position: absolute;
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
#deleteButton img {
  width: 40px;
  height: 40px;
}

#changeButton {
  width: 40px;
  height: 40px;
}

.client-edit-options-container {
  display: inline-block;
  margin: 0;
  /*als je delete button hebt verander minwidth naar 150px*/
  min-width: 100px;
}

.header {
  padding: 5px;
  text-align: right;
  position: absolute;
  right: 0;
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

.client-data-container {
  margin-top: 10px;
  border-bottom: 2px solid #bea276;
  padding: 20px;
  align-items: center;
  background-color: white;
  border: 2px solid lightgray;
  border-radius: 20px;
  position: relative;
}

.important-info {
  color: black;
  font-weight: bold;
  font-size: large;
}

.text-content {
  font-weight: 500;
  color: black;
  font-size: 1rem;
}

p {
  margin: 0;
  padding: 0;
}

.section {
  padding: 10px 0 10px 0;
  border-bottom: 2px solid lightgray;
}
#remaining-section{
  border-bottom: none;
}

.section-content{
  font-weight: 700;
  color: rgb(56, 56, 56);
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
}
</style>
