import {Supplier} from "../types/Supplier.ts";

function SupplierCommonFormFields({supplier, setSupplier}: Readonly<{ supplier: Supplier, setSupplier: (supplier: Supplier) => void }>) {
    return (
        <div className="row g-3">

            <div className="col-sm-6">
                <label htmlFor="name" className="form-label">Name des Lieferanten</label>
                <input type="text" className="form-control" id="name"
                       placeholder="Schreiben Sie den Name des Lieferanten" value={supplier.name}
                       onChange={(e) => setSupplier({...supplier, name: e.target.value})}/>
            </div>

            <div className="col-sm-6">
                <label htmlFor="contactName" className="form-label">Kontaktname</label>
                <input type="text" className="form-control" id="contactName"
                       placeholder="Schreiben Sie den Kontaktname" value={supplier.contactName}
                       onChange={(e) => setSupplier({...supplier, contactName: e.target.value})}/>
            </div>

            <div className="col-sm-6">
                <label htmlFor="email" className="form-label">E-Mail</label>
                <input type="text" className="form-control" id="email"
                       placeholder="Schreiben Sie den E-Mail-Adresse" value={supplier.email}
                       onChange={(e) => setSupplier({...supplier, email: e.target.value})}/>
            </div>

            <div className="col-sm-6">
                <label htmlFor="phone" className="form-label">Telefon</label>
                <input type="text" className="form-control" id="phone"
                       placeholder="Schreiben Sie die Telefonnummer" value={supplier.phone}
                       onChange={(e) => setSupplier({...supplier, phone: e.target.value})}/>
            </div>
        </div>
    );
}

export default SupplierCommonFormFields;