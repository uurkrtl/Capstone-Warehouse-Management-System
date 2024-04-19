import {Link, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {User} from "../types/User.ts";
import UserService from "../services/UserService.ts";

function Navbar() {
    const userService = new UserService();
    const [user, setUser] = useState<User | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        userService.getLoggedInUser()
            .then((response) => {
                setUser(response.data);
            })
            .catch(() => {
                setUser(null);
            });
    }, []);

    function logout() {
        userService.logout()
            .then(() => navigate('/login'))
            .catch((error) => console.error('Etwas ist schief gelaufen:', error))
            .finally(() => setUser(null));
    }

    return (
        <header data-bs-theme="dark">
            <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark" aria-label="Fourth navbar example">
                <div className="container-fluid">
                    <div className="mx-1">
                        <Link to={'/'} className="navbar-brand text-decoration-none">
                            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32">
                                <path
                                    fill="none"
                                    stroke="#fff"
                                    fillRule="evenodd"
                                    d="M16.178 3a.5.5 0 0 0-.006.002.5.5 0 0 0-.01 0 .5.5 0 0 0-.17.043L4.434 7.988a.5.5 0 0 0-.422.5V23.49a.5.5 0 0 0 .043.207.5.5 0 0 0 .002.006.5.5 0 0 0 .013.027.5.5 0 0 0 .256.239l11.567 4.945a.5.5 0 0 0 .341.092.5.5 0 0 0 .246-.08l11.594-4.957a.5.5 0 0 0 .307-.395.5.5 0 0 0 .004-.027.5.5 0 0 0 .002-.024.5.5 0 0 0 0-.025.5.5 0 0 0 0-.008V8.488a.5.5 0 0 0-.414-.498L16.406 3.045a.5.5 0 0 0-.031-.014.5.5 0 0 0-.004-.002.5.5 0 0 0-.045-.011.5.5 0 0 0-.015-.004.5.5 0 0 0-.034-.008.5.5 0 0 0-.015-.002.5.5 0 0 0-.033-.002.5.5 0 0 0-.051-.002zm.021 1.045 2.451 1.047L8.262 9.535a.5.5 0 0 0-.03.014L5.78 8.5 16.2 4.045zm3.723 1.592 2.974 1.271-10.582 4.526a.5.5 0 0 0-.3.51.5.5 0 0 0-.002.058v5.35l-1.084-1.627a.5.5 0 0 0-.639-.17l-1.277.638v-5.89l10.91-4.666zm4.246 1.814L26.621 8.5 16.2 12.955l-2.451-1.047 10.39-4.443a.5.5 0 0 0 .03-.014zM5.012 9.258l3 1.283v6.461a.5.5 0 0 0 .724.447l1.604-.803 1.728 2.592a.5.5 0 0 0 .256.233.5.5 0 0 0 .045.015.5.5 0 0 0 .002.002.5.5 0 0 0 .098.018.5.5 0 0 0 .05.002.5.5 0 0 0 .05-.002.5.5 0 0 0 .05-.01.5.5 0 0 0 .03-.008.5.5 0 0 0 .017-.004.5.5 0 0 0 .346-.54v-6.262l2.687 1.148v13.914l-10.687-4.57V9.258zm22.375.002v13.914l-10.688 4.572V13.83l10.688-4.57zM21.11 20.752a.5.5 0 0 0-.006.002.5.5 0 0 0-.18.043l-2.915 1.246a.5.5 0 0 0 .392.92l2.918-1.246a.5.5 0 0 0-.209-.965zm2.91.75a.5.5 0 0 0-.177.043l-2.918 1.246-2.916 1.248a.5.5 0 0 0 .392.92l2.918-1.248 2.916-1.248a.5.5 0 0 0-.215-.961z">
                                </path>
                            </svg>
                        </Link>
                    </div>
                    <Link to={'/'} className="navbar-brand text-decoration-none">Eagle WMS</Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarsExample04">
                        <ul className="navbar-nav me-auto mb-2 mb-md-0">
                            <li className="nav-item">
                                <Link to={'/'} className="nav-link active text-decoration-none"
                                      aria-current="page">Home</Link>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={'/'} className="nav-link dropdown-toggle text-decoration-none"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Product</Link>
                                <ul className="dropdown-menu">
                                    <li><Link to={'products'}
                                              className="dropdown-item text-decoration-none">Produktliste</Link></li>
                                    <li><Link to={'products/add'} className="dropdown-item text-decoration-none">Produkt
                                        erstellen</Link>
                                    </li>
                                </ul>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={'/'} className="nav-link dropdown-toggle text-decoration-none"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Kategorie</Link>
                                <ul className="dropdown-menu">
                                    <li><Link to={'categories'}
                                              className="dropdown-item text-decoration-none">Kategorieliste</Link></li>
                                    <li><Link to={'categories/add'} className="dropdown-item text-decoration-none">Kategorie
                                        erstellen</Link>
                                    </li>
                                </ul>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={'/'} className="nav-link dropdown-toggle text-decoration-none"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Lieferant</Link>
                                <ul className="dropdown-menu">
                                    <li><Link to={'suppliers'}
                                              className="dropdown-item text-decoration-none">Lieferantenliste</Link>
                                    </li>
                                    <li><Link to={'suppliers/add'} className="dropdown-item text-decoration-none">Lieferant
                                        erstellen</Link>
                                    </li>
                                </ul>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={'/'} className="nav-link dropdown-toggle text-decoration-none"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Kauf</Link>
                                <ul className="dropdown-menu">
                                    <li><Link to={'purchases'}
                                              className="dropdown-item text-decoration-none">Einkaufsliste</Link>
                                    </li>
                                    <li><Link to={'purchases/add'} className="dropdown-item text-decoration-none">Kauf
                                        erstellen</Link>
                                    </li>
                                </ul>
                            </li>
                            <li className="nav-item">
                                <Link to={'/orders'} className="nav-link text-decoration-none">Bestellung</Link>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={'/'} className="nav-link dropdown-toggle text-decoration-none"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Berichte</Link>
                                <ul className="dropdown-menu">
                                    <li><Link to={'stock-movements'}
                                              className="dropdown-item text-decoration-none">Lagerbewegung</Link>
                                    </li>
                                    <li><Link to={'reports/products-out-of-stock'}
                                              className="dropdown-item text-decoration-none">Nicht vorrätige
                                        Produkte</Link>
                                    </li>
                                    <li><Link to={'reports/products-low-stock'}
                                              className="dropdown-item text-decoration-none">Produkte mit niedrigem
                                        Lagerbestand</Link>
                                    </li>
                                    <li><Link to={'products'}
                                              className="dropdown-item text-decoration-none">Produktkaufhistorie</Link>
                                    </li>
                                    <li><Link to={'suppliers'}
                                              className="dropdown-item text-decoration-none">Kaufhistorie des
                                        Lieferanten</Link>
                                    </li>
                                    <li>
                                        <hr className="dropdown-divider"/>
                                    </li>
                                    <li><Link to={'reports'}
                                              className="dropdown-item text-decoration-none">Alle Berichte</Link>
                                    </li>
                                </ul>
                            </li>
                            {user && user.role === 'ADMIN' && (
                                <li className="nav-item dropdown">
                                    <Link to={'/'} className="nav-link dropdown-toggle text-decoration-none"
                                          data-bs-toggle="dropdown"
                                          aria-expanded="false">Benutzer</Link>
                                    <ul className="dropdown-menu">
                                        <li><Link to={'/users'}
                                                  className="dropdown-item text-decoration-none">Benutzerliste</Link>
                                        </li>
                                        <li><Link to={'/users/add'} className="dropdown-item text-decoration-none">Benutzer
                                            erstellen</Link>
                                        </li>
                                    </ul>
                                </li>
                            )}
                        </ul>
                        <div>
                            {!user && (

                                <Link to={'/login'} className="btn btn-outline-primary me-2">Anmelden</Link>
                            )}
                            {user && (
                                <li className="nav-item dropdown">
                                    <Link to={'/'} className="dropdown-toggle text-decoration-none"
                                          data-bs-toggle="dropdown"
                                          aria-expanded="false">
                                        <img height="38" width="38" className={'rounded-circle'}
                                             src={user.imageUrl}
                                             alt="user information">
                                        </img>
                                    </Link>
                                    <ul className="dropdown-menu dropdown-menu-end">
                                        <li><Link to={`/users/detail/${user.id}`}
                                                  className="dropdown-item text-decoration-none">{`Mein Konto (${user.firstName} ${user.lastName})`}</Link>
                                        </li>
                                        <li>
                                            <button onClick={logout}
                                                    className="dropdown-item text-decoration-none text-danger">Abmelden
                                            </button>
                                        </li>
                                    </ul>
                                </li>

                            )}
                        </div>
                    </div>
                </div>
            </nav>
        </header>
    );
}

export default Navbar;