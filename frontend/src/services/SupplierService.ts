import axios from "axios";
import {Supplier} from "../types/Supplier.ts";

export default class SupplierService {
    addSupplier(supplier: Supplier) {
        return axios.post('/api/suppliers', supplier);
    }
}