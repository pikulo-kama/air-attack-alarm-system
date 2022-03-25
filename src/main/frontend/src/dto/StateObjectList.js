import DistrictStateObject from "./DistrictStateObject";
import AlarmState from "./AlarmState.ts";

class StateObjectList {

    stateObjects: Array<DistrictStateObject>

    constructor(rawData: any) {
        this.stateObjects = rawData.map(record => new DistrictStateObject(record));
    }

    groupByCode(): Map<string, AlarmState> {
        let statesMap = [];
        this.stateObjects.forEach(stateObjects =>
            statesMap[stateObjects.districtCode] = stateObjects.alarmState);

        return statesMap;
    }
}

export default StateObjectList
