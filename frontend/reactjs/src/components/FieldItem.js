import React, { Component } from 'react';
import { Button, Form, Container, Col, Row, Card } from 'react-bootstrap'
import {authService} from '../services/authService'
import {fieldItemService} from '../services/fieldItemService'

export default class FieldItem extends Component {

    constructor(props) {
        super(props);
        this.selectedItem = props.selectedItem;
        this.refreshTree = props.refreshTree;
        this.parent = props.parent;

        this.state = {
            type : this.selectedItem.type,
            newArrayName: '',
            newPumpSerialNumber: ''
        };
        this.newFieldChange = this.newFieldChange.bind(this);
        this.addNewArray = this.addNewArray.bind(this);
        this.editArray = this.editArray.bind(this);
        this.deleteArray = this.deleteArray.bind(this);
        this.addNewSet = this.addNewSet.bind(this);
        this.editSet = this.editSet.bind(this);
        this.deleteSet = this.deleteSet.bind(this);
    }

    // Add array with pump and rain
    addNewArray(event) {
        if(this.state.newPumpEUI === this.state.newRainEUI) {
            alert("EUIs must be different!");
            return;
        }
        event.preventDefault();
        const this_ = this;
        const fieldId = Number(this.selectedItem.ID.split("field_")[1]);
        fieldItemService.addNewArray(fieldId, this.state.newPumpEUI, this.state.newRainEUI)
        .then(response => {
            // this_.refreshTree(fieldId);
            this.parent.componentDidMount(fieldId);
            this.setState({
                newPumpEUI: '',
                newRainEUI: ''
            })
        }).catch(error => {
            console.log(error);
        })

    }


    // Edit array (pump/rain)
    editArray(event) {
        if(this.state.newPumpEUI === this.state.newRainEUI) {
            alert("EUIs must be different!");
            return;
        }
        event.preventDefault();
        const arrayId = Number(this.selectedItem.ID.split("array_")[1]);
        fieldItemService.editArray(this.selectedItem.fieldId, arrayId, this.state.newPumpEUI, this.state.newRainEUI)
        .then(response => {
            // this_.refreshTree(fieldId);
            this.parent.componentDidMount(this.selectedItem.fieldId);
            this.setState({
                newPumpEUI: '',
                newRainEUI: ''
            })
        }).catch(error => {
            console.log(error);
        })

    }

    // Delete array
    deleteArray(event) {
        const arrayId = Number(this.selectedItem.ID.split("array_")[1]);
        fieldItemService.deleteArray(this.selectedItem.fieldId, arrayId)
        .then(response => {
            this.parent.componentDidMount(this.selectedItem.fieldId);
        }).catch(error => {
            console.log(error);
        })

    }

    // Add set with valve and moisture
    addNewSet(event) {
        if(this.state.newMoistureEUI === this.state.newValveEUI) {
            alert("EUIs must be different!");
            return;
        }
        event.preventDefault();
        const arrayId = Number(this.selectedItem.ID.split("array_")[1]);
        const fieldId = this.parent.fieldId;

        fieldItemService.addNewSet(fieldId, arrayId, this.state.newMoistureEUI, this.state.newValveEUI)
        .then(response => {
            // this_.refreshTree(fieldId);
            this.parent.componentDidMount(fieldId);
            this.setState({
                newMoistureEUI: '',
                newValveEUI: ''
            })
        }).catch(error => {
            console.log(error);
        })

    }

    // Edit set (valve/moisture)
    editSet(event) {
        if(this.state.newMoistureEUI === this.state.newValveEUI) {
            alert("EUIs must be different!");
            return;
        }
        event.preventDefault();
        const setPosition = Number(this.selectedItem.ID.split("set_")[1]);
        const arrayId = Number(this.selectedItem.categoryId.split("array_")[1]);
        const fieldId = this.parent.fieldId;
        fieldItemService.editSet(fieldId, arrayId, setPosition, this.state.newMoistureEUI, this.state.newValveEUI)
        .then(response => {
            // this_.refreshTree(fieldId);
            this.parent.componentDidMount(this.selectedItem.fieldId);
            this.setState({
                newMoistureEUI: '',
                newValveEUI: ''
            })
        }).catch(error => {
            console.log(error);
        })

    }

    // Delete set
    deleteSet(event) {
        const setPosition = Number(this.selectedItem.ID.split("set_")[1]);
        const arrayId = Number(this.selectedItem.categoryId.split("array_")[1]);
        const fieldId = this.parent.fieldId;
        fieldItemService.deleteSet(fieldId, arrayId, setPosition)
        .then(response => {
            this.parent.componentDidMount(this.selectedItem.fieldId);
        }).catch(error => {
            console.log(error);
        })

    }

    newFieldChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    render() {
        return (
            <div className={"bg-dark text-white text-left"}>
                <Card className={"bg-dark text-white text-left"}>
                    <Card.Header>Header
                        {this.selectedItem.type === 'array' ? 
                        <Button onClick={this.deleteArray} variant="danger" className="float-right">
                            Delete
                        </Button> : null }
                        {this.selectedItem.type === 'set' ? <Button onClick={this.deleteSet} variant="danger" type="submit" className="float-right">
                            Delete
                        </Button> : null }
                    </Card.Header>
                    <Card.Body>
                        {(() => {
                            if (this.selectedItem.type === 'field')
                                return (
                                    <div>
                                        <Container>
                                            <Form onSubmit={this.addNewArray}>
                                                <Card.Title className="text-center">Add new array</Card.Title>
                                                <Row></Row>
                                                <Row>
                                                    <Col>Pump EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newPumpEUI">
                                                            <Form.Control required
                                                                name="newPumpEUI"
                                                                placeholder="Pump EUI"
                                                                value={this.state.newPumpEUI}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col>Rain EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newRainEUI">
                                                            <Form.Control required
                                                                name="newRainEUI"
                                                                placeholder="Rain EUI"
                                                                value={this.state.newRainEUI}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col xs lg="2">
                                                        <Button variant="success" type="submit">
                                                            Add
                                                        </Button>
                                                    </Col>
                                                </Row>
                                            </Form>
                                        </Container>
                                    </div>)
                            else if (this.selectedItem.type === 'array')
                                return (
                                    <div>
                                        <Container>
                                            <Card.Title className="text-center">Edit array</Card.Title>
                                            <Form onSubmit={this.editArray}>
                                                <Row>
                                                    <Col>Pump EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newPumpEUI">
                                                            <Form.Control required
                                                                name="newPumpEUI"
                                                                placeholder="Pump EUI"
                                                                value={this.state.newPumpEUI}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col>Rain EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newRainEUI">
                                                            <Form.Control required
                                                                name="newRainEUI"
                                                                placeholder="Rain EUI"
                                                                value={this.state.newRainEUI}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col xs lg="2">
                                                        <Button variant="success" type="submit">
                                                            Edit
                                                        </Button>
                                                    </Col>
                                                </Row>
                                            </Form>
                                        </Container>
                                        <Container>
                                            <Form onSubmit={this.addNewSet}>
                                                <Card.Title className="text-center">Add new set</Card.Title>
                                                <Row></Row>
                                                <Row>
                                                    <Col>Moisture EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newMoistureEUI">
                                                            <Form.Control required
                                                                name="newMoistureEUI"
                                                                placeholder="Moisture EUI"
                                                                value={this.state.newMoistureEUI}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col>Valve EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newValveEUI">
                                                            <Form.Control required
                                                                name="newValveEUI"
                                                                placeholder="Valve EUI"
                                                                value={this.state.newValveEUI}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col xs lg="2">
                                                        <Button variant="success" type="submit">
                                                            Add
                                                        </Button>
                                                    </Col>
                                                </Row>
                                            </Form>
                                        </Container>
                                    </div>)
                            else if (this.selectedItem.type === 'set')
                                return (
                                    <div>
                                        <Container>
                                            <Form onSubmit={this.editSet}>
                                                <Card.Title className="text-center">Edit set</Card.Title>
                                                <Row></Row>
                                                <Row>
                                                    <Col>Moisture EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newMoistureEUI">
                                                            <Form.Control required
                                                                name="newMoistureEUI"
                                                                placeholder="Moisture EUI"
                                                                value={this.state.newMoistureEUI}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col>Valve EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newValveEUI">
                                                            <Form.Control required
                                                                name="newValveEUI"
                                                                placeholder="Valve EUI"
                                                                value={this.state.newValveEUI}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col xs lg="2">
                                                        <Button variant="success" type="submit">
                                                            Edit
                                                        </Button>
                                                    </Col>
                                                </Row>
                                            </Form>
                                        </Container>
                                    </div>)
                            // else if (this.selectedItem.type === 'pump')
                            //     return (
                            //         <div>
                            //             <Container>
                            //                 <Form onSubmit={this.addNewField}>
                            //                     <Card.Title className="text-center">Edit pump</Card.Title>
                            //                     <Row></Row>
                            //                     <Row>
                            //                         <Col>Pump EUI:</Col>
                            //                         <Col>
                            //                             <Form.Group controlId="newArrayName">
                            //                                 <Form.Control required
                            //                                     name="newArrayName"
                            //                                     placeholder="Name"
                            //                                     value={this.state.newArrayName}
                            //                                     onChange={this.newFieldChange} />
                            //                             </Form.Group>
                            //                         </Col>
                            //                         <Col xs lg="2">
                            //                             <Button variant="success" type="submit">
                            //                                 Edit
                            //                         </Button>
                            //                         </Col>
                            //                     </Row>
                            //                 </Form>
                            //             </Container>
                            //         </div>)
                            // else if (this.selectedItem.type === 'moisture')
                            //     return (
                            //         <div>
                            //             <Container>
                            //                 <Form onSubmit={this.addNewField}>
                            //                     <Card.Title className="text-center">Edit moisture</Card.Title>
                            //                     <Row></Row>
                            //                     <Row>
                            //                         <Col>Moisture EUI:</Col>
                            //                         <Col>
                            //                             <Form.Group controlId="newArrayName">
                            //                                 <Form.Control required
                            //                                     name="newArrayName"
                            //                                     placeholder="Name"
                            //                                     value={this.state.newArrayName}
                            //                                     onChange={this.newFieldChange} />
                            //                             </Form.Group>
                            //                         </Col>
                            //                         <Col xs lg="2">
                            //                             <Button variant="success" type="submit">
                            //                                 Edit
                            //                         </Button>
                            //                         </Col>
                            //                     </Row>
                            //                 </Form>
                            //             </Container>
                            //         </div>)
                            // else if (this.selectedItem.type === 'valve')
                            //     return (
                            //         <div>
                            //             <Container>
                            //                 <Form onSubmit={this.addNewField}>
                            //                     <Card.Title className="text-center">Edit valve</Card.Title>
                            //                     <Row></Row>
                            //                     <Row>
                            //                         <Col>Valve EUI:</Col>
                            //                         <Col>
                            //                             <Form.Group controlId="newArrayName">
                            //                                 <Form.Control required
                            //                                     name="newArrayName"
                            //                                     placeholder="Name"
                            //                                     value={this.state.newArrayName}
                            //                                     onChange={this.newFieldChange} />
                            //                             </Form.Group>
                            //                         </Col>
                            //                         <Col xs lg="2">
                            //                             <Button variant="success" type="submit">
                            //                                 Edit
                            //                         </Button>
                            //                         </Col>
                            //                     </Row>
                            //                 </Form>
                            //             </Container>
                            //         </div>)
                        })()}
                    </Card.Body>
                </Card>
            </div>
        );
    }
}