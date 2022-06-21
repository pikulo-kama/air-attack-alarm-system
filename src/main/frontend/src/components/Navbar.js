import {Container, Nav, Navbar} from "react-bootstrap";
import DistrictList from "./list/DistrictList";
import Logo from "./Logo";
import ViewMode from "./ViewMode";
import {useSelector} from "react-redux";
import {getActiveModeDataSelector} from "../features/viewModeSlice";

const NavBar = ({districtStateList}) => {

    const activeViewModeData = useSelector(getActiveModeDataSelector)

    return (
        <div
            style={{
                display: 'flex',
                justifyContent: 'space-between',
                marginRight: '.5rem'
            }}
        >
            <Navbar expand="md">
                <Container>
                    <Navbar.Brand>
                        <Logo/>{'   '}
                        <text style={{color: activeViewModeData.textColor}}>{'УВАГА ТРИВОГА!'}</text>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <DistrictList districtStateList={districtStateList}/>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <ViewMode/>
        </div>
    );
}

export default NavBar