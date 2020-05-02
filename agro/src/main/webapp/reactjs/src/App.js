import React from 'react';

import './App.css';

import {Container, Row, Col} from 'react-bootstrap';
import {Router, Switch, Route} from 'react-router-dom'

import NavigationBar from './components/NavigationBar';
import Welcome from './components/Welcome';
import Footer from './components/Footer';
import Users from './components/Users';
import Crops from './components/Crops'
import Login from './components/Login'
import {authService} from './services/authService'
import {Role} from './helpers/role'
import {history} from './helpers/history'
import {PrivateRoute} from './components/PrivateRoute'
class App extends React.Component {

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
        authService.currentUser.subscribe(x => this.setState({
            currentUser: x,
            isAdmin: x && x.role === Role.Admin
        }))
    }

    render() {
        const { currentUser, isAdmin } = this.state;
        return (
            <Router history={history}>
            <div className="App">
                { currentUser &&
                <NavigationBar/>
                }
                <Container>
                    <Row>
                        <Col lg={12} style={this.marginTop}>
                            <Switch>
                                <PrivateRoute path="/" exact component={Welcome}/>
                                <PrivateRoute path="/users" roles={[Role.Admin]} exact component={Users}/>
                                <PrivateRoute path="/crops" roles={[Role.Admin]} exact component={Crops}/>
                                <Route path="/login" exact component={Login}/>
                            </Switch>
                    </Col>
                    </Row>
                </Container>
                <Footer/>
            </div>
            </Router>
        );
    }
}

export default App;
