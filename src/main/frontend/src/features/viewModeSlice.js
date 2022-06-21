import {FaMoon, FaSun} from "react-icons/fa";
import {createSlice} from "@reduxjs/toolkit";
import {MAP_TILER_KEY} from "../constants";


const VIEW_MODE = 'viewMode'

export const LIGHT_MODE = 'light'
export const DARK_MODE = 'dark'

export const viewModeSlice = createSlice({
    name: 'viewModeSlice',
    initialState: {
        active: localStorage.getItem(VIEW_MODE) ?? LIGHT_MODE,
        modes: {
            light: {
                icon: FaSun,
                iconColor: '#F77E21',
                primaryColor: '#FFF',
                textColor: '#000',
                baseMap: `https://api.maptiler.com/maps/hybrid/{z}/{x}/{y}.jpg?key=${MAP_TILER_KEY}`,
                dropdownVariant: 'outline-dark',
                dropdownMenuStyle: {
                    background: 'white'
                },
                logo: {
                    upperBand: '#338AF3',
                    lowerBand: '#FFDA44',
                    favicon: 'favicon-light.ico'
                }
            },
            dark: {
                icon: FaMoon,
                iconColor: 'gray',
                primaryColor: '#171616',
                textColor: 'gray',
                baseMap: 'https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.png',
                dropdownVariant: 'dark',
                dropdownMenuStyle: {
                    background: 'black'
                },
                logo: {
                    upperBand: '#CD0000',
                    lowerBand: '#000000',
                    favicon: 'favicon-dark.ico'
                }
            }
        }
    },
    reducers: {
        switchMode: (state) => {
            const newMode = state.active === LIGHT_MODE ? DARK_MODE: LIGHT_MODE
            localStorage.setItem(VIEW_MODE, newMode)

            setFaviconFunc(state.modes[newMode].logo.favicon)

            return {...state, active: newMode}
        },
        setFavicon: (state) => {
            setFaviconFunc(state.modes[state.active].logo.favicon)
        }
    }
})

export const {
    switchMode,
    setFavicon
} = viewModeSlice.actions

const setFaviconFunc = (faviconFileName) => {
    const favicon = document.getElementById('favicon')
    favicon.href = `/${faviconFileName}`
}

export const getActiveModeSelector = (state) => state.viewMode.active

export const getLightModeDataSelector = (state) => state.viewMode.modes.light
export const getDarkModeDataSelector = (state) => state.viewMode.modes.dark
export const getActiveModeDataSelector = (state) => state.viewMode.modes[state.viewMode.active]

export default viewModeSlice.reducer