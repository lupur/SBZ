import { BehaviorSubject } from 'rxjs';
import axios from 'axios';

const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));

export const authService = {
    login,
    register,
    logout,
    currentUser: currentUserSubject.asObservable(),
    get currentUserValue () { return currentUserSubject.value }
};

function login(username, password) {
    return axios.post("http://localhost:8080/users/login", 
        JSON.stringify({ username, password }),
            {
                headers: { 'Content-Type': 'application/json' }
            }
        ).then( response => {
            localStorage.setItem('currentUser', JSON.stringify(response.data));
            currentUserSubject.next(response.data);
            return response.data;
        }).catch(error => 
        {
            logout();
            window.location.reload(true);
            alert(error)
            return Promise.reject(error);
        })
}

function register(username, password, passwordConfirm) {
    return axios.post("http://localhost:8080/users/registration", 
        JSON.stringify({ username, password, passwordConfirm }),
            {
                headers: { 'Content-Type': 'application/json' }
            }
        ).then( response => {
            localStorage.setItem('currentUser', JSON.stringify(response.data));
            currentUserSubject.next(response.data);
            return response.data;
        }).catch(error => 
        {
            logout();
            window.location.reload(true);
            alert(error)
            return Promise.reject(error);
        })
}
function logout() {
    localStorage.removeItem('currentUser');
    currentUserSubject.next(null);
}

