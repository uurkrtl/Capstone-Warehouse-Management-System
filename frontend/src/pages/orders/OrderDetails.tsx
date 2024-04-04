import OrderService from "../../services/OrderService.ts";
import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Order} from "../../types/Order.ts";
import {OrderDetail} from "../../types/OrderDetail.ts";
import OrderDetailService from "../../services/OrderDetailService.ts";
import {OrderStatus} from "../../types/enums/OrderStatus.ts";


const orderService = new OrderService();
const orderDetailService = new OrderDetailService();
function OrderDetails() {
    const { id = '' } = useParams<string>();
    const [orderDetails, setOrderDetails] = useState<OrderDetail[]>([]);
    const [order, setOrder] = useState<Order>({
        id: '',
        customerName: '',
        orderDate: new Date(),
        orderStatus: 'PENDING'
    });

    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            orderService.getOrderById(id)
                .then((response) => {
                    setOrder(response.data);
                })
                .catch((error) => {
                    console.error('Fehler beim Abrufen der Bestellung:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    useEffect(() => {
        orderDetailService.getOrderDeteailsByOrderId(id).then((response) => {
            setOrderDetails(response.data);
        });
    }, [id]);


    return (
        <div className="container">
            <div className="row flex-lg-row align-items-center g-5 py-2">
                <div className="col-lg-6">
                    <h1 className="display-5 fw-bold text-body-emphasis lh-1 mb-3">{order.customerName}</h1>
                    <p className="lead">{order.orderDate ? new Date(order.orderDate).toLocaleDateString() : '-'} - {OrderStatus[order.orderStatus as keyof typeof OrderStatus]}</p>

                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">ProduktName</th>
                            <th scope="col">Preis</th>
                            <th scope="col">Menge</th>
                            <th scope="col">Gesamtpreis</th>
                        </tr>
                        </thead>
                        <tbody>
                        {orderDetails.map((orderDetail) => {
                            return (
                                <tr key={orderDetail.id}>
                                    <td>{orderDetail.productName}</td>
                                    <td>{orderDetail.price.toLocaleString('de-DE', {
                                        style: 'currency',
                                        currency: 'EUR'
                                    })}</td>
                                    <td>{orderDetail.quantity}</td>
                                    <td>{orderDetail.totalPrice.toLocaleString('de-DE', {
                                        style: 'currency',
                                        currency: 'EUR'
                                    })}</td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>


                    <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                        <Link to={`/orders`} type="button"
                              className="btn btn-outline-secondary btn-lg px-4">Bestellliste</Link>
                    </div>

                </div>
            </div>
        </div>
    );
}

export default OrderDetails;