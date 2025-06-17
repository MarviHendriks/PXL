<template>
  <div class="horse-window">
    <div class="scrollable-content">
    <div v-for="mare in mares.mares" :key="mare.id" class="mare">
      <MareListItem :mare="mare"/>
    </div>
      <div v-for="stallion in mares.stallions" :key="stallion.id" class="stallion">
        <StallionListItem :stallion="stallion"/>
      </div>
    </div>
  </div>
</template>


<script>
import MareListItem from '@/components/MareComponents/MareListItem.vue'
import StallionListItem from '@/components/StallionComponents/StallionListItem.vue'

export default {
  name: 'HorseList',
  props: {
    mares: {
      type: Object,
      required: true
    }
  },
  components: {
    StallionListItem,
    MareListItem
  },
  data () {
    return {
      mare: [],
      token: localStorage.getItem('horseToken') || null
    }
  }
}

</script>
<style scoped>
.horse-window {
  overflow-y: auto;
  width: 90vw;
  padding-bottom: 20px;
}
.horse-window::-webkit-scrollbar {
  width: 5px;
  margin-left: 10px;
}

.horse-window::-webkit-scrollbar-track {
  border-radius: 8px;
  background-color: #e7e7e7;
  border: 1px solid #cacaca;
  box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
}

.horse-window::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #363636;
}
.stallion {
  width: 100%;
  margin: 0 auto;
  padding: 0;
}
.mare {
  width: 100%;
  margin: 0 auto;
  padding: 0;
}
.scrollable-content {
  margin-top: -10px;
}
html, body {
  scrollbar-gutter: stable;
}
@media screen and (min-width: 600px) {
  .horse-window {
  width: 50vw;
}
}
</style>
