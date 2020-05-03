import React, {Component} from 'react';
import {cropService} from '../services/cropService'
import {Card, Table, Button, Form } from 'react-bootstrap'
import {authService} from '../services/authService'
import {Role} from '../helpers/role'

export default class Crop extends Component {

    constructor(props) {
        super(props);

        this.state = {
            cropId: this.props.match.params.id,
            crop: null,

            currentUser: null,
            isAdmin: false,

            newGrowthName: '',
            newStartDay: '',
            newEndDay: '',
            newMoistureMin: '',
            newMoistureMax: ''
        }
        this.addGrowth = this.addGrowth.bind(this)
        this.newGrowthChange = this.newGrowthChange.bind(this);
        this.removePhase = this.removePhase.bind(this);
    }

    async componentDidMount() {
        
        await authService.currentUser.subscribe(x => {
            this.setState({
                currentUser: x,
                isAdmin: x && x.role === Role.ADMIN
            });
        });

        await cropService.getById(this.state.cropId).then(response =>{
            this.setState({crop : response })
        });
    }

    async addGrowth() {
        var newPhase = {
            name : this.state.newGrowthName,
            cropId: this.state.cropId,
            startDay: this.state.newStartDay,
            endDay: this.state.newEndDay,
            max: this.state.newMoistureMax,
            min: this.state.newMoistureMin
        }

        await cropService.addPhase(newPhase)
        .then( response => {
            this.setState({newGrowthName: ""});
            this.setState({newStartDay: ""});
            this.setState({newEndDay: ""});
            this.setState({newMoistureMax: ""});
            this.setState({newMoistureMin: ""});
            this.componentDidMount();
        })
        .catch( error => {
            alert(error);
            this.setState({newGrowthName: ""});
            this.setState({newStartDay: ""});
            this.setState({newEndDay: ""});
            this.setState({newMoistureMax: ""});
            this.setState({newMoistureMin: ""});
            this.componentDidMount();
        });
    }

    newGrowthChange(event) {
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    removePhase(event) {
        cropService.removePhase(event.target.id)
            .then( response => {
                this.componentDidMount();
            })
    }

    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
            <Card.Header><h3>{this.state.crop ? this.state.crop.name : "Crop not found"}</h3></Card.Header>
            <Card.Body>
                <Form onSubmit={this.addGrowth}>
                    <Table bordered>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Start day</th>
                            <th>End day</th>
                            <th>Moisture min</th>
                            <th>Moisture max</th>
                            {this.state.isAdmin ? <th></th> : null}
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.crop 
                        && this.state.crop.growthPhases
                            .sort((a, b) => a.id - b.id)
                            .map((phase, i) =>
                        <tr key={i}>
                            <td>{phase.name}</td>
                            <td>{phase.phaseStartDay}</td>
                            <td>{phase.phaseEndDay}</td>
                            <td>{phase.moistureLowerThreshold}</td>
                            <td>{phase.moistureUpperThreshold}</td>
                        {this.state.isAdmin ?
                            <td><Button id={phase.id} variant="danger" onClick={this.removePhase}>Remove</Button></td>
                        : null }
                        </tr>
                        )}

                    {this.state.isAdmin ?
                        <tr>
                            <td>
                            <Form.Group controlId="newGrowthName">
                                <Form.Control required
                                    name="newGrowthName"
                                    placeholder="Name"
                                    value={this.state.newGrowthName}
                                    onChange={this.newGrowthChange}/>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newStartDay">
                                <Form.Control required
                                    name="newStartDay"
                                    placeholder="Start"
                                    value={this.state.newStartDay}
                                    type="number"
                                    min="0"
                                    onChange={this.newGrowthChange}/>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newEndDay">
                                <Form.Control required
                                    name="newEndDay"
                                    placeholder="End"
                                    value={this.state.newEndDay}
                                    type="number"
                                    min="0"
                                    onChange={this.newGrowthChange}/>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newMoistureMin">
                                <Form.Control required
                                    name="newMoistureMin"
                                    placeholder="Min"
                                    value={this.state.newMoistureMin}
                                    type="number"
                                    min="0"
                                    max="100"
                                    onChange={this.newGrowthChange}/>
                            </Form.Group>
                            </td>
                            <td>
                            <Form.Group controlId="newMoistureMax">
                                <Form.Control required
                                    name="newMoistureMax"
                                    placeholder="Max"
                                    value={this.state.newMoistureMax}
                                    type="number"
                                    min="0"
                                    max="100"
                                    onChange={this.newGrowthChange}/>
                            </Form.Group>
                            </td>
                            <td>
                                <Button variant="success" type="submit">
                                    Add
                                </Button>
                            </td>
                        </tr>
                    : null }
                    </tbody>
                    </Table>
                </Form>
            </Card.Body>
            </Card>
        )
    }
}