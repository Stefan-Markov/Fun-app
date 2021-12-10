import './jokeFindStyle.css';
import {useEffect, useState} from "react";
import {getJokeByMostLikes, getJokesFromDbByKeyword, getLastTheeJokes} from "../../api/service/JokeService";
import {Link} from "react-router-dom";
import LastTheeJokes from "../Home/LastTheeJokes/LastTheeJokes";
import ResetScroll from "../../api/ResetScroll/ResetScroll";

const JokeFind = () => {
    ResetScroll();
    let [jokes, setJokes] = useState([]);
    let [lastTheeJokes, setLastTheeJokes] = useState([]);
    let [mostLiked, setMostLiked] = useState([]);

    function getJokesByKeyword(e) {
        let keyword = e.currentTarget.value;
        if (keyword.length > 1 || keyword) {
            getJokesFromDbByKeyword(keyword)

                .then(res => res.json())
                .then(data => {
                    setJokes(data)
                }).catch(err => err);
        } else {
            setJokes([])
        }
    }

    useEffect(() => {
        getJokeByMostLikes()
            .then(res => res.json())
            .then(data => {
                setMostLiked(data);
            })
            .then(err => err);
    }, [])

    useEffect(() => {
        getLastTheeJokes()
            .then(res => res.json())
            .then(data => {
                setLastTheeJokes(data)
            }).catch(err => err);
    }, [])


    return (
        <>
            <h1>Find jokes and fun stories</h1>
            {mostLiked ?
                <div className={'most-liked'}>
                    <p>Check the most liked joke:</p>
                    <ul className={'ul-usernames'}>{mostLiked.map(x =>
                        <li key={x.id} className={'li-usernames best-joke'}>
                            <Link className={'joke-read-link'} to={`/joke-read/${x.id}`}>{x.title}</Link>
                        </li>)}</ul>
                </div>
                : ''}
            <section className={'all-jokes'}>
                <Link className={'link-all'} to={'/find-all'}> <i className="fas fa-list"></i> Here can view all
                    jokes</Link>
            </section>
            <div className={'search-keyword-wrap'}>
                <div className={'div-search-keyword-wrap'}>
                    <label className={'label'}>Find by keyword</label>
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
            <div className={'lastThree'}>
                <h2 className={'h2-three'}><i className="fas fa-long-arrow-alt-down"></i> Here can read last three added
                    jokes <i className="fas fa-long-arrow-alt-down"></i></h2>
                {lastTheeJokes.map(x => <LastTheeJokes key={x.id} joke={x}/>)}
            </div>
        </>
    );
}

export default JokeFind;