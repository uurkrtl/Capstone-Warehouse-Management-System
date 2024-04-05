import PurchaseService from "../../services/PurchaseService.ts";
import {useEffect, useState} from "react";
import {Purchase} from "../../types/Purchase.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link, useParams} from "react-router-dom";
import DatePicker from "react-datepicker";

const purchaseService = new PurchaseService();
function PurchaseList() {
    const [purchases, setPurchases] = useState<Purchase[]>([]);
    const [filter, setFilter] = useState("");
    const [purchasesByDate, setPurchasesByDate] = useState<Purchase[]>(purchases);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const { productId = '' } = useParams<string>();
    const { supplierId = '' } = useParams<string>();
    const [startDate, setStartDate] = useState(new Date('2024-03-01'));
    const [endDate, setEndDate] = useState(new Date());

    useEffect(() => {
        if (productId) {
        purchaseService.getPurchaseByProductId(productId).then((response) => {
            setPurchases(response.data);
            setPurchasesByDate(response.data);
            setLoading(false);
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Käufe: ${error.message}`);
            setLoading(false);
            setPurchases([]);
        });}else if (supplierId) {
            purchaseService.getPurchaseBySupplierId(supplierId).then((response) => {
                setPurchases(response.data);
                setPurchasesByDate(response.data);
                setLoading(false);
            }).catch(error => {
                setErrorMessage(`Fehler beim Abrufen von Käufe: ${error.message}`);
                setLoading(false);
                setPurchases([]);
            });}else {
            purchaseService.getAllPurchases().then((response) => {
                setPurchases(response.data);
                setPurchasesByDate(response.data);
                setLoading(false);
            }).catch(error => {
                setErrorMessage(`Fehler beim Abrufen von Käufe: ${error.message}`);
                setLoading(false);
                setPurchases([]);
            });}
    }, [productId, supplierId]);

    const handleDateFilter = (handleStartDate: Date, handleEndDate: Date) => {
        setPurchasesByDate(purchases.filter((purchase) => new Date(purchase.purchaseDate) >= handleStartDate && new Date(purchase.purchaseDate) <= handleEndDate));
        setStartDate(handleStartDate);
        setEndDate(handleEndDate);
    }

    const filteredPurchases = purchasesByDate.filter(
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

            <div className="form-check-inline mb-3">
                <label className="form-check-label mx-1" htmlFor="purchaseStartDate">Startdatum</label>
                <DatePicker className="form-control" id="purchaseStartDate"
                            selected={startDate}
                            onChange={(date: Date) => {
                                const adjustedDate = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()));
                                handleDateFilter(adjustedDate, endDate);
                            }}
                            dateFormat={'dd.MM.yyyy'}
                            minDate={new Date('2024-03-01')}
                            maxDate={new Date()}/>
            </div>

            <div className="form-check-inline mb-3">
                <label className="form-check-label mx-1" htmlFor="purchaseStartDate">Enddatum</label>
                <DatePicker className="form-control" id="purchaseEndDate"
                            selected={endDate}
                            onChange={(date: Date) => {
                                const adjustedDate = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()));
                                handleDateFilter(startDate, adjustedDate);
                            }}
                            dateFormat={'dd.MM.yyyy'}
                            minDate={new Date('2024-03-01')}
                            maxDate={new Date()}/>
            </div>

            <div className="form-check-inline mb-3">
                <button className="btn btn-warning"
                        onClick={() => handleDateFilter(new Date('2024-03-01'), new Date())}>Zurücksetzen
                </button>
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