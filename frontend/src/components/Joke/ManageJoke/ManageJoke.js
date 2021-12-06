import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import JokeCard from "./JokeCard/JokeCard";
import {getJokeByUsername} from "../../../api/service/JokeService";
import './manageJokeStyle.css'

const ManageJoke = () => {

    let [jokes, setJokes] = useState([]);

    useEffect(() => {
        getJokeByUsername()
            .then(res => res.json())
            .then(data => {
                setJokes(data)

            })
            .catch(err => err)
    }, []);

    return (
        <>
            <h1>Manage jokes</h1>
            <div className={'wrap-manage'}>
                {jokes.length > 0 ? jokes.map(joke => <JokeCard key={joke.id} joke={joke}/>) :
                    <div className={'no-content'}>
                        <p>No created jokes for now.</p>
                        <Link className={'link'} to={'/joke-add'}>Create joke here</Link>
                    </div>
                }
            </div>
        </>
    );
}

export default ManageJoke;