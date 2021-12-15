import {Link} from "react-router-dom";
import './commentedJokeStyle.css'

const CommentedJoke = ({joke}) => {

    return (
        <div className={'commented-wrap'}>
            <p><span className={'commented-title'}>Title:</span> {joke.title}</p>
            <Link className={'commented-link'} to={`/joke-read/${joke.id}`}>Read <i className="fab fa-readme"></i></Link>
        </div>
    )
}
export default CommentedJoke;