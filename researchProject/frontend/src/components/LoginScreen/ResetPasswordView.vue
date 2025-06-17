<template>
  <div class="background">
    <div id="cover"></div>
    <div id="banner">
      <img src="../../assets/logo.png" />
    </div>
<div class="login">
    <form @submit.prevent="changePassword">
      <div>
        <h3>Stel uw nieuw wachtwoord in</h3>
      </div>
      <input type="hidden" name="token" v-model="token" />
      <div>
        <div>
          <p>
            <input v-model="password" type="password" name="password" id="password" class="form-control"
                   placeholder="Nieuw wachtwoord" required autofocus />
          </p>
          <p>
            <input v-model="confirmPassword" type="password" class="form-control" placeholder="Herhaal wachtwoord"
                   required />
          </p>
          <p class="text-center">
            <button type="submit">Verander wachtwoord</button>
          </p>
        </div>
      </div>
    </form>
</div>
  </div>
</template>

<script>
import { useGlobalStore } from '@/stores'

export default {
  name: 'ResetPasswordView',
  data: () => {
    return {
      global: null,
      token: '',
      password: '',
      confirmPassword: '',
      errorMessage: ''
    }
  },
  methods: {
    async changePassword () {
      try {
        this.errorMessage = ''
        if (this.password !== this.confirmPassword) {
          this.errorMessage = 'Passwords do not match'
          await this.global.confirmationPopUp('Uw wachtwoorden komen niet overeen', 'Gelieve het zelfde wachtwoord op te geven', 'Ok')
          return
        }

        if (this.global.validatePassword(this.password) === false) {
          await this.global.confirmationPopUp('Wachtwoord is niet complex', 'Maak gebruik van een hoofdletter, nummer,speciaal karakter en minstens een lengte van 8 karakters', 'Ok')
          return
        }
        await this.global.changePassword(this.token, this.password, this.confirmPassword)
        this.global.showToast('Wachtwoord succesvol gewijzigd')
        this.$router.push({ name: 'Dashboard' })
      } catch (error) {
        console.log(error)
        this.errorMessage = error.response.data
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.token = this.$route.params.token
    console.log(this.token)
  }
}
</script>

<style scoped>
#banner{
  display: block;
  text-align: center;
  margin-bottom: 15vh;
  background-color: #B8A27F;
  transform: skewY(-20deg);
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
  padding: 5px;
}

#banner img{
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
  font-size: large;
  border: 1px solid gray;
  border-radius: 7px;
  outline: none;
  margin-bottom: 8px;
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

@media screen and (min-width: 600px) {
  #banner {
    display: block;
    text-align: center;
    margin-bottom: 15vh;
    background-color: #B8A27F;
    transform: skewY(-4deg);
    border-bottom-left-radius: 20px;
    border-bottom-right-radius: 20px;
  }

  #banner img {
    height: 25vh;
    transform: skewY(4deg);
  }

  #cover {
    background-color: #B8A27F;
    position: absolute;
    width: 60vw;
    height: 10vh;
  }
}
</style>
