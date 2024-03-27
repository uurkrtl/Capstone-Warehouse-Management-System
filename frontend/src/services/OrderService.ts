import axios from "axios";
import {Order} from "../types/Order.ts";

export default class OrderService {
    addOrder(order: Order) {
        return axios.post('/api/orders', order);
    }
}