import './homeCardStyle.css'

const HomeCardRead = () => {

    return (
        <section className={'container'}>
            <div className="card">
                <div className={'box'}>
                    <div className={'content'}>
                        <i className="fas fa-book orange big"></i>
                        <h4 className={'title orange'}>Read</h4>
                        <p className={'orange bold'}>Read jokes or other fun stuff</p>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default HomeCardRead;

