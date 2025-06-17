<template>
  <section class="container">

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
    <div class='container-main'>
      <main class="main-content">
        <img class="main-image"
             src="https://cdn.builder.io/api/v1/image/assets/TEMP/89797b3dbf8bad6ae46f7d8277195fddc28a3b3e7e65d17066522f8151264a00?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
             alt="" loading="lazy" />
      </main>
      <p id="title">Voeg klant toe aan {{ this.foalName }}</p>
    </div>
    <form class="add-client-form" @submit.prevent="addClient">
      <input type="text" class="info-text" placeholder="Naam" v-model="name" autofocus required>
      <input type="email" class="info-text" placeholder="Email" v-model="email" autofocus required>
      <input type="tel" class="info-text" placeholder="Telefoon" v-model="phoneNumber" autofocus required>
      <input type="text" class="info-text" placeholder="Adres" v-model="homeAddress" autofocus required>
      <input type="text" class="info-text" placeholder="LeveringsAdress" v-model="deliveryAddress" autofocus required>
      <input type="text" class="info-text" placeholder="Vertrekmaand" pattern="januari|februari|maart|april|mei|juni|juli|augustus|september|oktober|november|december" title="Geef een geldige maand (bv. januari)" v-model="movingMonth" oninput="this.value = this.value.toLowerCase()" autofocus required>
      <button class="save-button">Opslaan</button>
    </form>
  </section>
</template>

<script>
import router from '@/router'
import { useGlobalStore } from '@/stores'

export default {
  name: 'AddClientComponent',
  data () {
    return {
      global: null,
      foalDetails: null,
      mareId: localStorage.getItem('mareId') || null,
      foalName: '',
      name: '',
      email: '',
      phoneNumber: '',
      homeAddress: '',
      deliveryAddress: '',
      movingMonth: '',
      monthMap: {
        januari: '01',
        februari: '02',
        maart: '03',
        april: '04',
        mei: '05',
        juni: '06',
        july: '07',
        augustus: '08',
        september: '09',
        oktober: '10',
        november: '11',
        december: '12'
      }
    }
  },
  methods: {
    goBack () {
      router.push('/foalDetails')
    },
    async getFoalData () {
      try {
        await this.global.getFoalByMareId(this.mareId)
        this.foalDetails = this.global.foal
        this.foalName = this.foalDetails.name
      } catch (error) {
        console.log(error)
      }
    },
    async addClient () {
      try {
        let nameParts = this.name.split(' ')
        nameParts = nameParts.map(part => part.charAt(0).toUpperCase() + part.slice(1))
        const client = {
          name: nameParts.join(' '),
          email: this.email,
          phoneNumber: this.phoneNumber,
          homeAddress: this.homeAddress,
          deliveryAddress: this.deliveryAddress,
          movingMonth: this.movingMonth
        }
        await this.global.addClient(client, this.foalDetails.id)
        this.global.showToast(`${this.name} succesvol gecreëerd`)
        await router.push('/clientDetails')
      } catch (error) {
        console.error('Fout bij het toevoegen van klant:', error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.getFoalData()
  }
}
</script>

<style scoped>
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

.add-client-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.save-button {
  justify-content: center;
  align-items: center;
  border-radius: 9px;
  background-color: #273b4a;
  align-self: center;
  margin-top: 50px;
  width: 217px;
  max-width: 100%;
  color: #fff;
  white-space: nowrap;
  text-align: center;
  letter-spacing: -0.3px;
  padding: 16px 60px;
  font: 600 15px/100% Inter, sans-serif;
}

::-webkit-calendar-picker-indicator {
  margin-right: -10vw;
}

.container {
  z-index: 10;
  display: flex;
  flex-direction: column;
  width: 100vw;
  height: 100vh;
  padding: 0;
  background-color: #F3DAB1;
  margin: 0 auto;
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

#back-button {
  aspect-ratio: 1;
  object-fit: cover;
  align-self: flex-start;
  margin-top: 10vh;
  margin-left: 10vw;
  position: absolute;
}

#title {
  font-size: 1.6em;
  text-align: center;
  margin-bottom: 20px;
  font-weight: bold;
}

</style>
