import axios from "axios";
import {Order} from "../types/Order.ts";

export default class OrderService {
    getAllOrders() {
        return axios.get('/api/orders');
    }

    getOrderById(id: string) {
        return axios.get(`/api/orders/${id}`);
    }

    addOrder(order: Order) {
        return axios.post('/api/orders', order);
    }
}