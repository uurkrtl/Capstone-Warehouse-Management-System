import axios from "axios";
import {Purchase} from "../types/Purchase.ts";

export default class PurchaseService {
    addPurchase(purchase: Purchase) {
        return axios.post('/api/purchases', purchase);
    }
}