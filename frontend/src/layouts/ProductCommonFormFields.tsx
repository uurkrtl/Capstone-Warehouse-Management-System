import {Product} from "../types/Product.tsx";
import {Category} from "../types/Category.tsx";

function ProductCommonFormFields({ product, setProduct, categories }: { product: Product, setProduct: (product: Product) => void, categories: Category[] }) {
    return (
        <div className="row g-3">

            <div className="col-sm-6">
                <label htmlFor="name" className="form-label">Produktname</label>
                <input type="text" className="form-control" id="name"
                       placeholder="Schreiben Sie den Produktnamen" value={product.name}
                       onChange={(e) => setProduct({...product, name: e.target.value})}/>
            </div>

            <div className="col-sm-6">
                <label htmlFor="description" className="form-label">Beschreibung</label>
                <input type="text" className="form-control" id="description"
                       placeholder="Schreiben Sie die Beschreibung" value={product.description}
                       onChange={(e) => setProduct({...product, description: e.target.value})}/>
            </div>

            <div className="col-sm-4">
                <label htmlFor="salePrice" className="form-label">Verkaufspreis</label>
                <input type="number" step="0.01" className="form-control" id="salePrice"
                       placeholder="Schreiben Sie den Verkaufspreis" value={product.salePrice}
                       onChange={(e) => setProduct({
                           ...product,
                           salePrice: e.target.valueAsNumber
                       })}/>
            </div>

            <div className="col-sm-4">
                <label htmlFor="criticalStock" className="form-label">Kritischer Lagerbestand</label>
                <input type="number" className="form-control" id="criticalStock"
                       placeholder="Schreiben Sie den kritischen Lagerbestand" value={product.criticalStock}
                       onChange={(e) => setProduct({
                           ...product,
                           criticalStock: e.target.valueAsNumber
                       })}/>
            </div>

            <div className="col-sm-4">
                <label htmlFor="categoryName" className="form-label">Kategoriename</label>
                <select className="form-select" id="categoryName"
                        value={product.categoryId ? product.categoryId : ''}
                        onChange={(e) => setProduct({...product, categoryId: e.target.value})}>
                    {!product.categoryId && <option value="">Wählen...</option>}
                    <option
                        value={categories.filter(category => category.name === product.categoryName).map(category => category.id)}>{product.categoryName}</option>
                    {categories.filter((category => category.name !== product.categoryName)).map((category) => {
                        return (
                            <option key={category.id}
                                    value={category.id}>{category.name}</option>
                        )
                    })};
                </select>
            </div>

            <div className="col-sm-12">
                <label htmlFor="imageUrl" className="form-label">Bild URL</label>
                <input type="text" className="form-control" id="imageUrl"
                       placeholder="Schreiben Sie die Bild URL" value={product.imageUrl}
                       onChange={(e) => setProduct({...product, imageUrl: e.target.value})}/>
            </div>
        </div>
    );
}

export default ProductCommonFormFields;