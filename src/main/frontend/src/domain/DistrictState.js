
class DistrictState {
    constructor(districtCode, alarmState) {
        this.districtCode = districtCode;
        this.alarmState = alarmState;
        this.isOngoing = alarmState === 'ON';
    }

    updateAlarmState(alarmState) {
        this.isOngoing = this.alarmState === 'ON' && alarmState === 'ON';
        this.alarmState = alarmState;
    }
}

export default DistrictState