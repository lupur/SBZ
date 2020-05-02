import axios from "axios"
import {authHeader} from "../helpers/authHeader"

export const cropService = {
    getAll,
    getById,
    add,
    remove
}

function getAll() {
    return axios.get("http://localhost:8080/crops",
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}

function getById() {

}

function add(cropName) {
    return axios.post("http://localhost:8080/crops",
    { name : cropName},
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}

function remove(id) {
    return axios.delete("http://localhost:8080/crops/"+id,
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}