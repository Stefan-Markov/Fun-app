import {getUserByUsername} from "../../api/service/UserService";
import {useEffect, useState} from "react";
import './accountStyle.css'
import {Link, useNavigate} from "react-router-dom";
import CommentedJoke from "./CommentedJoke/CommentedJoke";
import {
    deleteFavJokeByUsernameAndId,
    getCommentedJokeByUsername,
    getFavouritesJokeByUsername
} from "../../api/service/JokeService";
import JokeFav from "./JokeFav";

const Account = () => {
    let navigate = useNavigate();
    let username = sessionStorage.getItem('authenticatedUser');
    let [user, setUser] = useState([]);
    let [date, setDate] = useState([]);
    let [roles, setRoles] = useState([]);
    let [favouritesJoke, setFavouritesJoke] = useState([]);
    let [names, setNames] = useState({username: '', user: ''})

    let [commentedJokes, setCommentedJokes] = useState([]);

    useEffect(() => {
        getCommentedJokeByUsername(username)
            .then(res => res.json())
            .then(data => {

                setCommentedJokes(data)
            }).catch(err => err);
    }, [username])
    useEffect(() => {
        getUserByUsername(username)
            .then(res => res.json())
            .then(data => {
                setUser(data)
                setNames({username: username, user: data.username})

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

    useEffect(() => {
        let owner = names.username === names.user;
        if (!owner) {
            navigate('/');
        }
    }, [names, navigate])

    return (
        <div className={'wrap-main'}>
            <h1>{usernameData}'s account</h1>
            <div className={'profile'}>
                <p className={'futura'}><i className="fas fa-user"></i><span
                    className={'account-data'}> Username:</span> {usernameData}</p>
                <p className={'futura'}><i className="fas fa-envelope-open"></i><span
                    className={'account-data'}> Email:</span> {email}</p>
                <p className={'futura'}><i className="far fa-address-book"></i><span className={'account-data'}> User ID:</span> {user.id}
                </p>
                {date === 'Invalid date' ? '' :
                    <p className={'futura'}><i className="far fa-calendar-alt"></i><span className={'account-data'}> Date joined:</span> {date}
                    </p>}
                <p className={'futura'}><i className="fas fa-user-shield"></i><span
                    className={'account-data'}>Roles:</span> {roles}</p>
            </div>
            <div className={'div-buttons'}>
                <Link className={'link pad'} to={'/joke-add'}>Create joke here <i
                    className="fas fa-greater-than"></i></Link>
                <Link className={'link pad'} to={'/joke-find'}>Find jokes here <i
                    className="fab fa-searchengin"></i></Link>
            </div>
            <div className={'wrap-commented-jokes'}>
                <p className={'commented-jokes-head'}>Commented jokes by you </p>
                <section className={'commented-jokes'}>
                    {commentedJokes.length ?
                        commentedJokes.map((x, id) => <CommentedJoke key={++id} joke={x}/>)
                        :
                        <p>No commented jokes so far</p>
                    }
                </section>
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