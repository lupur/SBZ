import React, {Component} from 'react';
import {Card } from 'react-bootstrap';
import { TreeView } from 'devextreme-react';
import FieldItem from './FieldItem'
import {authService} from '../services/authService'
import {fieldItemService} from '../services/fieldItemService'
import {Role} from '../helpers/role'

import '../css/Tree.css';

export default class Field extends Component {

    // State can be defined here as well 
    // state = {

    // }


    constructor(props) {
        super(props);
        this.fieldId = this.props.match.params.id;
        this.state = {
            fieldId: this.props.match.params.id,
            products: []
        }
        this.selectItem = this.selectItem.bind(this);
        this.addNewProduct = this.addNewProduct.bind(this);
       
    }

    async componentDidMount(fieldId) {
      fieldId = fieldId == null || fieldId == undefined ? this.fieldId : fieldId;
      await authService.currentUser.subscribe(x => {
          this.setState({
              currentUser: x,
              isAdmin: x && x.role === Role.ADMIN
          });
      });

      await fieldItemService.getFieldItems(fieldId).then(response =>{
        this.items = [];
        this.items.push({
          ID: 'field_' + fieldId,
          name: response.name,
          expanded: true,
          type: 'field',
          icon: '../images/land.png'
        });
        for(let i=0; i<response.arrays.length; i++) {
          this.items.push({
            ID: 'array_' + response.arrays[i].id,
            categoryId: 'field_' + fieldId,
            name: 'Array_' + response.arrays[i].id,
            expanded: true,
            type: 'array',
            icon: '../images/array.png'
          });
          this.items.push({
            ID: response.arrays[i].pumpEUI,
            categoryId: 'array_' + response.arrays[i].id,
            name: response.arrays[i].pumpEUI,
            expanded: false,
            type: 'pump',
            icon: '../images/pump.png'
          });
          this.items.push({
            ID: response.arrays[i].rainEUI,
            categoryId: 'array_' + response.arrays[i].id,
            name: response.arrays[i].rainEUI,
            expanded: false,
            type: 'pump',
            icon: '../images/rain.png'
          });
          for(let j=0; j<response.arrays[i].sets.length; j++) {
            this.items.push({
              ID: 'array_' + response.arrays[i].id + 'set_' + response.arrays[i].sets[j].position,
              categoryId: 'array_' + response.arrays[i].id,
              name: 'Set_' + response.arrays[i].sets[j].position,
              expanded: true,
              type: 'set',
              icon: '../images/set.png'
            });
            this.items.push({
              ID: response.arrays[i].sets[j].moistureEUI,
              categoryId: 'array_' + response.arrays[i].id + 'set_' + response.arrays[i].sets[j].position,
              name: response.arrays[i].sets[j].moistureEUI,
              expanded: false,
              type: 'moisture',
              icon: '../images/moisture.png'
            });
            this.items.push({
              ID: response.arrays[i].sets[j].valveEUI,
              categoryId: 'array_' + response.arrays[i].id + 'set_' + response.arrays[i].sets[j].position,
              name: response.arrays[i].sets[j].valveEUI,
              expanded: false,
              type: 'valve',
              icon: '../images/valve.png'
            });
          }
        }
        // this.setState({products: null})
        if(this.fieldId == undefined) {
          const products = this.items;
          this.setState({products: products})
          this.render();
        }
        else this.setState({products: this.items})
        console.log(this);
      })

      // if(this.state.isAdmin) console.log("ADMIN");
      // {
      //     await userService.getAll().then(response =>{
      //         this.setState({users : response });
      //         if(response.length > 0)
      //             this.setState({newOwnerId : response[0].id})
      //     });

      //     await cropService.getAll().then(response =>{
      //         this.setState({crops : response })
      //         if(response.length > 0)
      //             this.setState({newCropId : response[0].id})
      //     });
      // }
      // await fieldService.getAll().then(response =>{
      //     this.setState({fields : response })
      // });
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
                    {this.state.currentItem  && <FieldItem selectedItem = {{...this.state.currentItem}} refreshTree = {this.componentDidMount} parent = {this}/>}
                </div>
            </div>

        )
    }

    selectItem(e) {
        // if(e.itemData.type ==='moisture' || e.itemData.type ==='valve') return;
        e.itemData.fieldId = this.fieldId;
        this.setState({currentItem: ""});
        this.setState({
          currentItem: Object.assign({}, e.itemData)
        });
    }

}