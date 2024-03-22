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
                                <Link to={`#`} className="nav-link dropdown-toggle text-decoration-none" type="button" data-bs-toggle="dropdown"
                                      aria-expanded="false">Produkt</Link>
                                <ul className="dropdown-menu">
                                    <Link to={`/products`} className="dropdown-item text-decoration-none">Produktliste</Link>
                                    <Link to={`/products/add`} className="dropdown-item text-decoration-none">Produkt erstellen</Link>
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