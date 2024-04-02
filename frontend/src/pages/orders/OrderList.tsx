import OrderService from "../../services/OrderService.ts";
import React, {useEffect, useState} from "react";
import {Order} from "../../types/Order.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link} from "react-router-dom";
import {OrderStatus} from "../../types/enums/OrderStatus.ts";

const orderService = new OrderService();
function OrderList() {
    const [orders, setOrders] = useState<Order[]>([]);
    const [selectedCustomerName, setSelectedCustomerName] = useState("");
    const [ordersByStatus, setOrdersByStatus] = useState<Order[]>(orders);

    const filteredOrders = ordersByStatus.filter(
        (order) =>
            order.customerName.toLowerCase().includes(selectedCustomerName.toLowerCase())
    );

    const handleStatusChange = (e: React.MouseEvent<HTMLInputElement>) => {
        const target = e.target as HTMLElement;
        setSelectedCustomerName("");
        if (target.id === "allOrders") {
            setOrdersByStatus(orders)
        }else {
            setOrdersByStatus(orders.filter((order) => order.orderStatus === target.id));
        }
    };

    const handleSelectedCustomerName = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedCustomerName(e.target.value);
    };

    useEffect(() => {
        orderService.getAllOrders().then((response) => {
            setOrders(response.data);
            setOrdersByStatus(response.data);
        }).catch((error) => {
            console.error('Fehler beim Abrufen der Bestellungen:', error);
        });
    }, []);

    return (
        <div className={'container'}>
            <PageHeader title="Bestellliste" pageType="order"/>

            <div className="form-check form-check-inline mb-3">
                <p>Bestellstatus:</p>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="allOrders"
                       value="allSuppliers" onClick={handleStatusChange} defaultChecked/>
                <label className="form-check-label" htmlFor="allSuppliers">Alle Bestellungen</label>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="PENDING"
                       value="PENDING" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="activeSuppliers">Ausstehend</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="CONFIRMED"
                       value="CONFIRMED" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="passiveSuppliers">Bestätigt</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="SHIPPED"
                       value="SHIPPED" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="passiveSuppliers">Ausgeliefert</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="DELIVERED"
                       value="DELIVERED" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="passiveSuppliers">Geliefert</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="CANCELLED"
                       value="CANCELLED" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="passiveSuppliers">Abgesagt</label>
            </div>

            <div className="input-group">
                <span className="input-group-text" id="basic-addon3">Schreiben einen Kundennamenfilter</span>
                <input
                    type="text"
                    className="form-control"
                    id="basic-url"
                    aria-describedby="basic-addon3 basic-addon4"
                    onChange={handleSelectedCustomerName}
                />
            </div>

            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Kundenname</th>
                    <th scope="col">Auftragsdatum</th>
                    <th scope="col">Status</th>
                    <th scope="col">Detail</th>
                </tr>
                </thead>
                <tbody>
                {filteredOrders.map((order) => {
                    return (
                        <tr key={order.id}>
                            <td>
                                {order.customerName}
                                {order.orderStatus === "PENDING" && (
                                    <span className="badge text-bg-primary rounded-pill mx-1">Neu</span>
                                )}
                            </td>

                            <td>{order.orderDate ? new Date(order.orderDate).toLocaleDateString() : '-'}</td>
                            <td>{OrderStatus[order.orderStatus as keyof typeof OrderStatus]}</td>
                            <td><Link to={`/orders/detail/${order.id}`}
                                      className="btn btn-outline-info">Detail</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
}

export default OrderList;