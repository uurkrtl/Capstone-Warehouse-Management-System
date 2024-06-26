import './ReportHome.css'
import {Link} from "react-router-dom";
import {useEffect} from "react";

function ReportsHome() {
    useEffect(() => {
        window.scrollTo(0, 0);
    }, []);

    return (
        <div className="container px-4 py-4" id="featured-3">
            <h2 className="pb-2 border-bottom">Berichtstool</h2>
            <div className="row g-4 py-4 row-cols-1 row-cols-lg-3">
                <div className="feature col">
                    <div
                        className="feature-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">

                        <svg className="bi" xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="none"
                             viewBox="0 0 48 48" id="out-stock">
                            <path fill="#fff"
                                  d="M27.707 7.70711C28.0975 7.31658 28.0975 6.68342 27.707 6.29289 27.3165 5.90237 26.6833 5.90237 26.2928 6.29289L24.0001 8.58554 21.7075 6.29289C21.317 5.90237 20.6838 5.90237 20.2933 6.29289 19.9027 6.68342 19.9027 7.31658 20.2933 7.70711L22.5859 9.99976 20.2931 12.2925C19.9026 12.683 19.9026 13.3162 20.2931 13.7067 20.6837 14.0973 21.3168 14.0973 21.7074 13.7067L24.0001 11.414 26.2929 13.7067C26.6834 14.0973 27.3166 14.0973 27.7071 13.7067 28.0976 13.3162 28.0976 12.683 27.7071 12.2925L25.4143 9.99976 27.707 7.70711zM33.9487 32.1838C34.1233 32.7077 33.8402 33.274 33.3162 33.4487L28.8162 34.9487C28.2923 35.1233 27.726 34.8402 27.5513 34.3162 27.3767 33.7923 27.6598 33.226 28.1838 33.0513L32.6838 31.5513C33.2077 31.3767 33.774 31.6598 33.9487 32.1838z"></path>
                            <path fill="#fff" fillRule="evenodd"
                                  d="M6.68378 26.4487L10 27.5541V36C10 36.4262 10.2701 36.8056 10.6729 36.945L23.6649 41.4422C23.8636 41.5131 24.086 41.5217 24.3015 41.4535L24.3161 41.4487L24.3313 41.4436L37.3271 36.945C37.7299 36.8056 38 36.4262 38 36V27.5541L41.3162 26.4487C41.6264 26.3453 41.8665 26.0968 41.9591 25.7832C42.0517 25.4697 41.9851 25.1306 41.7809 24.8753L37.7809 19.8753C37.6595 19.7236 37.5003 19.6145 37.3249 19.5542L24.3271 15.055C24.1152 14.9817 23.8848 14.9817 23.6729 15.055L10.6751 19.5543C10.4997 19.6145 10.3405 19.7236 10.2191 19.8753L6.21914 24.8753C6.01489 25.1306 5.94836 25.4697 6.04096 25.7832C6.13356 26.0968 6.3736 26.3453 6.68378 26.4487ZM21.3192 30.5735L23 28.1724V39.0956L12 35.2879V28.2208L20.1838 30.9487C20.6036 31.0886 21.0655 30.936 21.3192 30.5735ZM14.0571 20.5L24 23.9418L33.9429 20.5L24 17.0582L14.0571 20.5ZM26.6808 30.5735L25 28.1724V39.0956L36 35.2879V28.2208L27.8162 30.9487C27.3964 31.0886 26.9345 30.936 26.6808 30.5735ZM11.3399 21.6759L8.67677 25.0048L14.1881 26.8419L20.1086 28.8154L22.4212 25.5117L22.2861 25.465L11.3399 21.6759ZM39.3232 25.0048L36.6601 21.6759L25.5789 25.5117L27.8915 28.8154L39.3232 25.0048Z"
                                  clipRule="evenodd"></path>
                        </svg>

                    </div>
                    <h3 className="fs-2 text-body-emphasis">Nicht vorrätige Produkte</h3>
                    <p>Sie können die Liste der aktiven Produkte sehen, die nicht vorrätig sind.</p>
                    <Link to={`/reports/products-out-of-stock`} className="icon-link">
                        Gehen zum Bericht
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                             className="bi bi-chevron-double-right" viewBox="0 0 16 16">
                            <path fillRule="evenodd"
                                  d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708"></path>
                            <path fillRule="evenodd"
                                  d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708"></path>
                        </svg>
                    </Link>
                </div>
                <div className="feature col">
                    <div
                        className="feature-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">
                        <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 512 512" id="loss">
                            <path
                                d="M248.48 142.35a11.36 11.36 0 0 0 16 0l44.74-44.73a11.34 11.34 0 0 0-8-19.36h-21.47V37.67a11.36 11.36 0 0 0-11.34-11.34h-23.82a11.35 11.35 0 0 0-11.34 11.34v40.59h-21.49a11.34 11.34 0 0 0-8 19.36Zm-11.57-49.09a11.36 11.36 0 0 0 11.34-11.34V41.33h16.5v40.59a11.35 11.35 0 0 0 11.33 11.34h16.31l-35.89 35.89-35.9-35.89ZM162.5 168h56.2a17.6 17.6 0 0 1 12.53 5.19l14.29 14.3a12.53 12.53 0 0 0 17.68 0l14.3-14.3A17.57 17.57 0 0 1 290 168h60.5a27.54 27.54 0 0 0 27.5-27.5v-113A27.54 27.54 0 0 0 350.5 0h-188A27.53 27.53 0 0 0 135 27.5v113a27.53 27.53 0 0 0 27.5 27.5ZM150 27.5A12.51 12.51 0 0 1 162.5 15h188A12.52 12.52 0 0 1 363 27.5v113a12.52 12.52 0 0 1-12.5 12.5H290a32.51 32.51 0 0 0-23.14 9.58l-12.53 12.53-12.53-12.53a32.49 32.49 0 0 0-23.1-9.58h-56.2a12.51 12.51 0 0 1-12.5-12.5Zm-31.87 216 44.79-44.8L266.13 302a12.51 12.51 0 0 0 17.68 0l43.69-43.69 92.55 92.15h-36.84a7.5 7.5 0 0 0 0 15h55.74l.34-.05.39-.06.38-.09.33-.09.36-.13a3.21 3.21 0 0 0 .33-.12 3.49 3.49 0 0 0 .34-.15l.33-.16.31-.19a3.37 3.37 0 0 0 .32-.19l.36-.27.23-.17a6.52 6.52 0 0 0 .55-.5l.05-.07c.15-.15.3-.31.44-.48l.18-.24.25-.35c.07-.1.13-.2.19-.3l.19-.33c.06-.11.1-.21.15-.32s.11-.23.16-.35a3 3 0 0 0 .12-.32c.05-.12.09-.24.13-.37l.09-.33.09-.38.06-.39V358.13a.66.66 0 0 0 0-.14v-55a7.5 7.5 0 0 0-15 0v36.95l-94.4-94a12.53 12.53 0 0 0-17.66 0L275 289.59 171.76 186.36a12.49 12.49 0 0 0-17.68 0l-44.79 44.8-96.49-96.51a7.5 7.5 0 1 0-10.6 10.61l98.25 98.27a12.52 12.52 0 0 0 17.68 0ZM504.5 497H482v-94.4a17.52 17.52 0 0 0-17.5-17.5h-59a17.52 17.52 0 0 0-17.5 17.5V497h-69.12V355.5a17.53 17.53 0 0 0-17.5-17.5h-59a17.52 17.52 0 0 0-17.5 17.5V497h-69.13V295.5a17.52 17.52 0 0 0-17.5-17.5h-59a17.52 17.52 0 0 0-17.5 17.5V497h-22.5a7.5 7.5 0 0 0 0 15H504.5a7.5 7.5 0 0 0 0-15Zm-363.75 0h-64V295.5a2.5 2.5 0 0 1 2.5-2.5h59a2.5 2.5 0 0 1 2.5 2.5Zm163.13 0h-64V355.5a2.5 2.5 0 0 1 2.5-2.5h59a2.51 2.51 0 0 1 2.5 2.5ZM467 497h-64v-94.4a2.5 2.5 0 0 1 2.5-2.5h59a2.5 2.5 0 0 1 2.5 2.5Z"
                                fill="#fff"></path>
                        </svg>

                    </div>
                    <h3 className="fs-2 text-body-emphasis">Produkte mit niedrigem Lagerbestand</h3>
                    <p>Sie können die Liste der Produkte sehen, deren Lagerbestand unter dem kritischen Lagerbestand
                        liegt. Produkte, die nicht vorrätig sind, werden hier nicht aufgeführt</p>
                    <Link to={`/reports/products-low-stock`} className="icon-link">
                        Gehen zum Bericht
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                             className="bi bi-chevron-double-right" viewBox="0 0 16 16">
                            <path fillRule="evenodd"
                                  d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708"></path>
                            <path fillRule="evenodd"
                                  d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708"></path>
                        </svg>
                    </Link>
                </div>
                <div className="feature col">
                    <div
                        className="feature-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">
                        <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 64 64">
                            <path stroke="#ffffff" fill="none"
                                  d="M27 51.87A3.9916 3.9916 0 0030 48V28a3.9916 3.9916 0 00-3-3.87A3.65643 3.65643 0 0026 24H6a3.99887 3.99887 0 00-4 4V48a3.99891 3.99891 0 004 4H26A3.65874 3.65874 0 0027 51.87zM11 48H7a1.00011 1.00011 0 01.00006-2H11A1.00011 1.00011 0 0111 48zm2-4H7a1.00011 1.00011 0 01.00006-2H13A1.00011 1.00011 0 0113 44zm9-15a3.00883 3.00883 0 01-3 3H13a3.00883 3.00883 0 01-3-3V26h2v3a1.003 1.003 0 001 1h6a1.003 1.003 0 001-1V26h2zM36 17H46a3.01107 3.01107 0 003-3V9a3.00879 3.00879 0 00-3-3H36a3.00879 3.00879 0 00-3 3v5A3.01309 3.01309 0 0036 17z"></path>
                            <path stroke="#ffffff" fill="none"
                                  d="M57 10H51v4h4a2.00587 2.00587 0 012 2V52a2.00591 2.00591 0 01-2 2c-5.10565.00378-30.35992-.004-34.9 0A5.00051 5.00051 0 0025 58H57a5.00181 5.00181 0 005-5V15A5.00181 5.00181 0 0057 10zM25 16a2.00587 2.00587 0 012-2h4V10H25a5.00181 5.00181 0 00-5 5v7h5z"></path>
                            <path stroke="#ffffff" fill="none"
                                  d="M27,22.08A5.99882,5.99882,0,0,1,32,28V48a5.979,5.979,0,0,1-1.53,4H55V16H50.58A5.05069,5.05069,0,0,1,46,19H36a5.01743,5.01743,0,0,1-4.57-3H27Zm17.79,2.21a1.008,1.008,0,0,1,1.42,0l1.29,1.3,3.79-3.8a1.00429,1.00429,0,0,1,1.42,1.42L48.21,27.71a1.01673,1.01673,0,0,1-1.42,0l-2-2A1.008,1.008,0,0,1,44.79,24.29Zm0,9a1.008,1.008,0,0,1,1.42,0l1.29,1.3,3.79-3.8a1.00429,1.00429,0,0,1,1.42,1.42L48.21,36.71a1.01673,1.01673,0,0,1-1.42,0l-2-2A1.008,1.008,0,0,1,44.79,33.29Zm0,9a1.008,1.008,0,0,1,1.42,0l1.29,1.3,3.79-3.8a1.00429,1.00429,0,0,1,1.42,1.42L48.21,45.71a1.01673,1.01673,0,0,1-1.42,0l-2-2A1.008,1.008,0,0,1,44.79,42.29ZM33.5,25c.08313.00183,8.02789-.00134,8.00006,0a1.00011,1.00011,0,0,1-.00012,2H33.5A1.00011,1.00011,0,0,1,33.5,25Zm0,9c.08313.00183,8.02789-.00134,8.00006,0a1.00011,1.00011,0,0,1-.00012,2H33.5A1.00011,1.00011,0,0,1,33.5,34Zm0,9c.08313.00183,8.02789-.00134,8.00006,0a1.00011,1.00011,0,0,1-.00012,2H33.5A1.00011,1.00011,0,0,1,33.5,43Z"></path>
                        </svg>

                    </div>
                    <h3 className="fs-2 text-body-emphasis">Produktkaufhistorie</h3>
                    <p>Sie können die Liste der Kaufhistorie der Produkte sehen. Klicken Sie in der sich öffnenden Liste
                        auf die Schaltfläche „Kaufhistorie“ neben dem Produkt, dessen Bericht Sie sehen möchten.</p>
                    <Link to={`/products`} className="icon-link">
                        Gehen zum Bericht
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                             className="bi bi-chevron-double-right" viewBox="0 0 16 16">
                            <path fillRule="evenodd"
                                  d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708"></path>
                            <path fillRule="evenodd"
                                  d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708"></path>
                        </svg>
                    </Link>
                </div>
            </div>
            <div className="row g-4 py-5 row-cols-1 row-cols-lg-3">
                <div className="feature col">
                    <div
                        className="feature-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor"
                             className="bi bi-person-video2" viewBox="0 0 16 16">
                            <path d="M10 9.05a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5"/>
                            <path
                                d="M2 1a2 2 0 0 0-2 2v9a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2zM1 3a1 1 0 0 1 1-1h2v2H1zm4 10V2h9a1 1 0 0 1 1 1v9c0 .285-.12.543-.31.725C14.15 11.494 12.822 10 10 10c-3.037 0-4.345 1.73-4.798 3zm-4-2h3v2H2a1 1 0 0 1-1-1zm3-1H1V8h3zm0-3H1V5h3z"/>
                        </svg>

                    </div>
                    <h3 className="fs-2 text-body-emphasis">Kaufhistorie des Lieferanten</h3>
                    <p>Sie können die Liste der Kaufhistorie des Lieferanten sehen. Klicken Sie in der sich öffnenden
                        Liste
                        auf die Schaltfläche „Kaufhistorie“ neben dem Lieferant, dessen Bericht Sie sehen möchten.</p>
                    <Link to={`/suppliers`} className="icon-link">
                        Gehen zum Bericht
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                             className="bi bi-chevron-double-right" viewBox="0 0 16 16">
                            <path fillRule="evenodd"
                                  d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708"></path>
                            <path fillRule="evenodd"
                                  d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708"></path>
                        </svg>
                    </Link>
                </div>
                <div className="feature col">
                    <div
                        className="feature-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor"
                             className="bi bi-arrow-left-right" viewBox="0 0 16 16">
                            <path fillRule="evenodd"
                                  d="M1 11.5a.5.5 0 0 0 .5.5h11.793l-3.147 3.146a.5.5 0 0 0 .708.708l4-4a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708.708L13.293 11H1.5a.5.5 0 0 0-.5.5m14-7a.5.5 0 0 1-.5.5H2.707l3.147 3.146a.5.5 0 1 1-.708.708l-4-4a.5.5 0 0 1 0-.708l4-4a.5.5 0 1 1 .708.708L2.707 4H14.5a.5.5 0 0 1 .5.5"/>
                        </svg>

                    </div>
                    <h3 className="fs-2 text-body-emphasis">Lagerbewegung</h3>
                    <p>Sie können alle Lagerein- und -ausgänge einsehen.</p>
                    <Link to={`/suppliers`} className="icon-link">
                    Gehen zum Bericht
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                             className="bi bi-chevron-double-right" viewBox="0 0 16 16">
                            <path fillRule="evenodd"
                                  d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708"></path>
                            <path fillRule="evenodd"
                                  d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708"></path>
                        </svg>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default ReportsHome;