import CategoryService from "../../services/CategoryService.ts";
import {Category} from "../../types/Category.ts";
import {useEffect, useState} from "react";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link} from "react-router-dom";


const categoryService = new CategoryService();
function CategoryList() {
    const [categories, setCategories] = useState<Category[]>([]);
    const [filter, setFilter] = useState("");
    const [categoriesByStatus, setCategoriesByStatus] = useState<Category[]>(categories);

    const filteredCategories = categoriesByStatus.filter(
        (category) =>
            category.name.toLowerCase().includes(filter.toLowerCase())
    );

    const handleStatusChange = (e: React.MouseEvent<HTMLInputElement>) => {
        const target = e.target as HTMLElement;
        if (target.id === "activeCategories") {
            setCategoriesByStatus(categories.filter((category) => category.active));
        } else if (target.id === "passiveCategories") {
            setCategoriesByStatus(categories.filter((category) => !category.active));
        } else {
            setCategoriesByStatus(categories);
        }
    };

    useEffect(() => {
        categoryService.getAllCategories().then((response) => {
            setCategories(response.data);
            setCategoriesByStatus(response.data);
        });
    }, []);

    return (
        <div className={'container'}>
            <PageHeader title="Kategorieliste" pageType="category"/>

            <div className="d-flex justify-content-end">
                <Link to={"/categories/add"} className="btn btn-outline-secondary">Kategorie erstellen</Link>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="allCategories"
                       value="allCategories" onClick={handleStatusChange} defaultChecked/>
                <label className="form-check-label" htmlFor="allCategories">Alle Kategorien</label>
            </div>

            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="activeCategories"
                       value="activeCategories" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="activeCategories">Aktive Kategorien</label>
            </div>
            <div className="form-check form-check-inline mb-3">
                <input className="form-check-input" type="radio" name="inlineRadioOptions" id="passiveCategories"
                       value="passiveCategories" onClick={handleStatusChange}/>
                <label className="form-check-label" htmlFor="passiveCategories">Passive Kategorien</label>
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
                    <th scope="col">Status</th>
                    <th scope="col">Detail</th>
                    <th scope="col">Produkte der Kategorie</th>
                </tr>
                </thead>
                <tbody>
                {filteredCategories.map((category) => {
                    return (
                        <tr key={category.id}>
                            <td className={!category.active ? "text-danger" : "text-black"}>{category.name}</td>
                            <td>
                                {category.active ?
                                    <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                    : <span className="badge text-bg-danger rounded-pill">Passiv</span>}
                            </td>
                            <td><Link to={`/categories/detail/${category.id}`}
                                      className="btn btn-outline-info">Detail</Link></td>
                            <td><Link to={"/"} className="btn btn-outline-secondary">Produkte der Kategorie</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
}

export default CategoryList;