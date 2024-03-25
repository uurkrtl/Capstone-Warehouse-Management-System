import {Supplier} from "../../types/Supplier.ts";
import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import SupplierService from "../../services/SupplierService.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import SupplierCommonFormFields from "../../layouts/SupplierCommonFormFields.tsx";


function SupplierAdd() {
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
    const supplierService = new SupplierService();
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        supplierService.addSupplier(supplier)
            .then(response => {
                console.log(response)
                navigate('/suppliers')
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response.data);
                    setErrorMessage(error.response.data.message);
                } else {
                    console.log('Etwas ist schief gelaufen:', error.message);
                    setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
                }
            });
    };

    return (
        <main className={'container'}>
            <PageHeader title="Lieferant hinzufügen" pageType="supplier"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <SupplierCommonFormFields supplier={supplier} setSupplier={setSupplier}/>
                        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Speichern</button>
                    </form>

                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}

                </div>
            </div>
        </main>
    );
}

export default SupplierAdd;