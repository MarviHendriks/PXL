<template>
  <div class="animal-profile">
    <svg fill="#000000" height="40px" width="40px" id="back-button" xmlns="http://www.w3.org/2000/svg"
         xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 219.151 219.151" xml:space="preserve" @click='goBack'>
      <g>
        <path
          d="M109.576,219.151c60.419,0,109.573-49.156,109.573-109.576C219.149,49.156,169.995,0,109.576,0S0.002,49.156,0.002,109.575
  C0.002,169.995,49.157,219.151,109.576,219.151z M109.576,15c52.148,0,94.573,42.426,94.574,94.575
  c0,52.149-42.425,94.575-94.574,94.576c-52.148-0.001-94.573-42.427-94.573-94.577C15.003,57.427,57.428,15,109.576,15z"/>
        <path d="M94.861,156.507c2.929,2.928,7.678,2.927,10.606,0c2.93-2.93,2.93-7.678-0.001-10.608l-28.82-28.819l83.457-0.008
  c4.142-0.001,7.499-3.358,7.499-7.502c-0.001-4.142-3.358-7.498-7.5-7.498l-83.46,0.008l28.827-28.825
  c2.929-2.929,2.929-7.679,0-10.607c-1.465-1.464-3.384-2.197-5.304-2.197c-1.919,0-3.838,0.733-5.303,2.196l-41.629,41.628
  c-1.407,1.406-2.197,3.313-2.197,5.303c0.001,1.99,0.791,3.896,2.198,5.305L94.861,156.507z"/>
      </g>
    </svg>
    <div class="main-container">
      <!--      <img loading="lazy"-->
      <!--        src="https://cdn.builder.io/api/v1/image/assets/TEMP/89797b3dbf8bad6ae46f7d8277195fddc28a3b3e7e65d17066522f8151264a00?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"-->
      <!--        alt="Primary animal image" class="primary-image" />-->

      <div class="mare-detail-container">
        <h2>{{ mareDetails.name }}</h2>
        <div class="mare-edit-options-container">
          <img class="pdfButton" src="https://img.icons8.com/ios/50/attach.png" alt='PDF' @click='goToPDFUploadPage()'>
          <img id="changeButton" @click="goToChangeHorsePage()" v-if="isAdmin" loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/5d0d36354281e84eb77a644ee731d39dc8749e6a3772f132d18095ed54e20440?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
            alt="Quaternary animal image" class="quaternary-image" />

          <button :disabled="hasFoals" id="add-foal-button-container" @click="addFoalPage" v-if="isAdmin && isPregnant" loading="lazy">
            <img id="addFoalButton"
                 src="https://cdn.builder.io/api/v1/image/assets/TEMP/3175ede57b77f4901a738d17a81ef4dbece920d9fe3d1a8d94a5667e8b757bf9?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
                 alt="Quinary animal image" class="quinary-image" /></button>
          <button id="deleteButton" v-if="isAdmin" @click="deleteHorse"><img width="50" height="50"
                                                                             src="https://img.icons8.com/sf-black-filled/64/trash.png"
                                                                             alt="trash"/></button>
        </div>
      </div>
      <div class="mare-image-container">
        <img loading="lazy" src="https://i.imgur.com/8s1rflF.png" alt="Tertiary animal image" class="tertiary-image" />
        <div class="text-content">
          <div class="breeding-status">
            <p>Drachtig: <span v-if="isPregnant"> {{ mareDetails.daysPregnant }} dagen</span><span v-else>
                Nee</span></p>
          </div>
          <div>
            <p v-if="isPregnant" class="days-to-delivery ">Dagen tot: <span
              v-if="mareDetails.daysUntilDueDate > 0" style="font-weight: bold">{{ mareDetails.daysUntilDueDate }}
              dagen</span>
              <span v-else style="font-weight: bold">Nu</span>
            </p>
          </div>
          <div>
            <p v-if="isPregnant">Verwachte datum: <span style="font-weight: bold">{{ formattedDueDate }}</span></p>
          </div>
          <div>
            <p>Stokmaat: <span style="font-weight: bold">{{ mareDetails.height }}m</span></p>
          </div>
          <div>
            <p>Geboortedatum: <span style="font-weight: bold">{{ formattedBirthDate }}</span></p>
          </div>
        </div>
      </div>
      <div v-if="this.role === 'ADMIN'" id="dekdatums">
        <CoverDatesComponent :isPregnant="isPregnant" id="dairy"></CoverDatesComponent>
      </div>
      <div v-else-if="hasCoverings && this.role === 'STABLEKEEPER'" id="dekdatums">
        <CoverDatesComponent :isPregnant="isPregnant" id="dairy"></CoverDatesComponent>
      </div>
      <DiaryComponent></DiaryComponent>
      <button :hidden="!hasFoals" @click="GoToFoalDetailsPage" class="foal-button">Veulen</button>
    </div>
  </div>
  <MenuComponent/>
</template>

<script>

import { useGlobalStore } from '@/stores/index.js'
import DiaryComponent from '../DiaryComponents/DiaryComponent.vue'
import CoverDatesComponent from '@/components/CoverDatesComponents/CoverDatesComponent.vue'
import MenuComponent from '@/components/HomeScreen/MenuComponent.vue'

export default {
  name: 'MareDetailsPage',
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  data () {
    return {
      global: null,
      coveringDates: [],
      mareDetails: [],
      token: localStorage.getItem('horseToken') || null,
      role: localStorage.getItem('userRole') || null,
      mareId: localStorage.getItem('mareId') || null,
      formattedDueDate: '',
      formattedBirthDate: '',
      hasFoals: false,
      dekDatums: [],
      newDatum: '',
      showInput: false,
      isPregnant: false,
      hasCoverings: false
    }
  },
  components: {
    MenuComponent,
    DiaryComponent,
    CoverDatesComponent
  },
  methods: {
    goBack () {
      this.$router.push('/home')
    },
    goToChangeHorsePage () {
      this.$router.push({
        path: '/changeHorseData',
        query: { gender: 'Mare' }
      })
    },
    goToPDFUploadPage () {
      this.$router.push({
        path: '/PDFUpload'
      })
    },
    addFoalPage () {
      this.$router.push('/addFoal')
    },
    GoToFoalDetailsPage () {
      this.$router.push('/foalDetails')
    },
    async deleteHorse () {
      try {
        const result = await this.global.confirmationPopUp(`Wilt u ${this.mareDetails.name} verwijderen?`, 'U kan deze actie niet terugdraaien', 'Verwijder')
        if (result.isConfirmed) {
          await this.global.deleteMare(this.mareDetails.id)
          this.global.showToast(`${this.mareDetails.name} succesvol verwijderd`)
          this.$router.push('/home')
        }
      } catch (error) {
        console.error('Error deleting mare', error)
      }
    },
    async getMareData () {
      try {
        await this.global.getMareById(this.mareId)
        this.mareDetails = this.global.mare
        this.formattedBirthDate = this.global.formatDate(this.mareDetails.dateOfBirth)
        this.isPregnant = this.mareDetails.pregnant
        this.formattedDueDate = this.global.formatDate(this.mareDetails.dueDate)
        this.hasCoverings = this.mareDetails.coverings.length > 0
        console.log(this.mareDetails)
        await this.getFoalAmount()
      } catch (error) {
        console.log(error)
      }
    },
    async getFoalAmount () {
      await this.global.getFoalAmountOfMare(this.mareId)

      this.hasFoals = this.global.foalAmount === 1
    }
  },
  async mounted () {
    this.global = useGlobalStore()
    this.getMareData(this.mareId)
    await this.global.getMareCoverdates(this.mareId)
    this.hasCoverings = this.global.coverDateEntries.length > 0
  }
}
</script>

<style scoped>

.main-container {
  display: flex;
  flex-direction: column;
  max-width: 500px;
  padding: 0 31px 0 31px;
  background-color: #F3DAB1;
  height: 86vh;
  margin: 7vh auto;
  margin-bottom: 0;
  overflow-y: scroll;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.main-container::-webkit-scrollbar {
  display: none;
}

#back-button {
  aspect-ratio: 1;
  object-fit: cover;
  align-self: flex-start;
  margin-top: -4vh;
  margin-left: 5vw;
  position: absolute;
}

.tertiary-image {
  object-fit: cover;
  width: 100px;
  align-self: center;
}
ul {
  columns: 2;
  -webkit-columns: 2;
  -moz-columns: 2;
}
h2 {
  align-self: center;
  font-weight: bold;

}

p {
  margin-top: 8px;
}

.breeding-status span {
  font-weight: bold;
}

.foal-button {
  justify-content: center;
  align-items: center;
  border-radius: 9px;
  background-color: #273b4a;
  align-self: center;
  width: 217px;
  margin-top: 1.5vh;
  margin-bottom: 1.5vh;
  max-width: 100%;
  color: #fff;
  white-space: nowrap;
  text-align: center;
  letter-spacing: -0.3px;
  padding: 16px 60px;
  font: 600 15px/100% Inter, sans-serif;
  border: 2px solid #273b4a;
  justify-content: center;
}
.foal-button button{
  background-color: #273b4a;
  color: #fff;
  font: 600 15px/100% Inter, sans-serif;
  border: 2px solid #273b4a;
}

.foal-button:disabled {
  background-color: #E1CDAA;
  color: #273b4a;
}

#deleteButton {
  background-color: transparent;
  padding: 0;
  border: none;
}

#add-foal-button-container{
  background-color: transparent;
  padding: 0;
  border: none;
}

#add-foal-button-container:disabled{
  opacity: 0.0;
  position: absolute;
}

.mare-detail-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid #bea276;
  padding: 5px;
}

.mare-detail-container h2 {
  display: inline-block;
  margin: 0;
  vertical-align: middle;
  max-width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
}

.mare-edit-options-container {
  display: inline-block;
  margin: 0;
  /*als je delete buttun hebt verander minwidth naar 150px*/
  min-width: 100px;
}

.mare-image-container {
  margin-top: 10px;
  border-bottom: 2px solid #bea276;
  padding: 5px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.text-content {
  flex: 1;
  display: inline-block;
  padding: 0;
  margin: 0;
  width: 50vw;
}

.text-content p {
  width: auto;
  display: inline-block;
  padding: 0;
  margin: 0;
  font-weight: 470;
}

#coveringDates {
  padding: 5px;
  border-bottom: 2px solid #bea276;
}

.pdfButton{
  height: 38px;
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

  .tertiary-image {
    min-width: 50px;
    width: 25vw;
  }
}

</style>
