import React, {useEffect, useState} from "react";
import {StockMovement} from "../../types/StockMovement.ts";
import StockMovementService from "../../services/StockMovementService.ts";
import PageHeader from "../../layouts/PageHeader.tsx";

const stockMovementService = new StockMovementService();
function StockMovementList() {
    const [stockMovements, setStockMovements] = useState<StockMovement[]>([]);
    const [filter, setFilter] = useState("");
    const [stockMovementsByStatus, setStockMovementsByStatus] = useState<StockMovement[]>(stockMovements);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        stockMovementService.getAllStockMovements().then((response) => {
            setStockMovements(response.data);
            setStockMovementsByStatus(response.data);
            setLoading(false);
        });
    }, []);

    const handleStatusChange = (e: React.MouseEvent<HTMLInputElement>) => {
        const target = e.target as HTMLElement;
        if (target.id === "stockEntry") {
            setStockMovementsByStatus(stockMovements.filter((stockMovement) => stockMovement.type));
        } else if (target.id === "stockIssue") {
            setStockMovementsByStatus(stockMovements.filter((stockMovement) => !stockMovement.type));
        } else {
            setStockMovementsByStatus(stockMovements);
        }
    };

    const filteredStockMovements = stockMovementsByStatus.filter(
        (stockMovement) =>
            (stockMovement.productName ? stockMovement.productName.toLowerCase() : '').includes(filter.toLowerCase())
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
            <PageHeader title="Lagerbewegungsliste" pageType="stockMovement"/>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="allStockMovements"
                       value="allStockMovements" onClick={handleStatusChange} defaultChecked/>
                <label className="form-check-label" htmlFor="allStockMovements">Alle Lagerbewegungen</label>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="stockEntry"
                       value="stockEntry" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="stockEntry">Lagereintrag</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="stockIssue"
                       value="stockIssue" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="stockIssue">Lagerausgabe</label>
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
                    <th scope="col">Menge</th>
                    <th scope="col">Typ</th>
                    <th scope="col">Grund</th>
                    <th scope="col">Zeit</th>
                </tr>
                </thead>
                <tbody>
                {filteredStockMovements.map((stockMovement) => {
                    return (
                        <tr key={stockMovement.id}>
                            <td className={!stockMovement.type ? "text-danger" : "text-black"}>{stockMovement.productName}</td>
                            <td>{stockMovement.quantity}</td>
                            <td>
                                {stockMovement.type ?
                                    <span className="badge text-bg-success rounded-pill">Lagereintrag</span>
                                    : <span className="badge text-bg-danger rounded-pill">Lagerausgabe</span>}
                            </td>
                            <td>{stockMovement.reason}</td>
                            <td>{stockMovement.createdAt ? new Date(stockMovement.createdAt).toLocaleString('tr-TR') : "-"}</td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
}

export default StockMovementList;