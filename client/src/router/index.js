import { createRouter, createWebHistory } from 'vue-router'

import { Dashboard, Photos } from '../pages/index.js'

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
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;