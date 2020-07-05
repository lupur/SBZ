import React from 'react';

import Chart from './Chart'
import Array from './Array'
import { Form } from 'react-bootstrap'
import { fieldItemService } from '../services/fieldItemService'
import { fieldService } from '../services/fieldService'

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
        this.selectedFieldId = null;
        this.getFieldDeatils = this.getFieldDeatils.bind(this);
        this.getArrays = this.getArrays.bind(this);
        this.updateChart = this.updateChart.bind(this);
        this.isolateErrors = this.isolateErrors.bind(this);
        this.pumpChart = React.createRef();
        this.rainChart = React.createRef();
        this.moistureChart = React.createRef();
        this.valveChart = React.createRef();
    }
    async componentDidMount() {
        await fieldService.getAll().then(response => {
            if (response !== undefined && response.length != 0) {
                this.setState({ fields: response })
                this.selectedFieldId =  response[0].id;
                this.getArrays();
                setInterval(this.getArrays, 2000);
            }
        });
    }

    getFieldDeatils(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
        this.selectedFieldId = event.target.value
        console.log(event.target.text);
    }

    getArrays() {
        fieldItemService.getFieldItems(this.selectedFieldId)
            .then(response => {
                response.arrays.sort(function (a, b) {
                    return a.id - b.id;
                  });
                this.setState({ arrays: [] });
                this.setState({ arrays: response.arrays });
                this.setState({fieldError: response.inErrorState});
                this.isolateErrors(response);
            }).catch(error => {
                console.log(error);
            })
    }

    isolateErrors(field) {
        // if(field.inErrorState) console.log("Field: " + field.name + " in error state!");
        for(let i=0; i<field.arrays.length; i++) {
            if(field.arrays[i].arrayInErrorState) console.log("Array: " + field.arrays[i].id + " in error state!");
            // if(field.arrays[i].pumpInErrorState) console.log("Pump: " + field.arrays[i].pumpEUI + " in error state!");
            // for(let j=0; j<field.arrays[i].sets.length; j++) {
            //     if(field.arrays[i].sets[j].moistureInErrorState) console.log("Moisture: " + field.arrays[i].sets[j].moistureEUI + " in error state!");
            //     if(field.arrays[i].sets[j].valveInErrorState) console.log("Valve: " + field.arrays[i].sets[j].valveEUI + " in error state!");
            // }
        }
    }

    updateChart(deviceEUI, deviceType) {
        if (deviceType === 'MOISTURE') {
            this.moistureChart.current.changeDeviceEUI(deviceEUI);
        } else if (deviceType === 'VALVE') {
            this.valveChart.current.changeDeviceEUI(deviceEUI);
        } else if (deviceType === 'PUMP') {
            console.log("Klikla pumpu");
            this.pumpChart.current.changeDeviceEUI(deviceEUI);
        } else if (deviceType === 'RAIN') {
            this.rainChart.current.changeDeviceEUI(deviceEUI);
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
                {this.state.fieldError ? 
                    <div className="row">
                        <div className="col-lg-12">
                            <h3 className="text-danger">FIELD IN ERROR STATE!</h3>
                            </div>
                        </div>
                        : null }
                <div className="row">
                    <div className="col-lg-6">
                        <div className="row">
                            {this.state.arrays && this.state.arrays.map((array, i) =>
                                <div className="col-lg-4">
                                    <Array array={array} parent={this}></Array>
                                </div>
                            )}
                        </div>

                    </div>

                    <div className="col-lg-6">
                        <div className="row">
                            <div className="col-lg-6">
                                Pump status
                                <Chart ref = {this.pumpChart} deviceEUI={this.state.selectedPump} readingType='STATE'></Chart>
                            </div>
                            <div className="col-lg-6">
                                Rain status
                                <Chart ref= {this.rainChart} readingType='RAIN'></Chart>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-lg-6">
                                Moisture values
                                <Chart ref = {this.moistureChart} readingType='MOISTURE'></Chart>
                            </div>
                            <div className="col-lg-6">
                                Valve status
                                <Chart ref={this.valveChart} readingType='STATE'></Chart>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Dashboard;