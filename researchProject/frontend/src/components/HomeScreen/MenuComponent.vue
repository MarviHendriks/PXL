<template>
  <footer>
    <font-awesome-icon :icon="['fas', 'house']" size="2x" class="home-button" @click="goToHomePage()"/>
    <div class="notification-button">
    <font-awesome-icon :icon="['fas', 'bell']" size="2x"  @click="goToNotificationPage" />
    <div class="notification" role="status">{{ amountOfUnreadMessages }}</div>
    </div>
  </footer>
</template>
<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import * as byPrefixAndName from '@fortawesome/free-solid-svg-icons'
import { useGlobalStore } from '@/stores/index.js'

export default {
  name: 'MenuComponent',
  computed: {
    byPrefixAndName () {
      return byPrefixAndName
    },
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  components: {
    FontAwesomeIcon
  },
  data () {
    return {
      global: null,
      amountOfUnreadMessages: 0,
      token: localStorage.getItem('horseToken') || null
    }
  },
  methods: {
    goToHomePage () {
      this.$router.push('/home')
    },
    goToNotificationPage () {
      this.$router.push('/notifications')
    },
    async getAmountOfUnreadNotifications () {
      this.amountOfUnreadMessages = await this.global.getAmountOfUnreadNotifications()
    },
    async keepUptoDate () {
      setInterval(() => {
        this.getAmountOfUnreadNotifications()
      }, 3600000)
    }

  },
  mounted () {
    this.global = useGlobalStore()
    this.getAmountOfUnreadNotifications()
  }
}

</script>

<style scoped>
footer {
  display: flex;
  height: 6vh;
  min-height: 40px;
  position: absolute;
  bottom: 0;
  width: 100vw;
  background-color: #bea276;
  border-radius: 10px 10px 0 0;
  justify-content: space-between;
  overflow-x: hidden;
  max-width: 100vw;
}
.home-button {
  margin-left: 2vw;
  align-self: center;
}
.notification-button {
  margin-right: 2vw;
  align-self: center;
}
.notification {
  position: absolute;
  right: 0.4em;
  top: 0.9em;
  min-width: 1.6em;
  height: 1.6em;
  border-radius: 0.8em;
  border: 0.05em solid white;
  background-color: red;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.6em;
  color: white;
}
</style>
