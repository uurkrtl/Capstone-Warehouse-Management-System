import CategoryService from "../../services/CategoryService.ts";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Category} from "../../types/Category.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import CategoryCommonFormFields from "../../layouts/CategoryCommonFormFields.tsx";


const categoryService = new CategoryService();

function CategoryUpdate() {
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
    const [errorMessage, setErrorMessage] = useState<string>('');

    useEffect(() => {
        if (id) {
            categoryService.getCategoryById(id)
                .then((response) => {
                    setCategory(prevCategory => ({...prevCategory, ...response.data}));
                })
                .catch((error) => {
                    console.error('Fehler beim Abrufen der Kategorie:', error);
                    navigate('*');
                });
        }
    }, [id, navigate]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        categoryService.updateCategory(id, category)
            .then(response => {
                console.log(response)
                navigate('/categories/detail/' + id)
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response.data);
                    setErrorMessage(error.response.data.message);
                } else {
                    console.log('Etwas ist schief gelaufen:', error.message);
                    setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
                }
            });
    };

    return (
        <main className={'container'}>
            <PageHeader title="Kategorieaktualisierung" pageType="category"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <CategoryCommonFormFields category={category} setCategory={setCategory} />
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

export default CategoryUpdate;