import axios from 'axios';
import { authState } from "../store/index.js";

// Base URL for Axios
const API = axios.create({
    baseURL: 'http://localhost:8080/api/v1/users',
});

// Interceptor to attach the token to requests
API.interceptors.request.use((req) => {
    const token = JSON.parse(localStorage.getItem('token'));
    if (token) {
        req.headers.Authorization = `Bearer ${ token }`;
    }
    return req;
});

const photoService = {

    async getUserSentMessages() {
        try {
            const authUserId = JSON.parse(localStorage.getItem('user')).id;
            const response = await API.get(`/${ authUserId }/messages/sent`);
            return response.data;
        } catch (error) {
            return error;
        }
    },

    async getUserReceivedMessages() {
        try {
            const authUserId = JSON.parse(localStorage.getItem('user')).id;
            const response = await API.get(`/${ authUserId }/messages/received`);
            return response.data;
        } catch (error) {
            return error;
        }
    },

    async sendMessage(receiverUserId, messageContent) {
        try {
            const senderId = JSON.parse(localStorage.getItem('user')).id;
            const response = await API.post(`/${senderId}/messages/send`, {
                receiverId: receiverUserId,
                content: messageContent
            });
            return response.data;
        } catch (error) {
            return error;
        }
    },
};

export default photoService;