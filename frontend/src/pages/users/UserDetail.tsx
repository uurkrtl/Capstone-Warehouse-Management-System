import UserService from "../../services/UserService.ts";
import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {User} from "../../types/User.ts";


const userService = new UserService();
function UserDetail() {
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const { id = '' } = useParams<string>();
    const [user, setUser] = useState<User | null>(null);
    const [searchUser, setSearchUser] = useState<User>({
        id: '',
        username: '',
        password: '',
        role: 'USER',
        firstName: '',
        lastName: '',
        email: '',
        imageUrl: ''
    });

    useEffect(() => {
        userService.getLoggedInUser()
            .then((response) => {
                setUser(response.data);
            })
            .catch(() => {
                setUser(null);
            });
    }, []);

    useEffect(() => {
        if (id && user) {
            userService.getUserById(user.id, user.role, id)
                .then((response) => {
                    setSearchUser(response.data);
                    setLoading(false);
                })
                .catch((error) => {
                    console.error('Fehler beim Abrufen des Benutzers:', error);
                    navigate('*');
                });
        }
    }, [id, user, navigate]);

    if (loading) {
        return <div className={'container'}>
            <div className={'spinner-border text-primary'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }

    return (
        <div className="container">
            <div className="row flex-lg-row-reverse align-items-center g-5 py-2">
                <div className="col-10 col-sm-8 col-lg-6">
                    <img src={searchUser.imageUrl} className="d-block mx-lg-auto img-fluid rounded-circle" alt="User"
                         width="300" height="300" loading="lazy"/>
                </div>
                <div className="col-lg-6">
                    <h1 className="display-5 fw-bold text-body-emphasis lh-1 mb-3">{`${searchUser.firstName} ${searchUser.lastName}`}</h1>

                    <table className="table table-striped-columns">
                        <tbody>
                        <tr>
                            <th scope="row">Benutzername</th>
                            <td>{searchUser.username}</td>
                        </tr>
                        <tr>
                            <th scope="row">Rolle</th>
                            <td>{searchUser.role}</td>
                        </tr>
                        <tr>
                            <th scope="row">E-Mail-Adresse</th>
                            <td>{searchUser.email}</td>
                        </tr>
                        </tbody>
                    </table>

                    <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                        <Link to={`/`} type="button"
                              className="btn btn-primary btn-lg px-4 me-md-2">Aktualisieren</Link>
                        {searchUser.role === 'ADMIN' && (
                            <button type="button"
                                    className={'btn btn-danger px-4 me-md-2'}
                            >
                                Löschen</button>
                        )}
                        {searchUser.role === 'ADMIN' && (
                            <Link to={`/users`} type="button"
                                  className="btn btn-outline-secondary btn-lg px-4">Benutzerliste</Link>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default UserDetail;