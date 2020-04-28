import React, {Component} from 'react';
import {Card, Form, Button, Col} from 'react-bootstrap'
import axios from 'axios';

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {username:'', password:''};
        this.submitLogin = this.submitLogin.bind(this);
        this.loginChange = this.loginChange.bind(this);
    }

    componentDidMount() {
        // ovo je dobro koristiti za komponente koje se popunjavaju podacima
    }

    submitLogin(event) {
        axios.interceptors.request.use(request => {
            console.log('Starting Request', request)
            return request
          })
        event.preventDefault();

        axios.post("http://localhost:8080/users/login", 
            {
                username : this.state.username,
                password : this.state.password
            }
            ).then( response => {
                var token = response.data
                localStorage.setItem('token', token)
            }).catch(error => 
                {
                    alert(error.response.data);
                })
          
    }

    loginChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        })
    }
    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
                <Card.Header>Login</Card.Header>
                <Form onSubmit={this.submitLogin} id="loginFormId" >
                    <Card.Body>
                    <Form.Row>
                        <Form.Group as={Col} controlId="formGridUsername">
                            <Form.Control required
                                name="username"
                                value={this.state.username}
                                onChange={this.loginChange}
                                className={"bg-dark text-white"}
                                placeholder="Username" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} controlId="formGridPassword">
                            <Form.Control required
                                name="password"
                                type="password"
                                value={this.state.password}
                                onChange={this.loginChange}
                                className={"bg-dark text-white"}
                                placeholder="Password" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group  as={Col} controlId="formGridSubmit">
                            <Button variant="primary" type="submit">
                                Login
                            </Button>
                        </Form.Group>
                    </Form.Row>
                    </Card.Body>
                </Form>
                
            </Card>
            );
    }
}