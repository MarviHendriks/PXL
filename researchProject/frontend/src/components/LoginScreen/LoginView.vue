<template>
  <div class="background">
    <div id="cover"></div>
    <div id="banner">
      <img src="../../assets/logo.png" />
    </div>

    <div class="login">
      <p v-if="errorMessage.length > 0">{{ errorMessage }}</p>
      <form @submit.prevent="login">
        <input v-model="email" type="email" name="email" placeholder="Vul uw emailadres in" required >
        <br>
        <input v-model="password" type="password" name="password"  placeholder="Vul uw wachtwoord in" required >
        <br>
        <button type="submit">Login</button>
      </form>
    </div>
    <div class="login">
      <a @click="goToForgottenPasswordPage">Wachtwoord vergeten?</a>
    </div>
  </div>
</template>

<script>
import { useGlobalStore } from '@/stores'
import router from '@/router'

export default {
  name: 'LoginView',
  data: () => {
    return {
      global: null,
      email: '',
      password: '',
      errorMessage: ''
    }
  },
  methods: {
    goToForgottenPasswordPage () {
      this.$router.push('/forgot-password/')
    },
    async login () {
      try {
        this.errorMessage = ''
        await this.global.login(this.email, this.password)
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
  padding: 5px;
}

#banner img {
  height: 25vh;
  transform: skewY(20deg);
}

#cover {
  background-color: #B8A27F;
  position: absolute;
  width: 60vw;
  height: 20vh;
}

.login {
  display: block;
  text-align: center;
  font-family: Arial, Helvetica, sans-serif;
}

.login input {
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

.login button {
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

.login p {
  color: #ff0000;
  font-size: large;
  margin-bottom: 10px;
}

.background {
  height: 100vh;
  width: 100vw;
  background-color: #F1DAB2;
}

@media screen and (min-width: 800px) {
  #banner {
    transform: skewY(-8deg);
  }

  #banner img {
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
