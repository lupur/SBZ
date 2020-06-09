import axios from "axios"
import {authHeader} from "../helpers/authHeader"

export const readingService = {
    getReadings
}

function getReadings(deviceEUI, readingType) {
    return axios.get("http://localhost:8080/reading/" + deviceEUI + "/" + readingType,
    {
        headers: authHeader()
    }).then( response => {
        return response.data;
    }).catch(error =>
        {
            return Promise.reject(error);
        });
}