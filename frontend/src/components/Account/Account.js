import {addPictureToUser, getUserByUsername} from "../../api/UserService";
import {useEffect, useState} from "react";
import JokeCard from "../Joke/ManageJoke/JokeCard/JokeCard";
import moment from "moment";
import './accountStyle.css'
import {Link} from "react-router-dom";

const Account = () => {

    let username = sessionStorage.getItem('authenticatedUser');
    let [user, setUser] = useState([]);
    let [date, setDate] = useState([]);
    let [roles, setRoles] = useState([]);
    useEffect(() => {
        getUserByUsername(username)
            .then(res => res.json())
            .then(data => {
                setUser(data)
                setRoles(data.authorities.map(x => x.authority + " "));
                setDate(moment(user.createdDate).format('YYYY-MM-DD'))
            })
            .catch(err => err);
    }, [username])

    roles = roles.map(x => x.toLowerCase().replace('role_', ' ')).join(', ');

    let usernameData = user.username;
    let email = user.email;

    return (
        <div className={'wrap-main'}>
            <h1>{usernameData}'s account</h1>
            <div className={'profile'}>
                <p><i className="fas fa-user"></i> Username: {usernameData}</p>
                <p><i className="fas fa-envelope-open"></i> Email: {email}</p>
                <p>User ID: {user.id}</p>
                {date === 'Invalid date' ? '' : <p> Date joined: {date}</p>}
                <p>Roles: {roles}</p>
            </div>

            <div className={'wrap-cards'}>

                    <Link className={'link'} to={'/joke-add'}>Create joke here</Link>

                {user.joke  ? user.joke.map(x => <JokeCard key={x.id} joke={x}/>) :
                 ''
                }
            </div>
        </div>
    );
}

export default Account;