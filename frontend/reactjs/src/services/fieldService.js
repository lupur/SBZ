import axios from "axios"
import {authHeader} from "../helpers/authHeader"

export const fieldService = {
    getAll,
    add,
    remove
}

function getAll() {
    return axios.get("http://localhost:8080/fields",
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}

function add(newCrop) {
    console.log(newCrop)
    return axios.post("http://localhost:8080/fields",
    { 
        name : newCrop.name,
        area: newCrop.area,
        ownerId: newCrop.ownerId,
        cropId: newCrop.cropId,
        seedingDate: newCrop.seedingDate
    },
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}

function remove(id) {
    return axios.delete("http://localhost:8080/fields/"+id,
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}