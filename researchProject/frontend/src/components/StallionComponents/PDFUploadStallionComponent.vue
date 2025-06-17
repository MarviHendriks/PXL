<template>
  <section class="container">

    <svg fill="#000000" height="40px" width="40px" id="back-button" xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 219.151 219.151" xml:space="preserve" @click='goBack'>
      <g>
        <path
          d="M109.576,219.151c60.419,0,109.573-49.156,109.573-109.576C219.149,49.156,169.995,0,109.576,0S0.002,49.156,0.002,109.575
  C0.002,169.995,49.157,219.151,109.576,219.151z M109.576,15c52.148,0,94.573,42.426,94.574,94.575
  c0,52.149-42.425,94.575-94.574,94.576c-52.148-0.001-94.573-42.427-94.573-94.577C15.003,57.427,57.428,15,109.576,15z" />
        <path d="M94.861,156.507c2.929,2.928,7.678,2.927,10.606,0c2.93-2.93,2.93-7.678-0.001-10.608l-28.82-28.819l83.457-0.008
  c4.142-0.001,7.499-3.358,7.499-7.502c-0.001-4.142-3.358-7.498-7.5-7.498l-83.46,0.008l28.827-28.825
  c2.929-2.929,2.929-7.679,0-10.607c-1.465-1.464-3.384-2.197-5.304-2.197c-1.919,0-3.838,0.733-5.303,2.196l-41.629,41.628
  c-1.407,1.406-2.197,3.313-2.197,5.303c0.001,1.99,0.791,3.896,2.198,5.305L94.861,156.507z" />
      </g>
    </svg>
    <div class='container-main'>
      <main class="main-content">
        <img class="main-image"
          src="https://cdn.builder.io/api/v1/image/assets/TEMP/89797b3dbf8bad6ae46f7d8277195fddc28a3b3e7e65d17066522f8151264a00?apiKey=b0bc2eec7c514b6d83c6e9f7b8834748&"
          alt="" loading="lazy" />
        <p id="title">PDF</p>
        <input class='fileInput' v-if='isAdmin' type='file' @change='onFileChange' />
        <p class="error">{{ error }}</p>
        <button v-if='isAdmin' @click='uploadFile' class="update-button">Upload</button>
        <button @click='readFile' class="update-button">Read</button>
      </main>
    </div>
  </section>
</template>

<script>
import { useGlobalStore } from '@/stores/index.js'
export default {
  name: 'PDFUploadStallionComponent',
  computed: {
    isAdmin() {
      return this.role === 'ADMIN'
    }
  },
  data() {
    return {
      stallionId: localStorage.getItem('stallionId') || null,
      role: localStorage.getItem('userRole') || null,
      global: null,
      file: null,
      error: ''

    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    onFileChange(event) {
      this.file = event.target.files[0]
    },
    uploadFile: async function () {
      this.error = '';
      const formData = new FormData()
      formData.append('file', this.file)
      try {
        await this.global.PDFUploadStallion(this.stallionId, formData)
        this.global.showToast("File is succesvol geupload");
      }
      catch (error) {
        if (!this.file) {
          this.error = 'kies een document'
        } else {
          this.error = 'bestand is te groot!';
        }
      }
    },
    readFile: async function () {
      await this.global.getPDFStallion(this.stallionId)
      this.horseDetails = this.global.stallion
      this.file = this.horseDetails.file
      const reader = new FileReader()
      reader.onload = (res) => {
        this.content = res.target.result
      }
      reader.onerror = (err) => console.log(err)
      reader.readAsText(this.file)
    }
  },
  mounted() {
    this.global = useGlobalStore()
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  width: 100vw;
  height: 100vh;
  padding: 0;
  background-color: #F3DAB1;
  padding-top: 50px;
}

.main-content {
  z-index: 10;
  display: flex;
  width: 100%;
  flex-direction: column;
  padding: 0 21px;
}

.main-image {
  width: 201px;
  aspect-ratio: 1;
  object-fit: cover;
  margin-left: 10px;
  max-width: 100%;
  align-self: center;
}

#back-button {
  aspect-ratio: 1;
  object-fit: cover;
  align-self: flex-start;
  margin-top: -4vh;
  margin-left: 5vw;
  position: absolute;
}

.update-button {
  justify-content: center;
  align-items: center;
  border-radius: 9px;
  background-color: #273b4a;
  align-self: center;
  margin-top: 50px;
  width: 217px;
  max-width: 100%;
  color: #fff;
  white-space: nowrap;
  text-align: center;
  letter-spacing: -0.3px;
  padding: 16px 60px;
  font: 600 15px/100% Inter, sans-serif;
}

#title {
  font-size: 1.6em;
  text-align: center;
  margin-bottom: 20px;
  font-weight: bold;
}

.fileInput {
  align-self: center;
}

.error {
  color: red;
  align-self: center;
  margin: 0;
  margin-top: 20px;
}
</style>
