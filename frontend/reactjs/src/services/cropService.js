import axios from "axios"
import {authHeader} from "../helpers/authHeader"

export const cropService = {
    getAll,
    getById,
    add,
    remove,
    addPhase,
    removePhase
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

function getById(id) {
    return axios.get("http://localhost:8080/crops/"+id,
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
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

function addPhase(phase) {
    return axios.post("http://localhost:8080/crops/phases",
    {
        name: phase.name,
        cropId: phase.cropId,
        phaseStartDay: phase.startDay,
        phaseEndDay: phase.endDay,
        moistureUpperThreshold: phase.max,
        moistureLowerThreshold: phase.min
    },
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}

function removePhase(id) {
    return axios.delete("http://localhost:8080/crops/phases/"+id,
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}