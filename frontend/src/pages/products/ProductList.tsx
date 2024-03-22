import {useEffect, useState} from "react";
import {Product} from "../../types/Product.ts";
import ProductService from "../../services/ProductService.ts";
import {Link} from "react-router-dom";
import PageHeader from "../../layouts/PageHeader.tsx";

const productService = new ProductService();

function ProductList() {
    const [products, setProducts] = useState<Product[]>([]);
    const [filter, setFilter] = useState("");
    const [productByStatus, setProductByStatus] = useState<Product[]>(products);

    const filteredProducts = productByStatus.filter(
        (product) =>
            product.name.toLowerCase().includes(filter.toLowerCase())
    );

    const handleStatusChange = (e: React.MouseEvent<HTMLInputElement>) => {
        const target = e.target as HTMLElement;
        if (target.id === "activeProducts") {
            setProductByStatus(products.filter((product) => product.active));
        } else if (target.id === "passiveProducts") {
            setProductByStatus(products.filter((product) => !product.active));
        } else {
            setProductByStatus(products);
        }
    };

    useEffect(() => {
        productService.getAllProducts().then((response) => {
            setProducts(response.data);
            setProductByStatus(response.data);
        });
    }, []);

    return (
        <div className={'container'}>
            <PageHeader title="Produktliste" pageType="product" />

            <div className="d-flex justify-content-end">
                <Link to={"/products/add"} className="btn btn-outline-secondary">Produkt erstellen</Link>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="allProducts"
                       value="allProducts" onClick={handleStatusChange} defaultChecked/>
                <label className="form-check-label" htmlFor="allProducts">Alle Produkte</label>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="activeProducts"
                       value="activeProducts" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="activeProducts">Aktive Produkte</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="passiveProducts"
                       value="passiveProducts" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="passiveProducts">Passive Produkte</label>
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
                    <th scope="col">Sale Price</th>
                    <th scope="col">Kategoriename</th>
                    <th scope="col">Status</th>
                    <th scope="col">Detail</th>
                    <th scope="col">Kaufhistorie</th>
                    <th scope="col">Verkaufsübersicht</th>
                </tr>
                </thead>
                <tbody>
                {filteredProducts.map((product) => {
                    return (
                        <tr key={product.id}>
                            <td className={!product.active ? "text-danger" : "text-black"}>{product.name}</td>
                            <td>{product.salePrice}</td>
                            <td>{product.categoryName}</td>
                            <td>
                                {product.active ?
                                    <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                    : <span className="badge text-bg-danger rounded-pill">Passiv</span>}
                            </td>
                            <td><Link to={`/products/detail/${product.id}`}
                                      className="btn btn-outline-info">Detail</Link></td>
                            <td><Link to={"/"} className="btn btn-outline-secondary">Kaufhistorie</Link></td>
                            <td><Link to={"/"} className="btn btn-outline-success">Verkaufsübersicht</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
}

export default ProductList;