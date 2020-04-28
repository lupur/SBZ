import React from 'react';

import './App.css';

import {Container, Row, Col} from 'react-bootstrap';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'

import NavigationBar from './components/NavigationBar';
import Welcome from './components/Welcome';
import Footer from './components/Footer';
import Users from './components/Users';
import Crops from './components/Crops'
import Login from './components/Login'

function App() {

  const marginTop = {
    marginTop:"20px"
    }

  return (
    <Router>
    <div className="App">
        <NavigationBar/>
        <Container>
            <Row>
                <Col lg={12} style={marginTop}>
                    <Switch>
                        <Route path="/" exact component={Welcome}/>
                        <Route path="/users" exact component={Users}/>
                        <Route path="/crops" exact component={Crops}/>
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

export default App;
