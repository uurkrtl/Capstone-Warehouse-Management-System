import axios from "axios";
import {Supplier} from "../types/Supplier.ts";

export default class SupplierService {
    getAllSuppliers() {
        return axios.get('/api/suppliers');
    }

    getSupplierById(id: string) {
        return axios.get(`/api/suppliers/${id}`);
    }

    addSupplier(supplier: Supplier) {
        return axios.post('/api/suppliers', supplier);
    }

    updateSupplier(id:string, supplier: Supplier) {
        return axios.put(`/api/suppliers/${id}`, supplier);
    }

    changeSupplierStatus(id: string, status: boolean) {
        return axios.put(`/api/suppliers/status/${id}?status=${status}`);
    }
}