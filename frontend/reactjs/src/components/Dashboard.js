import React from 'react';

import Chart from './Chart'
import Array from './Array'
import {Form } from 'react-bootstrap'
import {fieldItemService} from '../services/fieldItemService'
import {fieldService} from '../services/fieldService'

class Dashboard extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            options: [
                'one', 'two', 'three'
            ],
            defaultOption: 'two',
            selectedField: ''
        };
        this.getFieldDeatils = this.getFieldDeatils.bind(this);
        this.getArrays = this.getArrays.bind(this);
        this.updateChart = this.updateChart.bind(this);

    }
    async componentDidMount() {
        await fieldService.getAll().then(response =>{
            this.setState({fields : response })
            this.getArrays(response[0].id)
        });
    }

    getFieldDeatils(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
        this.getArrays(event.target.value);
    }

    getArrays(fieldId) {
        fieldItemService.getFieldItems(fieldId)
        .then(response => {
            this.setState({arrays: response.arrays });
            console.log(this.state.arrays);
        }).catch(error => {
            console.log(error);
        })
    }

    updateChart(deviceEUI, deviceType) {
        if(deviceType === 'MOISTURE') {
            this.setState({selectedMoisture: deviceEUI});
        } else if(deviceType === 'VALVE') {
            this.setState({selectedValve: deviceEUI});
        } else if(deviceType === 'PUMP') {
            this.setState({selectedPump: deviceEUI});
        } else if(deviceType === 'RAIN') {
            this.setState({selectedRain: deviceEUI});
        }
    }

    render() {
        return (
            <div className="bg-dark text-white">
                <h1>Dashboard</h1>
                <div className="row d-flex justify-content-center">
                    <Form.Group controlId="selectedField" className="col-4 d-flex justify-content-center">
                        <Form.Control required as="select"
                            name="selectedField"
                            value={this.state.selectedField}
                            onChange={this.getFieldDeatils}>
                            {this.state.fields && this.state.fields.map((field, i) =>
                            <option key={field.id} value={field.id}>{field.name}</option>
                            )}
                        </Form.Control>
                    </Form.Group>
                </div>
                <div className="row">
                    <div className="col-lg-6">
                        <div className="row">
                            {this.state.arrays && this.state.arrays.map((array, i) =>
                                <div className="col-lg-3">
                                    <Array array = {array} parent = {this}></Array>
                                </div>
                            )}                       
                        </div>

                    </div>
                    
                    <div className="col-lg-6">
                        <div className="row">
                            <div className="col-lg-6">
                                Pump status
                                {this.state.selectedPump ? <Chart deviceEUI ={this.state.selectedPump} readingType='STATE'></Chart> : null}
                            </div>
                            <div className="col-lg-6">
                                Rain status
                                {this.state.selectedRain ? <Chart deviceEUI ={this.state.selectedRain} readingType='RAIN'></Chart> : null}
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-lg-6">
                                Moisture values
                                {this.state.selectedMoisture ? <Chart deviceEUI ={this.state.selectedMoisture} readingType='MOISTURE'></Chart> : null}
                            </div>
                            <div className="col-lg-6">
                                Valve status
                                {this.state.selectedValve ? <Chart deviceEUI ={this.state.selectedValve} readingType='STATE'></Chart> : null}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
	}
}

export default Dashboard;