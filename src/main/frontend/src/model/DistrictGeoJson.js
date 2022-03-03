import React, {useEffect, useState} from "react";
import axios from "axios";
import {REACT_APP_BACKEND_URL} from "../constants/HttpConstants";
import {GeoJSON} from "react-leaflet";
import {alarmOffDistrictStyle, alarmOnDistrictStyle} from "../geodata/styles";


export default function DistrictGeoJson(props) {
    const [districtStatesData, setDistrictStatesData] = useState([]);

    async function loadStates() {
        let response = await axios.get(`${REACT_APP_BACKEND_URL}/api/v1/districts/states`);
        setDistrictStatesData(response.data);
    }

    useEffect(() => {
        let timerId = setInterval(() => loadStates(), 2000);
        return () => clearInterval(timerId);
    });

    let statesMap = [];
    let districtCode = String(window.location.hash).substring(1);

    districtStatesData.map(district => statesMap[district['districtCode']] = district['alarmState']);
    props.geoData.forEach(geo => {

        if (districtCode === geo['districtCode']) {
            console.log(geo['alarmState'], statesMap[geo['districtCode']]);
            if (geo['alarmState'] === 'OFF' && statesMap[geo['districtCode']] === 'ON') {
                console.log('play');
                let audio = new Audio("alarm.mp3");
                audio.play();
            }
        }
        geo['alarmState'] = statesMap[geo['districtCode']];
    });

    return props.geoData.map((district) =>
        <GeoJSON
            data={JSON.parse(district['geoJson'])}
            style={district['alarmState'] === 'ON' ? alarmOnDistrictStyle : alarmOffDistrictStyle}
        />);
}