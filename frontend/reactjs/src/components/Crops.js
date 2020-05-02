import React, {Component} from 'react';
import {Card, Table} from 'react-bootstrap'

export default class Crops extends Component {

    constructor(props) {
        super(props);
        this.state = {id:'', name:''};
    }

    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
                <Card.Header>Crops</Card.Header>
                <Card.Body>
                    <Table>

                    </Table>
                </Card.Body>
            </Card>
            );
    }
}