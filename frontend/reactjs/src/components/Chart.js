import React from 'react';
import { VictoryAxis, VictoryLine, VictoryChart} from 'victory';
import {readingService} from '../services/readingService'

const data = [
    {x: new Date(1590590718318), y: 13000},
    {x: new Date(1590891718318), y: 16500},
    {x: new Date(1591592718318), y: 14250},
    {x: new Date(1591593718318), y: 19000}
  ];


class Chart extends React.Component {

  constructor(props) {
    super(props);

    this.deviceEUI = props.deviceEUI;
    this.readingType = props.readingType;

    this.print = this.print.bind(this);
    this.getReadings = this.getReadings.bind(this);
  }  

  async componentDidMount() {
    this.print();
    await this.getReadings();
  }

  async getReadings() {
    await readingService.getReadings(this.deviceEUI, this.readingType).then(response =>{
      console.log(response);
  });
  }

  print() {
    console.log(this.deviceEUI);
    console.log(this.readingType);
  }

  render() {
        return (
          <VictoryChart>
                  <VictoryLine data={data} style={{data: { stroke: "#c43a31" }}} />
          </VictoryChart>
        )
      }
}

export default Chart;


