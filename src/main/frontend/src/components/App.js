import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/App.css';
import Map from "./Map";
import NavBar from "./Navbar";

function App() {

    localStorage.clear();

    return (
        <>
            <NavBar/>
            <div className="d-flex flex-row justify-content-end">
                <div className="d-flex justify-content-end">
                    <Map/>
                </div>
            </div>
        </>
    );
}

export default App;
