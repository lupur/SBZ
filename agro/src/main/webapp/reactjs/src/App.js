import React from 'react';

import logo from './logo.svg';
import './App.css';

import {Container, Row, Col} from 'react-bootstrap';

import NavigationBar from './components/NavigationBar';
import Welcome from './components/Welcome';
import Footer from './components/Footer';

function App() {
	
  const marginTop = {
	marginTop:"20px"
  }
  return (
    <div className="App">
    	<NavigationBar/>
    	<Container>
    		<Row>
    			<Col lg={12} style={marginTop}>
		    		<Welcome/>
		    	</Col>
    		</Row>
    	</Container>
    	<Footer/>
	</div>
  );
}

export default App;
