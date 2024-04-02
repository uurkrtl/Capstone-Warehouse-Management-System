import {Order} from "../types/Order.ts";

function OrderCommonFormFields({order, setOrder}: Readonly<{ order: Order, setOrder: (order: Order) => void }>) {
    return (
        <div className="row g-3">

            <div className="col-sm-12">
                <label htmlFor="customerName" className="form-label">Kundenname</label>
                <input type="text" className="form-control" id="customerName"
                       placeholder="Schreiben Sie den Kundenname" value={order.customerName}
                       onChange={(e) => setOrder({...order, customerName: e.target.value})}/>
            </div>
        </div>
    );
}

export default OrderCommonFormFields;