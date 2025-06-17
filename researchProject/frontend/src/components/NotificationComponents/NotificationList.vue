<template>
  <div class="horse-window">
    <div class="scrollable-content">
      <div v-for="notification in notifications" :key="notification.id" class="notification">
        <NotificationEntryComponent :notification="notification" @updateNotifications="updateAllNotifications"/>
      </div>
    </div>
  </div>
</template>

<script>

import NotificationEntryComponent from '@/components/NotificationComponents/NotificationEntryComponent.vue'
import { useGlobalStore } from '@/stores/index.js'

export default {
  name: 'NotificationList',
  components: { NotificationEntryComponent },

  data () {
    return {
      global: null,
      notification: [],
      notifications: []
    }
  },
  methods: {
    async getNotifications () {
      try {
        await this.global.getNotifications()
        this.notifications = this.global.notifications
      } catch (error) {
        console.error('Error fetching notifications:', error)
      }
    },
    async updateAllNotifications (data) {
      if (data) {
        await this.global.getNotifications()
        this.notifications = this.global.notifications
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
.horse-window {
  position: relative;
  max-height: 570px;
  height: 570px;
  overflow-y: auto;
  width: fit-content;
  align-self: center;
  margin-top: 2vh;
  padding: 10px;
}

.scrollable-content {
  margin-top: -10px;
}
</style>
