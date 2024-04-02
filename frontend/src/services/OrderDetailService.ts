import axios from "axios";
import {OrderDetail} from "../types/OrderDetail.ts";

export default class OrderDetailService {
    getOrderDeteailsByOrderId(orderId: string) {
        return axios.get('/api/order-details/' + orderId);
    }
    addOrderDetail(orderDetail: OrderDetail) {
        return axios.post('/api/order-details', orderDetail);
    }
}