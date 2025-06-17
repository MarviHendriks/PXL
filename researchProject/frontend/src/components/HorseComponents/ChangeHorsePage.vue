<template>
  <section class="container">

    <svg  fill="#000000" height="40px" width="40px" id="back-button" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
          viewBox="0 0 219.151 219.151" xml:space="preserve" @click='goBack'>
<g><path d="M109.576,219.151c60.419,0,109.573-49.156,109.573-109.576C219.149,49.156,169.995,0,109.576,0S0.002,49.156,0.002,109.575
  C0.002,169.995,49.157,219.151,109.576,219.151z M109.576,15c52.148,0,94.573,42.426,94.574,94.575
  c0,52.149-42.425,94.575-94.574,94.576c-52.148-0.001-94.573-42.427-94.573-94.577C15.003,57.427,57.428,15,109.576,15z"/>
  <path d="M94.861,156.507c2.929,2.928,7.678,2.927,10.606,0c2.93-2.93,2.93-7.678-0.001-10.608l-28.82-28.819l83.457-0.008
  c4.142-0.001,7.499-3.358,7.499-7.502c-0.001-4.142-3.358-7.498-7.5-7.498l-83.46,0.008l28.827-28.825
  c2.929-2.929,2.929-7.679,0-10.607c-1.465-1.464-3.384-2.197-5.304-2.197c-1.919,0-3.838,0.733-5.303,2.196l-41.629,41.628
  c-1.407,1.406-2.197,3.313-2.197,5.303c0.001,1.99,0.791,3.896,2.198,5.305L94.861,156.507z"/>
</g>
</svg>
    <div class='container-main'>
      <main class="main-content">
        <img class="main-image" src="https://cdn.builder.io/api/v1/image/assets/TEMP/89797b3dbf8bad6ae46f7d8277195fddc28a3b3e7e65d17066522f8151264a00?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&" alt="" loading="lazy" />
        <p id="title">Wijzig {{gender === 'Stallion' ? 'Hengst' : 'Merrie'}}</p>
        <form class="update-horse-form" @submit.prevent="gender === 'Stallion' ? updateStallion() : updateMare()">
        <input type="text" class="info-text" placeholder="Naam" v-model="name" required autofocus>
        <input type="date" class="info-text" placeholder="Geboortejaar" v-model="dateOfBirth" required autofocus>
        <input type="number" step="any" class="info-text" placeholder="Schofthoogte" v-model="height" required autofocus>
        <input type="text" class="info-text" v-if="gender === 'Stallion'" placeholder="Kleurcode" v-model="colorCode" required autofocus>
        <div class="pregnant-radio" v-if="gender === 'Mare' && horseDetails && hasCoverings">
          <input type="radio" name="drachtig" id="pregnant" :checked="marePregnant === true" v-model="marePregnant" v-bind:value="true">
          <label for="pregnant">Drachtig</label>

          <input type="radio" name="drachtig" id="notPregnant" :checked="marePregnant === false" v-model="marePregnant" v-bind:value="false">
          <label for="notPregnant">Niet drachtig</label>
        </div>
        <div v-if="coverings && coverings.length > 0 && gender === 'Mare' " class="coverings-list">
          <h3>Coverings:</h3>
          <ul>
            <li v-for="(covering, index) in coverings" :key="index">{{ covering }}</li>
          </ul>
        </div>
        <button class="save-button">Opslaan</button>
        </form>
      </main>
    </div>
  </section>
</template>

<script>

import { useGlobalStore } from '@/stores/index.js'

export default {
  name: 'ChangeHorsePage',
  data () {
    return {
      global: null,
      coverings: [],
      token: localStorage.getItem('horseToken') || null,
      mareId: localStorage.getItem('mareId') || null,
      stallionId: localStorage.getItem('stallionId') || null,
      gender: this.$route.query.gender,
      name: '',
      dateOfBirth: '',
      height: '',
      colorCode: '',
      hasCoverings: false,
      marePregnant: Boolean(false)
    }
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    async created () {
      try {
        if (this.gender === 'Mare') {
          await this.global.getMareById(this.mareId)
          this.horseDetails = this.global.mare
          this.name = this.horseDetails.name[0].toUpperCase() + this.horseDetails.name.slice(1) || ''
          this.dateOfBirth = this.horseDetails.dateOfBirth || ''
          this.height = this.horseDetails.height || ''
          this.marePregnant = Boolean(this.horseDetails.pregnant)
          this.coverings = this.horseDetails.coverings || ''
        } else if (this.gender === 'Stallion') {
          await this.global.getStallionDetails(this.stallionId)
          this.horseDetails = this.global.stallion
          this.name = this.horseDetails.name[0].toUpperCase() + this.horseDetails.name.slice(1) || ''
          this.colorCode = this.horseDetails.colorCode || ''
          this.height = this.horseDetails.height || ''
          this.dateOfBirth = this.horseDetails.dateOfBirth
        }
      } catch (error) {
        console.log(error)
      }
    },
    async updateMare () {
      try {
        const mare = {
          name: this.name[0].toUpperCase() + this.name.slice(1),
          gender: this.gender,
          isPregnant: this.marePregnant,
          dateOfBirth: this.dateOfBirth,
          height: this.height,
          coverings: this.coverings
        }
        await this.global.updateMare(this.mareId, mare)
        this.global.showToast(`${this.name} succesvol aangepast`)
        this.$router.go(-1)
      } catch (error) {
        console.error('Error updating mare:', error)
      }
    },
    async updateStallion () {
      try {
        const stallion = {
          name: this.name[0].toUpperCase() + this.name.slice(1),
          gender: this.gender,
          dateOfBirth: this.dateOfBirth,
          height: this.height,
          colorCode: this.colorCode
        }
        await this.global.updateStallion(this.stallionId, stallion)
        this.global.showToast(`${this.name} succesvol aangepast`)
        this.$router.go(-1)
      } catch (error) {
        console.error('Error updating stallion:', error)
      }
    },
    async getCoveringsAmount () {
      await this.global.getCoveringAmountOfMare(this.mareId)
      this.hasCoverings = this.global.coveringAmount === 1
    }
  },
  mounted () {
    this.gender = this.$route.query.gender
    this.global = useGlobalStore()
    this.getCoveringsAmount()
    this.created()
  }
}
</script>

<style scoped>
.container {
  z-index: 10;
  display: flex;
  flex-direction: column;
  width: 100vw;
  height:100vh;
  padding: 0;
  background-color: #F3DAB1;
  margin: 0 auto;
}
#title {
  font-size:1.6em;
  text-align: center;
  margin-bottom: 20px;
  font-weight: bold;
}
.info-text {
  border-radius: 7.59px;
  border: 1px solid #666;
  background-color: #fff;
  margin-top: 20px;
  width: 347px;
  align-self: center;
  color: #323941;
  padding: 20px 60px 20px 16px;
  font: 400 16px PT Mono, sans-serif;
}
.save-button {
  justify-content: center;
  align-items: center;
  border-radius: 9px;
  background-color: #273b4a;
  align-self: center;
  margin-top: 40px;
  width: 217px;
  max-width: 100%;
  color: #fff;
  white-space: nowrap;
  text-align: center;
  letter-spacing: -0.3px;
  padding: 16px 60px;
  font: 600 15px/100% Inter, sans-serif;
}

.main-content {
  z-index: 10;
  display: flex;
  width: 100%;
  flex-direction: column;
  padding: 0 21px;
}

.main-image {
  width: 201px;
  aspect-ratio: 1;
  object-fit: cover;
  margin-left: 10px;
  max-width: 100%;
  align-self: center;
}

::-webkit-calendar-picker-indicator{
  margin-right: -10vw;
}
#back-button {
  aspect-ratio: 1;
  object-fit: cover;
  align-self: flex-start;
  margin-top: 10vh;
  margin-left: 10vw;
  position: fixed;
}

.gender-radio input[type="radio"] {
  display: none;
}

.gender-radio label {
  margin-left: 50px;
  display: inline-block;
  margin-right: 35px;
  cursor: pointer;
}

.gender-radio input[type="radio"] + label {
  padding-left: 30px;
  position: relative;
}

.gender-radio input[type="radio"] + label:before {
  content: '';
  position: absolute;
  left: 0;
  top: 2px;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  border: 2px solid #333;
  background-color: #fff;
}

.gender-radio input[type="radio"]:checked + label:before {
  background-color: #333;
}

.pregnant-radio input[type="radio"] {
  display: none;
}
.pregnant-radio{
  display: flex;
  align-self: center;
  gap:8.6vw;
}

.pregnant-radio label {
  margin-top: 10px;
  display: inline-block;
  cursor: pointer;
  font-size: 1.3em;
  text-align: center;

}

.pregnant-radio input[type="radio"] + label {
  padding-left: 30px;
  position: relative;
}

.pregnant-radio input[type="radio"] + label:before {
  content: '';
  position: absolute;
  left: 0;
  top: 0.6vh;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  border: 2px solid #333;
  background-color: #fff;
}

.pregnant-radio input[type="radio"]:checked + label:before {
  background-color: #333;
}

.update-horse-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
