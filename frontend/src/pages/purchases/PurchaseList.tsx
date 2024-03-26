import React, {useEffect, useState} from 'react';
import PurchaseService from "../../services/PurchaseService.ts";
import {Purchase} from "../../types/Purchase.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link} from "react-router-dom";

const purchaseService = new PurchaseService();
function PurchaseList() {
    const [purchases, setPurchases] = useState<Purchase[]>([]);
    const [filter, setFilter] = useState("");
    const [purchasesByStatus, setPurchasesByStatus] = useState<Purchase[]>(purchases);

    const filteredPurchasesByProduct = purchasesByStatus.filter(
        (purchase) =>
            purchase.productName && purchase.productName.toLowerCase().includes(filter.toLowerCase())
    );

    const handleStatusChange = (e: React.MouseEvent<HTMLInputElement>) => {
        const target = e.target as HTMLElement;
        if (target.id === "activePurchases") {
            setPurchasesByStatus(purchases.filter((purchase) => purchase.active));
        } else if (target.id === "passivePurchases") {
            setPurchasesByStatus(purchases.filter((purchase) => !purchase.active));
        } else {
            setPurchasesByStatus(purchases);
        }
    };

    useEffect(() => {
        purchaseService.getAllPurchases().then((response) => {
            setPurchases(response.data);
            setPurchasesByStatus(response.data);
        });
    }, []);

    return (
        <div className={'container'}>
            <PageHeader title="Einkaufsliste" pageType="purchase"/>

            <div className="d-flex justify-content-end">
                <Link to={"/purchases/add"} className="btn btn-outline-secondary">Kauf erstellen</Link>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="allPurchases"
                       value="allPurchases" onClick={handleStatusChange} defaultChecked/>
                <label className="form-check-label" htmlFor="allPurchases">Alle Einkäufe</label>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="activePurchases"
                       value="activePurchases" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="activePurchases">Aktive Einkäufe</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="passivePurchases"
                       value="passivePurchases" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="passivePurchases">Passive Einkäufe</label>
            </div>

            <div className="input-group">
                <span className="input-group-text" id="basic-addon3">Schreiben einen Produktnamenfilter</span>
                <input
                    type="text"
                    className="form-control"
                    id="basic-url"
                    aria-describedby="basic-addon3 basic-addon4"
                    onChange={(e) => setFilter(e.target.value)}
                />
            </div>

            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Produktname</th>
                    <th scope="col">Name des Lieferanten</th>
                    <th scope="col">Kaufdatum</th>
                    <th scope="col">Detail</th>
                </tr>
                </thead>
                <tbody>
                {filteredPurchasesByProduct.map((purchase) => {
                    return (
                        <tr key={purchase.id}>
                            <td className={!purchase.active ? "text-danger" : "text-black"}>{purchase.productName}</td>
                            <td>{purchase.supplierName}</td>
                            <td>
                                {purchase.active ?
                                    <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                    : <span className="badge text-bg-danger rounded-pill">Passiv</span>}
                            </td>
                            <td><Link to={`/purchases/detail/${purchase.id}`}
                                      className="btn btn-outline-info">Detail</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
}

export default PurchaseList;