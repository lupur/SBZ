import React, {Component} from 'react';
import {Card } from 'react-bootstrap';
import { TreeView } from 'devextreme-react';
import FieldItem from './FieldItem'

import '../css/Tree.css';

export default class Field extends Component {

    // State can be defined here as well 
    // state = {

    // }

    constructor(props) {
        super(props);
        this.state = {
            fieldId: this.props.match.params.id
        }
        this.products = [{
            ID: '1',
            name: 'Field',
            expanded: true,
            type: 'field',
            icon: '../images/land.png'
          }, {
            ID: '1_1',
            categoryId: '1',
            name: 'Array',
            type: 'array',
            expanded: true,
            icon: '../images/array.png'
          }, {
            ID: '1_1_1',
            categoryId: '1_1',
            name: 'Pump',
            type: 'pump',
            icon: '../images/pump.png'
          }, {
            ID: '1_1_2',
            categoryId: '1_1',
            name: 'Set',
            type: 'set',
            icon: '../images/set.png',
            expanded: true,
            price: 220
          }, {
            ID: '1_1_2_1',
            categoryId: '1_1_2',
            name: 'Moisture',
            type: 'moisture',
            icon: '../images/moisture.png',
            price: 270
          },
          {
            ID: '1_1_2_2',
            categoryId: '1_1_2',
            name: 'Valve',
            type: 'valve',
            icon: '../images/valve.png',
            price: 270
          }
          ];
          this.state = {
              products: this.products
            };
          this.selectItem = this.selectItem.bind(this);
          this.addNewProduct = this.addNewProduct.bind(this);
       
    }

    addNewProduct(item) {
        let products = this.state.products;
        if(item.type === 'array' || item.type === 'set') item.expanded = true;
        products.push(
            item
        );
        this.setState({products: null})
        this.setState({products})
    }

    render() {
        return (
            // <Card className={"border border-dark bg-dark text-white"}>
            // <Card.Header><h3>Field {this.state.fieldId}</h3></Card.Header>
            // </Card>
            <div>
                <div className={"float-left"}>
                    <TreeView className={"bg-dark text-white text-left"}
                    items={[...this.state.products]}
                    dataStructure="plain"
                    displayExpr="name"
                    parentIdExpr="categoryId"
                    keyExpr="ID"
                    width={300}
                    onItemClick={this.selectItem} />
                </div>
                <div className={"float-center"}>
                    {this.state.currentItem  && <FieldItem selectedItem = {{...this.state.currentItem}} addNewProduct = {this.addNewProduct}/>}
                </div>
            </div>

        )
    }

    selectItem(e) {
        if(e.itemData.type ==='moisture' || e.itemData.type ==='valve') return;
        this.setState({currentItem: ""});
        this.setState({
          currentItem: Object.assign({}, e.itemData)
        });
    }

}