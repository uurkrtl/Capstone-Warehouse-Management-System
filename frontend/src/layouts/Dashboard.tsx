import {Route, Routes} from "react-router-dom";
import ProductAdd from "../pages/products/ProductAdd.tsx";
import ProductList from "../pages/products/ProductList.tsx";
import ProductDetail from "../pages/products/ProductDetail.tsx";
import ProductUpdate from "../pages/products/ProductUpdate.tsx";
import PageNotFound404 from "../pages/notFound404/PageNotFound404.tsx";
import Homepage from "../pages/home/Homepage.tsx";
import Navbar from "./Navbar.tsx";
import Footer from "./Footer.tsx";
import CategoryAdd from "../pages/categories/CategoryAdd.tsx";
import CategoryList from "../pages/categories/CategoryList.tsx";
import CategoryDetail from "../pages/categories/CategoryDetail.tsx";
import CategoryUpdate from "../pages/categories/CategoryUpdate.tsx";
import SupplierAdd from "../pages/suppliers/SupplierAdd.tsx";
import SupplierList from "../pages/suppliers/SupplierList.tsx";
import SupplierDetail from "../pages/suppliers/SupplierDetail.tsx";
import SupplierUpdate from "../pages/suppliers/SupplierUpdate.tsx";
import PurchaseAdd from "../pages/purchases/PurchaseAdd.tsx";
import PurchaseList from "../pages/purchases/PurchaseList.tsx";

function Dashboard() {
    return (
        <>
            <Navbar />
            <div className="mt-5">
                <Routes>
                    <Route path={'/'} element={<Homepage/>}/>
                    <Route path={'/products'} element={<ProductList/>}/>
                    <Route path={'/products/update/:id'} element={<ProductUpdate/>}/>
                    <Route path={'/products/detail/:id'} element={<ProductDetail/>}/>
                    <Route path={'/products/add'} element={<ProductAdd/>}/>
                    <Route path={'/categories'} element={<CategoryList/>}/>
                    <Route path={'/categories/update/:id'} element={<CategoryUpdate/>}/>
                    <Route path={'/categories/detail/:id'} element={<CategoryDetail/>}/>
                    <Route path={'/categories/add'} element={<CategoryAdd/>}/>
                    <Route path={'/suppliers'} element={<SupplierList/>}/>
                    <Route path={'/suppliers/update/:id'} element={<SupplierUpdate/>}/>
                    <Route path={'/suppliers/detail/:id'} element={<SupplierDetail/>}/>
                    <Route path={'/suppliers/add'} element={<SupplierAdd/>}/>
                    <Route path={'/purchases'} element={<PurchaseList/>}/>
                    <Route path={'/purchases/add'} element={<PurchaseAdd/>}/>
                    <Route path={'*'} element={<PageNotFound404/>}/>
                </Routes>
                <Footer />
            </div>

        </>
    );
}

export default Dashboard;