import axios from 'axios';

// Base URL for Axios
const API = axios.create({
    baseURL: 'http://localhost:8080/api/v1/category',
});

// Interceptor to attach the token to requests
API.interceptors.request.use((req) => {
    const token = JSON.parse(localStorage.getItem('token'));
    if (token) {
        req.headers.Authorization = `Bearer ${token}`;
    }
    return req;
});

const categoryService = {

    async getByPhotoId(id) {
        try {
            const response = await API.get('/list/' + id);
            return response.data;
        } catch (error) {
            return error;
        }
    },

    // Get all
    async getAll() {
        try {
            const response = await API.get('/list');
            return response.data;
        } catch (error) {
            return error;
        }
    },
};

export default categoryService;