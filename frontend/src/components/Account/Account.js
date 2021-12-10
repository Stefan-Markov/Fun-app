import {getUserByUsername} from "../../api/service/UserService";
import {useEffect, useState} from "react";
import './accountStyle.css'
import {Link} from "react-router-dom";
import {deleteFavJokeByUsernameAndId, getFavouritesJokeByUsername} from "../../api/service/JokeService";
import JokeFav from "./JokeFav";

const Account = () => {

    let username = sessionStorage.getItem('authenticatedUser');
    let [user, setUser] = useState([]);
    let [date, setDate] = useState([]);
    let [roles, setRoles] = useState([]);
    let [favouritesJoke, setFavouritesJoke] = useState([]);
    useEffect(() => {
        getUserByUsername(username)
            .then(res => res.json())
            .then(data => {
                setUser(data)
                setRoles(data.authorities.map(x => x.authority + " "));
                let date = data.createdDate[0] + "-" + data.createdDate[1] + "-" + data.createdDate[2];

                setDate(date)
            })
            .catch(err => err);
    }, [username])


    useEffect(() => {
        getFavouritesJokeByUsername(username)
            .then(res => res.json())
            .then(data => {
                setFavouritesJoke(data);
            }).catch(err => err)
    }, [username]);

    function deleteFavJoke({username, id}) {

        deleteFavJokeByUsernameAndId(username, id)
            .then(res => res.json())
            .then(data => {
                let filteredFavJokes = favouritesJoke.filter(x => x.id !== data.jokeId);
                setFavouritesJoke(filteredFavJokes);
            }).catch(err => console.log(err))
    }

    roles = roles.map(x => x.toLowerCase().replace('role_', ' ')).join(', ');

    let usernameData = user.username;
    let email = user.email;

    return (
        <div className={'wrap-main'}>
            <h1>{usernameData}'s account</h1>
            <div className={'profile'}>
                <p className={'futura'}><i className="fas fa-user"></i> Username: {usernameData}</p>
                <p className={'futura'}>    <i className="fas fa-envelope-open"></i> Email: {email}</p>
                <p className={'futura'}><i className="far fa-address-book"></i> User ID: {user.id}</p>
                {date === 'Invalid date' ? '' :
                    <p className={'futura'}><i className="far fa-calendar-alt"></i> Date joined: {date}</p>}
                <p className={'futura'}><i className="fas fa-user-shield"></i>Roles: {roles}</p>
            </div>
            <div className={'div-buttons'}>
                <Link className={'link pad'} to={'/joke-add'}>Create joke here</Link>
                <Link className={'link pad'} to={'/joke-find'}>Find jokes here</Link>
            </div>
            <div className={'wrap-cards fav'}>
                <p className={'title-fav'}>Favourites jokes</p>

                {favouritesJoke.length ? favouritesJoke.map(x => <JokeFav key={x.id} joke={x} username={username}
                                                                          deleteFavJoke={deleteFavJoke}/>)
                    : <div className={'fav-no'}>No favourites joke for now</div>}
            </div>
        </div>
    );
}

export default Account;