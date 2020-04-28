import React from 'react';

import {Navbar, Nav} from 'react-bootstrap';

class NavigationBar extends React.Component {
	render() {
		return (
			<Navbar bg="dark" variant="dark">
				<Navbar.Brand href="#home">Agro 2020
				</Navbar.Brand>
				<Nav className="mr-auto">
			      <Nav.Link href="#home">Home</Nav.Link>
			      <Nav.Link href="#features">Features</Nav.Link>
			    </Nav>
			</Navbar>
		);
	}
}

export default NavigationBar;