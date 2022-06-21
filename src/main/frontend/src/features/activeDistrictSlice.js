import {createSlice} from "@reduxjs/toolkit";

export const activeDistrictSlice = createSlice({
    name: 'activeDistrictSlice',
    initialState: {
        'districtName': 'Обласний центр',
        'districtCode': undefined
    },
    reducers: {
        setActiveDistrict: (state, action) => {
            window.location.hash = action.payload.districtCode
            return action.payload
        },
        tryToRestoreFromHash: (state, action) => {
            if (!action.payload) return

            const hashDistrictCode = String(window.location.hash).substring(1)
            const matchDistricts = action.payload.filter(districtState =>
                districtState.districtCode === hashDistrictCode)

            if (matchDistricts?.length !== 1) {
                return
            }

            return matchDistricts[0]
        }
    }
})

export const {
    setActiveDistrict,
    tryToRestoreFromHash
} = activeDistrictSlice.actions

export const useGetActiveDistrictSelector = (state) => state.activeDistrict

export default activeDistrictSlice.reducer