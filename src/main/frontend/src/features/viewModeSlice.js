import {FaMoon, FaSun} from "react-icons/fa";
import {createSlice} from "@reduxjs/toolkit";


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
                baseMap: 'https://api.maptiler.com/maps/hybrid/{z}/{x}/{y}.jpg?key=dm8A81cL8t5oeZpgxXbY',
                dropdownVariant: 'outline-dark',
                dropdownMenuStyle: {
                    background: 'white'
                },
                logo: {
                    upperBand: '#338AF3',
                    lowerBand: '#FFDA44'
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
                    lowerBand: '#000000'
                }
            }
        }
    },
    reducers: {
        switchMode: (state) => {
            const newMode = state.active === LIGHT_MODE ? DARK_MODE: LIGHT_MODE
            localStorage.setItem(VIEW_MODE, newMode)

            return {...state, active: newMode}
        }
    }
})

export const {
    switchMode
} = viewModeSlice.actions

export const useGetActiveModeSelector = (state) => state.viewMode.active

export const useGetLightModeDataSelector = (state) => state.viewMode.modes.light
export const useGetDarkModeDataSelector = (state) => state.viewMode.modes.dark
export const useGetActiveModeDataSelector = (state) => state.viewMode.modes[state.viewMode.active]

export default viewModeSlice.reducer