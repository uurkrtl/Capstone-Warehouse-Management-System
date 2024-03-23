import {Category} from "../types/Category.tsx";

function CategoryCommonFormFields({ category, setCategory }: Readonly<{ category: Category, setCategory: (category: Category) => void }>) {
    return (
        <div className="row g-3">

            <div className="col-sm-6">
                <label htmlFor="name" className="form-label">Kategoriename</label>
                <input type="text" className="form-control" id="name"
                       placeholder="Schreiben Sie den Kategorienamen" value={category.name}
                       onChange={(e) => setCategory({...category, name: e.target.value})}/>
            </div>

            <div className="col-sm-6">
                <label htmlFor="description" className="form-label">Beschreibung</label>
                <input type="text" className="form-control" id="description"
                       placeholder="Schreiben Sie die Beschreibung" value={category.description}
                       onChange={(e) => setCategory({...category, description: e.target.value})}/>
            </div>
        </div>
    );
}

export default CategoryCommonFormFields;