import React, {useState} from "react";
import {Order} from "../../types/Order.ts";
import {useNavigate} from "react-router-dom";
import OrderService from "../../services/OrderService.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import OrderCommonFormFields from "../../layouts/OrderCommonFormFields.tsx";

function OrderAdd() {
    const [order, setOrder] = useState<Order>({
        id: '',
        customerName: '',
        orderDate: new Date(),
        orderStatus: 'PENDING'
    });

    const navigate = useNavigate();
    const orderService = new OrderService();
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        orderService.addOrder(order)
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
            <PageHeader title="Bestellung hinzufügen" pageType="order"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <OrderCommonFormFields order={order} setOrder={setOrder}/>
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

export default OrderAdd;