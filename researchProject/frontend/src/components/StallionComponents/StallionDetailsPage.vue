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
        <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/89797b3dbf8bad6ae46f7d8277195fddc28a3b3e7e65d17066522f8151264a00?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&" alt="Primary animal image" class="primary-image" />
      <div class="stallion-detail-container">
        <h2>{{ stallionDetails.name }}</h2>
        <div class="stallion-edit-options-container">
          <img class="pdfButton" src="https://img.icons8.com/ios/50/attach.png" alt='PDF' @click='goToPDFUploadStallionPage()'>
          <img id="changeButton" @click="goToChangeHorsePage()" v-if="isAdmin" loading="lazy"
               src="https://cdn.builder.io/api/v1/image/assets/TEMP/5d0d36354281e84eb77a644ee731d39dc8749e6a3772f132d18095ed54e20440?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
               alt="Quaternary animal image" class="quaternary-image" />
          <!--<img v-if="isAdmin" loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/3175ede57b77f4901a738d17a81ef4dbece920d9fe3d1a8d94a5667e8b757bf9?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
            alt="Quinary animal image" class="quinary-image" />-->
          <button id="deleteButton" v-if="isAdmin" @click="deleteHorse"><img width="50" height="50"
                                                                             src="https://img.icons8.com/sf-black-filled/64/trash.png" alt="trash" /></button>
        </div>
      </div>
      <div class="stallion-image-container">
        <img loading="lazy"
             src="https://i.imgur.com/wLER7bE.png"
             alt="Tertiary animal image" class="tertiary-image" />
        <div class="text-content">
          <div class="color-code">
            <p>Kleur code: <span>{{stallionDetails.colorCode}}</span></p>
          </div>
          <p class="height">Stokmaat: <span style="font-weight: bold;">{{stallionDetails.height}}m</span></p>
          <p class="dateOfBirth">Geboortedatum: <span style="font-weight: bold;">{{formattedDate}}</span></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useGlobalStore } from '@/stores/index.js'

export default {
  name: 'StallionDetailsPage',
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  data () {
    return {
      global: null,
      stallionDetails: [],
      token: localStorage.getItem('horseToken') || null,
      stallionId: localStorage.getItem('stallionId') || null,
      role: localStorage.getItem('userRole') || null,
      formattedDate: ''
    }
  },
  methods: {
    goBack () {
      this.$router.push('/home')
    },
    goToChangeHorsePage () {
      this.$router.push({
        path: '/changeHorseData',
        query: { gender: 'Stallion' }
      })
    },
    goToPDFUploadStallionPage () {
      this.$router.push({
        path: '/PDFUploadStallion'
      })
    },
    async getStallionById () {
      await this.global.getStallionDetails(this.stallionId)
      this.stallionDetails = this.global.stallion
      this.formattedDate = this.global.formatDate(this.stallionDetails.dateOfBirth)
    },
    async deleteHorse () {
      try {
        const result = await this.global.confirmationPopUp(`Wilt u ${this.stallionDetails.name} verwijderen?`, 'U kan deze actie niet terugdraaien', 'Verwijder')
        if (result.isConfirmed) {
          await this.global.deleteStallion(this.stallionDetails.id)
          this.global.showToast(`${this.stallionDetails.name} succesvol verwijderd`)
          this.$router.push('/home')
        }
      } catch (error) {
        console.error('Error deleting mare', error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.getStallionById(this.stallionId)
  }
}
</script>

<style scoped>
.animal-profile {
  z-index: 10;
  width: 100vw;
  height:100vh;
  padding: 0;
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
.primary-image {
  width: 201px;
  aspect-ratio: 1;
  object-fit: cover;
  margin-left: 10px;
  max-width: 100%;
  align-self: center;
}
.tertiary-image {
  object-fit: cover;
  width:100px;
  align-self: center;
}
#back-button {
  aspect-ratio: 1;
  object-fit: cover;
  align-self: flex-start;
  margin-top: 10vh;
  margin-left: 10vw;
  position: absolute;
}
h2 {
  font-weight: bold;

}

p {
  margin-top: 8px;
}
.stallion-detail-container{
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid #bea276;
  padding: 5px;
}

.stallion-detail-container h2{
  display: inline-block;
  margin: 0;
  vertical-align: middle;
  max-width: 500px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
}
.stallion-image-container{
  margin-top: 10px;
  border-bottom: 2px solid #bea276;
  padding: 5px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}
.stallion-edit-options-container{
  display: inline-block;
  margin: 0;
  /*als je delete buttun hebt verander minwidth naar 150px*/
  min-width: 100px;
}
.text-content {
  flex: 1;
  display: inline-block;
  padding: 0;
  margin: 0;
  width: 50vw;
}
.pdfButton{
  height: 38px;
}

.text-content p {
  width: auto;
  display: inline-block;
  padding: 0;
  margin: 0;
  font-weight:470;
}
.color-code span {
  font-weight: bold;
}
@media screen and (min-width: 800px) {
  .stallion-edit-options-container{
    margin-left: 10vw;
  }
}

h2 {
  font-weight: bold;
}

p {
  margin-top: 8px;
}

#deleteButton {
  background-color: transparent;
  padding: 0;
  border: none;
}

</style>
