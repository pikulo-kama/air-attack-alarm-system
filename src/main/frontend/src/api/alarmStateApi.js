import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {BACKEND_URL} from "../constants";


export const alarmStateApi = createApi({
    reducerPath: 'alarmStateApi',
    baseQuery: fetchBaseQuery({
        baseUrl: BACKEND_URL
    }),
    endpoints: builder => ({
        getDistrictStates: builder.query({
            query: () => '/api/v1/districts/states'
        }),
    }),
})

export const {
    useGetDistrictStatesQuery
} = alarmStateApi
