import {OrderDetail} from "../types/OrderDetail.ts";
import {Product} from "../types/Product.ts";


function OrderDetailCommonFormFields({orderDetail, setOrderDetail, products}: Readonly<{ orderDetail: OrderDetail, setOrderDetail: (OrderDetail: OrderDetail) => void, products: Product[] }>) {
    return (
        <div className="row g-3">

            <div className="col-sm-8">
                <label htmlFor="productName" className="form-label">Produktname</label>
                <select className="form-select" id="productName"
                        value={orderDetail.productId ? orderDetail.productId : ''}
                        onChange={(e) => setOrderDetail({...orderDetail, productId: e.target.value})}>
                    {!orderDetail.productId && <option value="">Wählen...</option>}
                    <option
                        value={products.filter(product => product.name === orderDetail.productName).map(product => product.id)}>{orderDetail.productName}</option>
                    {products.filter((product => product.name !== orderDetail.productName)).map((product) => {
                        return (
                            <option key={product.id}
                                    value={product.id}>{product.name}</option>
                        )
                    })};
                </select>
            </div>

            <div className="col-sm-4">
                <label htmlFor="quantity" className="form-label">Menge</label>
                <input type="number" className="form-control" id="quantity"
                       placeholder="Schreiben Sie die Menge" value={orderDetail.quantity}
                       onChange={(e) => setOrderDetail({
                           ...orderDetail,
                           quantity: e.target.valueAsNumber
                       })}/>
            </div>
        </div>
    );
}

export default OrderDetailCommonFormFields;