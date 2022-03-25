import AlarmState from "./AlarmState.ts";

class DistrictStateObject {

    districtName: string
    districtCode: string
    alarmState: AlarmState

    constructor(record: any) {
        this.districtName = record.districtName;
        this.districtCode = record.districtCode;
        this.alarmState = AlarmState[record.alarmState];
    }
}

export default DistrictStateObject