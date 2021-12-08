import {useEffect, useState} from "react";
import {getAllJokes} from "../../api/service/JokeService";
import './allJokesStyle.css'
import JokeBaseCard from "./JokeBaseCard/JokeBaseCard";

const AllJokes = () => {

    let [jokes, setJokes] = useState([]);

    useEffect(() => {
        getAllJokes()
            .then(res => res.json())
            .then(data => {
                setJokes(data);
            });
    }, []);
    return (
        <>
            <h1>All jokes</h1>
            <section className={'wrap-all'}>
                {jokes ? jokes.map((joke, id) =>
                        <JokeBaseCard key={joke.id} joke={joke} id={++id}/>)
                    : <p>No jokes in database</p>}
            </section>
        </>
    );
}

export default AllJokes;