import {Link} from "react-router-dom";
import './jokeCardStyle.css'

const JokeCard = ({joke}) => {
    let date = joke.createdDate[0] + "-" + joke.createdDate[1] + "-" + joke.createdDate[2];
    return (
        <section className={'main-section'}>
            <div className={'content'}>
                <p className={'title'}><i className="fas fa-text-height"></i> <span
                    className={'property'}>Title:</span> {joke.title} </p>
                <p><i className="fas fa-align-right"></i> <span className={'property'}>Content:</span> {joke.content}
                </p>
                <p><i className="fas fa-user"></i> <span className={'property'}>Created by:</span> {joke.creator}</p>
                <p><i className="fas fa-key"></i> <span className={'property'}>Keyword:</span> {joke.keyword}</p>
                <p><i className="fas fa-calendar-alt"></i> <span className={'property'}>Date added:</span> {date}</p>
            </div>
            <Link className={'link'} to={`/joke/${joke.id}`}>Edit joke</Link>

        </section>
    );
}

export default JokeCard;