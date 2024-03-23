import axios from "axios";
import {Category} from "../types/Category.ts";

export default class CategoryService {
    getAllCategories() {
        return axios.get('/api/categories');
    }

    addCategory(category: Category) {
        return axios.post('/api/categories', category);
    }
}