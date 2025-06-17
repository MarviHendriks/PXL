<template>
  <div class="background">
    <svg fill="#000000" height="40px" width="40px" id="back-button" xmlns="http://www.w3.org/2000/svg"
         xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 219.151 219.151" xml:space="preserve"
         @click='goBack'>
            <g>
                <path d="M109.576,219.151c60.419,0,109.573-49.156,109.573-109.576C219.149,49.156,169.995,0,109.576,0S0.002,49.156,0.002,109.575
C0.002,169.995,49.157,219.151,109.576,219.151z M109.576,15c52.148,0,94.573,42.426,94.574,94.575
c0,52.149-42.425,94.575-94.574,94.576c-52.148-0.001-94.573-42.427-94.573-94.577C15.003,57.427,57.428,15,109.576,15z" />
              <path d="M94.861,156.507c2.929,2.928,7.678,2.927,10.606,0c2.93-2.93,2.93-7.678-0.001-10.608l-28.82-28.819l83.457-0.008
c4.142-0.001,7.499-3.358,7.499-7.502c-0.001-4.142-3.358-7.498-7.5-7.498l-83.46,0.008l28.827-28.825
c2.929-2.929,2.929-7.679,0-10.607c-1.465-1.464-3.384-2.197-5.304-2.197c-1.919,0-3.838,0.733-5.303,2.196l-41.629,41.628
c-1.407,1.406-2.197,3.313-2.197,5.303c0.001,1.99,0.791,3.896,2.198,5.305L94.861,156.507z" />
            </g>
        </svg>
    <div id="cover"></div>
    <div id="banner">
      <img src="../../assets/logo.png" />
    </div>
    <div class="login">
      <form @submit.prevent="sendResetLink" style="max-width: 400px; margin: 0 auto;">
        <div>
          <div>
            <p>een reset wachtwoord link is naar uw emailadres verstuurd.</p>
          </div>
          <div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { useGlobalStore } from '@/stores'
import router from '@/router'

export default {
  name: 'ConfirmLinkSendView',
  data: () => {
    return {
      global: null,
      email: '',
      password: '',
      errorMessage: ''
    }
  },
  methods: {
    goBack () {
      this.$router.push('/')
    },
    async sendResetLink () {
      try {
        this.errorMessage = ''

        await this.global.sendResetLink(this.email)

        if (this.global.user != null) {
          router.push({ name: 'Dashboard' })
        }
      } catch (error) {
        console.log(error)
        this.errorMessage = error.response.data
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
  }
}
</script>

<style scoped>
#banner {
  display: block;
  text-align: center;
  margin-bottom: 15vh;
  background-color: #B8A27F;
  transform: skewY(-20deg);
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
}

#banner img {
  height: 25vh;
  transform: skewY(20deg);
}

#cover{
  background-color: #B8A27F;
  position: absolute;
  width: 60vw;
  height: 20vh;
}

.login{
  display: block;
  text-align: center;
  font-family: Arial, Helvetica, sans-serif;
}

.login input{
  display: inline-block;
  min-width: 250px;
  width: 85vw;
  padding: 10px;
  margin-bottom: 30px;
  font-size: large;
  border: 1px solid gray;
  border-radius: 7px;
  outline: none;
}
#back-button {
  object-fit: cover;
  align-self: flex-start;
  margin-top: 3vh;
  margin-left: 85vw;
  position: absolute;
  z-index: 99;
}
.login button{
  display: inline-block;
  min-width: 250px;
  width: 85vw;
  padding: 10px;
  font-size: large;
  border: 1px solid gray;
  border-radius: 7px;
  outline: none;
  font-weight: bold;
  background-color: #293B4A;
  color: white;
  margin-bottom: 8px;
}

.background{
  height: 100vh;
  width: 100vw;
  background-color: #F1DAB2;
}

@media screen and (min-width: 800px) {
  #banner{
    transform: skewY(-8deg);
  }

  #banner img{
    transform: skewY(8deg);
  }

}
</style>
