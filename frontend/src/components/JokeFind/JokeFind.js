import './jokeFindStyle.css';
import {useState} from "react";
import {getJokesFromDbByKeyword} from "../../api/JokeService";
import {Link} from "react-router-dom";

const JokeFind = () => {
    let [jokes, setJokes] = useState([]);

    function getJokesByKeyword(e) {
        let keyword = e.currentTarget.value;
        if (keyword.length > 1 || keyword) {
            getJokesFromDbByKeyword(keyword)
                .then(res => res.json())
                .then(data => {

                    setJokes(data)
                });
        } else {
            setJokes([])
        }
    }

    return (
        <>
            <h1>Find jokes and fun stories</h1>
            <div className={'search-keyword-wrap'}>
                <div className={'div-search-keyword-wrap'}>
                    <label>Find by keyword </label>
                    <input className={'input-keyword-search'} name='keyword' onKeyUp={getJokesByKeyword}/>
                </div>
                {
                    jokes.length >= 1 ?
                        <div>
                            <p className={'p-fetch-usernames'}>Jokes (title) from database</p>
                            <ul className={'ul-usernames'}>{jokes.map(x =>
                                < li key={x.id} className={'li-usernames'}>
                                    <Link className={'joke-read-link'} to={`/joke-read/${x.id}`}>{x.title}</Link>
                                </li>)}</ul>
                        </div>
                        : ''
                }
            </div>
        </>
    );
}

export default JokeFind;