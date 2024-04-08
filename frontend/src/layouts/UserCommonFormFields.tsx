import {User} from "../types/User.ts";


function UserCommonFormFields({ user, setUser}: Readonly<{ user: User, setUser: (user: User) => void }>) {
    return (
        <div className="row g-3">

            <div className="col-sm-5">
                <label htmlFor="username" className="form-label">Benutzername</label>
                <input type="text" className="form-control" id="username"
                       placeholder="Schreiben Sie den Benutzername" value={user.username}
                       onChange={(e) => setUser({...user, username: e.target.value})}/>
            </div>

            <div className="col-sm-5">
                <label htmlFor="password" className="form-label">Passwort</label>
                <input type="password" className="form-control" id="password"
                       placeholder="Schreiben Sie das Passwort" value={user.password}
                       onChange={(e) => setUser({...user, password: e.target.value})}/>
            </div>

            <div className="col-sm-2">
                <label htmlFor="role" className="form-label">Rolle</label>
                <select className="form-select" id="role"
                        onChange={(e) => setUser({...user, role: e.target.value as "ADMIN" | "USER"})}>
                    <option value="USER">USER</option>
                    <option value="ADMIN">ADMIN</option>
                </select>
            </div>

        </div>
    );
}

export default UserCommonFormFields;