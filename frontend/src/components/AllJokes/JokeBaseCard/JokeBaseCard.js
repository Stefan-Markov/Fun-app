import './jokeBaseCardStyle.css'
import {Link} from "react-router-dom";
import {useState} from "react";

const JokeBaseCard = ({joke, id}) => {

    let [comments, setComments] = useState({
        commentsData: joke.comments,
        display: false
    });

    const changeR = (e) => e.target.style.color = 'red';
    const changeL = (e) => e.target.style.color = 'lightblue';

    const hide = () => setComments({display: false});
    const show = () => setComments({display: true});

    return (
        <>
            <section className={'joke-card'}>
                <div onMouseOver={changeR} onMouseOut={changeL}>
                    <p className={'joke-card-data'}>No: {id}</p>
                    <p className={'joke-card-data'}>Title: {joke.title}</p>
                    <p className={'joke-card-data'}>Keyword: {joke.keyword}</p>
                    <p className={'joke-card-data'}>Total likes: {joke.likes ? joke.likes.length : 0}</p>
                    <p onMouseEnter={show}
                       onMouseLeave={hide}
                       className={'joke-card-data show-comments'}
                    >Total comments: {joke.comments ? joke.comments.length : 0} </p>
                </div>
                {comments.display ?
                    joke.comments.map((x, id) =>
                        <p key={id} className={'comment'}>{++id}. {x.content}</p>) : ''}

                <Link className={'joke-card-link'} to={`/joke-read/${joke.id}`}>View joke</Link>
            </section>
        </>
    );
}

export default JokeBaseCard;