import axios from "axios";
import {Purchase} from "../types/Purchase.ts";

export default class PurchaseService {
    getAllPurchases() {
        return axios.get('/api/purchases');
    }

    getPurchaseById(id: string) {
        return axios.get(`/api/purchases/${id}`);
    }

    addPurchase(purchase: Purchase) {
        return axios.post('/api/purchases', purchase);
    }
}