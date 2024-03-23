import {useState} from "react";
import {Category} from "../../types/Category.ts";
import {useNavigate} from "react-router-dom";
import CategoryService from "../../services/CategoryService.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import CategoryCommonFormFields from "../../layouts/CategoryCommonFormFields.tsx";


function CategoryAdd() {
    const [category, setCategory] = useState<Category>({
        name: '',
        id: '',
        description: '',
        createdAt: new Date(),
        updatedAt: new Date(),
        active: false
    });
    const navigate = useNavigate();
    const categoryService = new CategoryService();
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        categoryService.addCategory(category)
            .then(response => {
                console.log(response)
                navigate('/categories')
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
            <PageHeader title="Kategorie hinzufügen" pageType="category"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <CategoryCommonFormFields category={category} setCategory={setCategory} />
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

export default CategoryAdd;