import {Product} from "../../types/Product.ts";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ProductService from "../../services/ProductService.ts";
import {Category} from "../../types/Category.ts";
import CategoryService from "../../services/CategoryService.ts";
import ProductCommonFormFields from "../../layouts/ProductCommonFormFields.tsx";
import PageHeader from "../../layouts/PageHeader.tsx";


function ProductAdd() {
    const [product, setProduct] = useState<Product>({
        name: '',
        id: '',
        description: '',
        salePrice: 0,
        stock: 0,
        criticalStock: 0,
        imageUrl: '',
        categoryId: '',
        categoryName: '',
        createdAt: new Date(),
        updatedAt: new Date(),
        active: false
    });
    const navigate = useNavigate();
    const productService = new ProductService();
    const categoryService = new CategoryService();
    const [categories, setCategories] = useState<Category[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>('');

    useEffect(() => {
        categoryService.getAllCategories().then((response) => {
            setCategories(response.data);
        });
    });

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        productService.addProduct(product)
            .then(response => {
                console.log(response)
                navigate('/products')
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response.data);
                    setErrorMessage(error.response.data.message);
                } else {
                    console.log('Something went wrong:', error.message);
                    setErrorMessage('Something went wrong: ' + error.message);
                }
            });
    };

    return (
        <main>
            <PageHeader title="Produkt hinzufügen" pageType="product" />

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <ProductCommonFormFields product={product} setProduct={setProduct} categories={categories} />
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

export default ProductAdd;