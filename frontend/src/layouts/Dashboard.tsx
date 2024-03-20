import {Route, Routes} from "react-router-dom";
import ProductAdd from "../pages/products/ProductAdd.tsx";
import ProductList from "../pages/products/ProductList.tsx";
import ProductDetail from "../pages/products/ProductDetail.tsx";
import ProductUpdate from "../pages/products/ProductUpdate.tsx";
import PageNotFound404 from "../pages/PageNotFound404.tsx";

function Dashboard() {
    return (
        <div>
            <Routes>
                <Route path={'/products'} element={<ProductList/>}/>
                <Route path={'/products/update/:id'} element={<ProductUpdate/>}/>
                <Route path={'/products/detail/:id'} element={<ProductDetail/>}/>
                <Route path={'/products/add'} element={<ProductAdd/>}/>
                <Route path={'*'} element={<PageNotFound404/>}/>
            </Routes>
        </div>
    );
}

export default Dashboard;