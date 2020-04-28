import React from 'react';

import {Navbar, Nav} from 'react-bootstrap';
import {Link} from 'react-router-dom'
import {logout, isLoggedIn} from "./Utils.js"

class NavigationBar extends React.Component {

    render() {

        return (
            <Navbar bg="dark" variant="dark">
                <Link to={""} className="navbar-brand">
                    Agro 2020
                </Link>
               <Nav className="mr-auto">
                   <Link to={"users"} ref={this.users} className="nav-link">Users</Link>
                   <Link to={"crops"} ref={this.crops} className="nav-link">Crops</Link>
                   <Link to={"login"} ref={this.login} className="nav-link">Login</Link>
                   <Link to={"login"} ref={this.logout} className="nav-link" onClick={logout}>Logout</Link>
               </Nav>
           </Navbar>
       );
    }
}

export default NavigationBar;