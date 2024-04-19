import './Homepage.css'
import {Link} from "react-router-dom";
import UserService from "../../services/UserService.ts";
import {useEffect, useState} from "react";
import {User} from "../../types/User.ts";

function Homepage() {
    const userService = new UserService();
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        userService.getLoggedInUser()
            .then((response) => {
                setUser(response.data);
            })
            .catch(() => {
                setUser(null);
            });
    }, []);

    return (
        <main>
            <div id="myCarousel" className="carousel slide mb-6" data-bs-ride="carousel">
                <div className="carousel-inner">
                    <div className="carousel-item active">
                        <img src="https://www.epg.com/fileadmin/_processed_/e/3/csm_EPG_Warehouse-Management-System_Keyvisual_77109d926d.jpg"
                             className="d-block w-100" alt="Warehouse Management"/>
                    </div>
                </div>
            </div>

            <div className="container marketing">

                <div className="row mb-3">
                    <div className="col-lg-3">
                        <Link to={`/products`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://cdn-icons-png.freepik.com/512/8386/8386263.png"
                                className="bd-placeholder-img" width="140" height="140"
                                alt="Produktmanagement"/>
                            <h3 className="fw-normal">Produktverwaltung</h3>
                        </Link>
                    </div>

                    <div className="col-lg-3">
                        <Link to={`/categories`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://westerngrocer.com/wp-content/uploads/2016/10/Shopping-Cart-Logo-lg-891x1024.jpg"
                                className="bd-placeholder-img" width="140" height="140"
                                alt="Kategorienverwaltung"/>
                            <h3 className="fw-normal">Kategorien-<br/>verwaltung</h3>
                        </Link>
                    </div>

                    <div className="col-lg-3">
                        <Link to={`/suppliers`} className="nav-link active text-decoration-none" aria-current="page">
                            <img src="https://i.ibb.co/RQSGwG4/suppliermanagement.png"
                                 className="bd-placeholder-img" width="140" height="140"
                                 alt="Lieferantenverwaltung"/>
                            <h3 className="fw-normal">Lieferanten-<br/>verwaltung</h3>
                        </Link>
                    </div>

                    <div className="col-lg-3">
                        <Link to={`/purchases`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://cdn-icons-png.flaticon.com/512/10112/10112452.png"
                                className="bd-placeholder-img" width="140" height="140"
                                alt="Einkaufsmanagement"/>
                            <h3 className="fw-normal">Einkaufsverwaltung</h3>
                        </Link>
                    </div>

                </div>

                <div className="row">

                    <div className="col-lg-3">
                        <Link to={`/orders`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://cdn3.iconfinder.com/data/icons/tools-and-materials-ecommerce-hazel-vol-2/256/Work_Order_Management-512.png"
                                className="bd-placeholder-img" width="140" height="140"
                                alt="Bestellungsverwaltung"/>
                            <h3 className="fw-normal">Bestellungs-<br/>verwaltung</h3>
                        </Link>
                    </div>

                    <div className="col-lg-3">
                        <Link to={`/stock-movements`} className="nav-link active text-decoration-none"
                              aria-current="page">
                            <img src="https://cdn-icons-png.flaticon.com/512/7656/7656399.png"
                                 className="bd-placeholder-img" width="140" height="140"
                                 alt="Lagerbewegung"/>
                            <h3 className="fw-normal">Lagerbewegung</h3>
                        </Link>
                    </div>

                    <div className="col-lg-3">
                        <Link to={`/reports`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://cdn-icons-png.flaticon.com/512/5738/5738277.png"
                                className="bd-placeholder-img" width="140" height="140"
                                alt="Einkaufsmanagement"/>
                            <h3 className="fw-normal">Berichte</h3>
                        </Link>
                    </div>

                    {user && user.role === 'ADMIN' && (
                        <div className="col-lg-3">
                            <Link to={`/users`} className="nav-link active text-decoration-none" aria-current="page">
                                <img
                                    src="https://www.iconpacks.net/icons/1/free-user-group-icon-296-thumb.png"
                                    className="bd-placeholder-img" width="140" height="140"
                                    alt="Einkaufsmanagement"/>
                                <h3 className="fw-normal">Benutzer-<br/>verwaltung</h3>
                            </Link>
                        </div>
                    )}


                </div>

            </div>
        </main>
    );
}

export default Homepage;