import axios from "axios";

export default class StockMovementService {
    getAllStockMovements() {
        return axios.get('/api/stock-movements');
    }
}