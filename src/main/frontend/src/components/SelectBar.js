import React, {useEffect, useState} from "react";
import Dropdown from "react-bootstrap/Dropdown";
import axios from "axios";
import {REACT_APP_DISTRICT_STATE_ENDPOINT} from "../constants/BackendRoutes";
import DistrictList from "./DistrictList";
import LocalStorageService from "../services/LocalStorageService";

const CustomMenu = React.forwardRef(
    ({children, style, className, 'aria-labelledby': labeledBy}, ref) => {
        return (
            <div
                ref={ref}
                style={style}
                className={className}
                aria-labelledby={labeledBy}
            >
                <ul className="list-unstyled" style={{height: "200px", overflowY: "auto"}}>
                    {children}
                </ul>
            </div>
        );
    },
);

export default function SelectBar() {

    const [districts, setDistricts] = useState([]);
    const [activeDistrictCode, setActiveDistrictCode] = useState( null);
    const [activeDistrict, setActiveDistrictName] = useState('Oбласний центр');

    function getDistricts() {
        axios.get(REACT_APP_DISTRICT_STATE_ENDPOINT)
            .then(function (response) {
                setDistricts(response.data);
            });
    }

    useEffect(getDistricts, [])

    useEffect(() => {
        let hash = window.location.hash;
        hash !== '' && setActiveDistrictCode(String(hash).substring(1));

        let active = districts.find(district => district.districtCode === activeDistrictCode);
        if (active !== undefined) {
            setActiveDistrictName(active.districtName);
            localStorage.clear();
        }
    }, [getDistricts])

    function handleDistrictChange(key, event) {
        setActiveDistrictCode(String(key).substring(1));
        setActiveDistrictName(event.currentTarget.text);
    }

    return (
        <Dropdown onSelect={handleDistrictChange}>
            <Dropdown.Toggle id="dropdown-custom-components" variant="outline-dark">
                {activeDistrict}
            </Dropdown.Toggle>

            <Dropdown.Menu as={CustomMenu} variant="outline-dark">
                <DistrictList districts={districts} />
            </Dropdown.Menu>
        </Dropdown>
    );
}