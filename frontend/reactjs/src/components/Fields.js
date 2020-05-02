import React, {Component} from 'react';
import {fieldService} from '../services/fieldService'
import {userService} from '../services/userService'
import {cropService} from '../services/cropService'
import {Card, Table, Button, Form} from 'react-bootstrap'
import {authService} from '../services/authService'
import {Role} from '../helpers/role'

export default class Fields extends Component {

    constructor(props) {
        super(props);

        this.state = {
            fields: null,
            users: null,
            crops: null,

            currentUser: null,
            isAdmin: false,

            newFieldName: '',
            newFieldArea: '',
            newOwnerId: '',
            newCropId: ''
        }

        this.newFieldChange = this.newFieldChange.bind(this);

    }

    async componentDidMount() {
        await authService.currentUser.subscribe(x => {
            this.setState({
                currentUser: x,
                isAdmin: x && x.role === Role.ADMIN
            });
        });

        if(this.state.isAdmin)
        {
            await userService.getAll().then(response =>{
                this.setState({users : response })
            });

            await cropService.getAll().then(response =>{
                this.setState({crops : response })
            });
        }
        await fieldService.getAll().then(response =>{
            this.setState({fields : response })
        });

        console.log(this.state.users);
    }

    newFieldChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
                <Card.Body>
                <Card.Header><h3>Fields</h3></Card.Header>
                    <Table bordered>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Area [m2]</th>
                        { this.state.isAdmin ? 
                            <th>Owner</th> 
                        : null }
                            <th>Crop</th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.fields && this.state.fields.map((field, i) =>
                        <tr key={i}>
                            <td>{field.id}</td>
                            <td>{field.name}</td>
                            <td>{field.area}</td>
                        {this.state.isAdmin ? 
                            <td>{field.ownerName}</td>
                        : null }
                            <td>{field.cropName}</td>
                        </tr>
                    )}
    {this.state.isAdmin ?
                        <tr>
                            <td></td>
                            <td>
                            <Form.Group controlId="newFieldName">
                                <Form.Control required
                                    name="newFieldName"
                                    value={this.state.newFieldName}
                                    onChange={this.newFieldChange}/>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newFieldArea">
                                <Form.Control required
                                    name="newFieldArea"
                                    value={this.state.newFieldArea}
                                    onChange={this.newFieldChange}/>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newOwnerId">
                                <Form.Control required as="select"
                                    name="newOwnerId"
                                    value={this.state.newOwnerId}
                                    onChange={this.newFieldChange}>
                                {this.state.users && this.state.users.map((user, i) =>
                                    <option key={user.id}>{user.username}</option>
                                )}
                            </Form.Control>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newCropId">
                                <Form.Control required as="select"
                                    name="newOwnerId"
                                    value={this.state.newOwnerId}
                                    onChange={this.newFieldChange}>
                                {this.state.crops && this.state.crops.map((crop, i) =>
                                    <option key={crop.id}>{crop.name}</option>
                                )}
                            </Form.Control>
                            </Form.Group>
                            </td>
                        </tr>
    : null}
                    </tbody>
                    </Table>
                </Card.Body>
                </Card>

            );
    }
}