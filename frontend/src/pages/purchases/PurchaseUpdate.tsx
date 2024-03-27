import PurchaseService from "../../services/PurchaseService.ts";
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {Purchase} from "../../types/Purchase.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import PurchaseCommonFormFields from "../../layouts/PurchaseCommonFormFields.tsx";
import {Product} from "../../types/Product.ts";
import {Supplier} from "../../types/Supplier.ts";
import ProductService from "../../services/ProductService.ts";
import SupplierService from "../../services/SupplierService.ts";


const purchaseService = new PurchaseService();
const productService = new ProductService();
const supplierService = new SupplierService();
function PurchaseUpdate() {
    const { id = '' } = useParams<string>();
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

    const [suppliers, setSuppliers] = useState<Supplier[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [products, setProducts] = useState<Product[]>([]);

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

    useEffect(() => {
        if (id) {
            purchaseService.getPurchaseById(id)
                .then((response) => {
                    setPurchase(prevPurchase => ({...prevPurchase, ...response.data}));
                })
                .catch((error) => {
                    console.error('Etwas ist schief gelaufen:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        purchaseService.updatePurchase(id, purchase)
            .then(response => {
                console.log(response)
                navigate('/purchases/detail/' + id)
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
            <PageHeader title="Kaufsaktualisierung" pageType="purchase"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <PurchaseCommonFormFields purchase={purchase} setPurchase={setPurchase} products={products} suppliers={suppliers}/>
                        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Aktualisieren</button>
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

export default PurchaseUpdate;