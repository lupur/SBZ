import 'devextreme/dist/css/dx.common.css';
import 'devextreme/dist/css/dx.light.css';
import React from 'react';
import * as V from 'victory';

import './App.css';

import {Container, Row, Col} from 'react-bootstrap';
import {Router, Switch, Route} from 'react-router-dom'

import NavigationBar from './components/NavigationBar';
import Welcome from './components/Welcome';
import Dashboard from './components/Dashboard';
import Footer from './components/Footer';
import Array from './components/Array';
import Users from './components/Users';
import Crops from './components/Crops'
import Login from './components/Login'
import Fields from './components/Fields'
import Field from './components/Field'
import Crop from './components/Crop'
import DeviceTypes from './components/DeviceTypes'
import {authService} from './services/authService'
import {Role} from './helpers/role'
import {history} from './helpers/history'
import {PrivateRoute} from './components/PrivateRoute'
import {Chart} from './components/Chart'
class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            currentUser: null
        };

        this.marginTop = {
            marginTop:"20px"
            }
    }

    componentDidMount() {
        authService.currentUser.subscribe(x => this.setState({
            currentUser: x
        }))
    }

    render() {
        const { currentUser } = this.state;
        return (
            <Router history={history}>
            <div className="App">
                { currentUser &&
                <NavigationBar/>
                }
                <Container className="mw-100">
                    <Row>
                        <Col lg={12} style={this.marginTop}>
                            <Switch>
                                <PrivateRoute path="/" exact component={Welcome}/>
                                <PrivateRoute path="/dashboard" exact component={Dashboard}/>
                                <PrivateRoute path="/users" roles={[Role.ADMIN]}  component={Users}/>
                                <PrivateRoute exact path="/crops" roles={[Role.ADMIN]}  component={Crops}/>
                                <PrivateRoute exact path="/deviceTypes" roles={[Role.ADMIN]}  component={DeviceTypes}/>
                                <PrivateRoute exact path="/fields" component={Fields}/>
                                <PrivateRoute path="/fields/:id" component={Field} />
                                <PrivateRoute path="/crops/:id" component={Crop} />
                                <Route path="/login" component={Login}/>
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
