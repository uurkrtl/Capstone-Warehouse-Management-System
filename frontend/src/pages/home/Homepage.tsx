import './Homepage.css'
import {Link} from "react-router-dom";

function Homepage() {
    return (
        <main>
            <div id="myCarousel" className="carousel slide mb-6" data-bs-ride="carousel">
                <div className="carousel-indicators">
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" className="active"
                            aria-label="Slide 1"></button>
                </div>
                <div className="carousel-inner">
                    <div className="carousel-item active">
                        <img src="https://www.epg.com/fileadmin/_processed_/e/3/csm_EPG_Warehouse-Management-System_Keyvisual_77109d926d.jpg"
                             className="d-block w-100" alt="Warehouse Management"/>
                        <div className="container">
                        </div>
                    </div>
                </div>
            </div>


            <div className="container marketing">

                <div className="row mb-3">
                    <div className="col-lg-4">
                        <Link to={`/products`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://cdn-icons-png.freepik.com/512/8386/8386263.png"
                                className="bd-placeholder-img rounded-circle" width="140" height="140" alt="Produktmanagement"/>
                            <h2 className="fw-normal">Produktmanagement</h2>
                        </Link>
                    </div>

                    <div className="col-lg-4">
                        <Link to={`/categories`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://westerngrocer.com/wp-content/uploads/2016/10/Shopping-Cart-Logo-lg-891x1024.jpg"
                                className="bd-placeholder-img rounded-circle" width="140" height="140" alt="Kategorienverwaltung"/>
                            <h2 className="fw-normal">Kategorienverwaltung</h2>
                        </Link>
                    </div>

                    <div className="col-lg-4">
                        <Link to={`/suppliers`} className="nav-link active text-decoration-none" aria-current="page">
                            <img src="https://i.ibb.co/RQSGwG4/suppliermanagement.png"
                                 className="bd-placeholder-img rounded-circle" width="140" height="140" alt="Lieferantenverwaltung"/>
                            <h2 className="fw-normal">Lieferantenverwaltung</h2>
                        </Link>
                    </div>

                </div>

                <div className="row">
                    <div className="col-lg-4">
                        <Link to={`/purchases`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://cdn-icons-png.flaticon.com/512/10112/10112452.png"
                                className="bd-placeholder-img rounded-circle" width="140" height="140" alt="Einkaufsmanagement"/>
                            <h2 className="fw-normal">Einkaufsmanagement</h2>
                        </Link>
                    </div>

                    <div className="col-lg-4">
                        <Link to={`/`} className="nav-link active text-decoration-none" aria-current="page">
                            <img
                                src="https://cdn3.iconfinder.com/data/icons/tools-and-materials-ecommerce-hazel-vol-2/256/Work_Order_Management-512.png"
                                className="bd-placeholder-img rounded-circle" width="140" height="140" alt="Bestellungsverwaltung"/>
                            <h2 className="fw-normal">Bestellungsverwaltung</h2>
                        </Link>
                    </div>

                    <div className="col-lg-4">
                        <Link to={`/`} className="nav-link active text-decoration-none" aria-current="page">
                            <img src="https://cdn-icons-png.flaticon.com/512/7656/7656399.png"
                                 className="bd-placeholder-img rounded-circle" width="140" height="140" alt="Lagerbewegung"/>
                            <h2 className="fw-normal">Lagerbewegung</h2>
                        </Link>
                    </div>
                </div>

            </div>
        </main>
    );
}

export default Homepage;