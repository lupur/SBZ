import React from 'react';

class Array extends React.Component {

    constructor(props) {
        super(props);

        this.array = props.array;
        this.array.sets.sort(function (a, b) {
            return a.position - b.position;
          });
        this.parent = props.parent;

        this.selectDevice = this.selectDevice.bind(this);

    }

    selectDevice(deviceEUI, deviceType) {
        this.parent.updateChart(deviceEUI, deviceType);
    }

    render() {
        return (
            <div className={this.array.arrayInErrorState ? "border border-light m-3 pb-3 bg-danger" : "border border-light m-3 pb-3"}>
                <h5>Array {this.array.id}</h5> 
                <div className="font-weight-bold">Main Devices</div>
                <div className="btn-group" role="group" aria-label="Basic example">
                    <button type="button" onClick={() => this.selectDevice(this.array.pumpEUI, 'PUMP')} className={this.array.pumpInErrorState ? "btn btn-secondary btn btn-outline-light bg-danger" : "btn btn-secondary btn btn-outline-light"}>{this.array.pumpEUI}</button>
                    <button type="button" onClick={() => this.selectDevice(this.array.rainEUI, 'RAIN')} className="btn btn-secondary btn btn-outline-light">{this.array.rainEUI}</button>
                </div>
                <div className="font-weight-bold">Set Devices</div>
                {this.array.sets && this.array.sets.map((set, i) =>
                    <div className="row">
                    <div className="col-12 align-self-center">
                        <div className="btn-group" role="group" aria-label="Basic example">
                            <button type="button" onClick={() => this.selectDevice(set.moistureEUI, 'MOISTURE')} className={set.moistureInErrorState ? "btn btn-secondary btn btn-outline-light bg-danger" : "btn btn-secondary btn btn-outline-light"}>{set.moistureEUI}</button>
                            <button type="button" onClick={() => this.selectDevice(set.valveEUI, 'VALVE')} className={set.valveInErrorState ? "btn btn-secondary btn btn-outline-light bg-danger" : "btn btn-secondary btn btn-outline-light"}>{set.valveEUI}</button>
                        </div>
                    </div>
                </div>
                 )}  
            </div>
        )
      }
}

export default Array;