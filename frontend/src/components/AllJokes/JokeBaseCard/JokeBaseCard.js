import './jokeBaseCardStyle.css'
import {Link} from "react-router-dom";
const JokeBaseCard = ({joke,id}) => {

    return (
        <>
            <section className={'joke-card'}>
                <p className={'joke-card-data'} >No: {id}</p>
                <p className={'joke-card-data'} >Title: {joke.title}</p>
                <p className={'joke-card-data'} >Keyword: {joke.keyword}</p>
                <p className={'joke-card-data'}  >Total likes: {joke.likes ? joke.likes.length : 0}</p>
                <p className={'joke-card-data'}  >Total comments: {joke.comments ? joke.comments.length : 0}</p>
                <Link className={'joke-card-link'} to={`/joke-read/${joke.id}`} >View joke</Link>
            </section>
        </>
    );
}

export default JokeBaseCard;