import axios from "axios";
import {Order} from "../types/Order.ts";

export default class OrderService {
    getAllOrders() {
        return axios.get('/api/orders');
    }
    addOrder(order: Order) {
        return axios.post('/api/orders', order);
    }
}