import { reactive } from 'vue';

export const authState = reactive({
    isAuthenticated: false,
    user: null,
});

export function setAuthStatus(status) {
    authState.isAuthenticated = status;
}

export function setUser(user) {
    authState.user = user;
}