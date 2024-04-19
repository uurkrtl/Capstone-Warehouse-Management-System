import './Login.css';
import React, { useState } from "react";
import { User } from "../../types/User.ts";
import UserService from "../../services/UserService.ts";
import { useNavigate } from "react-router-dom";

function Login() {
    const userName: string = 'demo';
    const password: string = 'useruser';
    const navigate = useNavigate();
    const userService = new UserService();
    const [errorMessage, setErrorMessage] = useState<string>('');
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

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        userService.login(user)
            .then(() => {
                setUser(prevUser => ({ ...prevUser, username:'', password: '' }));
                setErrorMessage('');
                navigate('/');
                window.location.reload()
            })
            .catch((error) => {
                if (error.response) {
                    if (error.response.status === 401) {
                        setErrorMessage('Benutzername oder Passwort sind falsch.');
                    }else {
                        setErrorMessage(error.response.data.message)
                    }
                } else {
                    setErrorMessage('Fehler beim Ändern des Status: ' + error.message);
                }
            });
    };

    return (
        <div className="body d-flex align-items-center py-4 bg-body-tertiary">
            <main className="form-signin w-100 m-auto">
                <form onSubmit={handleSubmit}>
                    <img className="mb-4" src="/warehouse.svg" alt="" width="72" height="57"/>
                    <h1 className="h3 mb-3 fw-normal">Bitte einloggen</h1>

                    <div className="form-floating">
                        <input type="text" className="form-control" id="username" placeholder="user"
                               value={user.username}
                               onChange={(e) => setUser({...user, username: e.target.value})}/>
                        <label htmlFor="user">Benutzername</label>
                    </div>
                    <div className="form-floating">
                        <input type="password" className="form-control" id="password" placeholder="Password"
                               value={user.password}
                               onChange={(e) => setUser({...user, password: e.target.value})}/>
                        <label htmlFor="password">Password</label>
                    </div>

                    <button className="btn btn-primary w-100 py-2" type="submit">Anmelden</button>
                    <button className="btn btn-success w-100 py-2 mt-3"
                            onClick={() => setUser({
                                ...user,
                                password: password,
                                username: userName
                            })} type="submit">Demo-Login (ohne Benutzer)
                    </button>
                    {errorMessage && (
                        <div className="alert alert-danger mt-3" role="alert">
                            {errorMessage}
                        </div>
                    )}
                    <p className="mt-5 mb-3 text-body-secondary">© 2024 Ugur Kartal</p>
                </form>
            </main>
        </div>
    );
}

export default Login;
