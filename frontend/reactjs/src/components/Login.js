import React, {Component} from 'react';
import {Card, Form, Button, Col, Table} from 'react-bootstrap'
import {authService} from '../services/authService'

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loginUsername:'', 
            loginPassword:'',
            registerUsername: '',
            registerPassword: '',
            registerPassConf: ''
            };
        this.submitLogin = this.submitLogin.bind(this);
        this.loginChange = this.loginChange.bind(this);
        this.login = this.login.bind(this);
        this.register = this.register.bind(this);
        this.submitRegistration = this.submitRegistration.bind(this);

        if (authService.currentUserValue) { 
            this.props.history.push('/');
        }
    }

    submitLogin(event) {
        event.preventDefault();
        authService.login(this.state.loginUsername, this.state.loginPassword)
            .then(
                user => {
                    const { from } = this.props.location.state || { from: { pathname: "/" } };
                    this.props.history.push(from);
                },
                error => {
                    console.log("This is error: " + error)
                }
            );
    }

    submitRegistration(event) {
        event.preventDefault();
        if(this.state.registerPassConf !== this.state.registerPassword)
        {
            alert("Password and confirmation password are not the same")
        }
        authService.register(this.state.registerUsername,
                            this.state.registerPassword,
                            this.state.registerPassConf)
            .then(
                user => {
                    const { from } = this.props.location.state || { from: { pathname: "/" } };
                    this.props.history.push(from);
                },
                error => {
                    console.log("This is error: " + error)
                }
            );
    }

    loginChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        })
    }
    
    login() {
        return (
            <Card className={"border border-dark bg-dark text-white loginCard"}>
                <Card.Header><h3>Login</h3></Card.Header>
                <Form onSubmit={this.submitLogin} id="loginFormId" >
                    <Card.Body>
                    <Form.Row className={"loginRow"}>
                        <Form.Group as={Col} controlId="loginUsername">
                            <Form.Control required
                                name="loginUsername"
                                value={this.state.username}
                                onChange={this.loginChange}
                                placeholder="Username" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row className={"loginRow"}>
                        <Form.Group as={Col} controlId="loginPassword">
                            <Form.Control required
                                name="loginPassword"
                                type="password"
                                value={this.state.password}
                                onChange={this.loginChange}
                                placeholder="Password" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row className={"loginRow"}>
                        <Form.Group as={Col} controlId="loginSubmit">
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

    register() {
        return( 
            <Card className={"border border-dark bg-dark text-white loginCard"}>
                <Card.Header><h3>Register</h3></Card.Header>
                <Form onSubmit={this.submitRegistration} id="registerFormId">
                    <Card.Body>
                    <Form.Row className={"loginRow"}>
                        <Form.Group as={Col} controlId="registerUsername">
                            <Form.Control required
                                name="registerUsername"
                                value={this.state.username}
                                onChange={this.loginChange}
                                placeholder="Username" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row className={"loginRow"}>
                        <Form.Group as={Col} controlId="registerPassword">
                            <Form.Control required
                                name="registerPassword"
                                type="password"
                                value={this.state.password}
                                onChange={this.loginChange}
                                placeholder="Password" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row className={"loginRow"}>
                        <Form.Group as={Col} controlId="registerConf">
                            <Form.Control required
                                name="registerPassConf"
                                type="password"
                                value={this.state.password}
                                onChange={this.loginChange}
                                placeholder="Confirm Password" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row className={"loginRow"}>
                        <Form.Group  as={Col} controlId="registerSubmit">
                            <Button type="submit" variant="success">
                                Register
                            </Button>
                        </Form.Group>
                    </Form.Row>
                    </Card.Body>
                </Form>
            </Card>
        );
    }
    render() {
        return (
            [
            this.login(),
            this.register()
            ]
            );
    }
}