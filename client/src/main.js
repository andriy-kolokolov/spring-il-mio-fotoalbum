import { createApp } from 'vue'
import axios from 'axios';

import './assets/scss/main.scss' // styles
import 'ant-design-vue/dist/reset.css'; // antdv styles reset

import Antd from 'ant-design-vue'

import router from './router/index.js';

import App from './App.vue'
import { authState, setAuthStatus, setUser } from "./store/index.js";


// set IT locale
//import dayjs from 'dayjs';
// import 'dayjs/locale/it.js';
// dayjs.locale('it-IT');

const app = createApp(App)
    .use(Antd)
    .use(router)

// set the base URL for Axios
axios.defaults.baseURL = 'http://localhost:8080/';

// auth check, save user data in storage
if (localStorage.getItem('token') && localStorage.getItem('user')) {
    setAuthStatus(true);
    setUser(JSON.parse(localStorage.getItem('user'))) ;
}

app.mount('#app')
