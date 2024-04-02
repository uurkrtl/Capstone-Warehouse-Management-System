import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {OrderDetail} from "../../types/OrderDetail.ts";
import OrderDetailService from "../../services/OrderDetailService.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import OrderDetailCommonFormFields from "../../layouts/OrderDetailCommonFormFields.tsx";
import ProductService from "../../services/ProductService.ts";
import {Product} from "../../types/Product.ts";


function OrderDetailAdd() {
    const {orderId = ''} = useParams<string>();
    const [orderDetail, setOrderDetail] = useState<OrderDetail>({
        id: '',
        orderId: orderId,
        productId: '',
        productName: '',
        quantity: 0,
        price: 0,
        totalPrice: 0,
        active: true
    });

    const navigate = useNavigate();
    const orderDetailService = new OrderDetailService();
    const productService = new ProductService();
    const [products, setProducts] = useState<Product[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>('');

    useEffect(() => {
        productService.getAllProducts().then((response) => {
            setProducts(response.data);
        });
    });

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        orderDetail.orderId = orderId;
        orderDetailService.addOrderDetail(orderDetail)
            .then(response => {
                console.log(response)
                navigate('/orders')
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
            <PageHeader title="Bestelldetails hinzufügen" pageType="orderDetail"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <OrderDetailCommonFormFields orderDetail={orderDetail} setOrderDetail={setOrderDetail} products={products}/>
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

export default OrderDetailAdd;