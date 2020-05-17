import React, { Component } from 'react';
import { Button, Form, Container, Col, Row, Card } from 'react-bootstrap'

export default class FieldItem extends Component {

    constructor(props) {
        super(props);
        this.selectedItem = props.selectedItem;
        this.addNewProduct = props.addNewProduct;
        this.item = {
            ID: Math.random().toString(36).substring(7),
            categoryId: this.selectedItem.ID,
            name: 'Savica',
            type: 'moisture',
            icon: '../images/' + 'moisture' + '.png'
        };
        this.state = {
            newArrayName: '',
            newPumpSerialNumber: ''
        };
        this.newFieldChange = this.newFieldChange.bind(this);
    }

    // FIELD SELECTED
    // add an array with a pump

    // ARRAY SELECTED
    // Edit array info
    // add set
    // remove array

    // PUMP SELECTED
    // edit

    // SET SELECTED
    // edit moisture and valve
    // remove set

    addNewDevice(deviceType) {
        const id = Math.random().toString(36).substring(7);
        return {
            ID: id,
            categoryId: this.selectedItem.ID,
            name: 'Savica' + id,
            type: deviceType,
            icon: '../images/' + deviceType + '.png'
        };
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
                        {(this.selectedItem.type !== 'field' && this.selectedItem.type !== 'pump') && <Button onClick={this.deleteArray} variant="danger" type="submit" className="float-right">
                            Delete
                        </Button>}
                    </Card.Header>
                    <Card.Body>
                        {(() => {
                            if (this.selectedItem.type === 'field')
                                return (
                                    <div>
                                        <Container>
                                            <Form onSubmit={this.addNewField}>
                                                <Card.Title className="text-center">Add new array</Card.Title>
                                                <Row></Row>
                                                <Row>
                                                    <Col>Name:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newArrayName">
                                                            <Form.Control required
                                                                name="newArrayName"
                                                                placeholder="Name"
                                                                value={this.state.newArrayName}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col>Pump Serial No:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newPumpSerialNumber">
                                                            <Form.Control required
                                                                name="newPumpSerialNumber"
                                                                placeholder="Pump Serial No"
                                                                value={this.state.newPumpSerialNumber}
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
                                            <Form onSubmit={this.addNewField}>
                                                <Row>
                                                    <Col>Name:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newArrayName">
                                                            <Form.Control required
                                                                name="newArrayName"
                                                                placeholder="Name"
                                                                value={this.state.newArrayName}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col>Pump EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newPumpSerialNumber">
                                                            <Form.Control required
                                                                name="newPumpSerialNumber"
                                                                placeholder="Pump Serial No"
                                                                value={this.state.newPumpSerialNumber}
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
                                            <Form onSubmit={this.addNewField}>
                                                <Card.Title className="text-center">Add new set</Card.Title>
                                                <Row></Row>
                                                <Row>
                                                    <Col>Moisture EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newArrayName">
                                                            <Form.Control required
                                                                name="newArrayName"
                                                                placeholder="Name"
                                                                value={this.state.newArrayName}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col>Valve EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newPumpSerialNumber">
                                                            <Form.Control required
                                                                name="newPumpSerialNumber"
                                                                placeholder="Pump Serial No"
                                                                value={this.state.newPumpSerialNumber}
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
                                            <Form onSubmit={this.addNewField}>
                                                <Card.Title className="text-center">Edit set</Card.Title>
                                                <Row></Row>
                                                <Row>
                                                    <Col>Moisture EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newArrayName">
                                                            <Form.Control required
                                                                name="newArrayName"
                                                                placeholder="Name"
                                                                value={this.state.newArrayName}
                                                                onChange={this.newFieldChange} />
                                                        </Form.Group>
                                                    </Col>
                                                    <Col>Valve EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newPumpSerialNumber">
                                                            <Form.Control required
                                                                name="newPumpSerialNumber"
                                                                placeholder="Pump Serial No"
                                                                value={this.state.newPumpSerialNumber}
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
                            else if (this.selectedItem.type === 'pump')
                                return (
                                    <div>
                                        <Container>
                                            <Form onSubmit={this.addNewField}>
                                                <Card.Title className="text-center">Edit pump</Card.Title>
                                                <Row></Row>
                                                <Row>
                                                    <Col>Pump EUI:</Col>
                                                    <Col>
                                                        <Form.Group controlId="newArrayName">
                                                            <Form.Control required
                                                                name="newArrayName"
                                                                placeholder="Name"
                                                                value={this.state.newArrayName}
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
                        })()}
                    </Card.Body>
                </Card>
            </div>
        );
    }
}