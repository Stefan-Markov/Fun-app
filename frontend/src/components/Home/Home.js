import './homeStyle.css'
import HomeCardCreate from "./HomeCard/HomeCardCreate";
import HomeCardRead from "./HomeCard/HomeCardRead";
import HomeCardShare from "./HomeCard/HomeCardShare";
import LastTheeJokes from './LastTheeJokes/LastTheeJokes';
import {useEffect, useState} from "react";
import {getLastTheeJokes} from "../../api/JokeService";
import {Link} from "react-router-dom";
import * as AuthenticationService from "../../api/AuthenticationService";

const Home = () => {
    let isLogin = AuthenticationService.isUserLoggedIn();
    let [lastTheeJokes, setLastTheeJokes] = useState([]);

    useEffect(() => {
        getLastTheeJokes()
            .then(res => res.json())
            .then(data => {

                setLastTheeJokes(data)
            }).catch(err => err);
    }, [])
    return (
        <div>
            <h1>Fun App</h1>
            {isLogin ? <p className={'welcome'}>Welcome, {AuthenticationService.getLoggedInUserName()}!</p> : ''}
            <section className={'main'}>
                <HomeCardCreate/>
                <HomeCardRead/>
                <HomeCardShare/>
            </section>
            <section className={'three'}>
                <h2 className={'h2-three'}><i className="fas fa-long-arrow-alt-down"></i> Here can read last three added
                    jokes <i className="fas fa-long-arrow-alt-down"></i></h2>
                {!isLogin ? <h2 className={'h2-three'}>For more content <Link className={'home-login'}
                                                                              to={'/login'}>login.</Link>
                    </h2>
                    : ''}

                {lastTheeJokes.map(x => <LastTheeJokes key={x.id} joke={x}/>)}
            </section>
        </div>
    );
}

export default Home;