import Dashboard from "./layouts/Dashboard.tsx";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'react-datepicker/dist/react-datepicker.css';
import UserService from "./services/UserService.ts";
import {useEffect, useState} from "react";
import {User} from "./types/User.ts";
import Login from "./pages/login/Login.tsx";
import {useLocation} from "react-router-dom";

function App() {
    const userService = new UserService();
    const [user, setUser] = useState<User | null>(null);

    const Navigation = () => {
        const location = useLocation();

        const isLoginRoute = () => {
            return location.pathname.includes('/login');
        };

        let componentToRender;
        if (isLoginRoute() || !user) {
            componentToRender = <Login/>;
        } else {
            componentToRender = <Dashboard/>;
        }

        return componentToRender;
    };

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
    <div >
        <Navigation/>
    </div>
  )
}

export default App
