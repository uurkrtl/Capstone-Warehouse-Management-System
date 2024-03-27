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
                            <th scope="row">Datum der Erstellung</th>
                            <td>{purchase.createdAt ? new Date(purchase.createdAt).toLocaleString('tr-TR') : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Datum der Aktualisierung</th>
                            <td>{purchase.updatedAt ? new Date(purchase.updatedAt).toLocaleString('tr-TR') : "-"}</td>
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

                        <Link to={`/purchases`} type="button"
                              className="btn btn-outline-secondary btn-lg px-4">Einkaufsliste</Link>
                    </div>

                </div>
            </div>
        </div>
    );
}

export default PurchaseDetail;