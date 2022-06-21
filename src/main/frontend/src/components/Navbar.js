import {Container, Nav, Navbar} from "react-bootstrap";
import DistrictList from "./list/DistrictList";
import Logo from "./Logo";
import ViewMode from "./ViewMode";
import {useSelector} from "react-redux";
import {useGetActiveModeDataSelector} from "../features/viewModeSlice";

const NavBar = ({ districtStateList }) => {

    const activeViewModeData = useSelector(useGetActiveModeDataSelector)

    return (
        <Navbar expand="md">
            <Container>
                <Navbar.Brand>
                    <Logo />{'   '}<text style={{color: activeViewModeData.textColor}}>{'УВАГА ТРИВОГА!'}</text>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <DistrictList districtStateList={districtStateList} />
                    </Nav>
                    <ViewMode />
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default NavBar