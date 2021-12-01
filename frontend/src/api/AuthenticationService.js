import axios from 'axios'
import {API_URL} from './Constants'
import jwt from 'jwt-decode'

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';
export const USER_NAME_SESSION_ROLES = 'roles';
export const USER_TOKEN = 'token';

// class AuthenticationService {
export function authJwtService(username, password) {
    return axios.post(`${API_URL}/authenticate`, {
        username,
        password
    })
}

export function login(username, token) {
    let decoded = jwt(token);
    let roles = decoded['roles'];
    roles = roles.map(x => x).join(' ');

    sessionStorage.setItem(USER_NAME_SESSION_ROLES, roles.toString());
    sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username);
    sessionStorage.setItem(USER_TOKEN, token);
    // this.setupAxiosInterceptors(this.createJWTToken(token))
}

export function createJWTToken(token) {
    return 'Bearer ' + token
}


export function logout() {
    sessionStorage.removeItem(USER_NAME_SESSION_ROLES);
    sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem(USER_TOKEN);
}

export function isUserLoggedIn() {
    let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return false
    return true
}

export function getLoggedInUserName() {
    let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
}

// setupAxiosInterceptors(token) {
//
//     axios.interceptors.request.use(
//         (config) => {
//             if (this.isUserLoggedIn()) {
//                 config.headers.authorization = token
//             }
//             return config
//         }
//     )
// }
// }

// export default new AuthenticationService()