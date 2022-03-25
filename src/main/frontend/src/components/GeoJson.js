import React, {useEffect, useState} from "react";
import axios from "axios";
import {REACT_APP_DISTRICT_STATE_ENDPOINT} from "../constants/BackendRoutes";
import {GeoJSON} from "react-leaflet";
import {alarmOffDistrictStyle, alarmOnDistrictStyle} from "../geodata/styles";
import LocalStorageService from "../services/LocalStorageService"
import DistrictCodeHelper from "../helper/DistrictCodeHelper";
import AlarmState from "../dto/AlarmState.ts";
import {ALARM_MEDIA_NAME, WINDOW_REFRESH_RATE} from "../constants/SystemConstants";
import StateObjectList from "../dto/StateObjectList";
import DistrictGeoObject from "../dto/DistrictGeoObject";


function GeoJson(props) {

    const geoData: Array<DistrictGeoObject> = props.geoData;
    const [districtStates, setDistrictStates] = useState({});

    useEffect(() => {
        let timerId = setInterval(loadStates, WINDOW_REFRESH_RATE);
        return () => clearInterval(timerId);
    });

    geoData.forEach(record => {

        if (isActiveDistrict(record)) {

            if (record.alarmState === AlarmState.ON && LocalStorageService.shouldPlayAlarm(record.districtCode)) {
                playAlarmSound();
                LocalStorageService.updateDistrictState(record.districtCode, false);
            } else if (record.alarmState === AlarmState.OFF) {
                LocalStorageService.updateDistrictState(record.districtCode, true);
            }
        }

        record.updateAlarmState(districtStates);
    });

    return geoData.map(record =>
        <GeoJSON
            data={record.geoJson}
            style={record.alarmState === AlarmState.ON ? alarmOnDistrictStyle : alarmOffDistrictStyle}
        />);



    async function loadStates() {
        let response = await axios.get(REACT_APP_DISTRICT_STATE_ENDPOINT);
        let statesData = new StateObjectList(response.data).groupByCode()

        setDistrictStates(statesData);
    }

    function isActiveDistrict(record: DistrictGeoObject): boolean {
        return DistrictCodeHelper.getDistrictCode() === record.districtCode;
    }

    function playAlarmSound() {
        new Audio(ALARM_MEDIA_NAME).play();
    }
}

export default GeoJson