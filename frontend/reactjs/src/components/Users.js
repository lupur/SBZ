import React, {Component} from 'react';
import {userService} from '../services/userService'
import {Card, Table, Dropdown} from 'react-bootstrap'

export default class Users extends Component {
    constructor(props) {
        super(props);

        this.state = {
            users: null
        };

        this.changeRole = this.changeRole.bind(this);
    }

    componentDidMount() {
        userService.getAll().then(response =>{
            this.setState({users : response })
        });
    }

    changeRole(event)
    {
        userService.changeRole(event.target.id, event.target.value)
            .then( response => 
            {
                this.componentDidMount()
            })
    }

    render() {
        return (
            <Card className={"border border-dark bg-dark text-white"}>
                <Card.Header><h3>Users</h3></Card.Header>
                <Card.Body>
                    <Table bordered className={"border border-dark bg-dark text-white"}>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Role</th>
                            </tr>
                        </thead>
                        <tbody>
                        { this.state.users && this.state.users.map((user, i) =>
                            <tr key={i}>
                                <td>{user.id}</td>
                                <td>{user.username}</td>
                                <td>
                                <select 
                                    id={user.id}
                                    value={user.role}
                                    onChange={this.changeRole} 
                                    // {user.id == this.}
                                    className={"border border-dark bg-dark text-white"}
                                >
                                    <option value="ADMIN">Admin</option>
                                    <option value="USER">User</option>
                                </select>
                                    
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>
            
            );
    }
}