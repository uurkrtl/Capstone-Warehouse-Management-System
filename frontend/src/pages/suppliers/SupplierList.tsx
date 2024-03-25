import SupplierService from "../../services/SupplierService.ts";
import {useEffect, useState} from "react";
import {Supplier} from "../../types/Supplier.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link} from "react-router-dom";

const supplierService = new SupplierService();
function SupplierList() {
    const [suppliers, setSuppliers] = useState<Supplier[]>([]);
    const [filter, setFilter] = useState("");
    const [suppliersByStatus, setSuppliersByStatus] = useState<Supplier[]>(suppliers);

    const filteredSuppliers = suppliersByStatus.filter(
        (category) =>
            category.name.toLowerCase().includes(filter.toLowerCase())
    );

    const handleStatusChange = (e: React.MouseEvent<HTMLInputElement>) => {
        const target = e.target as HTMLElement;
        if (target.id === "activeSuppliers") {
            setSuppliersByStatus(suppliers.filter((supplier) => supplier.active));
        } else if (target.id === "passiveSuppliers") {
            setSuppliersByStatus(suppliers.filter((supplier) => !supplier.active));
        } else {
            setSuppliersByStatus(suppliers);
        }
    };

    useEffect(() => {
        supplierService.getAllSuppliers().then((response) => {
            setSuppliers(response.data);
            setSuppliersByStatus(response.data);
        });
    }, []);

    return (
        <div className={'container'}>
            <PageHeader title="Lieferantenliste" pageType="supplier"/>

            <div className="d-flex justify-content-end">
                <Link to={"/suppliers/add"} className="btn btn-outline-secondary">Lieferant erstellen</Link>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="allSuppliers"
                       value="allSuppliers" onClick={handleStatusChange} defaultChecked/>
                <label className="form-check-label" htmlFor="allSuppliers">Alle Lieferanten</label>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="activeSuppliers"
                       value="activeSuppliers" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="activeSuppliers">Aktive Lieferanten</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="passiveSuppliers"
                       value="passiveSuppliers" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="passiveSuppliers">Passive Lieferanten</label>
            </div>

            <div className="input-group">
                <span className="input-group-text" id="basic-addon3">Schreiben einen Namensfilter</span>
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
                    <th scope="col">Name</th>
                    <th scope="col">Kontaktname</th>
                    <th scope="col">Status</th>
                    <th scope="col">Detail</th>
                    <th scope="col">Kaufhistorie</th>
                </tr>
                </thead>
                <tbody>
                {filteredSuppliers.map((supplier) => {
                    return (
                        <tr key={supplier.id}>
                            <td className={!supplier.active ? "text-danger" : "text-black"}>{supplier.name}</td>
                            <td>{supplier.contactName}</td>
                            <td>
                                {supplier.active ?
                                    <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                    : <span className="badge text-bg-danger rounded-pill">Passiv</span>}
                            </td>
                            <td><Link to={`/suppliers/detail/${supplier.id}`}
                                      className="btn btn-outline-info">Detail</Link></td>
                            <td><Link to={"/"} className="btn btn-outline-secondary">Kaufhistorie</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
}

export default SupplierList;