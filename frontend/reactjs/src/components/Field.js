import React, {Component} from 'react';
import {Card } from 'react-bootstrap'

export default class Fields extends Component {

    constructor(props) {
        super(props);
        this.state = {
            fieldId: this.props.match.params.id
        }
    }
    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
            <Card.Header><h3>Field {this.state.fieldId}</h3></Card.Header>
            </Card>
        )
    }
}