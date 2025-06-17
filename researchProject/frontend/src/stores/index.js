import { defineStore } from 'pinia'
import axios from 'axios'
import Swal from 'sweetalert2'

const url = 'http://localhost:8081'

export const useGlobalStore = defineStore('global', {
  state: () => {
    return {
      pdfFile: null,
      hasClient: false,
      user: null,
      token: localStorage.getItem('horseToken') || null,
      role: localStorage.getItem('userRole') || null,
      mareId: localStorage.getItem('mareId') || null,
      foalId: localStorage.getItem('foalId') || null,
      stallionId: localStorage.getItem('stallionId') || null,
      mare: null,
      stallion: null,
      mareList: [],
      foal: null,
      diaryEntries: [],
      mareName: localStorage.getItem('mareName') || null,
      foalAmount: 0,
      coveringAmount: 0,
      coverDateEntries: [],
      allUsers: [],
      client: null,
      passwordIsValid: false,
      notifications: []
    }
  },
  actions: {
    async login (email, password) {
      this.user = null

      const userLogin = {
        email,
        password
      }

      const response = await axios.post(`${url}/login`, userLogin)
      this.user = response.data.userDTO
      this.token = localStorage.setItem('horseToken', response.data.token)
      this.role = localStorage.setItem('userRole', response.data.userDTO.role)
    },
    async getMareById (mareId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/mares/search/${mareId}`)
      this.mare = response.data
      this.mareName = localStorage.setItem('mareName', response.data.name)
    },
    async updateMare (mareId, mare) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.put(`http://localhost:8081/mares/change/${mareId}`, mare)
      this.mare = response.data
    },
    async updateDiary (mareId, diary) {
      await axios.put(`http://localhost:8081/mares/${mareId}/diary`, { diary },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('horseToken')}`
          }
        }
      )
    },
    async getStallionDetails (stallionId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/stallions/search/${stallionId}`)
      this.stallion = response.data
    },
    async addMare (mare) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.post('http://localhost:8081/mares/add', mare)
    },
    async getFoalAmountOfMare (id) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/mares/${id}/foals`)
      this.foalAmount = response.data
    },
    async addStallion (stallion) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.post('http://localhost:8081/stallions/add', stallion)
    },
    async deleteStallion (stallionId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.delete(`http://localhost:8081/stallions/${stallionId}`)
    },
    async deleteMare (mareId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.delete(`http://localhost:8081/mares/${mareId}`)
    },
    async addFoal (foal, id) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.post(`http://localhost:8081/foals/mare/${id}/add`, foal)
    },
    async getFoalByMareId (mareId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/foals/search/${mareId}`)
      this.foal = response.data
    },
    async getFoalById (foalId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/foals/search/foal/${foalId}`)
      this.foal = response.data
    },
    async deleteFoal (foalId, mareId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.delete(`http://localhost:8081/foals/${foalId}/delete`, { data: { mareId } })
    },
    async updateFoal (foalId, foal) {
      const body = {
        name: foal.name,
        dateOfBirth: foal.dateOfBirth,
        height: foal.height,
        stallion: foal.stallion
      }

      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.put(`http://localhost:8081/foals/${foalId}/update`, body)
    },
    async updateDeworming (dewmormingId, isDone) {
      const body = {
        isDone
      }

      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.put(`http://localhost:8081/foals/deworming/${dewmormingId}/update`, body)
    },
    async updateStallion (stallionId, stallion) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.put(`http://localhost:8081/stallions/change/${stallionId}`, stallion)
      this.stallion = response.data
    },
    async getAllHorses () {
      axios.defaults.withCredentials = true
      const axiosConfig = { withCredentials: 'true' }
      const response = await axios.get('http://localhost:8081/horses', axiosConfig)
      this.mareList = response.data
    },
    async getMareDiary (mareId) {
      this.diaryEntries = []
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/mares/${mareId}/diary`)
      this.diaryEntries = response.data
    },
    async deleteMareEntry (diaryId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.delete(`http://localhost:8081/mares/diary/${diaryId}/delete`)
    },
    async addEntryToDiary (mareId, entry) {
      const newEntry = {
        entry
      }

      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.post(`http://localhost:8081/mares/${mareId}/diary/add`, newEntry)
    },
    async updateDiaryEntry (diaryId, entry) {
      const newEntry = {
        entry
      }

      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.put(`http://localhost:8081/mares/diary/${diaryId}/update`, newEntry)
    },
    async getMareCoverdates (mareId) {
      this.coverDateEntries = []
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/mares/${mareId}/coverDates`)
      this.coverDateEntries = response.data
    },
    async addEntryToCoverdates (mareId, entry) {
      const newEntry = {
        entry
      }
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.post(`http://localhost:8081/mares/${mareId}/coverDates/add`, newEntry)
    },
    async updateCoverdateEntry (coverDateId, entry) {
      const newEntry = {
        entry
      }
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.put(`http://localhost:8081/mares/coverDates/${coverDateId}/update`, newEntry)
    },
    async deleteCoverdateEntry (coverDateId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.delete(`http://localhost:8081/mares/coverDates/${coverDateId}/delete`)
    },
    async getAllStallions () {
      axios.defaults.withCredentials = true
      const axiosConfig = { withCredentials: 'true' }
      const response = await axios.get('http://localhost:8081/stallions', axiosConfig)
      this.stallionList = response.data
    },
    async addClient (client, foalId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.post(`http://localhost:8081/clients/foal/${foalId}/add`, client)
    },
    async foalHasClient (foalId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/foals/${foalId}/clients`)
      this.hasClient = response.data
    },
    async getClientByFoalId (foalId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/clients/search/${foalId}`)
      this.client = response.data
    },
    async deleteClient (clientId, foalId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.delete(`http://localhost:8081/clients/${clientId}/delete`, { data: { foalId } })
    },
    async updateClient (clientId, client) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.put(`http://localhost:8081/clients/${clientId}/update`, client)
    },
    async getCoveringAmountOfMare (id) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/mares/${id}/coverings`)
      this.coveringAmount = response.data
    },
    async changePassword (token, password, confirmPassword) {
      const body = { newPassword: password, passwordConfirmation: confirmPassword }
      await axios.post(`http://localhost:8081/password/reset/${token}`, body)
    },
    async sendResetLink (email) {
      await axios.post(`http://localhost:8081/password/send/${email}`, email)
    },
    async findAllUsers () {
      const response = await axios.get('http://localhost:8081/login/users')
      this.allUsers = response.data
    },
    async PDFUpload (mareId, pdfFile) {
      console.log('I am here')
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.post(`http://localhost:8081/mares/${mareId}/pdf`, pdfFile, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
    },
    async getPDF (mareId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/mares/${mareId}/pdf/read`, {
        responseType: 'arraybuffer',
        headers: {
          Accept: 'application/pdf',
          Authorization: `Bearer ${localStorage.getItem('horseToken')}`
        }
      })
      const pdfBlob = new Blob([response.data], { type: 'application/pdf' })
      const pdfUrl = URL.createObjectURL(pdfBlob)
      window.open(pdfUrl)
    },
    async PDFUploadStallion (stallionId, pdfFile) {
      console.log('I am here')
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      await axios.post(`http://localhost:8081/mares/${stallionId}/pdf`, pdfFile, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
    },
    async getPDFStallion (stallionId) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get(`http://localhost:8081/stallions/${stallionId}/pdf/read`, {
        responseType: 'arraybuffer',
        headers: {
          Accept: 'application/pdf',
          Authorization: `Bearer ${localStorage.getItem('horseToken')}`
        }
      })
      const pdfBlob = new Blob([response.data], { type: 'application/pdf' })
      const pdfUrl = URL.createObjectURL(pdfBlob)
      window.open(pdfUrl)
    },
    validatePassword (password) {
      const capitalRegex = /[A-Z]/
      const numberRegex = /[0-9]/
      const specialRegex = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/
      const isLengthValid = password.length > 8

      const hasCapital = capitalRegex.test(password)
      const hasNumber = numberRegex.test(password)
      const hasSpecial = specialRegex.test(password)
      this.passwordIsValid = hasSpecial && hasCapital && hasNumber && isLengthValid
      return this.passwordIsValid
    },
    async getNotifications () {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get('http://localhost:8081/notifications')
      this.notifications = response.data
    },
    async getAmountOfUnreadNotifications () {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.get('http://localhost:8081/notifications/unread')
      console.log(response.data)
      return response.data
    },
    async markNotification (id) {
      axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem('horseToken')}`
      const response = await axios.post(`http://localhost:8081/notifications/${id}`)
      if (response.status === 200) {
        await this.getNotifications()
      }
    },
    formatDate (dateToFormat) {
      const date = new Date(dateToFormat)
      const day = date.getDate()
      const month = date.getMonth() + 1
      const year = date.getFullYear()
      return `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`
    },
    confirmationPopUp (title, text, confirmButtonText) {
      return Swal.fire({
        title,
        text,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Annuleer',
        confirmButtonText
      })
    },
    showToast (title) {
      const toast = Swal.mixin({
        toast: true,
        position: 'top',
        showConfirmButton: false,
        timer: 2000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.onmouseenter = Swal.stopTimer
          toast.onmouseleave = Swal.resumeTimer
        }
      })
      toast.fire({
        icon: 'success',
        title
      })
    }
  }
})
