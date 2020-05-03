import React, {Component} from 'react';
import {fieldService} from '../services/fieldService'
import {userService} from '../services/userService'
import {cropService} from '../services/cropService'
import {Card, Table, Button, Form } from 'react-bootstrap'
import {authService} from '../services/authService'
import {Role} from '../helpers/role'
import DatePicker from 'react-date-picker'

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
            newCropId: '',
            newDate: new Date()
        }

        this.newFieldChange = this.newFieldChange.bind(this);
        this.removeField = this.removeField.bind(this);
        this.addNewField = this.addNewField.bind(this);
        this.dateFormatter = this.dateFormatter.bind(this);
        this.datePicked = this.datePicked.bind(this);
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
        if(event.target.name === "newCropId"
        || event.target.name === "newOwnerId") {
            var index = event.nativeEvent.target.selectedIndex;
            console.log(event.nativeEvent.target[index].value)
            this.setState({[event.target.name] : event.target.id})
        }
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    addNewField(event) {
        event.preventDefault();
        var newField = {
            name: this.state.newFieldName,
            area: this.state.newFieldArea,
            ownerId: this.state.newOwnerId,
            cropId: this.state.newCropId,
            seedingDate: this.state.newDate
        }

        console.log(newField);
    }

    removeField(event) {
        fieldService.remove(event.target.id)
            .then( response => {
                this.componentDidMount();
            })
    }

    dateFormatter(date) {
        var options = { year: 'numeric', month: 'short', day: 'numeric' };
        return new Intl.DateTimeFormat('en', options).format(new Date(date))
        // return new Intl.DateTimeFormat('en').format(date);
    }

    datePicked(date) {
        this.setState({newDate: date})
    }

    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
                <Card.Header><h3>Fields</h3></Card.Header>
                <Card.Body>
                <Form onSubmit={this.addNewField}>
                    <Table bordered>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Area [m2]</th>
                            <th>Crop</th>
                            <th>Seeding</th>
                        { this.state.isAdmin ? 
                            <th>Owner</th>
                        : null }
                        { this.state.isAdmin ? 
                            <th></th>
                        : null }
                        
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.fields && this.state.fields.map((field, i) =>
                        <tr key={i}>
                            <td>{field.id}</td>
                            <td>{field.name}</td>
                            <td>{field.area}</td>
                            <td>{field.cropName}</td>
                            <td>{this.dateFormatter(field.seedingDate)}</td>
                        {this.state.isAdmin ?
                            <td>{field.ownerName}</td>
                        : null }
                        {this.state.isAdmin ?
                            <td><Button id={field.id} onClick={this.removeField} variant="danger">
                                    Remove
                                </Button></td>
                        : null}
                        </tr>
                    )}
    {this.state.isAdmin ?
                        <tr>
                            <td></td>
                            <td>
                            <Form.Group controlId="newFieldName">
                                <Form.Control required
                                    name="newFieldName"
                                    placeholder="Name"
                                    value={this.state.newFieldName}
                                    onChange={this.newFieldChange}/>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newFieldArea">
                                <Form.Control required
                                    name="newFieldArea"
                                    placeholder="Area"
                                    value={this.state.newFieldArea}
                                    onChange={this.newFieldChange}/>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newCropId">
                                <Form.Control required as="select"
                                    name="newCropId"
                                    value={this.state.newCropId}
                                    onChange={this.newFieldChange}>
                                {this.state.crops && this.state.crops.map((crop, i) =>
                                    <option key={crop.id}>{crop.name}</option>
                                )}
                            </Form.Control>
                            </Form.Group>
                            </td>
                            <td>
                                <DatePicker style={{color: "white"}}
                                    value={this.state.newDate}
                                    onChange={this.datePicked}/>
                            </td>
                            <td>
                            <Form.Group controlId="newOwnerId">
                                <Form.Control required as="select"
                                    name="newOwnerId"
                                    value={this.state.newOwnerId}
                                    onChange={this.newFieldChange}>
                                {this.state.users && this.state.users.map((user, i) =>
                                    <option key={user.id} value={user.id}>{user.username}</option>
                                )}
                            </Form.Control>
                            </Form.Group>
                            </td>
                            <td>
                                <Button variant="success" type="submit">
                                    Add
                                </Button>
                            </td>
                            
                        </tr>
    : null}
                    </tbody>
                    </Table>
                </Form>
                </Card.Body>
                </Card>

            );
    }
}