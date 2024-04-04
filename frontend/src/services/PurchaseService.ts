import axios from "axios";
import {Purchase} from "../types/Purchase.ts";

export default class PurchaseService {
    getAllPurchases() {
        return axios.get('/api/purchases');
    }

    getPurchaseByProductId(productId: string) {
        return axios.get(`/api/purchases/product/${productId}`);
    }

    getPurchaseById(id: string) {
        return axios.get(`/api/purchases/${id}`);
    }

    addPurchase(purchase: Purchase) {
        return axios.post('/api/purchases', purchase);
    }

    updatePurchase(id: string, purchase: Purchase) {
        return axios.put(`/api/purchases/${id}`, purchase);
    }

    changePurchaseStatus(id: string, status: boolean) {
        return axios.put(`/api/purchases/status/${id}?status=${status}`);
    }
}