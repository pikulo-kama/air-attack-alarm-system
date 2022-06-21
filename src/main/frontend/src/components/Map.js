import 'leaflet/dist/leaflet.css'
import L from "leaflet"
import React, {useEffect, useState} from 'react'
import {GeoJSON, MapContainer} from 'react-leaflet'
import UA_BORDERS from "../geo/contryBorders"
import {useSelector} from "react-redux";
import {districtGeoJsonCollection} from "../geo/districtGeoJsonCollection";
import {
    DARK_MODE,
    LIGHT_MODE,
    useGetActiveModeSelector,
    useGetDarkModeDataSelector,
    useGetLightModeDataSelector
} from "../features/viewModeSlice";


const Map = ({ districtStateList: alarmStates }) => {

    const [map, setMap] = useState(null)

    const activeMode = useSelector(useGetActiveModeSelector)
    const darkModeData = useSelector(useGetDarkModeDataSelector)
    const lightModeData = useSelector(useGetLightModeDataSelector)

    useEffect(() => {
        if (!map) return

        const countryBorders = L.geoJSON(UA_BORDERS, {
            style: function () {
                return {
                    color: '#fff',
                    fillOpacity: .4,
                    fillColor: '#000'
                }
            },
            weight: 1,
        }).addTo(map)

        const newZoom = map.getBoundsZoom(countryBorders.getBounds(), false, undefined)
        map.setZoom(newZoom)

        map.setMaxBounds(countryBorders.getBounds())
        map.fitBounds(countryBorders.getBounds(), {reset: true})

    }, [map, setMap])


    useEffect(() => {
        if (!map) return

        let lightBaseMap = new L.TileLayer(lightModeData.baseMap)
        let darkBaseMap = new L.TileLayer(darkModeData.baseMap)

        if (activeMode === LIGHT_MODE) {
            map.removeLayer(darkBaseMap)
            map.addLayer(lightBaseMap)
        } else if (activeMode === DARK_MODE) {
            map.removeLayer(lightBaseMap)
            map.addLayer(darkBaseMap)
        }

    }, [map, setMap, activeMode]);

    return (
        <MapContainer
            center={[48.3794, 31.1656]}
            zoom={2}
            zoomSnap={false}
            style={{
                width: '100vw', height: '85vh', background: 'black'
            }}
            whenCreated={setMap}
        >
            {
                (alarmStates ?? []).map(alarmState =>
                    <GeoJSON
                        key={alarmState.districtCode}
                        data={districtGeoJsonCollection[alarmState.districtCode]}
                        style={{
                            color: alarmState.alarmState === 'ON' ? 'red' : 'transparent',
                            fillOpacity: .4
                        }}
                        weight={1}
                    />
                )
            }
        </MapContainer>
    )
}

export default Map