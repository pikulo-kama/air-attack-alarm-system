import React, {useEffect} from "react";
import Dropdown from "react-bootstrap/Dropdown";
import ApplicationDropdown from "./ApplicationDropdown";
import {useDispatch, useSelector} from "react-redux";
import {
    setActiveDistrict,
    tryToRestoreFromHash,
    useGetActiveDistrictSelector
} from "../../features/activeDistrictSlice";
import {getActiveModeDataSelector} from "../../features/viewModeSlice";

const DistrictList = ({ districtStateList }) => {

    const dispatch = useDispatch()
    const activeDistrict = useSelector(useGetActiveDistrictSelector)
    const activeModeData = useSelector(getActiveModeDataSelector)

    useEffect(() => {
        dispatch(tryToRestoreFromHash(districtStateList))
    })

    return (
        <Dropdown>
            <Dropdown.Toggle id="dropdown-custom-components" variant={activeModeData.dropdownVariant}>
                {activeDistrict.districtName}
            </Dropdown.Toggle>

            <Dropdown.Menu
                variant="outline-dark"
                style={activeModeData.dropdownMenuStyle}
                as={ApplicationDropdown}
            >
                {
                    !districtStateList?.length ?
                        <Dropdown.Header>{'Помилка при завантаженні'}</Dropdown.Header> :

                        districtStateList.map((district) =>
                            <Dropdown.Item
                                key={district.districtCode}
                                onClick={() => dispatch(setActiveDistrict({
                                    districtName: district.districtName,
                                    districtCode: district.districtCode
                                }))}
                            >
                                {district.districtName}
                            </Dropdown.Item>)
                }
            </Dropdown.Menu>
        </Dropdown>
    );
}

export default DistrictList