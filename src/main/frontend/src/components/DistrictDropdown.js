import React, {useEffect, useState} from "react";
import Dropdown from "react-bootstrap/Dropdown";
import axios from "axios";
import {REACT_APP_DISTRICT_STATE_ENDPOINT} from "../constants/BackendRoutes";
import DistrictCodeHelper from "../helper/DistrictCodeHelper";
import DistrictStateObject from "../dto/DistrictStateObject";
import {ERROR_LOADING_DATA_MESSAGE, NO_DISTRICT_SELECTED_PLACEHOLDER} from "../constants/SystemConstants";

function DistrictDropdown() {

    const [districts, setDistricts] = useState([]);
    const [activeDistrictCode, setActiveDistrictCode] = useState( null);
    const [activeDistrict, setActiveDistrictName] = useState(NO_DISTRICT_SELECTED_PLACEHOLDER);

    useEffect(getDistricts, [])

    useEffect(() => {
        let districtCode = DistrictCodeHelper.getDistrictCode();
        districtCode !== '' && setActiveDistrictCode(districtCode);

        let active = districts.find(district => district.districtCode === activeDistrictCode);
        if (active !== undefined) {
            setActiveDistrictName(active.districtName);
            localStorage.clear();
        }
    }, [getDistricts])

    return (
        <Dropdown onSelect={handleDistrictChange}>
            <Dropdown.Toggle id="dropdown-custom-components" variant="outline-dark">
                {activeDistrict}
            </Dropdown.Toggle>

            <Dropdown.Menu as={CustomMenu} variant="outline-dark">
                {
                    (districts.length === 0 && <Dropdown.Header>{ERROR_LOADING_DATA_MESSAGE}</Dropdown.Header>)
                    || districts.map((district) =>
                        <Dropdown.Item key={district.districtCode} href={`#${district.districtCode}`}>
                            {district.districtName}
                        </Dropdown.Item>)
                }
            </Dropdown.Menu>
        </Dropdown>
    );



    function handleDistrictChange(key, event) {
        setActiveDistrictCode(String(key).substring(1));
        setActiveDistrictName(event.currentTarget.text);
    }

    function getDistricts() {
        axios.get(REACT_APP_DISTRICT_STATE_ENDPOINT)
            .then(function (response) {
                let statesData = response.data.map(record => new DistrictStateObject(record));
                setDistricts(statesData);
            });
    }
}

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

export default DistrictDropdown