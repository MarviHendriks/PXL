<template>
  <article :class="{ 'card': true, 'unread': notification.unread }" :id="notification.id" v-if="notification.foalId == null" >
    <div @click="goToMare(notification.mareId)">
    <header class="box__header">
      <div class="box__app">
        <p id="name">{{ name }}</p>
      </div>
      <time>
         {{ formattedDate }}
      </time>
    </header>
    <section class="box__content">
      <p>{{ notification.title }}</p>
    </section>
    </div>
  </article>
  <article :class="{ 'card': true, 'unread': notification.unread }" :id="notification.id" v-else >
    <div  @click="goToFoal(this.notification.mareId)">
    <header class="box__header">
      <div class="box__app">
        <p id="name">{{ name }}</p>
      </div>
      <time>
        {{ formattedDate }}
      </time>
    </header>
    <section class="box__content">
      <p>{{ notification.title }}</p>
    </section>
    </div>
  </article>
</template>

<script>
import { useGlobalStore } from '@/stores/index.js'

export default {
  name: 'NotificationEntryComponent',
  computed: {
    isAdmin () {
      return this.role === 'ADMIN'
    }
  },
  props: {
    notification: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      global: null,
      formattedDate: null,
      name: '',
      token: localStorage.getItem('horseToken') || null,
      role: localStorage.getItem('userRole') || null
    }
  },
  methods: {
    async getName () {
      if (this.notification.foalId != null) {
        await this.global.getFoalById(this.notification.foalId)
        this.name = this.global.foal.name
      } else {
        await this.global.getMareById(this.notification.mareId)
        this.name = this.global.mare.name
      }
    },
    async goToMare (mareId) {
      try {
        await this.global.getMareById(mareId)
        localStorage.setItem('mareId', mareId)
        this.$router.push({ path: '/mareDetails/' })
      } catch (error) {
        console.error('Error fetching horses:', error)
      }
    },
    async goToFoal (mareId) {
      try {
        await this.global.getFoalByMareId(mareId)
        localStorage.setItem('mareId', mareId)
        this.$router.push({ path: '/foalDetails' })
      } catch (error) {
        console.error('Error fetching horses:', error)
      }
    },
    async markNotification () {
      await this.global.markNotification(this.notification.id)
      await this.global.getNotifications()
      this.$emit('updateNotifications', true)
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.getName()
    this.formattedDate = this.global.formatDate(this.notification.date)
  }
}
</script>

<style scoped>
.card {
  margin-top: 11px;
  padding: 15px;
  border-left: 4px solid slategray;
  transition: border 0.3s ease;
  border-radius: 10px;
  background-color: #f5f5f5;
  color: black;
  min-width: 90vw;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}

.box__header {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  color: #aaa;
  transition: border 0.3s ease;
}
.box__footer {
  color: #aaa;
  font-size: 0.9rem;
}
.box__app {
  display: flex;
  align-items: center;
  justify-content: center;
}
.unread {
  border-left: 4px solid red;
  transition: border 0.3s ease;
}
.unread:hover {
  border-left-color: #ff6347; /* Maak de border rood bij hover */
}
.box__app p {
  margin: 0 auto;
}
.box__footer p {
  margin-bottom: 0;
  text-decoration: underline;
  font-weight: bold;
  transition: transform 0.3s ease;
  cursor: pointer;
  pointer-events: auto;
}
#name {
  transition: transform 0.3s ease;
  cursor: pointer;
}
</style>
