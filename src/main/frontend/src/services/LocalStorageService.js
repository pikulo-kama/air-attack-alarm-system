
class LocalStorageService {

    /**
     * Checks whether alarm for current district could be played
     * @param districtCode unique code of Ukraine district
     *
     * @return true if could be should be played, false - if not
     * */
    static shouldPlayAlarm(districtCode: string): boolean {
        let couldBePlayed = localStorage.getItem(districtCode);

        if (couldBePlayed === null) {
            couldBePlayed = 'true';
        }
        return couldBePlayed === 'true';
    }

    /**
     * Updates alarm state of district in local storage
     * @param districtCode unique code of Ukraine district
     * @param couldBePlayed used to define whether alarm for this district could be played
     * */
    static updateDistrictState(districtCode: string, couldBePlayed: boolean): void {
        localStorage.setItem(districtCode, couldBePlayed);
    }
}

export default LocalStorageService