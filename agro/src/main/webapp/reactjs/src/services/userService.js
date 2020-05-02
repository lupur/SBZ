import axios from "axios"
import {authHeader} from "../helpers/authHeader"

export const userService = {
    getAll,
    getById
}

function getAll() {
    return axios.get("http://localhost:8080/users",
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error =>
        {
            return Promise.reject(error);
        });
}

function getById(id) {

}