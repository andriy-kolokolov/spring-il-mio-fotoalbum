import axios from 'axios';
import { setAuthStatus } from "../store/index.js";
import { message, notification } from "ant-design-vue";

const API_URL = 'http://localhost:8080/auth/';

class AuthService {
    async register(user) {
        const response = await axios.post(API_URL + 'signup', user);
        return response.data;
    }

    async login(user) {
        const response = await axios.post(API_URL + 'signin', user);
        if (response.data.success && response.data.token) {
            localStorage.setItem('token', JSON.stringify(response.data.token));
            localStorage.setItem('user', JSON.stringify(response.data.user));
            setAuthStatus(true);
        }
        return response.data;
    }

    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setAuthStatus(false);
        message.success('You successfully logged out !')
    }
}

export default new AuthService();