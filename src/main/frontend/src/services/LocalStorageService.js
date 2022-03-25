class LocalStorageService {

    static shouldPlayAlarm(districtCode: string): boolean {
        let couldBePlayed = localStorage.getItem(districtCode);

        if (couldBePlayed === null) {
            couldBePlayed = 'true';
        }
        return couldBePlayed === 'true';
    }

    /**
     *  Used to retrieve last observed alarm state from local storage
     *  @param districtCode unique code of Ukraine district
     *
     *  @return ON/OFF
     * **/
    static #getDistrictState(districtCode: string): boolean {
        if (districtCode === '') {
            return true;
        }
        let isPlayed = localStorage.getItem(districtCode);

        return isPlayed !== null ? isPlayed : false;
    }


    /**
     * Updates alarm state of district in local storage
     * @param districtCode unique code of Ukraine district
     * @param alarmState state of alarm that should be stored in storage
     * */
    static updateDistrictState(districtCode: string, couldBePlayed: boolean): void {
        localStorage.setItem(districtCode, couldBePlayed);
    }
}

export default LocalStorageService