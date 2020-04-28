import React, {Component} from 'react';
import { Redirect } from "react-router-dom"

export default class Users extends Component {
    render() {
        if (!localStorage.getItem('token')) {
            return <Redirect to={'/login'} />;
        }
        return (<div className={"border border-dark bg-dark text-white"}>Users</div>);
    }
}