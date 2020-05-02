import {authService} from "../services/authService";

export function authHeader() {
    const currentUser = authService.currentUserValue;
    if(currentUser && currentUser.token) {
        return { 'Content-Type': 'application/json',
                'Token': currentUser.token };
    } else {
        return {};
    }
}