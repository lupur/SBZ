import React from 'react';
import { VictoryAxis, VictoryLine, VictoryChart} from 'victory';
import {readingService} from '../services/readingService'

class Chart extends React.Component {

  constructor(props) {
    super(props);

    // this.deviceEUI = props.deviceEUI;
    this.readingType = props.readingType;

    this.getReadings = this.getReadings.bind(this);
    this.changeDeviceEUI = this.changeDeviceEUI.bind(this);
    this.fetchingData = false;
  }  

  async componentDidMount() {
    // await this.getReadings();
  }

  changeDeviceEUI(deviceEUI) {
    this.deviceEUI = deviceEUI;
    this.getReadings();
    if(!this.fetchingData) {
      this.fetchingData = true;
      setInterval(this.getReadings, 5000);
    }
  }



  async getReadings() {
    console.log("Salje za: " + this.deviceEUI);
    await readingService.getReadings(this.deviceEUI, this.readingType).then(response =>{
      this.setState({readings: response});
      let data = [];
      this.setState({data: data});
      response.forEach(element => {
        let y = element.value;
        if(this.readingType === 'STATE' || this.readingType === 'RAIN') y = element.value === 'ON' ? '1' : '0';
        data.push({x: new Date(element.timestamp), y: parseFloat(y)})
      });
      this.setState({data: data});
  });
  }

  render() {
        return (
          <VictoryChart>
             {this.state && this.state.data ? <VictoryLine data={this.state.data} style={{data: { stroke: "#c43a31" }}} /> : null}
          </VictoryChart>
        )
      }
}

export default Chart;


