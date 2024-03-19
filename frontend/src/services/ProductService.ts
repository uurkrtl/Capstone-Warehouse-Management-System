import axios from 'axios';
import {Product} from "../types/Product.ts";

export default class ProductService {
    addProduct(product: Product) {
        return axios.post('/api/products', product);
    }

}