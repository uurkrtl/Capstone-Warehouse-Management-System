import axios from "axios";
import {OrderDetail} from "../types/OrderDetail.ts";

export default class OrderDetailService {
    addOrderDetail(orderDetail: OrderDetail) {
        return axios.post('/api/order-details', orderDetail);
    }
}