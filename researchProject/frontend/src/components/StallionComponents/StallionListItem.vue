<template>
  <article class="card" :id="stallion.id" @click="getDetails(stallion.id)">
    <div class="stallion-card-header">{{ stallion.name }}</div>
    <div class="stallion-card-body">
      <img loading="lazy" src="https://i.imgur.com/wLER7bE.png" alt="Tina" class="card-image" />
      <div class="stallion-data">
        <div class="colorcode-text">
          <span class="text-content">Kleurcode: </span>
          <span class="important-info">{{ stallion.colorCode }}</span>
        </div>
        <div class="height-text">
          <span class="text-content">Stokmaat: </span>
          <span class="important-info">{{ stallion.height.toFixed(2) }}m</span>
        </div>
        <div class="dateofbirth-text">
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
  name: 'StallionListItem',
  props: {
    stallion: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      global: null,
      stallionDetails: [],
      formattedBirthDate: ''
    }
  },
  methods: {
    async getDetails (stallionId) {
      try {
        await this.global.getStallionDetails(stallionId)
        localStorage.setItem('stallionId', stallionId)
        this.stallionDetails = this.global.stallion
        this.$router.push({
          path: '/stallionDetails/'
        })
      } catch (error) {
        console.error('Error fetching horses:', error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
    this.formattedBirthDate = this.global.formatDate(this.stallion.dateOfBirth)
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
  height: 74.073px;
  aspect-ratio: 0.81;
  object-fit: cover;
  margin-left: 10px;
}

.stallion-card-header {
  display: block;
  border-bottom: 2px solid darkgray;
  font-weight: bold;
  padding-bottom: 5px;
  margin-bottom: 5px;
}

.stallion-card-body {
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

.stallion-data {
  flex: 1;
}
</style>
