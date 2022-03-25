import {Container, Nav, Navbar} from "react-bootstrap";
import SelectBar from "./SelectBar";

export default function NavBar() {
    return (
        <Navbar bg="light" expand="md">
            <Container>
                <Navbar.Brand>
                    <svg version="1.1" id="Layer_1" width="40px" height="40px"
                         viewBox="0 0 512 512">
                        <path style={{fill: "#828282"}} d="M434.439,110.654C401.179,45.876,334.359,0.003,255.98,0v101.329
                            c55.478,0.004,100.593,43.67,100.595,99.15c0,54.805-45.491,102.048-100.595,102.052V512
                            c70.646-73.617,151.953-154.542,187.529-251.428C461.298,212.121,457.979,156.5,434.439,110.654z"/>
                        <path style={{fill: "#979797"}} d="M68.492,260.572C104.067,357.458,185.374,438.383,256.02,512V302.531
                            c-55.103-0.004-100.595-47.247-100.595-102.052c0.002-55.479,45.117-99.146,100.595-99.15V0
                            c-78.379,0.003-145.199,45.876-178.46,110.654C54.021,156.5,50.702,212.121,68.492,260.572z"/>
                        <circle style={{fill: "#FFDA44"}} cx="256" cy="198.773" r="160"/>
                        <path style={{fill: "#338AF3"}} d="M96,198.773c0-88.367,71.634-160,160-160s160,71.633,160,160"/>
                        <path style={{fill:"#C7C7C7"}} d="M255.929,21.707c-73.745,0-141.451,47.552-166.61,116.806
                            c-25.343,69.773-3.142,149.836,53.974,197.071c57.236,47.338,140.369,53.625,203.978,15.148
                            c63.626-38.49,97.228-114.681,82.271-187.573C414.889,91.771,355.517,35.121,283.472,23.843
                            C274.361,22.416,265.141,21.707,255.929,21.707 M398.59,263.184c-30.241,67.009-105.732,104.802-177.479,88.399
                            c-55.215-12.621-100.384-55.764-115.778-110.195c-15.749-55.658,1.328-116.804,43.417-156.425
                            c45.534-42.867,114.172-54.571,171.321-28.799c68.228,30.767,105.971,108.144,87.651,180.844
                            C405.459,245.986,402.37,254.739,398.59,263.184C396.502,267.81,400.132,259.739,398.59,263.184"/>
                    </svg>
                    {'   '}УВАГА ТРИВОГА!
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <SelectBar />
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}