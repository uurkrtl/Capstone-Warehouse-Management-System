import axios from "axios";

export default class ReportService {
    getProductsOutOfStock() {
        return axios.get('/api/reports/products-out-of-stock');
    }

    getProductsLowInStock() {
        return axios.get('/api/reports/products-low-stock');
    }
}