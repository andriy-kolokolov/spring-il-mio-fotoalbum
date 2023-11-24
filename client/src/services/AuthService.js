import axios from 'axios';
import { setAuthStatus, setUser } from "../store/index.js";
import { message, notification } from "ant-design-vue";

const API_URL = 'http://localhost:8080/auth/';

class AuthService {
    async register(user) {
        const response = await axios.post(API_URL + 'signup', user);
        return response.data;
    }

    async login(user, router) {
        const response = await axios.post(API_URL + 'signin', user);
        if (response.data.success && response.data.token) {
            localStorage.setItem('token', JSON.stringify(response.data.token));
            localStorage.setItem('user', JSON.stringify(response.data.user));
            setAuthStatus(true);
            setUser(response.data.user)
        }
        return response.data;
    }

    logout(router) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setAuthStatus(false);
        setUser(null)
        message.success('You successfully logged out!');
        router.push('/login');
    }
}

export default new AuthService();