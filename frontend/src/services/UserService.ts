import axios from "axios";
import {User} from "../types/User.ts";

export default class UserService {
    login(user: User) {
        return axios.post('/api/users/login', {}, {
            auth: {
                username: user.username,
                password: user.password
            }
        });
    }

    register(user: User) {
        return axios.post('/api/users/register', user);
    }

    getLoggedInUser() {
        return axios.get('/api/users/me');
    }

    logout() {
        return axios.post('/api/users/logout');
    }

    getAllUsers() {
        return axios.get('/api/users');
    }
}