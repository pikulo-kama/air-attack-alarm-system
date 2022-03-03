import React, {useEffect, useState} from "react";
import Dropdown from "react-bootstrap/Dropdown";
import FormControl from "react-bootstrap/FormControl";
import axios from "axios";
import {REACT_APP_BACKEND_URL} from "../constants/HttpConstants";
import DropdownDistrictList from "../model/DropdownDistrictList";

const CustomMenu = React.forwardRef(
    ({children, style, className, 'aria-labelledby': labeledBy}, ref) => {
        const [value, setValue] = useState('');

        return (
            <div
                ref={ref}
                style={style}
                className={className}
                aria-labelledby={labeledBy}
            >
                {/*<FormControl*/}
                {/*    autoFocus*/}
                {/*    className="mx-3 my-2 w-auto"*/}
                {/*    placeholder="Введіть облцентр..."*/}
                {/*    onChange={(e) => setValue(e.target.value)}*/}
                {/*    value={value}*/}
                {/*/>*/}
                <ul className="list-unstyled" style={{height: "200px", overflowY: "auto"}}>
                    {React.Children.toArray(children).filter(
                        (child) =>
                            !value || child.props.children.toLowerCase().startsWith(value)
                    )}
                </ul>
            </div>
        );
    },
);

export default function SelectBar() {

    const [districts, setDistricts] = useState([]);
    const [activeDistrictCode, setActiveDistrictCode] = useState( null);
    const [activeDistrict, setActiveDistrict] = useState('Oбласний центр');

    function getDistricts() {
        axios.get(REACT_APP_BACKEND_URL + '/api/v1/districts/states')
            .then(function (response) {
                setDistricts(response.data);
            });
    }

    useEffect(getDistricts, [])

    useEffect(() => {
        let hash = window.location.hash;
        hash !== '' && setActiveDistrictCode(String(hash).substring(1));

        let active = districts.find(d => d['districtCode'] === activeDistrictCode);
        active !== undefined && setActiveDistrict(active['districtName']);
    }, [getDistricts])

    function handleDistrictChange(key, event) {
        setActiveDistrictCode(String(key).substring(1));
        setActiveDistrict(event.currentTarget.text);
    }

    return (
        <Dropdown onSelect={handleDistrictChange}>
            <Dropdown.Toggle id="dropdown-custom-components" variant="outline-dark">
                {activeDistrict}
            </Dropdown.Toggle>

            <Dropdown.Menu as={CustomMenu} variant="outline-dark">
                <DropdownDistrictList districts={districts} />
            </Dropdown.Menu>
        </Dropdown>
    );
}