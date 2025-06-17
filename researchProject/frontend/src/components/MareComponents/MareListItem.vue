<template>
  <article class="card" :id="mare.id" @click="getDetails(mare.id)">
    <div class="mare-card-header">{{ mare.name }}
    </div>
    <div class="mare-card-body">
      <img loading="lazy" src="https://i.imgur.com/8s1rflF.png" alt="Tina" class="card-image" />
      <div class="mare-data">
        <div v-if="mare.pregnant" class="count-down-text">
          <span class="text-content">Dagen tot: </span>
          <span class="important-info" v-if="!mare.pregnant" style="font-weight: bold">/</span>
          <span class="important-info" v-else-if="parseInt(mare.daysUntilDueDate) <= 0"
            style="font-weight: bold">Nu</span>
          <span class="important-info" v-else style="font-weight: bold">{{ mare.daysUntilDueDate }} dagen</span>
        </div>
        <div class="status-text">
          <span class="text-content">Drachtig: </span>
          <span class="important-info" v-if="mare.pregnant">{{ mare.daysPregnant }} dagen</span>
          <span class="important-info" v-else>Nee</span>
        </div>
        <div v-if="mare.pregnant">
          <span class="text-content">Uitgerekende datum: </span>
          <span v-if="mare.pregnant" class="important-info">{{ formattedDueDate }}</span>
          <span v-else class="important-info">/</span>
        </div>
        <div v-if="!mare.pregnant" class="height-text">
          <span class="text-content">Stokmaat: </span>
          <span class="important-info">{{ mare.height.toFixed(2) }}m</span>
        </div>
        <div v-if="!mare.pregnant" class="dateofbirth-text">
          <span class="text-content">Geboortedatum: </span>
          <span class="important-info">{{ formattedBirthDate }}</span>
        </div>
      </div>
    </div>
  </article>
</template>

<script>
import { useGlobalStore } from '@/stores/index.js'

export default {
  name: 'MareListItem',
  props: {
    mare: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      mareDetails: [],
      formattedDueDate: '',
      formattedHeight: 0,
      formattedBirthDate: ''
    }
  },
  methods: {
    async getDetails (mareId) {
      try {
        await this.global.getMareById(mareId)
        localStorage.setItem('mareId', mareId)
        this.mareDetails = this.global.mare

        this.$router.push({ path: '/mareDetails/' })
      } catch (error) {
        console.error('Error fetching horses:', error)
      }
    }
  },
  async mounted () {
    this.global = useGlobalStore()
    this.formattedDueDate = this.global.formatDate(this.mare.dueDate)
    this.formattedBirthDate = this.global.formatDate(this.mare.dateOfBirth)
  }
}
</script>

<style scoped>
.card {
  margin-top: 11px;
  padding: 8px;
  border: none;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  background-color: #f5f5f5;
  color: black;
  width: 100%;
}

.card-image {
  width: 60px;
  aspect-ratio: 0.81;
  object-fit: cover;
  margin-left: 10px;
}
.mare-card-header{
  border-bottom: 2px solid darkgray;
  display: block;
  font-weight: bold;
  padding-bottom: 5px;
  margin-bottom: 5px;
}
.mare-card-body {
  display: flex;
  flex-wrap: wrap;
  padding-bottom: 5px;
  margin-bottom: 5px;
  border-bottom: 2px solid darkgray;
}

.important-info {
  color: black;
  font-weight: bold;
}

.text-content {
  font-weight: 500;
  color: black;
}

.mare-data {
  flex: 1;
}
</style>
