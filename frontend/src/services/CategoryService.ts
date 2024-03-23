import axios from "axios";
import {Category} from "../types/Category.ts";

export default class CategoryService {
    getAllCategories() {
        return axios.get('/api/categories');
    }

    getCategoryById(id: string) {
        return axios.get(`/api/categories/${id}`);
    }

    addCategory(category: Category) {
        return axios.post('/api/categories', category);
    }

    updateCategory(id:string, category: Category) {
        return axios.put(`/api/categories/${id}`, category);
    }

    changeCategoryStatus(id: string, status: boolean) {
        return axios.put(`/api/categories/status/${id}?status=${status}`);
    }
}