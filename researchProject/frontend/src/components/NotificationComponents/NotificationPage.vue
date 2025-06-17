<template>

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
  <section class="container">
    <img loading="lazy"
         src="https://cdn.builder.io/api/v1/image/assets/TEMP/89797b3dbf8bad6ae46f7d8277195fddc28a3b3e7e65d17066522f8151264a00?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
         alt="" class="main-image"/>
    <div class="topLine">
    <h2>Meldingen</h2>
    </div>
    <NotificationList/>
  </section>
  <MenuComponent />
</template>

<script>

import { useGlobalStore } from '@/stores/index.js'
import NotificationList from '@/components/NotificationComponents/NotificationList.vue'
import MenuComponent from "@/components/HomeScreen/MenuComponent.vue";
export default {
  name: 'NotificationPage',
  components: {MenuComponent, NotificationList },
  data () {
    return {
      global: null,
      notifications: []
    }
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    async getNotifications () {
      try {
        await this.global.getNotifications()
        this.notifications = this.global.notifications
      } catch (error) {
        console.error('Error fetching notifications:', error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.getNotifications()
  }
}
</script>

<style scoped>
.container {
  z-index: 10;
  display: flex;
  flex-direction: column;
  width: 100vw;
  height:93vh;
  padding: 0 31px 0 31px;
  background-color: #F3DAB1;
  margin: 0 auto;
}
.main-image {
  width: 201px;
  height: 150px;
  aspect-ratio: 1;
  object-fit: cover;
  max-width: 100%;
  align-self: center;
}
#back-button {
  aspect-ratio: 1;
  object-fit: cover;
  align-self: flex-start;
  margin-top: 3vh;
  margin-left: 5vw;
  position: absolute;
  cursor: pointer; /* Added */
  transition: transform 0.3s ease; /* Added */
}
p {
  margin: 0;
  padding: 0;
}
h2 {
  align-self: center;
  font-weight: bold;

}
.topLine {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid #bea276;
  padding: 5px;
}

.topLine h2 {
  display: inline-block;
  margin: 0;
  vertical-align: middle;
  max-width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
}

</style>
