import React, {Component} from 'react';
import {Card, Table} from 'react-bootstrap'
import { Redirect } from "react-router-dom"

export default class Crops extends Component {

    constructor(props) {
        super(props);
        this.state = {id:'', name:''};
    }

    render() {
        if (!localStorage.getItem('token')) {
            return <Redirect to={'/login'} />;
        }
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