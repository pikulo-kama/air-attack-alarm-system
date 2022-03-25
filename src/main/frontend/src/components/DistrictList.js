import Dropdown from "react-bootstrap/Dropdown";
import React from "react";

export default function DistrictList(props) {
    const districts = props.districts;
    if (districts.length === 0) {
        return <Dropdown.Header>Помилка при завантаженні</Dropdown.Header>
    }
    return districts.map((district) => {
        return <Dropdown.Item key={district.districtCode} href={`#${district.districtCode}`}>{district.districtName}</Dropdown.Item>
    });
}