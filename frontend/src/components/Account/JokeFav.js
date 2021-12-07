import '../Joke/ManageJoke/JokeCard/jokeCardStyle.css'

const JokeFav = ({joke}) => {
    return (
        <section className={'main-section'}>
            <div className={'content'}>
                <p className={'title'}><i className="fas fa-text-height"></i> <span
                    className={'property'}>Title:</span> {joke.title} </p>
                <p><i className="fas fa-align-right"></i> <span className={'property'}>Content:</span> {joke.content}
                </p>
                <p><i className="fas fa-user"></i> <span className={'property'}>Created by:</span> {joke.creator}</p>
                <p><i className="fas fa-key"></i> <span className={'property'}>Keyword:</span> {joke.keyword}</p>
            </div>
        </section>
    );
}

export default JokeFav;