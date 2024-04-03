import ReportService from "../../services/ReportService.ts";
import {useEffect, useState} from "react";
import {Product} from "../../types/Product.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link, useNavigate, useParams} from "react-router-dom";


const reportService = new ReportService();
function ProductReport() {
    const { reportType = '' } = useParams<string>();
    const [pageTitle, setPageTitle] = useState<string>('');
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        switch (reportType) {
            case 'products-low-stock':
                setPageTitle('Produkte mit niedrigem Lagerbestand');
                reportService.getProductsLowInStock().then((response) => {
                    setProducts(response.data);
                    setLoading(false);
                }).catch(error => {
                    console.error("Fehler beim Abrufen von Produkten mit geringem Lagerbestand:", error);
                    setLoading(false);
                });
                break;
            case 'products-out-of-stock':
                setPageTitle('Nicht vorrätige Produkte');
                reportService.getProductsOutOfStock().then((response) => {
                    setProducts(response.data);
                    setLoading(false);
                }).catch(error => {
                    console.error("Fehler beim Abrufen nicht vorrätiger Produkte:", error);
                    setLoading(false);
                });
                break;
            default:
                navigate('*')
                break;
        }
    }, [reportType, navigate]);

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
            <PageHeader title = {pageTitle} pageType="report"/>
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

export default ProductReport;