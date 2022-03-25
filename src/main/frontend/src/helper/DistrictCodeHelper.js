
class DistrictCodeHelper {

    static getDistrictCode() {
        return String(window.location.hash).substring(1);
    }
}

export default DistrictCodeHelper