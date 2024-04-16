import axios from 'axios';
import {Product} from "../types/Product.ts";

export default class ProductService {
    getAllProducts() {
        return axios.get('/api/products');
    }

    getProductsByCategoryId(categoryId: string) {
        return axios.get(`/api/products/by-category/${categoryId}`);
    }

    getProductById(id: string) {
        return axios.get(`/api/products/${id}`);
    }

    addProduct(product: Product) {
        return axios.post('/api/products', product);
    }

    updateProduct(id:string, product: Product) {
        return axios.put(`/api/products/${id}`, product);
    }

    changeProductStatus(id: string, status: boolean) {
        return axios.put(`/api/products/status/${id}?status=${status}`);
    }
}