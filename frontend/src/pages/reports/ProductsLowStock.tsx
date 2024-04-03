import ReportService from "../../services/ReportService.ts";
import {useEffect, useState} from "react";
import {Product} from "../../types/Product.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link} from "react-router-dom";


const reportService = new ReportService();
function ProductsLowStock() {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        reportService.getProductsLowInStock().then((response) => {
            setProducts(response.data);
            setLoading(false);
        });
    }, []);

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
            <PageHeader title="Nicht vorrätige Produkte" pageType="report"/>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Verkaufspreis</th>
                    <th scope="col">Kategoriename</th>
                    <th scope="col">Kritischer Lagerbestand</th>
                    <th scope="col">Lagerbestand</th>
                    <th scope="col">Detail</th>
                </tr>
                </thead>
                <tbody>
                {products.map((product) => {
                    return (
                        <tr key={product.id}>
                            <td>{product.name}</td>
                            <td>{product.salePrice}</td>
                            <td>{product.categoryName}</td>
                            <td>{product.criticalStock}</td>
                            <td>{product.stock}</td>
                            <td><Link to={`/products/detail/${product.id}`}
                                      className="btn btn-outline-info" target='_blank'>Detail</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
}

export default ProductsLowStock;