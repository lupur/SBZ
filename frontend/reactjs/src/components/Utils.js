import React from 'react';
import {Redirect} from 'react-router-dom';

export function logout() {
    localStorage.removeItem('token')
    console.log("Logout");
    return <Redirect  to="/login" />
}

export function isLoggedIn() {
    if(localStorage.getItem('token') == null)
    {
        return false;
    }
    return true;
}