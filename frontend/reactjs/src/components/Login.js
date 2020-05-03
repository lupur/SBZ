import React, {Component} from 'react';
import {Card, Form, Button, Col, Table} from 'react-bootstrap'
import {authService} from '../services/authService'

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {username:'', password:''};
        this.submitLogin = this.submitLogin.bind(this);
        this.loginChange = this.loginChange.bind(this);

        if (authService.currentUserValue) { 
            this.props.history.push('/');
        }
    }

    submitLogin(event) {
        event.preventDefault();
        authService.login(this.state.username, this.state.password)
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
    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
                <Card.Header><h3>Login</h3></Card.Header>
                <Form onSubmit={this.submitLogin} id="loginFormId" >
                    <Card.Body>
                    <Table>
                    <Form.Row style={{padding:10}}>
                        <Form.Group as={Col} controlId="formGridUsername">
                            <Form.Control required
                                name="username"
                                value={this.state.username}
                                onChange={this.loginChange}
                                placeholder="Username" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row style={{padding:10}}>
                        <Form.Group as={Col} controlId="formGridPassword">
                            <Form.Control required
                                name="password"
                                type="password"
                                value={this.state.password}
                                onChange={this.loginChange}
                                placeholder="Password" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row style={{padding:10}}>
                        <Form.Group  as={Col} controlId="formGridSubmit">
                            <Button variant="primary" type="submit">
                                Login
                            </Button>
                        </Form.Group>
                    </Form.Row>
                    </Table>
                    </Card.Body>
                </Form>
                
            </Card>
            );
    }
}