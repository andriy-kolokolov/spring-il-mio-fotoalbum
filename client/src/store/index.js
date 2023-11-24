import { reactive } from 'vue';

export const authState = reactive({
    isAuthenticated: false
});

export function setAuthStatus(status) {
    authState.isAuthenticated = status;
}