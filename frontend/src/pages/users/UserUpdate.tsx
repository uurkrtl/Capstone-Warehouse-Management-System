import UserService from "../../services/UserService.ts";
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {User} from "../../types/User.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import UserCommonFormFields from "../../layouts/UserCommonFormFields.tsx";


const userService = new UserService();
function UserUpdate() {
    const { id = '' } = useParams<string>();
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);
    const [updateUser, setUpdateUser] = useState<User>({
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

    useEffect(() => {
        userService.getLoggedInUser()
            .then((resp) => {
                setUser(resp.data);
            })
            .catch(() => {
                setUser(null);
            });
    }, []);

    useEffect(() => {
        if (id && user) {
            userService.getUserById(user.id, user.role, id)
                .then((response) => {
                    setLoading(false);
                    setUpdateUser(response.data);
                })
                .catch((error) => {
                    console.error('Etwas ist schief gelaufen:', error);
                    navigate('*');
                });
        }
    }, [id, user, navigate]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (id && user) {
            userService.updateUser(user.id, user.role, id, updateUser)
                .then(response => {
                    console.log(response)
                    navigate('/users/detail/' + id)
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
        }
    };

    if (loading) {
        return <div className={'container'}>
            <div className={'spinner-border text-primary'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }

    return (
        <main className={'container'}>
            <PageHeader title="Benutzeraktualisierung" pageType="user"/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <UserCommonFormFields user={updateUser} setUser={setUpdateUser}/>
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

export default UserUpdate;