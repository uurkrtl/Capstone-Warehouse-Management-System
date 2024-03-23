import CategoryService from "../../services/CategoryService.ts";
import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Category} from "../../types/Category.ts";


const categoryService = new CategoryService();
function CategoryDetail() {
    const { id = '' } = useParams<string>();
    const [category, setCategory] = useState<Category>({
        name: '',
        id: '',
        description: '',
        createdAt: new Date(),
        updatedAt: new Date(),
        active: false
    });
    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            categoryService.getCategoryById(id)
                .then((response) => {
                    setCategory(response.data);
                })
                .catch((error) => {
                    console.error('Fehler beim Abrufen der Kategorie:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    return (
        <div className="container">
            <div className="row flex-lg-row align-items-center g-5 py-2">
                <div className="col-lg-6">
                    <h1 className="display-5 fw-bold text-body-emphasis lh-1 mb-3">{category.name}</h1>
                    <p className="lead">{category.description}</p>

                    <table className="table table-striped-columns">
                        <tbody>
                        <tr>
                            <th scope="row">Hergestellt am</th>
                            <td>{category.createdAt ? category.createdAt.toString() : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Aktualisiert am</th>
                            <td>{category.updatedAt ? category.updatedAt.toString() : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Status</th>
                            <td>{category.active ?
                                <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                : <span className="badge text-bg-danger rounded-pill">Passiv</span>}</td>
                        </tr>
                        </tbody>
                    </table>

                    <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                        <Link to={`/categories/update/${category.id}`} type="button"
                              className="btn btn-primary btn-lg px-4 me-md-2">Aktualisieren</Link>

                        <Link to={`/categories`} type="button"
                              className="btn btn-outline-secondary btn-lg px-4">Produktliste</Link>
                    </div>

                </div>
            </div>
        </div>
    );
}

export default CategoryDetail;