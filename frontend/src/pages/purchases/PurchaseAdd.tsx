import React, {useEffect, useState} from "react";
import {Purchase} from "../../types/Purchase.ts";
import {useNavigate} from "react-router-dom";
import ProductService from "../../services/ProductService.ts";
import SupplierService from "../../services/SupplierService.ts";
import {Product} from "../../types/Product.ts";
import {Supplier} from "../../types/Supplier.ts";
import PurchaseService from "../../services/PurchaseService.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import PurchaseCommonFormFields from "../../layouts/PurchaseCommonFormFields.tsx";


function PurchaseAdd() {
    const [purchase, setPurchase] = useState<Purchase>({
        id: '',
        productId: '',
        productName: '',
        supplierId: '',
        supplierName: '',
        purchasePrice: 0,
        quantity: 0,
        totalPrice: 0,
        purchaseDate: new Date,
        createdAt: new Date(),
        updatedAt: new Date(),
        active: true
    });

    const navigate = useNavigate();
    const purchaseService = new PurchaseService();
    const productService = new ProductService();
    const supplierService = new SupplierService();
    const [products, setProducts] = useState<Product[]>([]);
    const [suppliers, setSuppliers] = useState<Supplier[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>('');

    useEffect(() => {
        productService.getAllProducts().then((response) => {
            setProducts(response.data);
        });
    });

    useEffect(() => {
        supplierService.getAllSuppliers().then((response) => {
            setSuppliers(response.data);
        });
    });

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        purchaseService.addPurchase(purchase)
            .then(response => {
                console.log(response)
                navigate('/purchases')
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response.data);
                    setErrorMessage(error.response.data.message);
                } else {
                    console.log('Etwas ist schief gelaufen:', error.message);
                    setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
                }
            });
    };

    return (
        <main className={'container'}>
            <PageHeader title="Kauf hinzufügen" pageType="purchase"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <PurchaseCommonFormFields purchase={purchase} setPurchase={setPurchase} products={products} suppliers={suppliers}/>
                        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Speichern</button>
                    </form>

                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}

                </div>
            </div>
        </main>
    );
}

export default PurchaseAdd;