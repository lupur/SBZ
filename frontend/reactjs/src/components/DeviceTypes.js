import React, {Component} from 'react';
import {Card, Table, Button, Form } from 'react-bootstrap'
import {typeService} from '../services/deviceTypesService'

export default class Crop extends Component {
    constructor(props) {
        super(props);

        this.state = {
            checkValue: "",
        };
        this.updateType = this.updateType.bind(this);
        this.checkType = this.checkType.bind(this);
        this.checkChange = this.checkChange.bind(this);

    }

    updateType(event) {
        if(event.target.checked) {
            typeService.addType(event.target.name)
            .then( response => {
                console.log("Got 200 ok");
            })
            .catch( error => {
                console.log("Got error")
            })
        }
        else {
            typeService.removeType(event.target.name)
            .then( response => {
                console.log("Got 200 ok");
            })
            .catch( error => {
                console.log("Got error")
            })
        }
    }

    checkType(event) {
        typeService.getType(this.state.checkValue)
            .then( response => {
                if( response)
                    alert("Device of this type is supported.")
                else
                    alert("Device of unknown type.")
            })
            .catch( error => {
                
            })
    }
    checkChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
                <Table bordered>
                    <thead>
                        <tr>
                            <th>Device Type</th>
                            <th>Is Supported</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Pump</td>
                            <td>
                                <input 
                                type="checkbox"
                                name="PUMP"
                                onClick={this.updateType}/>
                            </td>
                        </tr>
                        <tr>
                            <td>Valve</td>
                            <td>
                                <input 
                                type="checkbox"
                                name="VALVE"
                                onClick={this.updateType}/>
                            </td>
                        </tr>
                        <tr>
                            <td>Rain</td>
                            <td><input 
                                type="checkbox"
                                name="RAIN"
                                onClick={this.updateType}/>
                            </td>
                        </tr>
                        <tr>
                            <td>Moisture</td>
                            <td><input 
                                type="checkbox"
                                name="MOISTURE"
                                onClick={this.updateType}/>
                            </td>
                        </tr>

                        <tr>
                        <td>
                            <Form.Group controlId="checkTypeId">
                                <Form.Control required 
                                    name="checkValue"
                                    placeholder="Device for testing"
                                    value={this.state.checkValue}
                                    onChange={this.checkChange}
                                    />
                            </Form.Group>
                        </td>
                        <td>
                            <Button variant="success" 
                                    type="submit"
                                    onClick={this.checkType}>
                                        Check
                            </Button>
                        </td>
                        </tr>
                    </tbody>
                </Table>
            </Card>
        );
    }
}