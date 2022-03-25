import AlarmState from "./AlarmState.ts";

class DistrictGeoObject {

    districtName: string
    districtCode: string
    alarmState: AlarmState
    geoJson: any

    constructor(record: any) {
        this.districtName = record.districtName;
        this.districtCode = record.districtCode;
        this.alarmState = AlarmState[record.alarmState];
        this.geoJson = JSON.parse(record.geoJson);
    }

    updateAlarmState(districtStates: Map<any, any>): void {
        this.alarmState = districtStates[this.districtCode];
    }
}

export default DistrictGeoObject