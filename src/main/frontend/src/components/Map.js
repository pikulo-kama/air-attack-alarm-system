import React, {useEffect, useState} from 'react';
import {GeoJSON, MapContainer, TileLayer} from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import {REACT_APP_BACKEND_URL} from "../constants/HttpConstants";
import axios from "axios";
import DistrictGeoJson from "../model/DistrictGeoJson";
import {UK_BORDERS_DATA} from "../geodata/ukraine_borders";
import {alarmOffDistrictStyle} from "../geodata/styles";

const outerBounds = [[22.10166, 44.02738], [40.25842, 52.42326]];

export default function Map() {

    const [geoData, setGeoData] = useState([]);

    useEffect(() => {
        axios.get(`${REACT_APP_BACKEND_URL}/api/v1/districts/geo`)
            .then(function (response) {
                setGeoData(response.data);
            });
    }, []);

    return (
        <MapContainer
            center={[48.379433, 31.165581]}
            zoom={5.5}
            style={{width: '100vw', height: '85vh'}}
            bounds={outerBounds}
        >
            <TileLayer
                url="https://api.maptiler.com/maps/hybrid/{z}/{x}/{y}.jpg?key=dm8A81cL8t5oeZpgxXbY"
                attribution='<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'
            />
            <GeoJSON data={UK_BORDERS_DATA} style={alarmOffDistrictStyle} />
            <DistrictGeoJson geoData={geoData}/>
        </MapContainer>
    );
}