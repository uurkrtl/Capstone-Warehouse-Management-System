
import {useEffect, useState} from "react";
import {Product} from "../../types/Product.ts";
import {useNavigate, useParams} from "react-router-dom";
import ProductService from "../../services/ProductService.ts";
import CategoryService from "../../services/CategoryService.ts";
import {Category} from "../../types/Category.ts";
import ProductCommonFormFields from "../../layouts/ProductCommonFormFields.tsx";
import PageHeader from "../../layouts/PageHeader.tsx";

const productService = new ProductService();
const categoryService = new CategoryService();

function ProductUpdate() {
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
    const [categories, setCategories] = useState<Category[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>('');

    useEffect(() => {
        categoryService.getAllCategories().then((response) => {
            setCategories(response.data);
        });
    });

    useEffect(() => {
        if (id) {
            productService.getProductById(id)
                .then((response) => {
                    setProduct(prevProduct => ({...prevProduct, ...response.data}));
                })
                .catch((error) => {
                    console.error('Error fetching product:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        productService.updateProduct(id, product)
            .then(response => {
                console.log(response)
                navigate('/products/detail/' + id)
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
        <main className={'container'}>
            <PageHeader title="Produktaktualisierung" pageType="product" />

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <ProductCommonFormFields product={product} setProduct={setProduct} categories={categories} />
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

export default ProductUpdate;