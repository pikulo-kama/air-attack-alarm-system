import React, {useEffect, useState} from "react";
import axios from "axios";
import {REACT_APP_DISTRICT_STATE_ENDPOINT} from "../constants/BackendRoutes";
import {GeoJSON} from "react-leaflet";
import {alarmOffDistrictStyle, alarmOnDistrictStyle} from "../geodata/styles";
import LocalStorageService from "../services/LocalStorageService"
import DistrictStateService from "../services/DistrictStateService"
import DistrictCodeHelper from "../helper/DistrictCodeHelper";
import AlarmState from "../dto/AlarmState.ts";


export default function GeoJson(props) {

    const geoData = props.geoData;

    const districtStateService = new DistrictStateService();

    const [districtStates, setDistrictStates] = useState([]);

    async function loadStates() {
        let response = await axios.get(REACT_APP_DISTRICT_STATE_ENDPOINT);
        let groupedResponse = districtStateService.hashGroupingByCode(response.data);
        setDistrictStates(groupedResponse);
    }

    useEffect(() => {
        let timerId = setInterval(() => loadStates(), 2000);
        return () => clearInterval(timerId);
    });

    let activeDistrictCode = DistrictCodeHelper.getDistrictCode();

    geoData.forEach(geo => {

        if (geo.districtCode === activeDistrictCode) {
            if (AlarmState[geo.alarmState] === AlarmState.ON &&
                LocalStorageService.shouldPlayAlarm(activeDistrictCode)) {

                new Audio("alarm.mp3").play();
                LocalStorageService.updateDistrictState(activeDistrictCode, false);
            } else if (AlarmState[geo.alarmState] === AlarmState.OFF) {
                LocalStorageService.updateDistrictState(activeDistrictCode, true);
            }
        }

        geo.alarmState = districtStates[geo.districtCode];
    });

    return geoData.map(district =>
        <GeoJSON
            data={JSON.parse(district.geoJson)}
            style={district.alarmState === 'ON' ? alarmOnDistrictStyle : alarmOffDistrictStyle}
        />);
}