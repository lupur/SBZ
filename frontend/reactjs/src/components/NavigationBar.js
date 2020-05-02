import React from 'react';

import {Navbar, Nav} from 'react-bootstrap';
import {Link} from 'react-router-dom'
import {authService} from '../services/authService'
import {Role} from '../helpers/role'
import {history} from '../helpers/history'

class NavigationBar extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            currentUser: null,
            isAdmin: false
        };

        this.marginTop = {
            marginTop:"20px"
            }
    }

    componentDidMount() {
        console.log(Role.ADMIN)
        authService.currentUser.subscribe(x => this.setState({
            currentUser: x,
            isAdmin: x && x.role === Role.ADMIN
        }))
    }

    logout() {
        authService.logout();
        history.push('/login');
    }

    render() {

        return (
            <Navbar bg="dark" variant="dark">
                <Link to={""} className="navbar-brand">
                    Agro 2020
                </Link>
               
               <Nav className="mr-auto">
                   { this.state.isAdmin ? <Link to={"users"} className="nav-link">Users</Link> : null}
                   { this.state.isAdmin ?<Link to={"crops"} className="nav-link">Crops</Link> : null}
                   <Link to={"fields"} className="nav-link">Fields</Link>
                   <Link to={"login"} className="nav-link" onClick={this.logout}>Logout</Link>
                </Nav>
                
           </Navbar>
       );
    }
}

export default NavigationBar;