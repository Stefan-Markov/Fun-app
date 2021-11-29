import './formStyle.css'
import {Link, useNavigate} from "react-router-dom";
import React, {useState} from 'react'

import AuthenticationService from "../../api/AuthenticationService";

const Login = ({onLogin}) => {

    let navigate = useNavigate();
    let [userInfo, setUserInfo] = useState({hasLoginFailed: false,});
    const onSubmitLogin = (e) => {
        e.preventDefault();
        let form = new FormData(e.target);
        let username = form.get('username');
        let password = form.get('password');
        AuthenticationService
            .executeJwtAuthenticationService(username, password)
            .then((response) => {
                AuthenticationService.registerSuccessfulLoginForJwt(username, response.data.token);

                onLogin();
                navigate('/');
            }).catch((err) => {
            e.target.password.value = '';
            setUserInfo({hasLoginFailed: true})
        })
    }


    return (
        <div className="login-box">
            <h2>Login</h2>
            {userInfo.hasLoginFailed && <div className="warning">Invalid Credentials</div>}
            <form onSubmit={onSubmitLogin}>
                <div className="user-box">
                    <input type="text" name="username" defaultValue=''
                           required={true}/>
                    <label>Username</label>
                </div>
                <div className="user-box">
                    <input type="password" name="password" defaultValue=''
                           required={true}/>
                    <label>Password</label>
                </div>
                <button type="submit">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    Submit
                </button>
                <Link to={'/register'}>Don't have account? Register here</Link>
            </form>
        </div>
    );
}

export default Login;