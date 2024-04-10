import {User} from "../types/User.ts";


function UserCommonFormFields({ user, setUser}: Readonly<{ user: User, setUser: (user: User) => void }>) {
    return (
        <div className="row g-3">

            <div className="col-sm-4">
                <label htmlFor="firstName" className="form-label">Vorname</label>
                <input type="text" className="form-control" id="firstName"
                       placeholder="Schreiben Sie den Vornamen" value={user.firstName}
                       onChange={(e) => setUser({...user, firstName: e.target.value})}/>
            </div>

            <div className="col-sm-4">
                <label htmlFor="lastName" className="form-label">Nachname</label>
                <input type="text" className="form-control" id="lastName"
                       placeholder="Schreiben Sie den Nachnamen" value={user.lastName}
                       onChange={(e) => setUser({...user, lastName: e.target.value})}/>
            </div>

            <div className="col-sm-4">
                <label htmlFor="email" className="form-label">E-Mail-Adresse</label>
                <input type="email" className="form-control" id="email"
                       placeholder="Schreiben Sie die E-Mail-Adresse" value={user.email}
                       onChange={(e) => setUser({...user, email: e.target.value})}/>
            </div>

            <div className="col-sm-5">
                <label htmlFor="username" className="form-label">Benutzername</label>
                <input type="text" className="form-control" id="username"
                       placeholder="Schreiben Sie den Benutzername" value={user.username}
                       onChange={(e) => setUser({...user, username: e.target.value})}/>
            </div>

            <div className="col-sm-5">
                <label htmlFor="password" className="form-label">Passwort</label>
                <input type="password" className="form-control" id="password"
                       placeholder="Schreiben Sie das Passwort" value={user.password || ''}
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

            <div className="col-sm-12">
                <label htmlFor="imageUrl" className="form-label">Bild URL</label>
                <input type="text" className="form-control" id="imageUrl"
                       placeholder="Schreiben Sie die Bild URL" value={user.imageUrl}
                       onChange={(e) => setUser({...user, imageUrl: e.target.value})}/>
            </div>

        </div>
    );
}

export default UserCommonFormFields;