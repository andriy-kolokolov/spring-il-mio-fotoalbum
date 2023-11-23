import { createRouter, createWebHistory } from 'vue-router'

import { Home, Photos } from '../pages/index.js'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/photos',
        name: 'Photos',
        component: Photos
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;