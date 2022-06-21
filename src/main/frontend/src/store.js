import {configureStore} from "@reduxjs/toolkit";
import {alarmStateApi} from "./api/alarmStateApi";
import {setupListeners} from "@reduxjs/toolkit/query";
import activeDistrictSliceReducer from "./features/activeDistrictSlice"
import viewModeReducer from "./features/viewModeSlice"

export const store = configureStore({
    reducer: {
        [alarmStateApi.reducerPath]: alarmStateApi.reducer,
        activeDistrict: activeDistrictSliceReducer,
        viewMode: viewModeReducer
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware()
        .concat(alarmStateApi.middleware)
})

setupListeners(store.dispatch)