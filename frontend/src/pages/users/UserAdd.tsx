import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import {User} from "../../types/User.ts";
import UserService from "../../services/UserService.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import UserCommonFormFields from "../../layouts/UserCommonFormFields.tsx";


function UserAdd() {
    const [user, setUser] = useState<User>({
        id: '',
        username: '',
        password: '',
        role: 'USER',
        firstName: '',
        lastName: '',
        email: '',
        imageUrl: '',
        createdAt: new Date(),
        updatedAt: new Date()
    });
    const navigate = useNavigate();
    const userService = new UserService();
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        userService.register(user)
            .then(response => {
                console.log(response)
                navigate('/users')
            })
            .catch(error => {
                if (error.response) {
                    setErrorMessage(error.response.data.message);
                } else {
                    setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
                }
            });
    };

    return (
        <main className={'container'}>
            <PageHeader title="Benutzer hinzufügen" pageType="user"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <UserCommonFormFields user={user} setUser={setUser}/>
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

export default UserAdd;