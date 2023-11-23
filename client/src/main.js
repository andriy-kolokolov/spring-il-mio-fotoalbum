import { createApp } from 'vue'
import axios from 'axios';

import './assets/scss/main.scss' // styles
import 'ant-design-vue/dist/reset.css'; // antdv styles reset

import Antd from 'ant-design-vue'

import dayjs from 'dayjs';
import 'dayjs/locale/it.js';

import router from './router/index.js';

import App from './App.vue'


// set locale
dayjs.locale('it-IT');

const app = createApp(App)
    .use(Antd)
    .use(router)

// make axios available throughout application
app.config.globalProperties.$axios = axios;
// set the base URL for Axios
axios.defaults.baseURL = 'http://localhost:8080/api/v1/';


app.mount('#app')
