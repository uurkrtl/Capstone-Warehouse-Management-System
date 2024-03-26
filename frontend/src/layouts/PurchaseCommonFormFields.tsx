import {Purchase} from "../types/Purchase.ts";
import {Product} from "../types/Product.ts";
import {Supplier} from "../types/Supplier.ts";
import DatePicker from "react-datepicker";

function PurchaseCommonFormFields({ purchase, setPurchase, products, suppliers }: Readonly<{ purchase: Purchase, setPurchase: (purchase: Purchase) => void, products: Product[], suppliers: Supplier[] }>) {
    return (
        <div className="row g-3">

            <div className="col-sm-6">
                <label htmlFor="productName" className="form-label">Produkt</label>
                <select className="form-select" id="productName"
                        value={purchase.productId ? purchase.productId : ''}
                        onChange={(e) => setPurchase({...purchase, productId: e.target.value})}>
                    {!purchase.productId && <option value="">Wählen...</option>}
                    <option
                        value={products.filter(product => product.name === purchase.productName).map(product => product.id)}>{purchase.productName}</option>
                    {products.filter((product => product.name !== purchase.productName)).map((product) => {
                        return (
                            <option key={product.id}
                                    value={product.id}>{product.name}</option>
                        )
                    })};
                </select>
            </div>

            <div className="col-sm-6">
                <label htmlFor="supplierName" className="form-label">Lieferant</label>
                <select className="form-select" id="supplierName"
                        value={purchase.supplierId ? purchase.supplierId : ''}
                        onChange={(e) => setPurchase({...purchase, supplierId: e.target.value})}>
                    {!purchase.supplierId && <option value="">Wählen...</option>}
                    <option
                        value={suppliers.filter(supplier => supplier.name === purchase.supplierName).map(supplier => supplier.id)}>{purchase.supplierName}</option>
                    {suppliers.filter((supplier => supplier.name !== purchase.supplierName)).map((supplier) => {
                        return (
                            <option key={supplier.id}
                                    value={supplier.id}>{supplier.name}</option>
                        )
                    })};
                </select>
            </div>

            <div className="col-sm-4">
                <label htmlFor="purchasePrice" className="form-label">Kaufpreis</label>
                <input type="number" step="0.01" className="form-control" id="purchasePrice"
                       placeholder="Schreiben Sie den Kaufpreis" value={purchase.purchasePrice}
                       onChange={(e) => setPurchase({
                           ...purchase,
                           purchasePrice: e.target.valueAsNumber
                       })}/>
            </div>

            <div className="col-sm-4">
                <label htmlFor="quantity" className="form-label">Menge</label>
                <input type="number" className="form-control" id="quantity"
                       placeholder="Schreiben Sie die Menge" value={purchase.quantity}
                       onChange={(e) => setPurchase({
                           ...purchase,
                           quantity: e.target.valueAsNumber
                       })}/>
            </div>

            <div className="col-sm-4">
                <label htmlFor="purchaseDate" className="form-label">Kaufdatum</label>
                <div className="input-group">
                <DatePicker className="form-control" id="purchaseDate"
                            selected={purchase.purchaseDate}
                            onChange={(date: Date) => {
                                const adjustedDate = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()));
                                setPurchase({...purchase, purchaseDate: adjustedDate});
                            }}
                            dateFormat={'dd.MM.yyyy'}
                            maxDate={new Date()}
                /></div>
            </div>
        </div>
    );
}

export default PurchaseCommonFormFields;