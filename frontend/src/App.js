import React, {useEffect, useState} from 'react';
import NavBar from "./components/NavBar/NavBar";
import Home from "./components/Home/Home";
import Footer from "./components/Footer/Footer";
import {Routes, Route} from 'react-router-dom';
import Login from "./components/Login/Login";
import Register from "./components/Register/Register";
import Logout from "./components/Logout";
import * as AuthenticationService from "./api/AuthenticationService";
import JokeAdd from "./components/Joke/JokeAdd/JokeAdd";
import ManageJoke from "./components/Joke/ManageJoke/ManageJoke";
import JokeById from "./components/Joke/JokeById/JokeById";
import Account from "./components/Account/Account";
import AdminPanel from "./components/AdminPanel/AdminPanel";
import JokeFind from "./components/JokeFind/JokeFind";
import Joke from "./components/JokeFind/Joke/Joke";

function App() {

    const [userInfo, setUserInfo] = useState({isAuthenticated: false, username: ''});


    useEffect(() => {
        let user = AuthenticationService.getLoggedInUserName();
        setUserInfo({
            isAuthenticated: Boolean(user),
            user,
        })
    }, []);

    const onLogin = (username) => {
        setUserInfo({
            isAuthenticated: true,
            user: username,
        })

    }

    const onLogout = () => {
        setUserInfo({
            isAuthenticated: false,
            user: null,
        })
    };


        return (
            <div>
                <NavBar {...userInfo}/>
                <Routes>
                    <Route path={'/'} element={<Home/>}/>
                    <Route path={'/login'} element={<Login onLogin={onLogin}/>}/>
                    <Route path={'/register'} element={<Register onLogin={onLogin}/>}/>
                    <Route path={'/logout'} element={<Logout onLogout={onLogout}/>}/>
                    <Route path={'/joke-add'} element={<JokeAdd/>}/>
                    <Route path={'/joke/:id'} element={<JokeById/>}/>
                    <Route path={`/joke-manage`} element={<ManageJoke/>}/>
                    <Route path={`/account`} element={<Account/>}/>
                    <Route path={`/admin`} element={<AdminPanel/>}/>
                    <Route path={`/joke-find`} element={<JokeFind/>}/>
                    <Route path={`/joke-read/:id`} element={<Joke/>}/>
                </Routes>
                <Footer/>
            </div>
        );

}

export default App;