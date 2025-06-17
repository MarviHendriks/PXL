<template>
  <form class="add-stallion-form" @submit.prevent="addStallion">
    <input type="text" class="info-text" placeholder="Naam" v-model="name" required autofocus>
    <input type="date" class="info-text" placeholder="GeboorteDatum" v-model="birthDate" required autofocus>
    <input type="number" step="any" class="info-text" placeholder="Schofthoogte" v-model="height" required autofocus>
    <input type="text" class="info-text" placeholder="Kleur" v-model="colorCode" required autofocus>
    <button class="save-button">Opslaan</button>
  </form>
</template>

<script>
import { useGlobalStore } from '@/stores'

export default {
  name: 'AddStallionComponent',
  data () {
    return {
      global: null,
      name: '',
      birthDate: '',
      height: '',
      colorCode: '',
      gender: ''
    }
  },
  methods: {
    async addStallion () {
      try {
        const stallion = {
          name: this.name[0].toUpperCase() + this.name.slice(1),
          dateOfBirth: this.birthDate,
          height: this.height,
          colorCode: this.colorCode,
          gender: 'Stallion'
        }
        await this.global.addStallion(stallion)
        this.global.showToast(`${this.name} succesvol gecreëerd`)
        setTimeout(() => {
          this.$router.push('/home')
        }, 500)
      } catch (error) {
        console.error('Fout bij het toevoegen van paard:', error)
      }
    }
  },
  mounted () {
    this.global = useGlobalStore()
  }
}
</script>

<style scoped>
.info-text{
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
.save-button {
  justify-content: center;
  align-items: center;
  border-radius: 9px;
  background-color: #273b4a;
  align-self: center;
  margin-top: 40px;
  width: 217px;
  max-width: 100%;
  color: #fff;
  white-space: nowrap;
  text-align: center;
  letter-spacing: -0.3px;
  padding: 16px 60px;
  font: 600 15px/100% Inter, sans-serif;
}

::-webkit-calendar-picker-indicator{
  margin-right: -10vw;
}

.add-stallion-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
