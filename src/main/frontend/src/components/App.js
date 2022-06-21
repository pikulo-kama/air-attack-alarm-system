import 'bootstrap/dist/css/bootstrap.min.css';
import Map from "./Map";
import NavBar from "./Navbar";
import {useGetDistrictStatesQuery} from "../api/alarmStateApi";
import {DATA_REFRESH_RATE} from "../constants";
import {useSelector} from "react-redux";
import {getActiveModeDataSelector} from "../features/viewModeSlice";

const App = () => {

    const {data: districtStateList} = useGetDistrictStatesQuery(null, {
        pollingInterval: DATA_REFRESH_RATE
    })

    const activeViewModeData = useSelector(getActiveModeDataSelector)

    return (
        <section className='main-container' style={{
            background: activeViewModeData.primaryColor
        }}>
            <NavBar districtStateList={districtStateList} />
            <Map districtStateList={districtStateList} />
        </section>
    );
}

export default App
