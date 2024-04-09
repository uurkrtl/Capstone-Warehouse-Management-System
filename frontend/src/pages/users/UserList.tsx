import UserService from "../../services/UserService.ts";
import {useEffect, useState} from "react";
import {User} from "../../types/User.ts";
import PageHeader from "../../layouts/PageHeader.tsx";
import {Link, useNavigate} from "react-router-dom";

const userService = new UserService();
function UserList() {
    const [users, setUsers] = useState<User[]>([]);
    const [usernameFilter, setUsernameFilter] = useState("");
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const navigate = useNavigate();

    useEffect(() => {
        userService.getAllUsers().then((response) => {
            setUsers(response.data);
            setUsernameFilter('');
            setLoading(false);
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Benutzer: ${error.message}`);
            setLoading(false);
            setUsers([]);
            navigate('/login')
        });
    }, [navigate]);

    const filteredUsers = users.filter(
        (user) =>
            user.username.toLowerCase().includes(usernameFilter.toLowerCase())
    );

    if (loading) {
        return <div className={'container'}>
            <div className={'spinner-border text-primary'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }

    return (
        <div className={'container'}>
            <PageHeader title="Benutzerliste" pageType="user"/>

            <div className="d-flex justify-content-end">
                <Link to={"/users/add"} className="btn btn-outline-secondary mb-3">Benutzer erstellen</Link>
            </div>

            <div className="input-group">
                <span className="input-group-text" id="basic-addon3">Schreiben einen Benutzernamenfilter</span>
                <input
                    type="text"
                    className="form-control"
                    id="basic-url"
                    aria-describedby="basic-addon3 basic-addon4"
                    onChange={(e) => setUsernameFilter(e.target.value)}
                />
            </div>

            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Vorname</th>
                    <th scope="col">Nachname</th>
                    <th scope="col">Benutzername</th>
                    <th scope="col">Rolle</th>
                    <th scope="col">Detail</th>
                </tr>
                </thead>
                <tbody>
                {filteredUsers.map((user) => {
                    return (
                        <tr key={user.id}>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>{user.username}</td>
                            <td>{user.role.charAt(0).toUpperCase() + user.role.slice(1).toLowerCase()}</td>
                            <td><Link to={`/users/detail/${user.id}`}
                                      className="btn btn-outline-info">Detail</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
            {errorMessage && (
                <div className="alert alert-danger" role="alert">
                    {errorMessage}
                </div>
            )}
        </div>
    );
}

export default UserList;