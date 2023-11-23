import axios from 'axios';

const API_URL = 'http://localhost:8080/auth/';

class AuthService {
    async register(user) {
        const response = await axios.post(API_URL + 'signup', user);
        return response.data;
    }

    async login(user) {
        const response = await axios.post(API_URL + 'signin', user);
        if (response.data.success && response.data.token) {
            localStorage.setItem('user', JSON.stringify(response.data.token));
        }
        return response.data;
    }

    logout() {
        localStorage.removeItem('user');
    }
}

export default new AuthService();