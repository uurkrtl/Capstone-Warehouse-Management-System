import {Route, Routes} from "react-router-dom";
import ProductAdd from "../pages/products/ProductAdd.tsx";
import ProductList from "../pages/products/ProductList.tsx";

function Dashboard() {
    return (
        <div>
            <Routes>
                <Route path={'/products'} element={<ProductList/>}/>
                <Route path={'/products/add'} element={<ProductAdd/>}/>
            </Routes>
        </div>
    );
}

export default Dashboard;