import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import VueAxios from 'vue-axios'
import BootstrapVueIcons  from 'bootstrap-vue'
import errorHandler from "./service/errorHandler";

Vue.config.productionTip = false

axios.defaults.baseURL = "http://localhost:8080"

Vue.use(VueAxios, axios)
Vue.use(BootstrapVueIcons)
errorHandler()


new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')