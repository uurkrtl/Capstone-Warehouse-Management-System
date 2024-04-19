import PurchaseService from "../../services/PurchaseService.ts";
import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Purchase} from "../../types/Purchase.ts";


const purchaseService = new PurchaseService();
function PurchaseDetail() {
    const { id = '' } = useParams<string>();
    const [purchase, setPurchase] = useState<Purchase>({
        id: '',
        productId: '',
        productName: '',
        supplierId: '',
        supplierName: '',
        purchasePrice: 0,
        quantity: 0,
        totalPrice: 0,
        purchaseDate: new Date,
        createdAt: new Date(),
        updatedAt: new Date(),
        active: true
    });
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleStatusChange = (status: boolean) => {
        purchaseService.changePurchaseStatus(id, status)
            .then(() => {
                console.log('Der Kaufstatus wurde erfolgreich geändert');
                setPurchase({
                    ...purchase,
                    active: status
                });
            })
            .catch((error) => {
                if (error.response) {
                    if (error.response.status === 403) {
                        setErrorMessage('Sie haben keine Berechtigung, den Status zu ändern.');
                    }else {
                        setErrorMessage(error.response.data.message)
                    }
                } else {
                    setErrorMessage('Fehler beim Ändern des Status: ' + error.message);
                    return;
                }
            });
    }

    useEffect(() => {
        if (id) {
            purchaseService.getPurchaseById(id)
                .then((response) => {
                    setPurchase(response.data);
                })
                .catch((error) => {
                    console.error('Fehler beim Abrufen des Kaufs:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    return (
        <div className="container">
            <div className="row flex-lg-row align-items-center g-5 py-2">
                <div className="col-lg-6">
                    <h1 className="display-5 fw-bold text-body-emphasis lh-1 mb-3">{purchase.productName}</h1>
                    <p className="lead">{purchase.supplierName}</p>

                    <table className="table table-striped-columns mt-4">
                        <tbody>
                        <tr>
                            <th scope="row">Einzelpreis</th>
                            <td>{purchase.purchasePrice.toLocaleString('de-DE', {
                                style: 'currency',
                                currency: 'EUR'
                            })}</td>
                        </tr>
                        <tr>
                            <th scope="row">Menge</th>
                            <td>{purchase.quantity}</td>
                        </tr>
                        <tr>
                            <th scope="row">Gesamtpreis</th>
                            <td>{purchase.totalPrice.toLocaleString('de-DE', {
                                style: 'currency',
                                currency: 'EUR'
                            })}</td>
                        </tr>
                        <tr>
                            <th scope="row">Kaufdatum</th>
                            <td>{purchase.purchaseDate ? new Date(purchase.purchaseDate).toLocaleDateString() : '-'}</td>
                        </tr>
                        <tr>
                            <th scope="row">Erstellung</th>
                            <td>{purchase.createdAt ? new Date(purchase.createdAt).toLocaleString('de-DE') : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Letzte Aktualisierung</th>
                            <td>{purchase.updatedAt ? new Date(purchase.updatedAt).toLocaleString('de-DE') : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Status</th>
                            <td>{purchase.active ?
                                <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                : <span className="badge text-bg-danger rounded-pill">Passiv</span>}</td>
                        </tr>
                        </tbody>
                    </table>

                    <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                        <Link to={`/purchases/update/${purchase.id}`} type="button"
                              className="btn btn-primary btn-lg px-4 me-md-2">Aktualisieren</Link>
                        <button type="button"
                                className={purchase.active ? 'btn btn-danger px-4 me-md-2' : 'btn btn-success px-4 me-md-2'}
                                onClick={() => handleStatusChange(!purchase.active)}>
                            {purchase.active ? 'Deaktivieren' : 'Aktivieren'}</button>
                        <Link to={`/purchases`} type="button"
                              className="btn btn-outline-secondary btn-lg px-4">Einkaufsliste</Link>
                    </div>
                    {errorMessage && (
                        <div className="alert alert-danger mt-3" role="alert">
                            {errorMessage}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default PurchaseDetail;