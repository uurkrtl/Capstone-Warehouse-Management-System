import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Product} from "../../types/Product.ts";
import ProductService from "../../services/ProductService.ts";

const productService = new ProductService();

function ProductDetail() {
    const { id = '' } = useParams<string>();
    const [product, setProduct] = useState<Product>({
        name: '',
        id: id,
        description: '',
        salePrice: 0,
        stock: 0,
        criticalStock: 0,
        imageUrl: '',
        categoryId: '',
        categoryName: '',
        createdAt: new Date(),
        updatedAt: new Date(),
        active: true
    });
    const navigate = useNavigate();
    const handleStatusChange = (status: boolean) => {
        productService.changeProductStatus(id, status)
            .then(() => {
                console.log('Product status changed successfully');
                setProduct({
                    ...product,
                    active: status
                });
            })
            .catch((error) => {
                console.error('Error changing product status:', error);
            });
    }

    useEffect(() => {
        if (id) {
            productService.getProductById(id)
                .then((response) => {
                    setProduct(response.data);
                })
                .catch((error) => {
                    console.error('Error fetching product:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    return (
        <div className="container">
        <div className="row flex-lg-row-reverse align-items-center g-5 py-2">
            <div className="col-10 col-sm-8 col-lg-6">
                <img src={product.imageUrl} className="d-block mx-lg-auto img-fluid" alt="Product"
                     width="400" height="300" loading="lazy"/>
            </div>
            <div className="col-lg-6">
                <h1 className="display-5 fw-bold text-body-emphasis lh-1 mb-3">{product.name}</h1>
                <p className="lead">{product.description}</p>

                <table className="table table-striped-columns">
                    <tbody>
                    <tr>
                        <th scope="row">Verkaufspreis</th>
                        <td>{product.salePrice}</td>
                    </tr>
                    <tr>
                        <th scope="row">Menge des Lagerbestands</th>
                        <td>{product.stock}</td>
                    </tr>
                    <tr>
                        <th scope="row">Kritischer Lagerbestand</th>
                        <td>{product.criticalStock}</td>
                    </tr>
                    <tr>
                        <th scope="row">Kategoriename</th>
                        <td>{product.categoryName}</td>
                    </tr>
                    <tr>
                        <th scope="row">Hergestellt am</th>
                        <td>{product.createdAt ? product.createdAt.toString() : "-"}</td>
                    </tr>
                    <tr>
                        <th scope="row">Aktualisiert am</th>
                        <td>{product.updatedAt ? product.updatedAt.toString() : "-"}</td>
                    </tr>
                    <tr>
                        <th scope="row">Status</th>
                        <td>{product.active ?
                            <span className="badge text-bg-success rounded-pill">Aktiv</span>
                            : <span className="badge text-bg-danger rounded-pill">Passiv</span>}</td>
                    </tr>
                    </tbody>
                </table>

                <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                    <Link to={`/products/update/${product.id}`} type="button" className="btn btn-primary btn-lg px-4 me-md-2">Aktualisieren</Link>
                    <button type="button" className={product.active ? 'btn btn-danger px-4 me-md-2' : 'btn btn-success px-4 me-md-2'} onClick={() => handleStatusChange(!product.active)}>
                        {product.active ? 'Deaktivieren' : 'Aktivieren'}</button>
                    <Link to={`/products`} type="button" className="btn btn-outline-secondary btn-lg px-4">Produktliste</Link>
                </div>

            </div>
        </div>
        </div>
    );
}

export default ProductDetail;