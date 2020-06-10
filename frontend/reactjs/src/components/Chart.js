import React from 'react';
import { VictoryAxis, VictoryLine, VictoryChart} from 'victory';
import {readingService} from '../services/readingService'

class Chart extends React.Component {

  constructor(props) {
    super(props);

    this.deviceEUI = props.deviceEUI;
    this.readingType = props.readingType;

    this.getReadings = this.getReadings.bind(this);
  }  

  async componentDidMount() {
    await this.getReadings();
  }

  async getReadings() {
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
      setInterval(this.getReadings, 2000);
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


