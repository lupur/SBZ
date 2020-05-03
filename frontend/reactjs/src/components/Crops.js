import React, {Component} from 'react';
import {Card, Table, Button, Form} from 'react-bootstrap'
import {cropService} from '../services/cropService'
import {Link} from 'react-router-dom'

export default class Crops extends Component {

    constructor(props) {
        super(props);

        this.state = {
            crops: null,
            newCrop: ''
        };

        this.addNewCrop = this.addNewCrop.bind(this);
        this.removeCrop = this.removeCrop.bind(this);
        this.newCropChange = this.newCropChange.bind(this);
    }

    componentDidMount() {
        cropService.getAll().then(response =>{
            this.setState({crops : response })
        });
    }

    addNewCrop(event)
    {
        event.preventDefault();
        cropService.add(this.state.newCrop)
            .then( response => {
                this.componentDidMount();
                this.setState({newCrop: ""});
            })
            .catch( error => {
                alert(error);
                this.setState({newCrop: ""});
                this.componentDidMount();
            });
    }

    removeCrop(event) {
        cropService.remove(event.target.id)
        .then( response => {
            this.componentDidMount()
        })
    }

    newCropChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
                <Card.Header><h3>Crops</h3></Card.Header>
                <Card.Body>
                <Form onSubmit={this.addNewCrop}>
                    <Table bordered>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.crops && this.state.crops.map((crop, i) =>
                        <tr key={i}>
                            <td>{crop.id}</td>
                            <td><Link className="tableLink" to={'/crops/'+ crop.id}>{crop.name}</Link></td>
                            <td><Button id={crop.id} onClick={this.removeCrop} variant="danger">Remove</Button></td>
                        </tr>
                        
                    )}
                        <tr>
                            <td></td>
                            <td>
                            <Form.Group controlId="newCropForm">
                                <Form.Control required 
                                    name="newCrop"
                                    placeholder="Name"
                                    value={this.state.newCrop}
                                    onChange={this.newCropChange}/>
                            </Form.Group>
                                   </td>
                            <td>
                                <Button variant="success" type="submit">
                                    Add
                                </Button>
                                
                            </td>
                        </tr>
                    </tbody>
                    </Table>
                    </Form>
                </Card.Body>
            </Card>
            );
    }
}