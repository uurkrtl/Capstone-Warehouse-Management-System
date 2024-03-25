import SupplierService from "../../services/SupplierService.ts";
import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Supplier} from "../../types/Supplier.ts";

const supplierService = new SupplierService();
function SupplierDetail() {
    const { id = '' } = useParams<string>();
    const [supplier, setSupplier] = useState<Supplier>({
        name: '',
        id: '',
        contactName: '',
        email: '',
        phone: '',
        createdAt: new Date(),
        updatedAt: new Date(),
        active: true
    });
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState<string>('');
    const handleStatusChange = (status: boolean) => {
        supplierService.changeSupplierStatus(id, status)
            .then(() => {
                console.log('Der Lieferantenstatus wurde erfolgreich geändert');
                setSupplier({
                    ...supplier,
                    active: status
                });
            })
            .catch((error) => {
                console.error('Fehler beim Ändern des Lieferantenstatus:', error);
                setErrorMessage(error.response.data.message);
            });
    }

    useEffect(() => {
        if (id) {
            supplierService.getSupplierById(id)
                .then((response) => {
                    setSupplier(response.data);
                })
                .catch((error) => {
                    console.error('Fehler beim Abrufen des Lieferantes:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    return (
        <div className="container">
            <div className="row flex-lg-row align-items-center g-5 py-2">
                <div className="col-lg-6">
                    <h1 className="display-5 fw-bold text-body-emphasis lh-1 mb-3">{supplier.name}</h1>

                    <table className="table table-striped-columns mt-4">
                        <tbody>
                        <tr>
                            <th scope="row">Kontaktname</th>
                            <td>{supplier.contactName}</td>
                        </tr>
                        <tr>
                            <th scope="row">E-Mail</th>
                            <td>{supplier.email}</td>
                        </tr>
                        <tr>
                            <th scope="row">Telefon</th>
                            <td>{supplier.phone}</td>
                        </tr>
                        <tr>
                            <th scope="row">Hergestellt am</th>
                            <td>{supplier.createdAt ? supplier.createdAt.toString() : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Aktualisiert am</th>
                            <td>{supplier.updatedAt ? supplier.updatedAt.toString() : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Status</th>
                            <td>{supplier.active ?
                                <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                : <span className="badge text-bg-danger rounded-pill">Passiv</span>}</td>
                        </tr>
                        </tbody>
                    </table>

                    <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                        <Link to={`/suppliers/update/${supplier.id}`} type="button"
                              className="btn btn-primary btn-lg px-4 me-md-2">Aktualisieren</Link>
                        <button type="button"
                                className={supplier.active ? 'btn btn-danger px-4 me-md-2' : 'btn btn-success px-4 me-md-2'}
                                onClick={() => handleStatusChange(!supplier.active)}>
                            {supplier.active ? 'Deaktivieren' : 'Aktivieren'}</button>
                        <Link to={`/suppliers`} type="button"
                              className="btn btn-outline-secondary btn-lg px-4">Lieferantenliste</Link>
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

export default SupplierDetail;