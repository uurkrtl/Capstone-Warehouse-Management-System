import "./UserNotPermitted.css"

function UserNotPermitted() {
    return (
        <div className="body">
            <div className="container">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 47.5 47.5" height="72" width="72">
                    <defs>
                        <clipPath id="a">
                            <path d="M0 38h38V0H0v38Z"></path>
                        </clipPath>
                        <clipPath id="b">
                            <path d="m5 16 9-7-4-8h18s4 9 0 9L5 22v-6z"></path>
                        </clipPath>
                        <clipPath id="c">
                            <path d="M0 38h38V0H0v38Z"></path>
                        </clipPath>
                    </defs>
                    <g clipPath="url(#a)" transform="matrix(1.25 0 0 -1.25 0 47.5)">
                        <path fill="#642116"
                              d="M20 37c-1.721 0-3.343-.406-4.793-1.111A6.964 6.964 0 0 1 14 36a7 7 0 0 1-7-7V17h.018C7.201 10.533 12.489 5.344 19 5.344c6.511 0 11.799 5.189 11.982 11.656H31v9c0 6.075-4.925 11-11 11"></path>
                        <path fill="#9268ca" d="M28 10H10a4 4 0 0 1-4-4V1h26v5a4 4 0 0 1-4 4"></path>
                        <path fill="#d79e84"
                              d="M15 32s-.003-5.308-5-5.936V20a9 9 0 0 1 9-9 9 9 0 0 1 9 9v5.019C16.89 25.395 15 32 15 32"></path>
                        <path fill="#bb1a34" d="M20 15a1 1 0 1 0-2 0 1 1 0 1 0 2 0"></path>
                        <path fill="#7450a8" d="m28 1-15 9-2-1 14-8h3Z"></path>
                        <path fill="#bf6952"
                              d="m3.655 22.984-.075.363V24c-.074 0-.144-.029-.217-.045a1.27 1.27 0 0 1-1.34.586 1.285 1.285 0 0 1-.997-1.516l.554-2.675V18a2 2 0 0 1 2-2h2v5s-.856 1.895-1.925 1.984"></path>
                        <path fill="#d79e84"
                              d="M2.58 29a1 1 0 0 1-1-1l1-6v-5a1 1 0 0 1 1-1h2v2L5 22l-1.42 6s-.447 1-1 1"></path>
                        <path fill="#9268ca" d="M28 10c4 0 0-9 0-9L5 16v6l23-12Z"></path>
                    </g>
                    <g clipPath="url(#b)" transform="matrix(1.25 0 0 -1.25 0 47.5)">
                        <path fill="#7450a8" d="m23 13-4 3-8-4 3-2 9 3zm6-2L13 1h-3l17 11 2-1z"></path>
                    </g>
                    <g clipPath="url(#c)" transform="matrix(1.25 0 0 -1.25 0 47.5)">
                        <path fill="#bf6952"
                              d="M35.977 24.541a1.276 1.276 0 0 1-1.341-.586c-.071.016-.141.045-.216.045v-.653l-.075-.363C33.276 22.895 32.42 21 32.42 21v-5h2a2 2 0 0 1 2 2v2.35l.554 2.674a1.286 1.286 0 0 1-.997 1.517"></path>
                        <path fill="#d79e84"
                              d="M35.42 29a1 1 0 0 0 1-1l-1-6v-5a1 1 0 0 0-1-1h-2v2l.58 4 1.42 6s.447 1 1 1"></path>
                        <path fill="#9268ca" d="M10 10V1l23 15v6L10 10Z"></path>
                        <path fill="#bf6952" d="M20 17.5h-2a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1"></path>
                        <path fill="#292f33"
                              d="M15 21a1 1 0 0 0-1 1v1a1 1 0 0 0 2 0v-1a1 1 0 0 0-1-1M23 21a1 1 0 0 0-1 1v1a1 1 0 1 0 2 0v-1a1 1 0 0 0-1-1"></path>
                    </g>
                </svg>
                <h1>403 - Zugriff abgelehnt</h1>
                <p>Sie sind nicht berechtigt, diese Seite anzuzeigen.</p>
                <p>Klicken Sie <a href="/"> hier um zur Startseite zurückzukehren</a>.</p>
            </div>
        </div>
    );
}

export default UserNotPermitted;