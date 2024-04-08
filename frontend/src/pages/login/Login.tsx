import './Login.css';
import React, { useState } from "react";
import { User } from "../../types/User.ts";
import UserService from "../../services/UserService.ts";
import { useNavigate } from "react-router-dom";

function Login() {
    const navigate = useNavigate();
    const userService = new UserService();
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [user, setUser] = useState<User>({
        id: '',
        username: '',
        password: ''
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
            .catch(error => {
                setUser(prevUser => ({ ...prevUser, password: '' }));
                setErrorMessage(error.message);
            });
    };

    return (
        <div className="body d-flex align-items-center py-4 bg-body-tertiary">
            <main className="form-signin w-100 m-auto">
                <form onSubmit={handleSubmit}>
                    <img className="mb-4" src="/warehouse.svg" alt="" width="72" height="57" />
                    <h1 className="h3 mb-3 fw-normal">Bitte einloggen</h1>

                    <div className="form-floating">
                        <input type="text" className="form-control" id="username" placeholder="user"
                               value={user.username}
                               onChange={(e) => setUser({ ...user, username: e.target.value })} />
                        <label htmlFor="user">Nutzername</label>
                    </div>
                    <div className="form-floating">
                        <input type="password" className="form-control" id="password" placeholder="Password"
                               value={user.password}
                               onChange={(e) => setUser({ ...user, password: e.target.value })} />
                        <label htmlFor="password">Password</label>
                    </div>

                    <button className="btn btn-primary w-100 py-2" type="submit">Anmelden</button>
                    <p className="mt-5 mb-3 text-body-secondary">© 2024 Ugur Kartal</p>
                </form>
                {errorMessage && (
                    <div className="alert alert-danger" role="alert">
                        {errorMessage}
                    </div>
                )}
            </main>
        </div>
    );
}

export default Login;
