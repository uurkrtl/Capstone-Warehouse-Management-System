import PurchaseService from "../../services/PurchaseService.ts";
import React, {useEffect, useState} from "react";
import {Purchase} from "../../types/Purchase.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link, useParams} from "react-router-dom";

const purchaseService = new PurchaseService();
function PurchaseList() {
    const [purchases, setPurchases] = useState<Purchase[]>([]);
    const [filter, setFilter] = useState("");
    const [purchasesByStatus, setPurchasesByStatus] = useState<Purchase[]>(purchases);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const { productId = '' } = useParams<string>();

    useEffect(() => {
        if (productId) {
        purchaseService.getPurchaseByProductId(productId).then((response) => {
            setPurchases(response.data);
            setPurchasesByStatus(response.data);
            setLoading(false);
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Käufe: ${error.message}`);
            setLoading(false);
            setPurchases([]);
        });}else {
            purchaseService.getAllPurchases().then((response) => {
                setPurchases(response.data);
                setPurchasesByStatus(response.data);
                setLoading(false);
            }).catch(error => {
                setErrorMessage(`Fehler beim Abrufen von Käufe: ${error.message}`);
                setLoading(false);
                setPurchases([]);
            });}
    }, [productId]);

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

    const filteredPurchases = purchasesByStatus.filter(
        (purchase) =>
            purchase.productName?.toLowerCase().includes(filter.toLowerCase())
    );

    if (loading) {
        return <div className={'container'}>
            <div className={'spinner-border text-primary'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }

    return (
        <div className={'container'}>
            <PageHeader title="Einkaufsliste" pageType="purchase"/>

            <div className="d-flex justify-content-end">
                <Link to={"/purchases/add"} className="btn btn-outline-secondary">Kauf erstellen</Link>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="allPurchases"
                       value="allPurchases" onClick={handleStatusChange} defaultChecked/>
                <label className="form-check-label" htmlFor="allSuppliers">Alle Einkäufe</label>
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
                <span className="input-group-text" id="basic-addon3">Schreiben einen Produktnamensfilter</span>
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
                    <th scope="col">Status</th>
                    <th scope="col">Detail</th>
                </tr>
                </thead>
                <tbody>
                {filteredPurchases.map((purchase) => {
                    return (
                        <tr key={purchase.id}>
                            <td className={!purchase.active ? "text-danger" : "text-black"}>{purchase.productName}</td>
                            <td>{purchase.supplierName}</td>
                            <td>{purchase.purchaseDate ? new Date(purchase.purchaseDate).toLocaleDateString() : 'Kein Datum verfügbar'}</td>
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
            {errorMessage && (
                <div className="alert alert-danger" role="alert">
                    {errorMessage}
                </div>
            )}
        </div>
    );
}

export default PurchaseList;