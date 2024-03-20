import {Route, Routes} from "react-router-dom";
import ProductAdd from "../pages/products/ProductAdd.tsx";
import ProductList from "../pages/products/ProductList.tsx";
import ProductDetail from "../pages/products/ProductDetail.tsx";

function Dashboard() {
    return (
        <div>
            <Routes>
                <Route path={'/products'} element={<ProductList/>}/>
                <Route path={'/products/detail/:id'} element={<ProductDetail/>}/>
                <Route path={'/products/add'} element={<ProductAdd/>}/>
            </Routes>
        </div>
    );
}

export default Dashboard;