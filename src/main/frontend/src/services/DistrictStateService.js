
class DistrictStateService {

    hashGroupingByCode(districtStateList) {
        let statesMap = [];
        districtStateList.forEach(district =>
            statesMap[district.districtCode] = district.alarmState);

        return statesMap;
    }
}

export default DistrictStateService