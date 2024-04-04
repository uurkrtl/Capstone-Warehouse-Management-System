import {Link} from "react-router-dom";


function Navbar() {
    return (
        <header data-bs-theme="dark">
            <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <div className="container-fluid">
                    <Link to={`/`} className="navbar-brand text-decoration-none">Eagle WMS</Link>
                    <div className="collapse navbar-collapse" id="navbarCollapse">
                        <ul className="navbar-nav me-auto mb-2 mb-md-0">
                            <li className="nav-item">
                                <Link to={`/`} className="nav-link active text-decoration-none"
                                      aria-current="page">Home</Link>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={`#`} className="nav-link dropdown-toggle text-decoration-none" type="button"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Produkt</Link>
                                <ul className="dropdown-menu">
                                    <Link to={`/products`}
                                          className="dropdown-item text-decoration-none">Produktliste</Link>
                                    <Link to={`/products/add`} className="dropdown-item text-decoration-none">Produkt
                                        erstellen</Link>
                                </ul>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={`#`} className="nav-link dropdown-toggle text-decoration-none" type="button"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Kategorie</Link>
                                <ul className="dropdown-menu">
                                    <Link to={`/categories`}
                                          className="dropdown-item text-decoration-none">Kategorieliste</Link>
                                    <Link to={`/categories/add`} className="dropdown-item text-decoration-none">Kategorie
                                        erstellen</Link>
                                </ul>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={`#`} className="nav-link dropdown-toggle text-decoration-none" type="button"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Lieferant</Link>
                                <ul className="dropdown-menu">
                                    <Link to={`/suppliers`}
                                          className="dropdown-item text-decoration-none">Lieferantenliste</Link>
                                    <Link to={`/suppliers/add`} className="dropdown-item text-decoration-none">Lieferant
                                        erstellen</Link>
                                </ul>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={`#`} className="nav-link dropdown-toggle text-decoration-none" type="button"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Kauf</Link>
                                <ul className="dropdown-menu">
                                    <Link to={`/purchases`}
                                          className="dropdown-item text-decoration-none">Einkaufsliste</Link>
                                    <Link to={`/purchases/add`} className="dropdown-item text-decoration-none">Kauf
                                        erstellen</Link>
                                </ul>
                            </li>
                            <li className="nav-item">
                                <Link to={`/orders`} className="nav-link text-decoration-none"
                                      aria-current="page">Bestellung</Link>
                            </li>
                            <li className="nav-item">
                                <Link to={`/stock-movements`} className="nav-link text-decoration-none"
                                      aria-current="page">Lagerbewegung</Link>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={`#`} className="nav-link dropdown-toggle text-decoration-none" type="button"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Berichte</Link>
                                <ul className="dropdown-menu">
                                    <Link to={`/reports/products-out-of-stock`}
                                          className="dropdown-item text-decoration-none">Nicht vorrätige Produkte</Link>
                                    <Link to={`/reports/products-low-stock`}
                                          className="dropdown-item text-decoration-none">Produkte mit niedrigem Lagerbestand</Link>
                                    <Link to={`/products`}
                                          className="dropdown-item text-decoration-none">Produktkaufhistorie</Link>
                                    <li>
                                        <hr className="dropdown-divider"/>
                                    </li>
                                    <li><Link to={`/reports`} className="dropdown-item text-decoration-none">Alle Berichte</Link></li>
                                </ul>
                            </li>
                        </ul>
                        <form className="d-flex" role="search">
                            <input className="form-control me-2" type="search" placeholder="Search"
                                   aria-label="Search"/>
                            <button className="btn btn-outline-success" type="submit">Search</button>
                        </form>
                    </div>
                </div>
            </nav>
        </header>
    );
}

export default Navbar;