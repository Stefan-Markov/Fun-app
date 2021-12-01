import * as AuthenticationService from "../api/AuthenticationService";
import {Navigate} from "react-router-dom";

const Logout = ({onLogout}) => {

    try {
        AuthenticationService.logout();
        onLogout();
        return <Navigate to="/login" replace={true} onLogout={onLogout}/>;
    } catch (err) {

    }
}

export default Logout;