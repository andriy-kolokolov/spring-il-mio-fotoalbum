import { createRouter, createWebHistory } from 'vue-router'

import { Dashboard, Photos, Messages } from '../pages/index.js'
import { Register, Login } from '../pages/auth/index.js'

const routes = [
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard
    },
    {
        path: '/photos',
        name: 'Photos',
        component: Photos
    },
    {
        path: '/messages',
        name: 'Messages',
        component: Messages
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;