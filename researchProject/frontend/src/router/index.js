import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/components/LoginScreen/LoginView.vue'
import HomePage from '@/components/HomeScreen/HomePage.vue'
import AddHorseView from '@/components/HorseComponents/AddHorseView.vue'
import MareDetailsPage from '@/components/MareComponents/MareDetailsPage.vue'
import StallionDetailsPage from '@/components/StallionComponents/StallionDetailsPage.vue'
import ChangeHorsePage from '@/components/HorseComponents/ChangeHorsePage.vue'
import AddFoalComponent from '@/components/FoalComponents/AddFoalComponent.vue'
import FoalDetailsPage from '@/components/FoalComponents/FoalDetailsPage.vue'
import ChangeFoalPage from '@/components/FoalComponents/ChangeFoalPage.vue'
import AddClientComponent from '../components/ClientComponents/AddClientComponent.vue'
import ClientDetailComponent from '@/components/ClientComponents/ClientDetailComponent.vue'
import ChangeClientPage from '@/components/ClientComponents/ChangeClientPage.vue'
import ForgotPasswordView from '@/components/LoginScreen/ForgotPasswordView.vue'
import confirmLinkSendView from '@/components/LoginScreen/ConfirmLinkSendView.vue'
import ResetPasswordView from '@/components/LoginScreen/ResetPasswordView.vue'
import notificationPage from '@/components/NotificationComponents/NotificationPage.vue'
import PDFUpload from '@/components/MareComponents/PDFUpload.vue'
import PDFUploadStallionComponent from '@/components/StallionComponents/PDFUploadStallionComponent.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/home',
      name: 'Dashboard',
      component: HomePage
    },
    {
      path: '/addHorse',
      name: 'Add horse',
      component: AddHorseView
    },
    {
      path: '/addFoal',
      name: 'Add foal',
      component: AddFoalComponent
    },
    {
      path: '/foalDetails',
      name: 'See details of foal',
      component: FoalDetailsPage
    },
    {
      path: '/mareDetails/',
      name: 'See details of mare',
      component: MareDetailsPage
    },
    {
      path: '/stallionDetails/',
      name: 'See details of stallion',
      component: StallionDetailsPage
    },
    {
      path: '/changeHorseData/',
      name: 'change data of horse',
      component: ChangeHorsePage
    },
    {
      path: '/PDFUpload',
      name: 'Upload PDF Mare',
      component: PDFUpload
    },
    {
      path: '/PDFUploadStallion',
      name: 'Upload PDF Stallion',
      component: PDFUploadStallionComponent
    },
    {
      path: '/changeFoalData/',
      name: 'change data of foal',
      component: ChangeFoalPage
    },
    {
      path: '/addClient/',
      name: 'add client',
      component: AddClientComponent
    },
    {
      path: '/clientDetails/',
      name: 'client details',
      component: ClientDetailComponent
    },
    {
      path: '/changeClientData/',
      name: 'change data of client',
      component: ChangeClientPage
    },
    {
      path: '/forgot-password/',
      name: 'forgot password',
      component: ForgotPasswordView
    },
    {
      path: '/confirmLink',
      name: 'confirm link',
      component: confirmLinkSendView
    },
    {
      path: '/password/reset/token/:token/',
      name: 'reset password',
      component: ResetPasswordView
    },
    {
      path: '/notifications/',
      name: 'View your notifications',
      component: notificationPage
    }
  ]
})

export default router
