import axios from "axios"
import {authHeader} from "../helpers/authHeader"

export const fieldItemService = {
    getFieldItems,
    addNewArray,
    editArray,
    deleteArray,
    addNewSet,
    editSet,
    deleteSet
    // add,
    // remove
}

function getFieldItems(id) {
    return axios.get("http://localhost:8080/fields/" + id + "/items",
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    });
}

function addNewArray(fieldId, pumpEUI, rainEUI) {
    return axios.post("http://localhost:8080/fields/" + fieldId + "/arrays?pumpEUI=" + pumpEUI + "&rainEUI=" + rainEUI, 
    {},
    {
       headers: authHeader() 
    }).then(response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    })
}

function editArray(fieldId, arrayId, pumpEUI, rainEUI) {
    return axios.put("http://localhost:8080/fields/" + fieldId + "/arrays/" + arrayId + "?pumpEUI=" + pumpEUI + "&rainEUI=" + rainEUI, 
    {},
    {
       headers: authHeader() 
    }).then(response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    })
}

function deleteArray(fieldId, arrayId) {
    return axios.delete("http://localhost:8080/fields/" + fieldId + "/arrays/" + arrayId, 
    {
       headers: authHeader() 
    }).then(response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    })
}

function addNewSet(fieldId, arrayId, moistureEUI, valveEUI) {
    return axios.post("http://localhost:8080/fields/" + fieldId + "/arrays/" + arrayId + "?moistureEUI=" + moistureEUI + "&valveEUI=" + valveEUI, 
    {},
    {
       headers: authHeader() 
    }).then(response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    })
}

function editSet(fieldId, arrayId, position, moistureEUI, valveEUI) {
    return axios.put("http://localhost:8080/fields/" + fieldId + "/arrays/" + arrayId + "/sets/" + position + "?moistureEUI=" + moistureEUI + "&valveEUI=" + valveEUI, 
    {},
    {
       headers: authHeader() 
    }).then(response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    })
}

function deleteSet(fieldId, arrayId, position) {
    return axios.delete("http://localhost:8080/fields/" + fieldId + "/arrays/" + arrayId + "/sets/" + position,
    {
       headers: authHeader() 
    }).then(response => {
        return response.data;
    }).catch(error => {
        return Promise.reject(error);
    })
}