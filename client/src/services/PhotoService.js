import axios from 'axios';

// Base URL for Axios
const API = axios.create({
    baseURL: 'http://localhost:8080/api/v1/photo',
});

// Interceptor to attach the token to requests
API.interceptors.request.use((req) => {
    const token = JSON.parse(localStorage.getItem('token'));
    if (token) {
        req.headers.Authorization = `Bearer ${token}`;
    }
    return req;
});

const photoService = {
    // Create a new photo
    async createPhoto(photoData) {
        try {
            const response = await API.post('/add', photoData);
            return response.data;
        } catch (error) {
            return error;
        }
    },

    // Get all photos
    async getPhotos() {
        try {
            const response = await API.get('/list');
            return response.data;
        } catch (error) {
            return error;
        }
    },

    // Get photos by user id
    async getPhotosByUserId(id) {
        try {
            const response = await API.get(`/list/${id}`);
            return response.data;
        } catch (error) {
            return error;
        }
    },

    // Update a photo
    async updatePhoto(id, updatedData) {
        try {
            const response = await API.put(`/${id}`, updatedData);
            return response.data;
        } catch (error) {
            return error;
        }
    },

    // Delete a photo
    async deletePhoto(id) {
        try {
            const response = await API.delete(`/delete/${id}`);
            return response.data;
        } catch (error) {
            return error;
        }
    },
};

export default photoService;