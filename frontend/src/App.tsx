import Dashboard from "./layouts/Dashboard.tsx";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'react-datepicker/dist/react-datepicker.css';
import UserService from "./services/UserService.ts";
import {useEffect, useState} from "react";
import {User} from "./types/User.ts";
import Login from "./pages/login/Login.tsx";

function App() {
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
    <div >
        {user ? <Dashboard /> : <Login />}
    </div>
  )
}

export default App
