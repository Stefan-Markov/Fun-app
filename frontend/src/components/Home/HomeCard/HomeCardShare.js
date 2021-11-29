import './homeCardStyle.css'

const HomeCardShare = () => {
    return (
        <section className={'container'}>
            <div className="card">
                <div className={'box'}>
                    <div className={'content'}>
                        <i className="fas fa-share-alt orange big"></i>
                        <h4 className={'title orange'}>Share</h4>
                        <p className={'orange bold'}>Share jokes with colleagues or friends</p>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default HomeCardShare;