import {useEffect, useState} from 'react';
import SupplierService from "../../services/SupplierService.ts";
import {useNavigate, useParams} from "react-router-dom";
import {Supplier} from "../../types/Supplier.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import SupplierCommonFormFields from "../../layouts/SupplierCommonFormFields.tsx";

const supplierService = new SupplierService();
function SupplierUpdate() {
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

    useEffect(() => {
        if (id) {
            supplierService.getSupplierById(id)
                .then((response) => {
                    setSupplier(prevCategory => ({...prevCategory, ...response.data}));
                })
                .catch((error) => {
                    console.error('Etwas ist schief gelaufen:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        supplierService.updateSupplier(id, supplier)
            .then(response => {
                console.log(response)
                navigate('/suppliers/detail/' + id)
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
            <PageHeader title="Lieferantenaktualisierung" pageType="supplier"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <SupplierCommonFormFields supplier={supplier} setSupplier={setSupplier}/>
                        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Aktualisieren</button>
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

export default SupplierUpdate;