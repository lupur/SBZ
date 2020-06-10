import axios from 'axios';
import {authHeader} from "../helpers/authHeader"

export const typeService = {
    getType,
    addType,
    removeType
}

function getType(device) {
    return axios.get("http://localhost:8080/template/" + device,
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}

function removeType(device) {
    return axios.delete("http://localhost:8080/template/remove/"+device,
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}

function addType(device) {
    return axios.put("http://localhost:8080/template/add/" + device,
    {},
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}